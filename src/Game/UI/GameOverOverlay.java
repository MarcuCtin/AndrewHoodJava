package Game.UI;

import Game.Main.Game;
import Game.gamestates.Gamestate;
import Game.gamestates.Playing;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverOverlay {
    private Playing playing;
    public GameOverOverlay(Playing playing){
        this.playing = playing;
    }
    public void update(){

    }
    public void draw(Graphics g){
        g.setColor(new Color(0,0,0, 131));
        g.fillRect(0,0, Game.GAME_WIDTH,Game.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,50));
        g.drawString("Game Over",Game.GAME_WIDTH/2-150,Game.GAME_HEIGHT/2);
        g.drawString("Press ESC to enter Main Menu",Game.GAME_WIDTH/2-300,Game.GAME_HEIGHT/2+100);
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
            playing.getLevelManager().setLvlIndex(0);

        }

    }

}
