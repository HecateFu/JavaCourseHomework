package hecate.filter;

import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * 出站过滤器
 */
@Slf4j
public class HttpOutboundFilter extends ChannelOutboundHandlerAdapter implements HttpResponseFilter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("my outbound filter write");
        if(msg instanceof FullHttpResponse){
            FullHttpResponse response = (FullHttpResponse)msg;
            // 添加自定义响应头
            filter(response);
        } else {
            log.info("outbound msg: {}",msg);
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("my-out-head", LocalDateTime.now());
    }
}
