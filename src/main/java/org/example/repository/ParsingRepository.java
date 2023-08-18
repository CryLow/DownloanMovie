package org.example.repository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

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
}
