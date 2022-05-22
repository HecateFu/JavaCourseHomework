package hecate.filter;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 入站过滤器
 */
@Slf4j
public class HttpInboundFilter extends ChannelInboundHandlerAdapter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("my-in-head",System.currentTimeMillis());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("my inbound filter read");
        if(msg instanceof FullHttpRequest){
            FullHttpRequest request = (FullHttpRequest) msg;
            // 设置自定义请求头
            filter(request,ctx);

            String uri = request.uri();
            if(uri.equals("/favicon.ico")) {
                // 拦截 favicon.ico
                FullHttpResponse response = new DefaultFullHttpResponse(
                        HTTP_1_1, NO_CONTENT, Unpooled.EMPTY_BUFFER);
                response.headers().set(CONTENT_TYPE, "text/plain; charset=utf-8");
                response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
                if (!HttpUtil.isKeepAlive(request)) {
                    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.writeAndFlush(response);
                }
            } else {
                // 其他请求继续
                super.channelRead(ctx, msg);
            }
        } else {
            log.info("msg: {}",msg);
        }
    }

}
