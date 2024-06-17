package Game.UI;

import Game.gamestates.Gamestate;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Game.utils.constants.UI.Buttons.*;

public class MenuButton {
    private int xPos,yPos,rowIndex,index;
    private int xOffsetCenter = B_WIDTH/2;
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver=false,mousePressed=false;
    private Rectangle btnHitbox;
    public MenuButton(int xPos,int yPos,int rowIndex,Gamestate state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBtnHitbox();
    }
    private void initBtnHitbox(){
        btnHitbox = new Rectangle(xPos-xOffsetCenter,yPos,B_WIDTH,B_HEIGHT);
    }

    private void loadImgs(){
        imgs = new BufferedImage[3];
        BufferedImage sprites = LoadSave.LoadImage(LoadSave.MENU_BUTTONS);
        for(int i = 0; i < imgs.length; i++){
            imgs[i] = sprites.getSubimage(i*B_WIDTH_DEFAULT,rowIndex*B_HEIGHT_DEFAULT , B_WIDTH_DEFAULT , B_HEIGHT_DEFAULT);
        }
    }
    public void draw(Graphics g){
        g.drawImage(imgs[index],xPos-xOffsetCenter,yPos,B_WIDTH,B_HEIGHT,null);

    }
    public void update(){
        index =0;
        if(mouseOver){
            index = 1;
            if(mousePressed){
                index = 2;
            }
        }
    }
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
    public void applyGamestate(){
        Gamestate.state = state;
    }
    public void resetBooleans(){
        mouseOver = false;
        mousePressed = false;
    }
    public Rectangle getBtnHitbox() {
        return btnHitbox;
    }

}
