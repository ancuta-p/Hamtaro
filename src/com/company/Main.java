package com.company;

import Items.Player;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Hamtaro");

        window.add(new GamePanel());

        window.setResizable(false);
        window.pack(); //le impacheteaza in functie de dimensiunea aleasa

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
