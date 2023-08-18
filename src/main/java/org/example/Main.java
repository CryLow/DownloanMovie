package org.example;

import org.example.controller.AppController;
import org.example.view.GUI;


public class Main {
    public static void main(String[] args) {

        AppController appController = new AppController();

        GUI gui = new GUI(appController);
        gui.createGUI();

    }
}