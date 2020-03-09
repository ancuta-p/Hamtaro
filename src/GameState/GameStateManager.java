package GameState;

import java.awt.*;

public class GameStateManager {
    private boolean paused;
    private PauseState pauseState;

    private GameState[] gameStates;
    private int currentState;
    private int previousState;


    public  final int MENU = 0;
    public  final int LEV1 = 1;
    public final int LEV2=2;
    public final int GAMEOVER = 3;
    public final int DEAD=4;

    public  final int NUM_STATES = 5;

    public GameStateManager() {
        paused = false;
        pauseState = new PauseState(this);
        gameStates = new GameState[NUM_STATES];
        setState(MENU);
    }

    public void setState(int i) {
        previousState = currentState;
        gameStates[i] = null; //unload state
        currentState = i;
        switch (i)
        {
            case MENU: {gameStates[i] = new MenuState(this); gameStates[4]=null; break;}
            case LEV1: {gameStates[i] = new Level1(this); gameStates[2]=null;break;}
            case LEV2: {gameStates[i] = new Level2(this); gameStates[1]=null;break;}
            case GAMEOVER: gameStates[i] = new GameOverState(this); break;
            case DEAD: gameStates[i] = new GameOverState(this); break;
        }
    }

    public void setPaused(boolean b) {
        paused = b;
    }

    public boolean isCompleted()
    {
        return (gameStates[2]!=null);
    }

    public boolean isDead()
    {
        return (gameStates[4]!=null);
    }

    public void update() {
        if(paused) {
            pauseState.update();
        }
        else if(gameStates[currentState] != null) {
            gameStates[currentState].update();
        }
    }

    public void draw(Graphics2D g) {
        if(paused) {
            pauseState.draw(g);
        }
        else if(gameStates[currentState] != null) {
            gameStates[currentState].draw(g);
        }
    }
}
