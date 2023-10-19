package com.example.tpcrypto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

public class DES {

    int taille_bloc = 64;
    int taille_sous_bloc = 32;
    int nb_ronde = 0;
    int[] tab_decalage = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2,2, 2, 2, 1};
    private static final int[] perm_initiale = {
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7,
            56, 48, 40, 32, 24, 16, 8, 0,
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6
    };
    private static final int[] PC1 = {
            56, 48, 40, 32, 24, 16, 8,
            0, 57, 49, 41, 33, 25, 17,
            9, 1, 58, 50, 42, 34, 26,
            18, 10, 2, 59, 51, 43, 35,
            62, 54, 46, 38, 30, 22, 14,
            6, 61, 53, 45, 37, 29, 21,
            13, 5, 60, 52, 44, 36, 28,
            20, 12, 4, 27, 19, 11, 3
    };
    private static final int[] PC2 = {
            13, 16, 10, 23, 0, 4,
            2, 27, 14, 5, 20, 9,
            22, 18, 11, 3, 25, 7,
            15, 6, 26, 19, 12, 1,
            40, 51, 30, 36, 46, 54,
            29, 39, 50, 44, 32, 47,
            43, 48, 38, 55, 33, 52,
            45, 41, 49, 35, 28, 31
    };

    int[][][] S = {
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },

            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },

            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },

            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },

            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },

            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },

            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            },

            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };


    int[] E = {
            31, 0, 1, 2, 3, 4,
            3, 4, 5, 6, 7, 8,
            7, 8, 9, 10, 11, 12,
            11, 12, 13, 14, 15, 16,
            15, 16, 17, 18, 19, 20,
            19, 20, 21, 22, 23, 24,
            23, 24, 25, 26, 27, 28,
            27, 28, 29, 30, 31, 0
    };


    int[] P = {
            15, 6, 19, 20, 28, 11, 27, 16,
            0, 14, 22, 25, 4, 17, 30, 9,
            1, 7, 23, 13, 31, 26, 2, 8,
            18, 12, 29, 5, 21, 10, 3, 24
    };
    private static final int[] Pinv = {
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25,
            32, 0, 40, 8, 48, 16, 56, 24
    };


    int[] masterkey;
    int[][] tab_cles ;

    public DES() {
        masterkey = genereMasterKey(64);
        tab_cles = new int[16][48];
    }

    public int[] crypte(String message_clair) {
        // message_code transforme un message chaîne de caractères, en un tableau d’entiers (0 ou 1) résultat du cryptage
        int[] messageBinaire = stringToBits(message_clair);
        // Calculate padding length
        if (messageBinaire.length % 64 != 0) {
            int paddingLength = 64 -(messageBinaire.length % 64);
            int[] padding = new int[paddingLength];

            // Create a new array with the padding included
            int[] paddedMessage = new int[messageBinaire.length + paddingLength];
            System.arraycopy(messageBinaire, 0, paddedMessage, 0, messageBinaire.length);
            System.arraycopy(padding, 0, paddedMessage, messageBinaire.length, paddingLength);
            messageBinaire = paddedMessage;
        }

        // découpage en blocs de 64 bits
        int[][] messagetab = decouppage(messageBinaire,64);
        //perm_initiale(messageBinaire);
        // for each blocs de 64 bits
        for (int i = 0; i < messagetab.length; i++) {
            messagetab[i]= permutation(perm_initiale,messagetab[i]);
            // cryptage du bloc
            int[] blocG = decouppage(messagetab[i], 32)[0];
            int[] blocD = decouppage(messagetab[i], 32)[1];
            nb_ronde = 0;
            for (int y = 0; y < 16; y++) {
                nb_ronde = y;
                if (i==0){
                    génèreClé(nb_ronde);
                }
                // Feistel function
                int[] fResult = functionF(blocD);
                // xor avec blocG
                int[] xorResult = xor(blocG, fResult);
                // Swap the halves
                blocG = blocD;
                blocD = xorResult;

            }
            messagetab[i] = recollage_bloc(new int[][]{blocG, blocD});
            // permutation P-1
            messagetab[i]=invPermutation(perm_initiale,messagetab[i]);
        }
        // return the tab of int
        return recollage_bloc(messagetab);
    }

    public String decrypte(int[] messageCodé) {
        //décrypte un tableau d’entiers (0 ou 1) résultat d’un cryptage en une chaîne de caractères donnat le message clair.
        int[][] messagetab = decouppage(messageCodé,64);
        // for each blocs de 64 bits
        for (int i = 0; i < messagetab.length; i++) {
            // cryptage du bloc
            int[] blocG = decouppage(messagetab[i], 32)[0];
            int[] blocD = decouppage(messagetab[i], 32)[1];
            nb_ronde = 15;
            for (int y = 15; y >-1; y--) {
                nb_ronde = y;
                // Feistel function
                int[] fResult = functionF(blocG);

                // XOR with the right half
                int[] xorResult = xor(blocD, fResult);

                // Swap the halves
                blocD = blocG;
                blocG = xorResult;

            }
            messagetab[i] = recollage_bloc(new int[][]{blocG, blocD});
            // permutation P-1
            messagetab[i]=invPermutation(perm_initiale,messagetab[i]);
        }
        // remove padding
        int[] result=(recollage_bloc(messagetab));
        // return the tab of int
        System.out.println("result = " + Arrays.toString(result));
        return bitsToString(result);
    }

    public int[] removePadding(int[] message) {
        // Assuming you padded with zeros, find the last non-zero bit
        int lastNonZeroIndex = message.length - 1;
        while (lastNonZeroIndex >= 0 && message[lastNonZeroIndex] == 0) {
            lastNonZeroIndex--;
        }

        // Create a new array without the padding
        int[] unpaddedMessage = new int[lastNonZeroIndex + 1];
        System.arraycopy(message, 0, unpaddedMessage, 0, lastNonZeroIndex + 1);

        return unpaddedMessage;
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

    public int[][] decouppage(int[] bloc, int tailleBloc) {
        // découpe un tableau d’entiers en un tableau de tableaux d’entiers de taille tailleBloc
        int[][] tab = new int[bloc.length / tailleBloc][tailleBloc];
        for (int i = 0; i < bloc.length; i++) {
            tab[i / tailleBloc][i % tailleBloc] = bloc[i];
        }
        return tab;
    }

    public int[] recollage_bloc(int[][] tab) {
        // recolle un tableau de tableaux d’entiers en un tableau d’entiers
        int[] bloc = new int[tab.length * tab[0].length];
        for (int i = 0; i < tab.length; i++) {
            System.arraycopy(tab[i], 0, bloc, i * tab[0].length + 0, tab[0].length);
        }
        return bloc;
    }

    public int[] genereMasterKey(int n) {
        // create a tab with random int range 0-1 with 64 elements
        Random rd = new Random();
        int[] MasterKey = new int[n];
        for (int i = 0; i < MasterKey.length; i++) {
            MasterKey[i] = rd.nextInt(0, 2);
        }
        return MasterKey;
    }


    public int[] permutation(int[] tab_permutation, int[] bloc) {
        //permutation d’un bloc d’entiers bloc selon le tableau d’entiers tab_permutation
        int[] bloc_permute = new int[tab_permutation.length];
        for (int i = 0; i < tab_permutation.length; i++) {
            bloc_permute[i] = bloc[tab_permutation[i]];
        }
        return bloc_permute;
    }

    public int[] invPermutation(int[] tab_permutation, int[] bloc) {
        //permutation inverse d’un bloc d’entiers bloc selon le tableau d’entiers tab_permutation
        int[] bloc_permute = new int[tab_permutation.length];
        for (int i = 0; i < tab_permutation.length; i++) {
            bloc_permute[tab_permutation[i]] = bloc[i];
        }
        return bloc_permute;
    }

    public int[] decalle_gauche(int[] bloc, int nbCran) {
        //décale un bloc d’entiers bloc de nbCran crans vers la gauche
        int[] bloc_decale = new int[bloc.length];
        for (int i = 0; i < bloc.length; i++) {
            bloc_decale[i] = bloc[(i + nbCran) % bloc.length];
        }
        return bloc_decale;
    }

    public int[] xor(int[] tab1, int[] tab2) {
        //effectue un ou exclusif entre deux tableaux d’entiers de même taille
        int[] tab_xor = new int[tab1.length];
        for (int i = 0; i < tab1.length; i++) {
            tab_xor[i] = tab1[i] ^ tab2[i];
        }
        return tab_xor;
    }

    public void génèreClé(int n) {
        //calcule la clé de la n ième ronde, la stocke aussi dans tab_clés (pour le décryptage …)
        int[] cle = new int[masterkey.length];
        // copy de PC1 dans cle
        System.arraycopy(masterkey, 0, cle, 0, masterkey.length);
        cle = permutation(PC1, cle);
        // découpage de la clé en deux blocs
        int[] bloc_gauche = new int[cle.length / 2];
        int[] bloc_droite = new int[cle.length / 2];

        System.arraycopy(cle, 0, bloc_gauche, 0, cle.length / 2);
        System.arraycopy(cle, cle.length / 2, bloc_droite, 0, cle.length / 2);
        // decalage vers la gauche en fonction de la ronde
        bloc_gauche = decalle_gauche(bloc_gauche, tab_decalage[n]);
        bloc_droite = decalle_gauche(bloc_droite, tab_decalage[n]);
        // fusion des deux blocs
        int[] bloc_fusion = new int[bloc_gauche.length + bloc_droite.length];
        System.arraycopy(bloc_gauche, 0, bloc_fusion, 0, bloc_gauche.length);
        System.arraycopy(bloc_droite, 0, bloc_fusion, bloc_gauche.length, bloc_droite.length);
        // permutation avec pc2
        bloc_fusion = permutation(PC2, bloc_fusion);
        // stockage de la clé dans tab_cles
        tab_cles[n]=bloc_fusion;
    }

    public int[] functionF(int[] unD){
        //extension du bloc Dn de 32 bits en un bloc D’n de  48 bits, `a l’aide de la transformation E en r´ep´etant  certains bits :
        // permutation avec E
        unD = permutation(E, unD);
        // xor avec la clé
        unD=xor(unD, tab_cles[nb_ronde]);
        // découpage en 8 blocs de 6 bits
        int[][] tab_blocs = decouppage(unD, 6);
        // pour chaque bloc, on applique la fonction S
        for (int i = 0; i < tab_blocs.length; i++) {
            tab_blocs[i] = functionS(tab_blocs[i]);
        }
        // on recolle les blocs
        unD = recollage_bloc(tab_blocs);
        // permutation avec P
        unD = permutation(P, unD);
        return unD;
    }

    public int[] functionS(int[] tab){
        // fonction S
        // on récupère la ligne et la colonne
        int ligne = tab[0] * 2 + tab[5];
        int colonne = tab[1] * 8 + tab[2] * 4 + tab[3] * 2 + tab[4];
        // on récupère la valeur dans la table S
        int valeur = S[0][ligne][colonne];
        // on convertit la valeur en binaire
        int[] tab_valeur = new int[4];
        for (int i = 0; i < tab_valeur.length; i++) {
            tab_valeur[i] = valeur % 2;
            valeur = valeur / 2;
        }
        return tab_valeur;
    }

    public static void main(String[] args) {
        DES des = new DES();
        //String msg = "Toutes les connaissances que les hommes avaient mises sur Internet lui étaient accessibles. Les grandes bibliothèques du monde entier n’avaient plus de secret pour lui. Il pouvait apprendre très vite, beaucoup plus vite que n’importe quel humain. Il avait appris toutes les connaissances du monde entier, visité tous les pays. C’est lui qui avait fait en sorte qu’Internet se déploie ainsi. Il pouvait alors, à chaque fois qu’un nouvel ordinateur se connectait, approfondir son savoir, se connecter à une nouvelle caméra vidéo, ou même se connecter à des robots.";
        String msg="bonjoura";
        int[] msgcrypt = des.crypte(msg);
        // print
        System.out.println("msg : " + Arrays.toString(des.stringToBits(msg)));
        System.out.println("msgcrypt : " + Arrays.toString(msgcrypt));
        //decrypt
        System.out.println("msgdecrypt : " + des.decrypte(msgcrypt));
    }
}