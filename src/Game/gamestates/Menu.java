package Game.gamestates;

import Game.Main.Game;
import Game.UI.MenuButton;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods {
    private  MenuButton[] buttons = new MenuButton[3];
    private BufferedImage menuBgImage;
    private BufferedImage BgImage;
    private int menuX,menuY,menuWidth,menuHeight;

    public Menu(Game game) {
        super(game);
        loadPauseBackground();
        loadPauseMenuBackground();
        loadButtons();
    }
    private void loadButtons(){
        buttons[0] = new MenuButton(Game.GAME_WIDTH/2,Game.GAME_HEIGHT/2-140,0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH/2,Game.GAME_HEIGHT/2-70,1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH/2,Game.GAME_HEIGHT/2,2,Gamestate.QUIT);
    }
    private void loadPauseBackground(){
        BgImage = LoadSave.LoadImage(LoadSave.PAUSE_BG);
    }
    private void loadPauseMenuBackground(){
        menuBgImage = LoadSave.LoadImage(LoadSave.MAIN_MENU_BG);
        menuWidth = (int)(menuBgImage.getWidth()*Game.scale);
        menuHeight = (int)(menuBgImage.getHeight()*Game.scale);
        menuX = (Game.GAME_WIDTH-menuWidth)/2;
        menuY = (int)(50*Game.scale);
    }
    @Override
    public void update() {
        //System.out.println("Gamestate is at menu"+Gamestate.state);
        for(MenuButton button:buttons){
            button.update();
        }
    }

    @Override
    public void render(Graphics g) {
        //g.drawImage(BgImage,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
         g.drawImage(menuBgImage,menuX,menuY,menuWidth,menuHeight,null);
        for(MenuButton button:buttons){
            button.draw(g);
        }


    }

    @Override
    public boolean mouseClicked(MouseEvent e) {

        return false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton button : buttons) {
            if(isMouseIn(e,button)){
                button.setMousePressed(true);
            break;}
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton button : buttons) {
            if(isMouseIn(e,button)){
                if(button.isMousePressed())
                    button.applyGamestate();
                    if(Gamestate.state == Gamestate.PLAYING) {
                        game.getPlaying().setPaused(false);
                    }
                break;
            }
        }
        resetButtons();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void resetButtons() {
        for(MenuButton button:buttons){
            button.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton button : buttons)
            button.setMouseOver(false);
        for (MenuButton mb : buttons) {
            if (isMouseIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.PLAYING;

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
