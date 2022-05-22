package hecate.client;


import io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

/**
 * 使用 okhttp 调用下游服务
 */
@Slf4j
public class OkClient extends HttpOutboundHandler {
    private final OkHttpClient client;

    public OkClient(List<String> backends) {
        super(backends);
        client =  new OkHttpClient();
        log.info("创建 "+ this);
    }

    @Override
    protected void fetchGet(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {
        byte[] body;
        try {
            Request.Builder requestBuilder = new Request.Builder().url(url);
            inbound.headers().forEach(entry -> requestBuilder.addHeader(
                    entry.getKey(), entry.getValue()));
            body = get(requestBuilder.build());
            log.info("获取到下游服务响应");
        } catch (IOException e) {
            log.error("调用下游服务异常",e);
            body = "服务调用失败".getBytes();
        }
        handleResponse(inbound,ctx,body);
    }

    public byte[] get(Request request) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().bytes();
        }
    }
}
