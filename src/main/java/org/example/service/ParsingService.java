package org.example.service;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class ParsingService {

    public String getKPID(Document document){   //Получаем kpid
        Elements elements = document.select("iframe");
        for(var element : elements){
            if(element.attr("src").contains("//arcchid.link/sow/")){
                String temp = element.attr("src");
                int index = 19;
                return temp.substring(index);
            }
        }
        return null;
    }
    public String getNameMovie(Document document){
        Elements elements = document.select("li > span ");
        for(var element : elements){
            if(element.attr("itemprop").equals("alternativeHeadline")) return element.text().replaceAll("\\s","");
        }
        return null;
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
    public String writingToString(InputStream inputStream){
        try {
            StringBuilder stringBuilder = new StringBuilder();
            int bytesRead;
            while ((bytesRead=inputStream.read())!=-1){
                stringBuilder.append((char) bytesRead);
            }
            inputStream.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String writingToStringAndFile(InputStream inputStream, String path){
        try {
            OutputStream outputStream = new FileOutputStream(path);
            StringBuilder stringBuilder = new StringBuilder();
            int bytesRead;
            while((bytesRead=inputStream.read())!=-1){
                stringBuilder.append((char) bytesRead);
                outputStream.write((char) bytesRead);
            }
            outputStream.close();
            inputStream.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void writingToFile(InputStream inputStream, String path){
        try {
            OutputStream outputStream = new FileOutputStream(path);
            int bytesRead;
            while((bytesRead=inputStream.read())!=-1){
                outputStream.write((char) bytesRead);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /*Scanner scanner = new Scanner(new File(path + "/Stream Resolution.m3u8"));
            scanner.nextLine();
    String temp = null;
            while (scanner.hasNextLine()){
        if(temp!=null) {streamingQuality.put(temp,scanner.nextLine());
            temp = null;}
        else {
            temp = tmp.substring(tmp.lastIndexOf("RESOLUTION=")+11,tmp.indexOf(",BANDWIDTH"));
        }
    }*/
    public LinkedHashMap<String, String > getResolutionFromFile(String path)
    {
        try {
            LinkedHashMap<String, String> streamingQuality = new LinkedHashMap<>();
            Scanner scanner = new Scanner(new File(path));
            scanner.nextLine();
            String resolution = null;
            while (scanner.hasNextLine()){
                if(resolution!=null) {
                    streamingQuality.put(resolution,scanner.nextLine());
                    resolution = null;
                }
                else {
                    String temporary = scanner.nextLine();
                    resolution = temporary.substring(temporary.lastIndexOf("RESOLUTION=")+11,temporary.indexOf(",BANDWIDTH"));
                }
            }
            return streamingQuality;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
