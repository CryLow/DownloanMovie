package org.example.controller;

import org.example.model.Movie;
import org.example.repository.ParsingRepository;
import org.example.service.ParsingService;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

public class AppController {
    Movie movie = null;

    ParsingRepository parsingRepository = new ParsingRepository();
    ParsingService parsingService = new ParsingService();
    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
        Document document = parsingRepository.getDocument(this.movie.getUrlOnMovie());
        this.movie.setKpid(parsingService.getKPID(document));
        this.movie.setNameMovie(parsingService.getNameMovie(document));
        String path = this.movie.getPathOnDirectory() + "/" + this.movie.getNameMovie();
        try {
            Files.createDirectories(Paths.get(path));
            this.movie.setUrlResponse("https://arcchid.link/player/responce.php?kpid="+ this.movie.getKpid() +"&season="+ "1" + "&episode="+ "1" +"&voice="+ "6" +"&type=undefined&uniq");
            this.movie.setDataForSeasonsAndEpisodes(parsingService.counterSeasons(this.movie.getKpid()));
            HttpURLConnection connection = parsingRepository.connection(this.movie.getUrlResponse());
            if(HttpURLConnection.HTTP_OK==connection.getResponseCode()){
                this.movie.setUrlResolutionIndex(new JSONObject(parsingService.writingToString(connection.getInputStream())).getString("src"));
            }
            connection = parsingRepository.connection(this.movie.getUrlResolutionIndex());
            if(HttpURLConnection.HTTP_OK==connection.getResponseCode()){
                parsingService.writingToFile(connection.getInputStream(),path + "/Stream Resolution.m3u8");
            }
            this.movie.setStreamingQuality(parsingService.getResolutionFromFile(path + "/Stream Resolution.m3u8"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Vector<Integer> countSeason(){
        Vector<Integer> temp = new Vector<>();
        for(int i = 1; i <= movie.getDataForSeasonsAndEpisodes().size();i++){
            temp.add(i);
        }
        return temp;
    }
    public Vector<Integer> countEp(int season){
        Vector<Integer> temp = new Vector<>();
        for(int i = 1; i < movie.getDataForSeasonsAndEpisodes().get(season);i++)
        {
            temp.add(i);
        }
        return temp;
    }

}
