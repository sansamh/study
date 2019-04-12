package netty;

import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Attack {

    public static final String URL = "http://www.baokuwu.com";
    static OkHttpClient okHttpClient = new OkHttpClient();
    static final Request request = new Request.Builder()
            .url(URL)
            .get()
            .build();

    public static void main(String[] args) {
//        for (int i = 0 ; i < 1000000; i++) {
//            Attack.start();
//        }
        int i = 010;
//        System.out.println(i);

        Map<String, Map<String,String>> parmMap = new HashMap<>(0);
        Iterator<Map.Entry<String, Map<String, String>>> iterator = parmMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Map<String, String>> next = iterator.next();
            String key = next.getKey();
            Map<String, String> value = next.getValue();
        }

    }

    public static void start() {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("onFailure" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("onResponse" + response.body().contentLength());
            }
        });
    }
}
