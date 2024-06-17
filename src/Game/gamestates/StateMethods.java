package Game.gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface StateMethods {
    public void update();
    public void render(Graphics g);
    public boolean mouseClicked(MouseEvent e);
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);

    void mouseEntered(MouseEvent e);

    void mouseExited(MouseEvent e);

    public void mouseMoved(MouseEvent e);
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
}
