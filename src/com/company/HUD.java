package com.company;

import Items.Player;
import Items.Seed;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class HUD {

    private BufferedImage seed;
    private BufferedImage health;
    private Player player;
    private int numSeeds;

    public HUD(Player p, ArrayList<Seed> d) {

        player = p;
        numSeeds = d.size();

        try{
            seed= ImageIO.read(new File("res/seed.png"));
            health=ImageIO.read(new File("res/heart.png"));
        }
        catch(Exception e) {}
    }

    public void draw(Graphics2D g) {

        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.BLACK);

        g.drawString(player.numSeeds() + "/" + numSeeds, 20, 20);
        g.drawString(player.getHealth()+"/"+player.getMaxHealth(),20,40);
        g.drawImage(seed,4,6,null);
        g.drawImage(health,4,26,null);



        // draw time
        int min = (int) (Player.ticks/ 1800);
        int sec = (int) ((Player.ticks / 30) % 60);

        if(min < 10) {
            if(sec < 10) g.drawString("0" + min + ":0" + sec, 280, 20);
            else g.drawString("0" + min + ":" + sec, 280, 20);
        }
        else {
            if(sec < 10)g.drawString(min + ":0" + sec, 280, 20);
            else g.drawString(min + ":" + sec, 280, 20);
        }
    }
}
