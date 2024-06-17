package Game.Main;
import Game.inputs.KeyboardInputs;
import Game.inputs.MouseInputs;
import javax.swing.*;
import java.awt.*;

import static Game.Main.Game.GAME_HEIGHT;
import static Game.Main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;
    public GamePanel(Game game) {
        this.game = game;
        mouseInputs = new MouseInputs(this);
        setPanelSize();
        //addMouseListener(game.getPause());

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);

    }
    public void updateGame(){

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        game.render(g);
    }
    public Game getGame() {
        return game;
    }


}
