package Game.UI;

import Game.Main.Game;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Coin {
    private int coin;
    int x,y;
    BufferedImage img;
    private Rectangle2D.Float hitbox;
    private boolean isCollected=false;
    public Coin(int x,int y){
        this.x = x;
        this.y = y;
        img = LoadSave.LoadImage(LoadSave.COIN);
        initHitbox();

    }
    private void initHitbox(){
        hitbox = new Rectangle2D.Float(x,y,20* Game.scale,20*Game.scale);
    }
    public void draw(Graphics g, int lvlOffset){
        g.drawImage(img,x-lvlOffset,y, (int) (20* Game.scale), (int) (20*Game.scale),null);
        //g.setColor(Color.RED);
        //g.drawRect((int)hitbox.getX()-lvlOffset,(int)hitbox.getY(),(int)hitbox.getWidth(),(int)hitbox.getHeight());
    }
    public void setCollected(boolean isCollected){
        this.isCollected = isCollected;
    }
    public boolean isCollected(){
        return isCollected;
    }
    public void updateCoin(int coin){
        this.coin = coin;
    }
    public void update(){

    }
    public void setMaxCoin(int coin){
        this.coin = coin;
    }
    public void reset(){
        coin = 0;
    }
    public void addCoin(int coin){
        this.coin += coin;
    }

    public int getCoin(){
        return coin;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}
