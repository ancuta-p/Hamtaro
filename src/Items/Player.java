package Items;

import GameState.GameStateManager;
import Map.TileMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Player{

    private BufferedImage [] idleSprites;
    private BufferedImage[] walkingSprites;
    private BufferedImage[] leftSprites;
    private BufferedImage[] rightSprites;
    private BufferedImage[] upSprites;


    // animation
    private final int IDLE=4;
    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private final int UP = 3;

    private int numSeeds;
    private int totalSeeds;

    public static long ticks;

    //---------
    private int health;
    private int maxHealth;
    private boolean flinching;
    private long flinchTimer;
    //----------
    // dimensions
    protected int width;
    protected int height;
    protected int cwidth;
    protected int cheight;

    // position
    protected int x;
    protected int y;
    protected int xdest;
    protected int ydest;
    protected int rowTile;
    protected int colTile;

    // movement
    protected boolean moving;
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;

    // attributes
    protected int moveSpeed;

    // tilemap
    protected TileMap tileMap;
    protected int tileSize;
    protected int xmap;
    protected int ymap;

    // animation
    protected Animation animation;
    protected int currentAnimation;

    public Player(TileMap tm) {

        tileMap = tm;
        tileSize = tileMap.getTileSize();
        animation = new Animation();

        width = 16;
        height = 16;
        cwidth = 12;
        cheight = 12;

        moveSpeed = 2;
        numSeeds = 0;
        health = maxHealth = 5;


        try{
          BufferedImage image;

          idleSprites=new BufferedImage[1];
          idleSprites[0]=ImageIO.read(new File("res/idle.png"));

          image =ImageIO.read(new File("res/down.png"));
          walkingSprites=new BufferedImage[2];
          walkingSprites[0]=image.getSubimage(0,0,width+1,height+1);
          walkingSprites[1]=image.getSubimage(width+1,0,width+1,height+1);

          image=ImageIO.read(new File("res/left.png"));
          leftSprites=new BufferedImage[4];
          for(int i=0;i<4;i++)
              leftSprites[i]=image.getSubimage(i*(width+1),0,width+1,height+1);

          image=ImageIO.read(new File("res/right.png"));
          rightSprites=new BufferedImage[4];
           for(int i=0;i<4;i++)
               rightSprites[i]=image.getSubimage(i*(width+1),0,width+1,height+1);

           image=ImageIO.read(new File("res/up.png"));
           upSprites=new BufferedImage[2];
           upSprites[0]=image.getSubimage(0,0,width+1,height+1);
           upSprites[1]=image.getSubimage(width+1,0,width+1,height+1);

       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

        animation.setFrames(idleSprites);
        animation.setDelay(-1);

    }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public void Damaged(GameStateManager gsm){
        if(flinching) return;
        health --;
        if(health < 0) health = 0;
        flinching = true;
        flinchTimer = System.nanoTime();
        if(health==0)
        { gsm.setState(gsm.DEAD); }

    }
    public int getx() { return x; }
    public int gety() { return y; }

    public void setTilePosition(int i1, int i2) {
        y = i1 * tileSize + tileSize / 2;
        x = i2 * tileSize + tileSize / 2;
        xdest = x;
        ydest = y;
    }

    private void setAnimation(int i, BufferedImage[] bi, int d) {
        currentAnimation = i;
        animation.setFrames(bi);
        animation.setDelay(d);
    }

    public void collectedSeeds() { numSeeds++; }
    public int numSeeds() { return numSeeds; }
    public int getTotalSeeds() { return totalSeeds; }
    public void setTotalSeeds(int i) { totalSeeds = i; }

    // Keyboard input. Moves the player.
    public void setDown() {
        if(moving) return;
        down = true;
        moving = validateNextPosition();
    }
    public void setLeft() {
        if(moving) return;
        left = true;
        moving = validateNextPosition();
    }
    public void setRight() {
        if(moving) return;
        right = true;
        moving = validateNextPosition();
    }
    public void setUp() {
        if(moving) return;
        up = true;
        moving = validateNextPosition();
    }

    public boolean intersects(Seed o) {
        Rectangle r=getRectangle();
        return r.intersects(o.getRectangle());
    }

    public boolean intersects(Enemy o) {
        Rectangle r=getRectangle();
        return r.intersects(o.getRectangle());
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, cwidth, cheight);
    }


    public boolean validateNextPosition() {

        if(moving) return true;

        rowTile = y / tileSize; //pt a afla pe ce tile este playerul, (x,y) poz curenta
        colTile = x / tileSize;

        if(left) {
            if(colTile == 0 || tileMap.isBlocked(rowTile, colTile - 1)) return false; //colTile-1, daca urm tile e blocat return false;
            else xdest = x - tileSize;

        }
        if(right) {
            if(colTile == tileMap.getNumCols()-1 || tileMap.isBlocked(rowTile, colTile + 1)) return false;
            else xdest = x + tileSize;

        }
        if(up) {
            if(rowTile == 0 || tileMap.isBlocked(rowTile - 1, colTile)) return false;

            else ydest = y - tileSize;
        }
        if(down) {
            if(rowTile == tileMap.getNumRows() - 1 || tileMap.isBlocked(rowTile + 1, colTile)) return false;
            else ydest = y + tileSize;
        }
        return true;

    }

    public void getNextPosition() {

        if(left && x > xdest) x -= moveSpeed;
        else left = false;
        if(left && x < xdest) x = xdest;

        if(right && x < xdest) x += moveSpeed;
        else right = false;
        if(right && x > xdest) x = xdest;

        if(up && y > ydest) y -= moveSpeed;
        else up = false;
        if(up && y < ydest) y = ydest;

        if(down && y < ydest) y += moveSpeed;
        else down = false;
        if(down && y > ydest) y = ydest;

    }

    public void update() {

        ticks++;
        if(flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed > 1000) {
                flinching = false;
            }
        }
        // set animation
        if(!moving) setAnimation(IDLE,idleSprites,-1);
        if(down) {
            if(currentAnimation != DOWN) {
                setAnimation(DOWN, walkingSprites, 10);
            }
        }
        if(up) {
            if(currentAnimation != UP) {
                setAnimation(UP, upSprites, 10);
            }
        }
        if(left) {

            if(currentAnimation != LEFT) {
                setAnimation(LEFT, leftSprites, 10);
            }
        }
        if(right) {
             if(currentAnimation != RIGHT) {
                setAnimation(RIGHT, rightSprites, 10);
            }
        }

        // get next position
        if(moving) getNextPosition();

        // check stop moving
        if(x == xdest && y == ydest) {
            left = right = up = down = moving = false;
            rowTile = y / tileSize;
            colTile = x / tileSize;
        }

        // update animation
        animation.update();

    }

    public void draw(Graphics2D g) {
        if(flinching) {
            long elapsed =
                    (System.nanoTime() - flinchTimer) / 1000000;
            if(elapsed / 100 % 2 == 0) {
                return;
            }
        }

        xmap = tileMap.getx();
        ymap = tileMap.gety();
        g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);
    }
}
