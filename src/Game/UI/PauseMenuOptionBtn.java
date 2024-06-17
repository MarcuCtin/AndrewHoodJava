package Game.UI;

import Game.Database.DatabaseHandler;
import Game.gamestates.Gamestate;
import Game.gamestates.Playing;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PauseMenuOptionBtn {
    private int x, y;
    private final float scale = 0.5f;

    private boolean mouseOver, mousePressed;
    private Rectangle btnHitbox;
    Gamestate state;
    BufferedImage img;
    Playing playing;
    private final int type;
    PauseMenuOptionBtn(int x, int y, int type, Gamestate state, Playing playing) {
        this.playing = playing;
        this.state = state;
        this.type = type;
        this.x = x;
        this.y = y;
        loadImg(type);
        initBtnHitbox();
    }
    private void setGameState(Gamestate state){
        this.state = state;
    }
    private void loadImg(int type){
        switch (type){
            case 0:

                img = LoadSave.LoadImage(LoadSave.HOME_BTN);
                break;
            case 1:

                img = LoadSave.LoadImage(LoadSave.SETTINGS_BTN);
                break;
            case 2:

                img = LoadSave.LoadImage(LoadSave.RESUME_BTN);
                break;
            case 3:

                img = LoadSave.LoadImage(LoadSave.RESTART_BTN);
                break;
            case 4:
                img = LoadSave.LoadImage(LoadSave.NEXTLVL_BTN);
                break;

        }
    }
    private void initBtnHitbox(){
        if(type==2){
            btnHitbox = new Rectangle(x-50,y+50,(int)(img.getWidth()*scale-30),(int)(img.getHeight()*scale-30));
        }
       else  btnHitbox = new Rectangle(x,y,(int)(img.getWidth()*scale),(int)(img.getHeight()*scale));

    }
    void update(){
        if(mouseOver){
            if(mousePressed){
                applyGameState();
                
                if(type==1){
                    DatabaseHandler.saveCurrentLevel(playing.getLevelIndex());
                }
                if(type==4){
                    playing.loadNextLevel();
                    playing.setLevelCompletedStatus(false);
                }
                if(type == 3) {
                    playing.resetAll();
                    playing.setPaused(false);
                }
                if(Gamestate.state == Gamestate.PLAYING)
                    playing.setPaused(false);
            }
        }
    }
    protected void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
    protected boolean isMouseOver() {
        return mouseOver;
    }
   public boolean isMousePressed() {
        return mousePressed;
    }
    protected void setMousePressed() {
        this.mousePressed = true;
    }
    protected void resetBooleans(){
        mouseOver = false;
        mousePressed = false;
    }

    protected void draw(Graphics g) {
        if(type==2)
            g.drawImage(img, x-50, y+50, (int) (img.getWidth() * scale-30), (int) (img.getHeight() * scale-30), null);
        else
            g.drawImage(img, x-17, y-20, (int) (img.getWidth() * scale), (int) (img.getHeight() * scale), null);
    }
    public void applyGameState() {
        Gamestate.state = state;
    }
    public int getType (){
        return type;
    }

    public Rectangle getBtnHitbox() {
        return btnHitbox;
    }
}
