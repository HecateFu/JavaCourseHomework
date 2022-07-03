package io.kimmking.rpcfx.client;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.net.InetSocketAddress;

public class NettyClient {
    private final ClientHandler clientHandler = new ClientHandler();
    private final String host;
    private final int port;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RpcfxResponse send(RpcfxRequest rpcfxRequest) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                            ch.pipeline().addLast(new HttpResponseDecoder());
                            // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                            ch.pipeline().addLast(new HttpRequestEncoder());
                            ch.pipeline().addLast(new HttpObjectAggregator(1024*1024));
                            ch.pipeline().addLast(new HttpServerExpectContinueHandler());
                            ch.pipeline().addLast(clientHandler);
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect().sync();

            // 发送请求
            RpcfxResponse response = this.post(rpcfxRequest);
            f.channel().closeFuture().sync();

            return response;
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    private RpcfxResponse post(RpcfxRequest rpcfxRequest) throws InterruptedException {
        byte[] bytes = JSON.toJSONBytes(rpcfxRequest);
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, "/",
                Unpooled.wrappedBuffer(bytes));
        request.headers().add(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        request.headers().add(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON);
        request.headers().add(HttpHeaderNames.CONTENT_LENGTH,request.content().readableBytes());
        ChannelPromise channelPromise = clientHandler.flushMessage(request);
        channelPromise.await();
        return clientHandler.getRpcfxResponse();
    }

}
