package com.example.tpcrypto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;

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

    public DES() {
        super();
        masterkey=genereMasterKey();
        ArrayList<String> tab_cles = new ArrayList<String>();

    }

    public int[] crypte (String message_clair){
        // message_code transforme un message chaîne de caractères, en un tableau d’entiers (0 ou 1) résultat du cryptage
        return new int[1];
    }

    public String decrypte(int[] messageCodé){
        //décrypte un tableau d’entiers (0 ou 1) résultat d’un cryptage en une chaîne de caractères donnat le message clair.
        return new String();
    }

    public int[] stringToBits(String message) {
        //transforme une chaîne de caractères en un tableau d’entiers : 0 et 1

        if (message == null || message.isEmpty()) {
            // Gérer le cas où la chaîne est nulle ou vide
            return new int[0]; // Retourner un tableau vide
        }

        int[] bits = new int[message.length() * 8]; // Chaque caractère est représenté par 8 bits

        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            for (int j = 0; j < 8; j++) {
                // Convertir le caractère en bits
                bits[i * 8 + j] = (c >> (7 - j)) & 1;
            }
        }
        System.out.println("bits.length = " + bits.length);
        /*if (bits.length % 64 != 0) {
            // Gérer le cas où le tableau n'est pas un multiple de 64, bourrage de 0
            int[] newBits = new int[bits.length % 64];
           // System.out.println("newBits.length = " + newBits.length);
            return ArrayUtils.addAll(bits, newBits);
        }
*/
        return bits;
    }
    public String bitsToString(int[] blocks) {
        if (blocks == null || blocks.length == 0) {
            // Gérer le cas où le tableau est nul ou vide
            return "";
        }

        int numBlocks = blocks.length / 8; // Chaque caractère est représenté par 8 bits
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < numBlocks; i++) {
            int value = 0;
            for (int j = 0; j < 8; j++) {
                // Reconstruire la valeur binaire à partir des bits
                value = (value << 1) | blocks[i * 8 + j];
            }
            stringBuilder.append((char) value); // Convertir la valeur en caractère
        }

        return stringBuilder.toString();
    }

    public int[][] decouppage(int [] bloc , int tailleBloc){
        // découpe un tableau d’entiers bloc en un tableau de tableaux d’entiers de taille tailleBloc
        if (bloc.length % tailleBloc != 0) {
             // Gérer le cas où le tableau n'est pas un multiple de 64, bourrage de 0
             int[] newBits = new int[tailleBloc - bloc.length % tailleBloc];
             bloc = ArrayUtils.addAll(bloc, newBits);
         }
        int[][] tab = new int[bloc.length/tailleBloc][tailleBloc];
        for (int i = 0; i < bloc.length; i++) {
            tab[i/tailleBloc][i%tailleBloc] = bloc[i];
        }
        return tab;
    }

    public int[] recollage_bloc(int[][] tab){
        // recolle un tableau de tableaux d’entiers en un tableau d’entiers
        int[] bloc = new int[tab.length*tab[0].length];
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                bloc[i*tab[0].length+j] = tab[i][j];
            }
        }
        return bloc;
    }

    public int[] genereMasterKey(){
        // create a tab with random int range 0-1 with 64 elements
        Random rd = new Random();
        int[] MasterKey = new int[64];
        for (int i = 0; i < MasterKey.length; i++) {
            MasterKey[i] = rd.nextInt(0 , 2);
        }
        return MasterKey;
    }

    public static void main(String[] args) {
        //launch();
        DES des = new DES();
        System.out.println( Arrays.toString(       des.stringToBits("Salut ! ")))    ;
        System.out.println(des.stringToBits("Hello World !").length);
        System.out.println(des.bitsToString(des.stringToBits("Hello World !")));
        System.out.println(Arrays.deepToString(des.decouppage(des.stringToBits("Hello World !"), 64)));
    }
}