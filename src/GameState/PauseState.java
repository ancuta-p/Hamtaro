package GameState;

import com.company.Keys;

import java.awt.*;

public class PauseState extends  GameState{
    public PauseState(GameStateManager gsm) {
        super(gsm);
    }

    public void update() {
        handleInput();
    }

    public void draw(Graphics2D g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("Century Gothic",Font.BOLD,22));
        g.drawString( "PAUSED", 130, 80);

        g.setFont(new Font("Arial",Font.BOLD,12));
        g.drawString("arrow keys: move", 120, 120);
        g.drawString( "F1: return to menu", 120, 150);

    }
    public void handleInput() {
        if(Keys.isPressed(Keys.ESCAPE)) {
            gsm.setPaused(false);
        }
        if(Keys.isPressed(Keys.F1)) {
            gsm.setPaused(false);
            gsm.setState(gsm.MENU);
        }
    }
}
