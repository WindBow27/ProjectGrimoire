package org.graphic.dictionary;

import org.graphic.app.AppConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TranslatorAPI {
    private final AppConfig appConfig = new AppConfig();
    private final String GOOGLE_TRANSLATE_API = appConfig.getAPIUrl();
    private final Map<String, String> cache = new HashMap<>();

    public String translateTo(String text, String from, String to) throws ExecutionException, InterruptedException {
        String translation = cache.get(text);
        if (translation == null) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<String> future = executorService.submit(() -> {
                return translate(from, to, text);
            });
            translation = future.get();
            cache.put(text, translation);
        }
        return translation;
    }

    public String translateEnToVi(String text) throws IOException, ExecutionException, InterruptedException {
        return translateTo(text, "en", "vi");
    }

    public String translateViToEn(String text) throws IOException, ExecutionException, InterruptedException {
        return translateTo(text, "vi", "en");
    }

    private String translate(String langFrom, String langTo, String text) throws IOException {
        double startTime = System.currentTimeMillis();
        String urlStr = GOOGLE_TRANSLATE_API
                +
                "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&target=" + langTo +
                "&source=" + langFrom;
        URI uri = URI.create(urlStr);
        URL url = uri.toURL();
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        double endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        return response.toString();
    }
}
