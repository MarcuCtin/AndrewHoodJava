package Game.Levels;

import Game.Main.Game;
import Game.UI.Coin;
import Game.entities.Ghoul;
import Game.entities.Skeleton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Game.utils.HelpMethods.*;

public class Level {
    private BufferedImage img;
    private ArrayList<Ghoul> ghouls;
    private ArrayList<Coin> coins;
    private ArrayList<Skeleton> skeletons;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private Point playerSpawn;
    private int maxLvlOffsetX ;
    private int[][] lvlData;
    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        loadCoins();
        calcLvlOffsets();
        calcPlayerSpawn();
    }
    private void loadCoins(){
        coins = GetCoins(img);
    }
    private void createLevelData(){
        lvlData = GetLevelData(img);
    }
    private void createEnemies(){
        ghouls = GetGhouls(img);
        skeletons = GetSkeletons(img);
    }
    private void calcPlayerSpawn(){
        playerSpawn = GetPlayerSpawn(img);
    }
    private void calcLvlOffsets(){
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide- Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE*maxTilesOffset;
    }
    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }
    public int[][] getLvlData() {
        return lvlData;
    }
    public int getLvlOffset(){
        return maxLvlOffsetX;
    }
    public ArrayList<Ghoul> getGhouls(){
        return ghouls;
    }
    public ArrayList<Skeleton> getSkeletons(){
        return skeletons;
    }
    public ArrayList<Coin> getCoins(){
        return coins;
    }

    public Point getPlayerSpawn(){
        return playerSpawn;
    }
}
