package io.github.kimmking.gateway.outbound.httpclient4;


import io.github.kimmking.gateway.filter.HeaderHttpResponseFilter;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpResponseFilter;
import io.github.kimmking.gateway.router.HttpEndpointRouter;
import io.github.kimmking.gateway.router.RandomHttpEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 调用下游服务并将结果返回客户端
 */
@Slf4j
public abstract class HttpOutboundHandler {
    
    private final ExecutorService proxyService;
    private final List<String> backendUrls;

    protected int cores = Runtime.getRuntime().availableProcessors();

    HttpResponseFilter filter = new HeaderHttpResponseFilter();
    HttpEndpointRouter router = new RandomHttpEndpointRouter();

    public HttpOutboundHandler(List<String> backends) {

        this.backendUrls = backends.stream().map(this::formatUrl).collect(Collectors.toList());

        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//.DiscardPolicy();
        // 创建调用下游服务的线程池
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);
    }

    private String formatUrl(String backend) {
        return backend.endsWith("/")?backend.substring(0,backend.length()-1):backend;
    }
    
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
        String backendUrl = router.route(this.backendUrls);
        // 下游服务调用地址
        final String url = backendUrl + fullRequest.uri();
        filter.filter(fullRequest, ctx);
        // 获取线程，调用下游服务
        proxyService.submit(()->fetchGet(fullRequest, ctx, url));
    }

    /**
     * 下游服务调实现
     * @param inbound 请求对象
     * @param ctx channel上下文
     * @param url 下游服务地址
     */
    protected abstract void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx,
                                     final String url);

    /**
     * 将下游服务的响应内容返回客户端
     * @param fullRequest 请求对象
     * @param ctx channel上下文
     * @param body 下游服务响应内容
     */
    protected void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx,
                                byte[] body){
        FullHttpResponse response = null;
        try {
            // 封装客户端响应
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));

            response.headers().set(CONTENT_TYPE, "text/plain; charset=utf-8");
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

            // 调用响应过滤器
            filter.filter(response);
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            // 向客户端输出响应
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
                log.info("输出响应");
            }
            ctx.flush();
            //ctx.close();
        }
        
    }
    
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    
}
