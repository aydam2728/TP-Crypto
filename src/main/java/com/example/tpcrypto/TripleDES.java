package com.example.tpcrypto;

import java.util.Arrays;

public class TripleDES {
    private DES des1;
    private DES des2;
    private DES des3;
    public boolean retirerBourrage;

    int[][] tab_cles = new int[16][48];

    public TripleDES() {
        genereCle();
    }

    public static void main(String[] args) {
        TripleDES tripleDES = new TripleDES();
        String plaintext = "Hello";
        int[] ciphertext = tripleDES.encrypt(plaintext);
        String decryptedText = tripleDES.decrypt(ciphertext);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext: " + Arrays.toString(ciphertext));
        System.out.println("Decrypted Text: " + decryptedText);
    }

    public int[] encrypt(String plaintext) {
        int[] encrypted1 = des1.crypte(plaintext);
        String decrypted1 = des2.decrypte(encrypted1);
        return des3.crypte(decrypted1);
    }

    public String decrypt(int[] ciphertext) {
        int[] decrypted1 = des3.decrypteINT(ciphertext);
        int[] encrypted1 = des2.crypte(decrypted1);
        return des1.decrypte(encrypted1);
    }

    private void genereCle() {
        des1 = new DES();
        this.tab_cles = des1.tab_cles;
        des2 = new DES(tab_cles);
        des2.genereCle = false;
        des3 = new DES(tab_cles);
        des3.genereCle = false;

        des1.retirerBourrage = retirerBourrage;
        des2.retirerBourrage = retirerBourrage;
        des3.retirerBourrage = retirerBourrage;
    }
}
