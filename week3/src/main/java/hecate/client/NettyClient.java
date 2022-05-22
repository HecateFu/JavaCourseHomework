package hecate.client;

import io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.List;

/**
 * 使用 netty 调用下游服务
 */
@Slf4j
public class NettyClient extends HttpOutboundHandler {
    public NettyClient(List<String> backends) {
        super(backends);
        log.info("创建 "+ this);
    }

    @Override
    protected void fetchGet(FullHttpRequest inbound, ChannelHandlerContext serverCtx, String url) {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            int port = uri.getPort();
            // 创建调用下游服务的 netty 客户端
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new HttpClientCodec());
                            p.addLast(new HttpObjectAggregator(1048576));
                            p.addLast(new SimpleChannelInboundHandler<HttpObject>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext clientCtx,
                                                            HttpObject msg) throws Exception {
                                    // 接收下游服务响应信息
                                    HttpContent content = (HttpContent) msg;
                                    ByteBuf buf = content.content();
                                    int readable = buf.readableBytes();
                                    int readerIndex = buf.readerIndex();
                                    byte[] body = new byte[readable];
                                    buf.getBytes(readerIndex, body);
                                    log.info("获取到下游服务响应");
                                    // 调用上层响应输出方法，将下游服务响应返回客户端
                                    handleResponse(inbound,serverCtx,body);
                                }
                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx,
                                                            Throwable cause) {
                                    log.error("调用下游服务异常",cause);
                                    ctx.close();
                                }
                            });
                        }
                    });

            // netty客户端和下游服务建立连接
            Channel ch = b.connect(host, port).sync().channel();

            // 封装请求对象
            HttpRequest request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath(), Unpooled.EMPTY_BUFFER);
            inbound.headers().forEach(entry->{
                request.headers().set(entry.getKey(),entry.getValue());
            });

            // 发送请求
            ch.writeAndFlush(request);

            // 等待下游服务关闭连接
            ch.closeFuture().sync();
        } catch (Exception e) {
            log.error("调用下游服务异常",e);
            byte[] body = "服务调用失败".getBytes();
            handleResponse(inbound,serverCtx,body);
        } finally {
            // 关闭 netty 客户端 的 EventLoopGroup
            group.shutdownGracefully();
        }
    }
}
