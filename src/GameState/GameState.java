package GameState;

import java.awt.*;

public abstract class GameState {

    protected GameStateManager gsm;

    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }


    public abstract void update();
    public abstract void draw(Graphics2D g);
    public abstract void handleInput();
}
