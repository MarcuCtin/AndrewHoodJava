package Game.UI;

import Game.gamestates.Gamestate;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PauseBtn {
    private Rectangle pauseHitbox;
    private BufferedImage pauseImg;
    private Gamestate state;
    private int x,y;
    private int width = 50;
    private int height = 50;
    private int index=0;
    protected boolean mouseOver,mousePressed;

    public PauseBtn(int x, int y, Gamestate state){
        this.x = x;
        this.y = y;
        this.state = state;
        initPauseHitbox();
        loadPauseImg();
    }
    private void initPauseHitbox(){
        pauseHitbox = new Rectangle(x,y,width,height);
    }
    private void loadPauseImg(){
        pauseImg = LoadSave.LoadImage(LoadSave.PAUSE_BTN);
    }
    public void update(){
        if(mouseOver){
            index = 1;
            if(mousePressed){
                index = 2;
            }
        }
    }
    public void draw(Graphics g){
        g.drawImage(pauseImg,x,y,width,height,null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
    public void applyGameState(){
        Gamestate.state = state;
    }
    public void resetBooleans(){
        mouseOver = false;
        mousePressed = false;
    }
    public Rectangle getPauseHitbox() {
        return pauseHitbox;
    }
}
