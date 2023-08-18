package org.example.model;

import java.util.LinkedHashMap;

public class Movie {
    public Movie(String urlOnMovie) {
        this.urlOnMovie = urlOnMovie;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public String getKpid() {
        return kpid;
    }

    public void setKpid(String kpid) {
        this.kpid = kpid;
    }

    public String[] getRESOLUTION() {
        return RESOLUTION;
    }

    public String getUrlOnMovie() {
        return urlOnMovie;
    }

    public void setUrlOnMovie(String urlOnMovie) {
        this.urlOnMovie = urlOnMovie;
    }

    public String getUrlResponse() {
        return urlResponse;
    }

    public void setUrlResponse(String urlResponse) {
        this.urlResponse = urlResponse;
    }

    public String getUrlResolutionIndex() {
        return urlResolutionIndex;
    }

    public void setUrlResolutionIndex(String urlResolutionIndex) {
        this.urlResolutionIndex = urlResolutionIndex;
    }

    public String getUrlSegmentIndex() {
        return urlSegmentIndex;
    }

    public void setUrlSegmentIndex(String urlSegmentIndex) {
        this.urlSegmentIndex = urlSegmentIndex;
    }

    public String getPathOnDirectory() {
        return pathOnDirectory;
    }

    public void setPathOnDirectory(String pathOnDirectory) {
        this.pathOnDirectory = pathOnDirectory;
    }

    public LinkedHashMap<String, String> getStreamingQuality() {
        return streamingQuality;
    }

    public void setStreamingQuality(LinkedHashMap<String, String> streamingQuality) {
        this.streamingQuality = streamingQuality;
    }

    String nameMovie = "";
    String kpid = "";
    String []RESOLUTION = {"1920x1080","1280x720","854x480","640x360"};
    String urlOnMovie = "";
    String urlResponse = "";
    String urlResolutionIndex = "";
    String urlSegmentIndex = "";
    String pathOnDirectory = System.getProperty("user.dir");
    int voices = 6;
    LinkedHashMap<String,String> streamingQuality = null;

    public LinkedHashMap<Integer, Integer> getDataForSeasonsAndEpisodes() {
        return dataForSeasonsAndEpisodes;
    }

    public void setDataForSeasonsAndEpisodes(LinkedHashMap<Integer, Integer> dataForSeasonsAndEpisodes) {
        this.dataForSeasonsAndEpisodes = dataForSeasonsAndEpisodes;
    }

    LinkedHashMap<Integer,Integer> dataForSeasonsAndEpisodes = null;
}
