package pw.pbdiary.maeari.blog;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class useAPIData {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    public String postRequest(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public String getRequest(String _url) throws IOException {
        Request request = new Request.Builder().url(_url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
