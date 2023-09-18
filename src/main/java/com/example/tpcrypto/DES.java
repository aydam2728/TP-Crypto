package com.example.tpcrypto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class DES extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DES.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    int taille_bloc = 64;
    int taille_sous_bloc = 32;
    int nb_ronde = 1;
    int tab_decalage = 0;
    int perme_initiale = 0;
    ArrayList<Integer> PI = new ArrayList<Integer>();
    ArrayList<Integer> PC1 = new ArrayList<Integer>();
    ArrayList<Integer> PC2 = new ArrayList<Integer>();
    ArrayList<Integer> S = new ArrayList<Integer>();
    ArrayList<Integer> E = new ArrayList<Integer>();

    int[] masterkey;
    ArrayList<Integer> tab_cles = new ArrayList<Integer>();

    public static void main(String[] args) {
        launch();
    }
}