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

public class Level2 extends GameState {
    private Player player;

    private TileMap tileMap;
    private ArrayList<Seed> seeds;
    private ArrayList<Enemy> enemy;

    private HUD hud;

    public Level2(GameStateManager gsm) {
        super(gsm);
        Player.ticks=0;
        seeds = new ArrayList<>();
        enemy = new ArrayList<>();
        // load map
        tileMap = new TileMap(16);
        tileMap.loadTiles("res/tiles2.png");
        tileMap.loadMap("res/level2map.txt");

        player = new Player(tileMap);

        addSeeds();
        addEnemy();

        player.setTilePosition(17, 17); //initializare player
        player.setTotalSeeds(seeds.size());

        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety());
        hud = new HUD(player, seeds);
    }


    private void addSeeds() {

        Seed d;

        d = new Seed(tileMap);
        d.setTilePosition(36, 12);
        seeds.add(d);
       d = new Seed(tileMap);
        d.setTilePosition(4, 21);
        d.addChange(new int[]{27,9});
        d.addChange(new int[] {28, 7});
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(37,31);
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(30, 20);
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(25, 14);
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(21, 4);
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(14, 9);
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(3, 4);
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(14, 20);
        d.addChange(new int[]{19,24});
        d.addChange(new int[]{19,23});
        seeds.add(d);
        d = new Seed(tileMap);
        d.setTilePosition(20, 13);
        seeds.add(d);

    }

    private void addEnemy()
    {
        Enemy e;

        for(int i=0;i<5;i++)
        { e=new Enemy(tileMap);
            e.setTilePosition(17+i,28);
            enemy.add(e);}

        e=new Enemy(tileMap);
        e.setTilePosition(17,30);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(38,26);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(38,25);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(6,28);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(5,28);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(5,29);
        enemy.add(e);
        e=new Enemy(tileMap);
        e.setTilePosition(6,29);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(29,18);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(29,15);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(31,13);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(3,6);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(4,6);
        enemy.add(e);

        e=new Enemy(tileMap);
        e.setTilePosition(18,6);
        enemy.add(e);

    }
    public void update() {

        handleInput();

        if(player.numSeeds() == player.getTotalSeeds()) {
            gsm.setState(gsm.GAMEOVER); return;
    }
        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getx(), GamePanel.HEIGHT / 2 - player.gety()); //update camera
        player.update();

        // update diamonds
        for(int i = 0; i < seeds.size(); i++) {

            Seed d = seeds.get(i);
            // player collects diamond
            if(player.intersects(d)) {
                seeds.remove(i);
                i--;
                player.collectedSeeds();
                ArrayList<int[]> aux = d.getChanges();
                for(int[] j : aux) {
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
        for(Seed s : seeds) {
            s.draw(g);
        }
        for(Enemy e:enemy)
        {
            e.draw(g);
        }
        hud.draw(g);
    }

    public void handleInput() {
        if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
        if(Keys.isDown(Keys.LEFT)) player.setLeft();
        if(Keys.isDown(Keys.RIGHT)) player.setRight();
        if(Keys.isDown(Keys.UP)) player.setUp();
        if(Keys.isDown(Keys.DOWN)) player.setDown();
    }

}
