package client;

import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ApacheHttpClient {
    public static void main(String[] args) {
        useHttpClient();
    }

    public static void useHttpClient(){
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8802");
        try(CloseableHttpClient httpclient = HttpClients.createDefault();
            CloseableHttpResponse response = httpclient.execute(httpGet)) {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String resp = EntityUtils.toString(entity);
            System.out.println(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void useFluentAPI(){
        try {
            Content content = Request.Get("http://127.0.0.1:8801").execute().returnContent();
            String result = content.toString();
            System.out.printf(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
