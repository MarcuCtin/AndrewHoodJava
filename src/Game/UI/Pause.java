package Game.UI;

import Game.Main.Game;
import Game.gamestates.Gamestate;
import Game.gamestates.Playing;
import Game.gamestates.State;
import Game.gamestates.StateMethods;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Pause extends State implements StateMethods {
    BufferedImage bgImage;
    private int xPos;
    private int yPos;
    private float scaleMain=2f;
    private float optionsScale=0.6f;
    private final int pauseBgOffset = 17;
    Playing playing;
    Rectangle hitbox = new Rectangle(Game.GAME_WIDTH/2-100,Game.GAME_HEIGHT/2-100,200,200);

    private final ArrayList<PauseMenuOptionBtn>options = new ArrayList<>();
    public Pause(int xPos,int yPos,Game game, Playing playing){
        super(game);
        this.xPos = xPos;
        this.yPos = yPos;
        this.playing = playing;
        loadPauseBg();
        initBtns();
    }
    private void loadPauseBg(){
        bgImage = LoadSave.LoadImage(LoadSave.PAUSE_MENU_BG);
    }
    private void initBtns(){
        int yPosBtns = Game.GAME_HEIGHT / 2;
        options.add(new PauseMenuOptionBtn(xPos-80, yPosBtns,0, Gamestate.MENU,playing));
        options.add(new PauseMenuOptionBtn(xPos, yPosBtns,1, Gamestate.QUIT,playing));
        options.add(new PauseMenuOptionBtn(xPos+80, yPosBtns,3, Gamestate.PLAYING,playing));
        options.add(new PauseMenuOptionBtn(xPos+200, yPosBtns -200,2, Gamestate.PLAYING,playing));
        System.out.println("Options size: "+options.size());
    }

    public void update(){
        //System.out.println("Gamestate is at pause"+Gamestate.state);
        for(PauseMenuOptionBtn option:options){
            option.update();
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bgImage,xPos-bgImage.getWidth()+pauseBgOffset,yPos-bgImage.getHeight()/2,(int)(bgImage.getWidth()*scaleMain),(int)(bgImage.getHeight()*scaleMain),null);
        for(PauseMenuOptionBtn option:options){
            option.draw(g);
        }
    }

    public boolean mouseClicked(MouseEvent e) {
        if(hitbox.contains(e.getX(),e.getY()))
            System.out.println("Mouse clicked");
        return false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for(PauseMenuOptionBtn option:options){
            if(isMouseInOptions(e,option)){

                option.setMousePressed();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(PauseMenuOptionBtn option:options){
            if(isMouseInOptions(e,option) ){
                if(option.isMousePressed() && option.isMouseOver()) {

                    if (Gamestate.state == Gamestate.PLAYING)
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

    public void resetButtons() {
        for(PauseMenuOptionBtn option:options){
            option.resetBooleans();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(PauseMenuOptionBtn option:options){
            option.setMouseOver(false);
        }
        for(PauseMenuOptionBtn option:options){
            if(isMouseInOptions(e,option)){
                //System.out.println("Mouse over");
                option.setMouseOver(true);
                break;
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            System.out.println("Escape pressed");
            game.setPaused(false);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
