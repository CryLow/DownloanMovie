package org.example.service;

import org.example.repository.ParsingRepository;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class ParsingService {

    ParsingRepository parsingRepository = new ParsingRepository();

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
    public LinkedHashMap<String, String> getResolutionFromFile(String path)
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

    public LinkedHashMap<Integer,Integer> counterSeasons(String kpid){
        LinkedHashMap<Integer,Integer> seasonAndEpisode = new LinkedHashMap<>();
        String url1 = "https://arcchid.link/player/responce.php?kpid="+ kpid +"&season=";
        String url2 = "&episode=1&voice="+ "6" +"&type=undefined&uniq";
        int season = 1;
        try {
            for(;;season++){
                HttpURLConnection connection =
                        parsingRepository.connection("https://arcchid.link/player/responce.php?kpid="+ kpid +"&season=" + season + "&episode=1&voice=6&type=undefined&uniq");
                if(writingToString(connection.getInputStream()).equals("<center>File not found</center>")){
                    return seasonAndEpisode;
                }
                int countEpisodes = counterEpisodes(kpid,season);
                seasonAndEpisode.put(season,countEpisodes);
            }
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public int counterEpisodes(String kpid ,int season){
        int episode = 1;
        try {
            for(;;episode++){
                HttpURLConnection connection = parsingRepository.connection
                        ("https://arcchid.link/player/responce.php?kpid=" + kpid + "&season=" + season + "&episode=" + episode + "&voice=6&type=undefined&uniq");
                if(writingToString(connection.getInputStream()).equals("<center>File not found</center>")) return (episode-1);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
