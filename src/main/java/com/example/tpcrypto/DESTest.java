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
        // Test encryption
        String plaintext = "coucou";
        int[] encrypted = des.crypte(plaintext);
        assertNotNull(encrypted);
        assertNotEquals(0, encrypted.length);
    }

    @Test
    void decrypte() {
        // Test decryption
        String plaintext = "coucou";
        int[] encrypted = des.crypte(plaintext);
        assertNotNull(encrypted);

        String decrypted = des.decrypte(encrypted);
        assertNotNull(decrypted);
        assertEquals(plaintext, decrypted);
    }

    @Test
    void stringToBits() {
        // Test string to bits conversion
        String input = "test";
        int[] bits = des.stringToBits(input);
        assertNotNull(bits);
        assertEquals(input.length() * 8, bits.length);
    }

    @Test
    void bitsToString() {
        // Test bits to string conversion
        String expected = "test";
        int[] bits = des.stringToBits(expected);
        String result = des.bitsToString(bits);
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void decouppage() {
        // Test block splitting
        int[] input = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}; // 12 bits
        int[][] blocks = des.decouppage(input, 6);
        assertNotNull(blocks);
        assertEquals(2, blocks.length);
        assertEquals(6, blocks[0].length);
        assertEquals(6, blocks[1].length);
    }

    @Test
    void recollage_bloc() {
        // Test block merging
        int[][] input = {{1, 0, 1}, {0, 1, 0}}; // Two 3-bit blocks
        int[] merged = des.recollage_bloc(input);
        assertNotNull(merged);
        assertEquals(6, merged.length);
    }

    @Test
    void genereMasterKey() {
        // Test master key generation
        int keyLength = 64;
        int[] key = des.genereMasterKey(keyLength);
        assertNotNull(key);
        assertEquals(keyLength, key.length);
    }

    @Test
    void permutation() {
        // Test permutation
        int[] input = {1, 0, 1, 0, 1, 0}; // 6 bits
        int[] permutationTable = {5, 3, 1, 6, 4, 2}; // Example permutation table
        des.permutation(permutationTable, input);
        // Validate the permutation result manually based on the permutationTable
        assertArrayEquals(new int[]{0, 1, 0, 1, 1, 0}, input);
    }

    @Test
    void invPermutation() {
        // Test inverse permutation
        int[] input = {0, 1, 0, 1, 1, 0}; // 6 bits
        int[] permutationTable = {5, 3, 1, 6, 4, 2}; // Example permutation table
        des.invPermutation(permutationTable, input);
        // Validate the inverse permutation result manually based on the permutationTable
        assertArrayEquals(new int[]{1, 0, 1, 0, 1, 0}, input);
    }

    @Test
    void decalle_gauche() {
        // Test left shift
        int[] input = {1, 0, 1, 0, 1}; // 5 bits
        int shiftAmount = 2;
        int[] shifted = des.decalle_gauche(input, shiftAmount);
        assertNotNull(shifted);
        assertArrayEquals(new int[]{1, 0, 1, 0, 1}, input); // Ensure input array is not modified
        assertArrayEquals(new int[]{1, 0, 1, 0, 1}, shifted); // Left shift by 2 is the same as no shift for this input
    }

    @Test
    void xor() {
        // Test XOR operation
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