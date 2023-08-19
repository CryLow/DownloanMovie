package org.example.view;

import org.example.controller.AppController;
import org.example.model.Movie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class GUI {

    private JFrame jFrame = new JFrame("Download Movie");

    AppController appController = null;
    public GUI(AppController appController){
        this.appController = appController;
    }
    public void createGUI(){
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(950,650);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);

        JLabel jLabel = new JLabel();

        JPanel jPanel1 = new JPanel();
        jPanel1.add(jLabel);

        JPanel jPanel = new JPanel();

        JTextField urlMovie = new JTextField("");
        urlMovie.setPreferredSize(new Dimension(750,30));

        JButton buttonSend = new JButton("Скачать");

        JPanel jPanel3 = new JPanel();
        jFrame.getContentPane().add(BorderLayout.SOUTH,jPanel3);

        jPanel.add(urlMovie);
        jPanel.add(buttonSend);
        jFrame.getContentPane().add(BorderLayout.NORTH,jPanel);

        final JPanel testPanel = new JPanel();
        final JScrollPane testScrollPane = new JScrollPane(testPanel);
        testPanel.setLayout(new BoxLayout(testPanel,BoxLayout.Y_AXIS));
        buttonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!urlMovie.getText().equals("")){
                    appController.setMovie(new Movie(urlMovie.getText()));
                    JComboBox<Integer> chooseSeason = new JComboBox<>(appController.countSeason());
                    chooseSeason.setSize(new Dimension(100,100));
                    testPanel.add(chooseSeason);
                    testScrollPane.revalidate();
                }
            }
        });
        jFrame.getContentPane().add(testScrollPane,BorderLayout.CENTER);
        jFrame.setVisible(true);

    }
}
