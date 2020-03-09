package GameState;

import Items.Player;
import com.company.GamePanel;
import com.company.Keys;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GameOverState extends GameState{
    private int rank;

    public GameOverState(GameStateManager gsm) {
        super(gsm);
        if(Player.ticks < 1800) rank = 1;
        else if(Player.ticks < 3600) rank = 2;
        else rank = 3;
    }

    public void update() { handleInput();}

    public void draw(Graphics2D g) {


        g.setColor(new Color(1,145,191));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        g.setColor(Color.ORANGE);
        g.setFont(new Font("Century Gothic",Font.PLAIN,12));
        g.drawString( "press F1 for MENU", 100, 190);
        g.setFont(new Font("Century Gothic",Font.BOLD,22));

        if(gsm.isDead()) {g.drawString("GAME OVER",90,80); return;}

        try{
            BufferedImage player= ImageIO.read(new File("res/victory.png"));
            g.drawImage(player,90,80,null);
        }
        catch(Exception e) {}

        if(gsm.isCompleted())   g.drawString( "GAME COMPLETED!!", 50, 80);
        else  g.drawString( "LEVEL COMPLETED", 50, 80);

        int minutes = (int) (Player.ticks / 1800);
        int seconds = (int) ((Player.ticks / 30) % 60);


        g.setFont(new Font("Century Gothic",Font.PLAIN,12));
        if(minutes < 10) {
            if(seconds < 10) g.drawString( "TIME: 0" + minutes + ":0" + seconds, 130, 100);
            else g.drawString( "TIME: 0" + minutes + ":" + seconds, 130, 100);
        }
        else {
            if(seconds < 10)g.drawString( "TIME: "+minutes + ":0" + seconds, 130, 100);
            else g.drawString("TIME: "+minutes + ":" + seconds, 130, 100);
        }

        if(rank == 1)  g.drawString( "RANK: advanced", 100, 130);
        else if(rank == 2) g.drawString("RANK: adventurer", 100, 130);
        else  g.drawString("RANK: beginner", 100, 130);

        if(!gsm.isCompleted()) g.drawString( "press ENTER for level 2", 90, 160);

    }

    public void handleInput() {
        if(!gsm.isCompleted() && Keys.isPressed(Keys.ENTER) && !gsm.isDead()){
            gsm.setState(gsm.LEV2);
        }
        if(Keys.isPressed(Keys.F1)) {
            gsm.setState(gsm.MENU);
        }
    }
}
