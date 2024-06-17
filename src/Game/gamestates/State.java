package Game.gamestates;

import Game.Main.Game;
import Game.UI.MenuButton;
import Game.UI.PauseBtn;
import Game.UI.PauseMenuOptionBtn;

import java.awt.event.MouseEvent;

public class State {
    protected Game game;
    public State(Game game){
        this.game=game;

    }
    public boolean isMouseInOptions(MouseEvent e, PauseMenuOptionBtn mb){
        //System.out.println("Mouse in options");
        //System.out.println(mb.getBtnHitbox());
        return mb.getBtnHitbox().contains(e.getX(),e.getY());
    }
    public boolean isMouseIn(MouseEvent e, MenuButton mb){
        return mb.getBtnHitbox().contains(e.getX(),e.getY());
    }
    public boolean isMouseInPause(MouseEvent e, PauseBtn mb){
        return mb.getPauseHitbox().contains(e.getX(),e.getY());
    }

    public Game getGame(){
        return game;
    }
}
