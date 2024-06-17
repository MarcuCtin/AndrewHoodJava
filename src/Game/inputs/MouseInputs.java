package Game.inputs;

import Game.Main.GamePanel;
import Game.gamestates.Gamestate;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        switch(Gamestate.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch(Gamestate.state){
            case COMPLETED:
                gamePanel.getGame().getCompleted().mousePressed(e);

                break;
            case PAUSE:
                gamePanel.getGame().getPause().mousePressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
        }
    }

    @Override

public void mouseReleased(MouseEvent e) {
    switch(Gamestate.state){
        case COMPLETED:

            gamePanel.getGame().getCompleted().mouseReleased(e);
            break;
        case PAUSE:
            gamePanel.getGame().getPause().mouseReleased(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().mouseReleased(e);
            break;
        case MENU:
            gamePanel.getGame().getMenu().mouseReleased(e);
            break;
    }
}

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    switch(Gamestate.state){
        case COMPLETED:

            gamePanel.getGame().getCompleted().mouseMoved(e);
            break;
        case PAUSE:
            gamePanel.getGame().getPause().mouseMoved(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().mouseMoved(e);
            break;
        case MENU:
            gamePanel.getGame().getMenu().mouseMoved(e);
            break;
    }
}
}
