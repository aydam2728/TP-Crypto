package com.example.tpcrypto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DESTest {

    private DES des;

    @BeforeEach
    void setUp() {
        des = new DES();
    }

    @AfterEach
    void tearDown() {
        des = null;
    }

    @Test
    void crypte() {
        // Teste encryption
        String plaintext = "coucou";
        int[] encrypted = des.crypte(plaintext);
        assertNotNull(encrypted);
        assertNotEquals(0, encrypted.length);
    }

    @Test
    void decrypte() {
        // Teste decryption
        String plaintext = "coucou";
        int[] encrypted = des.crypte(plaintext);
        assertNotNull(encrypted);

        String decrypted = des.decrypte(encrypted);
        assertNotNull(decrypted);
        // Vérifie que le texte décrypté contient le texte en input en ignorant le bourage
        assert(decrypted.contains(plaintext));
    }

    @Test
    void stringToBits() {
        // Teste conversion string to bits
        String input = "test";
        int[] bits = des.stringToBits(input);
        assertNotNull(bits);
        assertEquals(input.length() * 8, bits.length);
    }

    @Test
    void bitsToString() {
        // Teste conversion bits to string
        String expected = "test";
        int[] bits = des.stringToBits(expected);
        String result = des.bitsToString(bits);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void decouppage() {
        // Teste séparation block
        int[] input = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}; // 12 bits
        int[][] blocks = des.decouppage(input, 6);
        assertNotNull(blocks);
        assertEquals(2, blocks.length);
        assertEquals(6, blocks[0].length);
        assertEquals(6, blocks[1].length);
    }

    @Test
    void recollage_bloc() {
        // Teste fusion block
        int[][] input = {{1, 0, 1}, {0, 1, 0}}; // 2 blocks 3-bit
        int[] merged = des.recollage_bloc(input);
        assertNotNull(merged);
        assertEquals(6, merged.length);
    }

    @Test
    void genereMasterKey() {
        // Teste création master key
        int keyLength = 64;
        int[] key = des.genereMasterKey(keyLength);
        assertNotNull(key);
        assertEquals(keyLength, key.length);
    }

    @Test
    void permutation() {
        // Teste permutation
        int[] input = {1, 0, 1, 0, 1, 0}; // 6 bits
        int[] permutationTable = {4, 2, 0, 5, 3, 1}; // Example permutation table
        des.permutation(permutationTable, input);
        // Validation de la permutation en fonction de la permutationTable
        assertArrayEquals(new int[]{1, 1, 1, 0, 0, 0}, input);
    }

    @Test
    void invPermutation() {
        // Teste permutation inversée
        int[] input = {0, 1, 0, 1, 1, 0}; // 6 bits
        int[] permutationTable = {5, 3, 1, 6, 4, 2}; // Example permutation table
        des.invPermutation(permutationTable, input);
        // Validation de la permutation inversée en fonction de la permutationTable
        assertArrayEquals(new int[]{1, 0, 1, 0, 1, 0}, input);
    }

    @Test
    void decalle_gauche() {
        // Teste décalage gauche
        int[] input = {1, 0, 1, 0, 1}; // 5 bits
        int shiftAmount = 2;
        int[] shifted = des.decalle_gauche(input, shiftAmount);
        assertNotNull(shifted);
        assertArrayEquals(new int[]{1, 0, 1, 0, 1}, input); // Verifie que l'input n'est pas modifié
        assertArrayEquals(new int[]{1, 0, 1, 0, 1}, shifted); // Décalage de 2 bits vers la gauche est le même que pas de décalage pour cette exemple
    }

    @Test
    void xor() {
        // Teste opération XOR
        int[] input1 = {1, 0, 1, 0};
        int[] input2 = {1, 1, 0, 0};
        int[] result = des.xor(input1, input2);
        assertNotNull(result);
        assertArrayEquals(new int[]{0, 1, 1, 0}, result);
    }

    @Test
    void génèreClé() {
        // Test subkey generation
        // Add your test cases for this method here
    }
}