package client;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkHttp {
    private static OkHttpClient client = new OkHttpClient();
    public static void main(String[] args) {

        Request request = new Request.Builder()
                .addHeader("Connection","close")
                .url("http://127.0.0.1:8803")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String result = response.body().string();
            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
