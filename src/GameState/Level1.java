package GameState;

import Items.Enemy;
import Items.Player;
import Items.Seed;
import Map.TileMap;
import com.company.GamePanel;
import com.company.HUD;
import com.company.Keys;

import java.awt.*;
import java.util.ArrayList;

public class Level1 extends GameState {
    private Player player;
    private TileMap tileMap;
    private ArrayList<Seed> seeds;
    private ArrayList<Enemy> enemy;
    private HUD hud;


    public Level1(GameStateManager gsm) {
        super(gsm);

        Player.ticks = 0;
        seeds = new ArrayList<>();
        enemy=new ArrayList<>();
        tileMap = new TileMap(16);

        tileMap.loadTiles("res/tiles1.png");
        tileMap.loadMap("res/level1map.txt");

        addSeeds();
        addEnemy();

        player = new Player(tileMap);
        player.setTilePosition(11, 18);
        player.setTotalSeeds(seeds.size());

        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());
        //tileMap.setBounds(tileMap.getWidth() - 1 * tileMap.getTileSize(), tileMap.getHeight() - 2 * tileMap.getTileSize(), 0, 0);

        hud = new HUD(player, seeds);
    }

    private void addSeeds() {

        Seed d;

        d = new Seed(tileMap);
        d.setTilePosition(15, 8);
        seeds.add(d);
       d = new Seed(tileMap);
        d.setTilePosition(9, 1);
        d.addChange(new int[]{12, 2,1});
        d.addChange(new int[]{12, 1,1});
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(1, 13);
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(38, 4);
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(28, 6);
        d.addChange(new int[]{6, 32,1});
        d.addChange(new int[]{6, 31,1});
        d.addChange(new int[]{5, 34,1});
        seeds.add(d);

        d = new Seed(tileMap);
        d.setTilePosition(13, 18);
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(21, 27);
        d.addChange(new int[]{31, 23,1});
        seeds.add(d);

        d = new Seed(tileMap);
        d.setTilePosition(37, 18);
        seeds.add(d);

        d = new Seed(tileMap);
        d.setTilePosition(25, 25);
        d.addChange(new int[]{31, 28,1});
        d.addChange(new int[]{31, 29,1});
        seeds.add(d);

        d = new Seed(tileMap);
        d.setTilePosition(25, 1);
        seeds.add(d);


    }

    private void addEnemy()
    {
        Enemy e;
        e=new Enemy(tileMap);
        e.setTilePosition(34,4);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(2,30);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(18,28);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(35,20);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(6,8);
        enemy.add(e);
    }
    public void update() {

        handleInput();

        if (player.numSeeds() == player.getTotalSeeds()) {
            gsm.setState(gsm.GAMEOVER); return;
        }

        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety()); //update camera
        player.update();

        for (int i = 0; i < seeds.size(); i++) {

            Seed d = seeds.get(i);
            // player collects diamond
            if (player.intersects(d)) {
                seeds.remove(i);
                i--;
                player.collectedSeeds();// increment amount of collected diamonds
                // make any changes to tile map
                ArrayList<int[]> aux = d.getChanges();
                for (int[] j : aux) {
                    tileMap.setTile(j[0], j[1], 1);
                }
            }
        }
            for(int i=0;i<enemy.size();i++)
            {
                Enemy e=enemy.get(i);
                if(player.intersects(e)){
                    player.Damaged(gsm);
                }
            }
    }

    public void draw(Graphics2D g) {

        tileMap.draw(g);
        player.draw(g);
        for (Seed s : seeds) {
            s.draw(g);
        }
        for(Enemy e:enemy)
        {
            e.draw(g);
        }
        hud.draw(g);
    }

    public void handleInput() {
        if (Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
        if (Keys.isDown(Keys.LEFT)) player.setLeft();
        if (Keys.isDown(Keys.RIGHT)) player.setRight();
        if (Keys.isDown(Keys.UP)) player.setUp();
        if (Keys.isDown(Keys.DOWN)) player.setDown();
    }
}
