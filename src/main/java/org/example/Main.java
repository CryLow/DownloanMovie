package org.example;

import org.json.JSONObject;
import org.jsoup.*;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        String []RESOLUTION = {"1920x1080","1280x720","854x480","640x360"};
        String kpid = "";
        String name = null;
        String path = System.getProperty("user.dir");
        String urlFilm = "https://lordserial.run/zarubezhnye-serialy/25-psy-rezervacii-a1.html";
        int countSegments = 0;
        try {
            Document document = Jsoup.connect(urlFilm)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("https://yandex.ru")
                    .get();
            Elements elements = document.select("iframe");
            for(var i : elements){
                if(i.attr("src").contains("//arcchid.link/sow/")){
                    String temp = i.attr("src");
                    int index = 19;
                    kpid = temp.substring(index); //Получаем kpid
                }

            }
            Elements elements1 = document.select("li > span ");
            for(var i : elements1){
                if(i.attr("itemprop").equals("alternativeHeadline")) name = i.text();
            }
            name = name.replaceAll("\\s","");
            path += ("/" + name);
            Files.createDirectories(Paths.get( path));
            int episode = 1;
            int season = 1;
            int voice = 6;

            String urlResp = "https://arcchid.link/player/responce.php?kpid="+ kpid +"&season="+ season+ "&episode="+ episode +"&voice="+ voice +"&type=undefined&uniq";
            String urlMainIndex = null;
            InputStream responsible = null;

            URL resp = new URL(urlResp);
            HttpURLConnection httpURLConnection = (HttpURLConnection) resp.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);

            if(HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()){
                responsible = httpURLConnection.getInputStream();
            }
            StringBuilder stringBuilder = new StringBuilder();
            int bytesRead;
            while ((bytesRead=responsible.read()) != -1) {
                stringBuilder.append((char) bytesRead);
            }
            JSONObject jsonObject1 = new JSONObject(stringBuilder.toString());
            urlMainIndex = jsonObject1.getString("src");
            responsible.close();


            InputStream indexMainM3u8 = null;

            URL indexm3u8 = new URL(urlMainIndex);
            HttpURLConnection httpURLConnectionM3U8 = (HttpURLConnection) indexm3u8.openConnection();
            httpURLConnectionM3U8.setRequestMethod("GET");
            httpURLConnectionM3U8.setConnectTimeout(10000);
            httpURLConnectionM3U8.setReadTimeout(10000);

            if(HttpURLConnection.HTTP_OK == httpURLConnectionM3U8.getResponseCode()){
                indexMainM3u8 = httpURLConnectionM3U8.getInputStream();
            }
            stringBuilder.setLength(0);
            OutputStream mainM3U8 = new FileOutputStream(path + "/Stream Resolution.m3u8");
            while ((bytesRead=indexMainM3u8.read()) != -1) {
                stringBuilder.append((char) bytesRead);
                mainM3U8.write((char) bytesRead);
            }
            String index = stringBuilder.toString();
            mainM3U8.close();
            indexMainM3u8.close();

            LinkedHashMap<String, String> streamingQuality = new LinkedHashMap<>();
            Scanner scanner = new Scanner(new File(path + "/Stream Resolution.m3u8"));
            scanner.nextLine();
            String temp = null;
            while (scanner.hasNextLine()){
                if(temp!=null) {streamingQuality.put(temp,scanner.nextLine());
                                temp = null;}
                else {
                    String tmp =scanner.nextLine();
                    temp = tmp.substring(tmp.lastIndexOf("RESOLUTION=")+11,tmp.indexOf(",BANDWIDTH"));
                }
            }
            String localRes = RESOLUTION[0];
            String urlDownload = streamingQuality.get(localRes);

            InputStream streamUrlDownload = null;
            OutputStream streamOutDownload = new FileOutputStream(path + "/index.m3u8");
            URL urlStreamDownload = new URL(urlDownload);
            HttpURLConnection connectionUrlStreamDownload = (HttpURLConnection) urlStreamDownload.openConnection();
                connectionUrlStreamDownload.setRequestMethod("GET");
                connectionUrlStreamDownload.setConnectTimeout(10000);
                connectionUrlStreamDownload.setReadTimeout(10000);

                if(HttpURLConnection.HTTP_OK==connectionUrlStreamDownload.getResponseCode()){
                    streamUrlDownload = connectionUrlStreamDownload.getInputStream();
                }
            stringBuilder.setLength(0);
            while ((bytesRead = streamUrlDownload.read()) != -1) {
                stringBuilder.append((char) bytesRead);
                streamOutDownload.write((char) bytesRead);
            }
            String tmp = "";
            for(int ind = stringBuilder.length()-20; !String.valueOf(stringBuilder.charAt(ind)).equals("t"); ind--){
            tmp= String.valueOf(stringBuilder.charAt(ind)) + tmp;
            }
            countSegments = Integer.parseInt(tmp);
          /*  for(int fromIndex = 0;(fromIndex=stringBuilder.indexOf("segment",fromIndex))!=-1;fromIndex++,countSegments++) { }*/
            OutputStream info = new FileOutputStream(path + "/info.txt");
            info.write((urlFilm + "\n").getBytes());
            info.write((urlMainIndex + "\n").getBytes());
            info.write((urlResp + "\n").getBytes());
            info.close();
            boolean isNull = true;
            InputStream inputStream;
            OutputStream outputStream = new FileOutputStream(path + "/" + name + ".mp4");
            int tempe = urlMainIndex.indexOf("vod");
            String urlSegment = null;
            for(int indexSegment = 1; indexSegment<=countSegments;indexSegment++){
                urlSegment =
                        urlMainIndex.substring(0,urlMainIndex.indexOf("vod")) + "content/stream" +
                        urlMainIndex.substring(urlMainIndex.indexOf("vod")+3,urlMainIndex.length()-10) + "360" + "/segment" + indexSegment + ".ts";
                System.out.println(urlSegment);
                URL url = new URL(urlSegment);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                    inputStream = connection.getInputStream();
                   System.out.println(connection.getReadTimeout());
                    while ((bytesRead=inputStream.read()) != -1) {
                        outputStream.write((char) bytesRead);
                    }
                    System.out.println("Load....");
                    inputStream.close();
                }
                else {
                    System.out.println("The End");
                    isNull= false;
                }
            }
            outputStream.close();
        } catch (IOException e)  {
        throw new RuntimeException(e);
    }
}}