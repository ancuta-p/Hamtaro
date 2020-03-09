package Items;

import Map.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Enemy {
    private BufferedImage[] seedSprites;
    protected Animation animation;
    private ArrayList<int[]> tileChanges;
    protected TileMap tileMap;
    protected int tileSize;
    protected int xmap;
    protected int ymap;

    protected int width;
    protected int height;
    protected int cwidth;
    protected int cheight;

    protected int x;
    protected int y;

    public Enemy(TileMap tm) {

        tileMap = tm;
        tileSize = tileMap.getTileSize();
        animation = new Animation();

        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;
        try{
            seedSprites=new BufferedImage[1];
            seedSprites[0]= ImageIO.read(new File("res/snake.png"));
        }
        catch(Exception e) {e.printStackTrace();}
        animation.setFrames(seedSprites);

        tileChanges = new ArrayList<>();

    }
    public Rectangle getRectangle() {
        return new Rectangle(x, y, cwidth, cheight);
    }

    public void update() {
        animation.update();
    }

    public void setTilePosition(int i1, int i2) {
        y = i2 * tileSize + tileSize / 2;
        x = i1 * tileSize + tileSize / 2;
    }

    public void draw(Graphics2D g) {
        xmap = tileMap.getx();
        ymap = tileMap.gety();
        g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);
    }
}
