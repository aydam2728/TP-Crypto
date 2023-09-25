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
    private static final int[] perm_initiale = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };
    ArrayList<Integer> PI = new ArrayList<Integer>();
    private static final int[] PC1 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };
    private static final int[] PC2 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };
    int[][][] S = {
            {
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    },

    {
        {
            15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10
        },
        {
            3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5
        },
        {
            0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15
        },
        {
            13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9
        }
    },

    {
        {
            10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8
        },
        {
            13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1
        },
        {
            13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7
        },
        {
            1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12
        }
    },

    {
        {
            7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15
        },
        {
            13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9
        },
        {
            10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4
        },
        {
            3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14
        }
    },

    {
        {
            2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9
        },
        {
            14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6
        },
        {
            4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14
        },
        {
            11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3
        }
    },

    {
        {
            12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11
        },
        {
            10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8
        },
        {
            9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6
        },
        {
            4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13
        }
    },

    {
        {
            4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1
        },
        {
            1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2
        },
        {
            7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8
        },
        {
            2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
        }
    },

    {
        {
            13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7
        },
        {
            1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2
        },
        {
            7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8
        },
        {
            2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11
        }
    }
};


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


    public void permutation(int[] tab_permutation, int[] bloc) {
        //permutation d’un bloc d’entiers bloc selon le tableau d’entiers tab_permutation
        int[] bloc_permute = new int[tab_permutation.length];
        for (int i = 0; i < tab_permutation.length; i++) {
            bloc_permute[i] = bloc[tab_permutation[i]];
        }
    }

    public void invPermutation(int[] tab_permutation, int[] bloc) {
        //permutation inverse d’un bloc d’entiers bloc selon le tableau d’entiers tab_permutation
        int[] bloc_permute = new int[tab_permutation.length];
        for (int i = 0; i < tab_permutation.length; i++) {
            bloc_permute[tab_permutation[i]] = bloc[i];
        }
    }

   public int[] decalle_gauche(int[] bloc, int nbCran) {
        //décale un bloc d’entiers bloc de nbCran crans vers la gauche
        int[] bloc_decale = new int[bloc.length];
        for (int i = 0; i < bloc.length; i++) {
            bloc_decale[i] = bloc[(i + nbCran) % bloc.length];
        }
        return bloc_decale;
    }
    public int[] xor ( int[] tab1, int[] tab2){
        //effectue un ou exclusif entre deux tableaux d’entiers de même taille
        int[] tab_xor = new int[tab1.length];
        for (int i = 0; i < tab1.length; i++) {
            tab_xor[i] = tab1[i] ^ tab2[i];
        }
        return tab_xor;
    }

    public void génèreClé(int n){
        //calcule la clé de la n ième ronde, la stocke aussi dans tab_clés (pour le décryptage …)

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