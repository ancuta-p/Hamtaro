package com.company;

import GameState.GameStateManager;
import Items.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
//animatia este pusa intr-un tread local al JPanel
//bucla contine: game update, randare si un timp de sleep
public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int WIDTH = 320*1;
    public static final int HEIGHT = 240*1;
    public static final int SCALE = 2;

    private Thread thread;
    private boolean running;
    private final int FPS = 45;
    private final int time = 1000 / FPS;

    private BufferedImage image;
    private Graphics2D g;

    private GameStateManager gsm;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus(); //ex: pt keylistener
    }

    // ready to display
    public void addNotify(){ //este automat apelata
        /* Wait for the JPanel to be added to the
 JFrame/JApplet before starting. */
        super.addNotify();
        if(thread == null) {//incepe threadul{
            addKeyListener(this);
            thread = new Thread(this);
            thread.start();
        }
    }

    // run new thread
    public void run() {
        // initializes fields
        running = true;
        image = new BufferedImage(WIDTH,HEIGHT, 1);
        g = image.createGraphics(); //face un graphics care deseneaza in BuffererdImage
        gsm = new GameStateManager();

        long start;
        long elapsed;
        long wait;

        // game loop
        while(running) {

            start = System.nanoTime();
            System.out.println(Player.ticks+" ");
            gsm.update();
            Keys.update();

            gsm.draw(g); //draws game

            // copy buffer to screen, double buffering
            Graphics2D g2 =(Graphics2D)getGraphics();
            g2.drawImage(image, 0, 0, WIDTH *SCALE, HEIGHT *SCALE, null);
            g2.dispose(); //sterge

            elapsed = System.nanoTime() - start;

            wait = time - elapsed / 1000000; //ms
            if(wait < 0) wait = time;

            try {
                Thread.sleep(wait);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void keyTyped(KeyEvent key) {}
    public void keyPressed(KeyEvent key) { Keys.keySet(key.getKeyCode(), true); }
    public void keyReleased(KeyEvent key) { Keys.keySet(key.getKeyCode(), false); }
}
