package org.example.model;

import java.util.LinkedHashMap;

public class Movie {
    String nameMovie = "";
    String kpid = "";
    String []RESOLUTION = {"1920x1080","1280x720","854x480","640x360"};
    String urlOnMovie = "https://lordserial.run/zarubezhnye-serialy/25-psy-rezervacii-a1.html";
    String urlResponse = "";
    String urlResolutionIndex = "";
    String urlSegmentIndex = "";
    String pathOnDirectory = System.getProperty("user.dir");
    int episodes = 1;
    int seasons = 1;
    LinkedHashMap<String,String> streamingQuality = null;
}
