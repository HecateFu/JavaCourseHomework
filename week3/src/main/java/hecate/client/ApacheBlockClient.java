package hecate.client;

import io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * 使用 Apache HttpClient 调用服务
 */
@Slf4j
public class ApacheBlockClient extends HttpOutboundHandler {

    public ApacheBlockClient(List<String> backends) {
        super(backends);
        log.info("创建 "+ this);
    }

    @Override
    protected void fetchGet(FullHttpRequest inbound, ChannelHandlerContext ctx, String url) {
        byte[] body;
        try {
            HttpGet httpGet = new HttpGet(url);
            inbound.headers().forEach(entry -> httpGet.setHeader(entry.getKey(), entry.getValue()));
            // 接收下游服务响应
            body = useHttpClient(httpGet);
            log.info("获取到下游服务响应");
        } catch (IOException e) {
            log.error("调用下游服务异常",e);
            body = "服务调用失败".getBytes();
        }
        // 调用上层响应输出方法，将下游服务响应返回客户端
        handleResponse(inbound,ctx,body);
    }

    public byte[] useHttpClient(HttpGet httpGet) throws IOException {
        try(CloseableHttpClient httpclient= HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toByteArray(entity);
        }
    }

    public byte[] useFluentAPI(String url) throws IOException {
        Content content = Request.Get(url).execute().returnContent();
        return content.asBytes();
    }
}
