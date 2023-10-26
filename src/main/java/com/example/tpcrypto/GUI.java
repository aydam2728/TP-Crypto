package com.example.tpcrypto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI {
    private static final JFrame frame = new JFrame("TP DES");
    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel mainPanel = new JPanel(cardLayout);
    private static final JPanel startPanel = new JPanel();
    private static final JPanel cryptoPanel = new JPanel();

    private static boolean type = true; // true = encrypt, false = decrypt

    private static boolean tripleDESBool = false;

    private static boolean enleverBourrage = false;
    private static JLabel lblText1;
    private static JLabel lblText2;
    private static JButton actionButton;
    private static JTextArea textArea1;
    private static JTextArea textArea2;

    private static final TripleDES tripleDES = new TripleDES();

    private static final DES des = new DES();
    private static JLabel lblVerif;

    private static JFileChooser jFileChooser;
    

    public static void main(String[] args) {
        frame.setSize(800, 500);

        jFileChooser = new JFileChooser();

        setupStartPanel();
        setupCryptoPanel();
        startPanel.setForeground(Color.WHITE);
        startPanel.setBackground(new Color(64, 68, 75));

        mainPanel.add(startPanel, "StartPanel");
        JButton decryptButton = new JButton("Déchiffrer");
        decryptButton.setForeground(Color.WHITE);
        decryptButton.setBounds(326, 257, 150, 50);
        decryptButton.setBorder(BorderFactory.createLineBorder(Color.white));
        decryptButton.setFocusPainted(false);
        decryptButton.setContentAreaFilled(false);
        startPanel.add(decryptButton);

        JLabel lblNewLabel_2 = new JLabel("Bouteldja Dorian | Martignac Damien");
        lblNewLabel_2.setForeground(Color.WHITE);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(10, 406, 764, 19);
        startPanel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Octobre 2023");
        lblNewLabel_3.setForeground(Color.WHITE);
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_3.setBounds(10, 436, 764, 14);
        startPanel.add(lblNewLabel_3);

        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // set type to decrypt
                type = false;
                changeTexts();
                cardLayout.show(mainPanel, "CryptoPanel");
            }
        });
        cryptoPanel.setBackground(new Color(64, 68, 75));
        mainPanel.add(cryptoPanel, "CryptoPanel");
        cryptoPanel.setLayout(null);

        JButton returnButton = new JButton("<- Menu");
        returnButton.setBackground(new Color(64, 68, 75));
        returnButton.setForeground(Color.WHITE);
        returnButton.setBounds(10, 35, 131, 23);
        returnButton.setBorder(BorderFactory.createLineBorder(Color.white));
        returnButton.setFocusPainted(false);
        returnButton.setContentAreaFilled(false);
        cryptoPanel.add(returnButton);

        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // clear text areas
                textArea1.setText("");
                textArea2.setText("");
                lblVerif.setText("");
                cardLayout.show(mainPanel, "StartPanel");
            }
        });

        lblText1 = new JLabel("Texte en clair :");
        lblText1.setForeground(Color.WHITE);
        lblText1.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblText1.setVerticalAlignment(SwingConstants.BOTTOM);
        lblText1.setBounds(43, 109, 167, 25);
        cryptoPanel.add(lblText1);
        lblText2 = new JLabel("Tetxe chiffré :");
        lblText2.setForeground(Color.WHITE);
        lblText2.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblText2.setBounds(50, 293, 173, 46);
        cryptoPanel.add(lblText2);
        actionButton = new JButton("Chiffrer");
        actionButton.setBounds(344, 403, 124, 23);
        actionButton.setForeground(Color.WHITE);
        actionButton.setBorder(BorderFactory.createLineBorder(Color.white));
        actionButton.setFocusPainted(false);
        actionButton.setContentAreaFilled(false);

        cryptoPanel.add(actionButton);

        textArea1 = new JTextArea();
        textArea1.setWrapStyleWord(true);
        textArea1.setBounds(265, 50, 300, 150);
        cryptoPanel.add(textArea1);

        textArea2 = new JTextArea();
        textArea2.setEditable(false);
        textArea2.setWrapStyleWord(true);
        textArea2.setBounds(265, 231, 300, 150);
        cryptoPanel.add(textArea2);

        JLabel lblNewLabel = new JLabel("OPTIONS :");
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setBounds(646, 30, 93, 32);
        cryptoPanel.add(lblNewLabel);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 218, 555, 2);
        cryptoPanel.add(separator);

        JCheckBox chckbxTripleDES = new JCheckBox("Triple DES");
        chckbxTripleDES.setForeground(Color.WHITE);
        chckbxTripleDES.setBackground(new Color(64, 68, 75));
        chckbxTripleDES.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                tripleDESBool = chckbxTripleDES.isSelected();
            }
        });
        chckbxTripleDES.setBounds(627, 85, 97, 23);
        cryptoPanel.add(chckbxTripleDES);

        JCheckBox chckbxEnleverBourrage = new JCheckBox("Retirer le bourrage");
        chckbxEnleverBourrage.setForeground(Color.WHITE);
        chckbxEnleverBourrage.setBackground(new Color(64, 68, 75));
        chckbxEnleverBourrage.setBounds(626, 111, 131, 23);
        chckbxEnleverBourrage.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                enleverBourrage = chckbxEnleverBourrage.isSelected();
            }
        });
        cryptoPanel.add(chckbxEnleverBourrage);

        lblVerif = new JLabel("");
        lblVerif.setForeground(Color.WHITE);
        lblVerif.setBounds(43, 436, 731, 14);
        cryptoPanel.add(lblVerif);
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 784, 22);
        cryptoPanel.add(menuBar);
        
        JMenu mnNewMenu = new JMenu("File");
        menuBar.add(mnNewMenu);
        
        JMenuItem MenuImport = new JMenuItem("Importer un Fichier");
        mnNewMenu.add(MenuImport);
        
        JMenuItem MenuExport = new JMenuItem("Exporter le Texte");
        mnNewMenu.add(MenuExport);

        MenuImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadFileMenuMouseClicked(evt);
            }
        });
        MenuExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileMenuMouseClicked(evt);
            }
        });

        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                des.retirerBourrage = enleverBourrage;
                tripleDES.retirerBourrage = enleverBourrage;

                if (type) {
                    if (tripleDESBool) {
                        // encrypt
                        String plaintext = textArea1.getText();
                        int[] ciphertext = tripleDES.encrypt(plaintext);
                        textArea2.setText(Arrays.toString(ciphertext));
                        lblVerif.setText(tripleDES.decrypt(ciphertext));
                    } else {
                        // encrypt
                        String plaintext = textArea1.getText();
                        int[] ciphertext = des.crypte(plaintext);
                        textArea2.setText(Arrays.toString(ciphertext));
                        lblVerif.setText("Verification : " + des.decrypte(ciphertext));
                    }
                } else {
                    // decrypt
                    if (tripleDESBool) {
                        String plaintext = textArea1.getText();
                        String ciphertext = tripleDES.decrypt(stringToIntArray(plaintext));
                        textArea2.setText(ciphertext);

                    } else {
                        String plaintext = textArea1.getText();
                        String ciphertext = des.decrypte(stringToIntArray(plaintext));
                        textArea2.setText(ciphertext);
                    }
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    private static void setupStartPanel() {
        GridBagConstraints gbcEncrypt = new GridBagConstraints();
        GridBagConstraints gbcDecrypt = new GridBagConstraints();

        JButton encryptButton = new JButton("Chiffrer");
        encryptButton.setBounds(326, 165, 150, 50);
        encryptButton.setForeground(Color.WHITE);
        encryptButton.setBorder(BorderFactory.createLineBorder(Color.white));
        encryptButton.setFocusPainted(false);
        encryptButton.setContentAreaFilled(false);


        gbcEncrypt.gridx = 0;
        gbcEncrypt.gridy = 0;
        gbcEncrypt.insets = new Insets(10, 0, 10, 0);
        startPanel.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("TP DES");
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(10, 63, 764, 50);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
        startPanel.add(lblNewLabel_1);
        startPanel.add(encryptButton);

        gbcDecrypt.gridx = 0;
        gbcDecrypt.gridy = 1;
        gbcDecrypt.insets = new Insets(10, 0, 10, 0);

        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // set type to encrypt
                type = true;
                changeTexts();
                cardLayout.show(mainPanel, "CryptoPanel");
            }
        });
    }

    private static void setupCryptoPanel() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridy = 2;

        gbc.gridy = 3;

        gbc.gridy = 4;

        gbc.gridy = 5;
    }

    // method to change labels texts and button text
    private static void changeTexts() {
        if (type) {
            // encrypt
            lblText1.setText("Texte en clair :");
            lblText2.setText("Texte chiffré :");
            actionButton.setText("Chiffrer");
        } else {
            // decrypt
            lblText1.setText("Texte chiffré :");
            lblText2.setText("Texte en clair :");
            actionButton.setText("Déchiffrer");
        }
    }

    //method to convert a string of 0 and 1 to an array of int
    public static int[] stringToIntArray(String s) {
        int[] result = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            result[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
        }
        return result;
    }

    private static void loadFileMenuMouseClicked(ActionEvent evt) {

        jFileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
        jFileChooser.addChoosableFileFilter(filter);
        int returnValue = jFileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            try {
                // Read the content of the selected file
                StringBuilder content = new StringBuilder();
                BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append('\n');
                }
                reader.close();

                // Set the content in a JTextArea
                textArea1.setText(content.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static void saveFileMenuMouseClicked(ActionEvent evt) {
        String text = textArea2.getText();

        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
        chooser.addChoosableFileFilter(filter);

        int result = chooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                FileWriter writer = new FileWriter(file);
                writer.write(text);
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
