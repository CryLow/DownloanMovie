package org.example.repository;

import org.example.model.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParsingRepository {

    public Document getDocument(String urlMovie){
        try {
            return Jsoup.connect(urlMovie)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("https//yandex.ru")
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpURLConnection connection(String urlPath){
        try {
            URL url = new URL(urlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            return httpURLConnection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
