package Game.UI;

import Game.utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Star {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private BufferedImage img;

    public Star(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        img = LoadSave.LoadImage(LoadSave.STAR);

    }
    public void draw(Graphics g){
        g.drawImage(img,x,y,width,height,null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
