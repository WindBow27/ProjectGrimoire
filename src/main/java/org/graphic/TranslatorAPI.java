package org.graphic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TranslatorAPI {
    private final Map<String, String> cache = new HashMap<>();

    public String translateEnToVi(String text) throws IOException {
        String translation = cache.get(text);
        if (translation == null) {
            translation = translate("en", "vi", text);
            cache.put(text, translation);
        }

        return translation;
    }

    public String translateViToEn(String text) throws IOException {
        String translation = cache.get(text);
        if (translation == null) {
            translation = translate("vi", "en", text);
            cache.put(text, translation);
        }

        return translation;
    }

    private String translate(String langFrom, String langTo, String text) throws IOException {
        double startTime = System.currentTimeMillis();
        String urlStr = "https://script.google.com/macros/s/AKfycbxzCfT78zpe2hPNd75uEzo7Joq2m-ach6UxoEuKpnf0JEgUcBc4C7SKL6QFeZ5ghDJM/exec"
                +
                "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8) +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
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
