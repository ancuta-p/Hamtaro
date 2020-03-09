package GameState;

import com.company.Keys;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MenuState extends GameState {
    private BufferedImage bg;

    private int currentOption;
    private String[] options = {"START", "QUIT"};

    public MenuState(GameStateManager gsm) {

        super(gsm);
        try{
            bg=ImageIO.read(new File("res/background.png"));
        }
        catch(Exception e) { e.printStackTrace(); }
    }


    public void update() {
        handleInput();
    }

    public void draw(Graphics2D g) {

        g.drawImage(bg, 0, 0, null);
        g.setFont(new Font("Century Gothic",Font.BOLD,18));
        for(int i = 0; i < options.length; i++) {
            if(i == currentOption) {
                g.setColor(Color.ORANGE);
            }
            else {
                g.setColor(Color.DARK_GRAY);
            }
            g.drawString(options[i], 20, 100 + i * 25);
        }
    }

    public void handleInput() {
        if(Keys.isPressed(Keys.DOWN) ) {
            currentOption=1;
        }
        if(Keys.isPressed(Keys.UP) ) {
            currentOption=0;
        }
        if(Keys.isPressed(Keys.ENTER)) {
            if(currentOption == 0) {
                gsm.setState(gsm.LEV1);
            }
            if(currentOption == 1) {
                System.exit(0);
            }
        }
    }
}
