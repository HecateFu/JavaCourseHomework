package hecate.client;

import io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 * 使用 Apache HttpAsyncClient 调用下游服务
 */
@Slf4j
public class ApacheAsyncClient extends HttpOutboundHandler {
    private CloseableHttpAsyncClient httpclient;

    public ApacheAsyncClient(List<String> backends) {
        super(backends);

        // 创建 HttpAsyncClient
        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();

        httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response,context) -> 6000)
                .build();
        httpclient.start();
        log.info("创建 "+ this);
    }

    protected void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        // 封装请求
        final HttpGet httpGet = new HttpGet(url);
        //httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        httpGet.setHeader("mao", inbound.headers().get("mao"));
        // 调用下游服务
        httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse endpointResponse) {
                try {
                    // 接收下游服务响应
                    byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
                    // 调用上层响应输出方法，将下游服务响应返回客户端
                    handleResponse(inbound, ctx, body);
                } catch (Exception e) {
                    log.error("调用下游服务异常",e);
                }
            }

            @Override
            public void failed(final Exception ex) {
                httpGet.abort();
                log.error("调用下游服务异常",ex);
            }

            @Override
            public void cancelled() {
                httpGet.abort();
            }
        });
    }
}
