package Game.utils;

import Game.Main.Game;
import Game.UI.Coin;
import Game.entities.Ghoul;
import Game.entities.Skeleton;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Game.utils.constants.EnemyConstants.GHOUL;
import static Game.utils.constants.EnemyConstants.SKELETON;

public class HelpMethods {
    public static boolean CanMoveHere(float x,float y,float width,float height,int[][] lvlData){
        if(!IsSolid((int)x,(int)y,lvlData) && !IsSolid((int)(x+width),(int)y,lvlData) && !IsSolid((int)x,(int)(y+height),lvlData) && !IsSolid((int)(x+width),(int)(y+height),lvlData))
            return true;
        return false;
    }
    private static boolean IsSolid(float x,float y,int[][] lvlData){
        int maxWidth = lvlData[0].length*Game.TILES_SIZE;
        //System.out.println(maxWidth + "maxWidthswd"+lvlData[0].length+" levelData"+Game.TILES_SIZE);
        if(x<0 || x>= maxWidth)
            return true;
        if(y<0 || y>= Game.GAME_HEIGHT)
            return true;
        float xIndex =  x /Game.TILES_SIZE;
        float yIndex =  y /Game.TILES_SIZE;
        return isTileSolid((int)xIndex,(int)yIndex,lvlData);
    }
    public static boolean isTileSolid(int xTile,int yTile,int[][] lvlData){
        int value = lvlData[yTile][xTile];
        return value != 13;
    }
    private static boolean aboveAndBelowClear(int tileXpos,int tileYpos,int[][] lvlData){
       if(tileXpos>=0 && tileYpos >=0 && tileXpos<Game.TILES_IN_WIDTH)
       {
           int aboveIndex = lvlData[tileYpos-1][tileXpos];
           int belowIndex = lvlData[tileYpos+1][tileXpos];
           //System.out.println("aboveIndex "+aboveIndex+" belowIndex "+belowIndex);
           return aboveIndex == 13 && belowIndex == 13;

       }
       return false;
    }
    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed,int[][] lvlData){
        int currentTileXpos = (int)(hitbox.x/Game.TILES_SIZE);
        int currentTileYpos =(int)((hitbox.y+hitbox.height)/Game.TILES_SIZE);
        //+hitbox.height cuz height > Game.Tiles_Size
        if(xSpeed>0) {
            //right
            int nextTileXpos = currentTileXpos +1;
            int xOffSet = (int)(Game.TILES_SIZE - hitbox.width);

            int tileXPos = currentTileXpos * Game.TILES_SIZE;
             // diff btwn the width of the tile and player hitbox
            //System.out.println("nextTileXpos "+nextTileXpos+" currentTileYpos "+currentTileYpos+" result: "+ (currentTileXpos*Game.TILES_SIZE-Game.TILES_SIZE));
            if(aboveAndBelowClear(nextTileXpos,currentTileYpos,lvlData))
                return nextTileXpos*Game.TILES_SIZE-1;
            return nextTileXpos*Game.TILES_SIZE-1; //-1 to avoid getting stuck/clipped in the wall
        }
        else{
            //left

            int prevTileXpos = currentTileXpos -1;
            //System.out.println("nextTileXpos "+prevTileXpos+" currentTileYpos "+currentTileYpos+" result: "+ (currentTileXpos*Game.TILES_SIZE-Game.TILES_SIZE));

            if(aboveAndBelowClear(prevTileXpos,currentTileYpos,lvlData))
                return currentTileXpos*Game.TILES_SIZE;
            return currentTileXpos*Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveGround(Rectangle2D.Float hitbox,float airSpeed){
        int currentTile = (int)(hitbox.y/Game.TILES_SIZE);
        if(airSpeed>0){ //falling touch ground
            int tileYPos = currentTile*Game.TILES_SIZE;
            int yOffSet = (int)(Game.TILES_SIZE-hitbox.height);
            //System.out.println("TileSIze "+ Game.TILES_SIZE + " yOffSet "+yOffSet+" TileYPos "+tileYPos+" result: "+ (tileYPos + yOffSet-1));
            int offSetTileY = tileYPos + yOffSet-1;
            return offSetTileY+Game.TILES_SIZE;
        }
        else{
            //jumping touch roof
            return currentTile*Game.TILES_SIZE;
        }
    }
    public static boolean isEntityOnGround(Rectangle2D.Float hitbox,int[][] lvlData){
        if(!IsSolid((int) hitbox.x, (int) (hitbox.y+hitbox.height+1),lvlData) && !IsSolid((int) (hitbox.x+hitbox.width), (int) (hitbox.y+hitbox.height+1),lvlData))
            return false;
        return true;
    }
    public static boolean isFloor(Rectangle2D.Float hitbox,float xSpeed,int[][] lvlData){
        if(xSpeed > 0 ){
            return IsSolid(hitbox.x+hitbox.width+xSpeed,hitbox.y+hitbox.height+1,lvlData);
        }
        else{
            return IsSolid(hitbox.x+xSpeed,hitbox.y+hitbox.height+1,lvlData);
        }
    }
    public static boolean isAllTileWalkable(int xStart,int xEnd,int y, int[][] lvlData){
        for (int i = 0; i < xEnd - xStart; i++) {
            if (isTileSolid(xStart + i, y, lvlData))
                return false;

        }
        return true;
    }

    public static boolean isSightClear(int [][]lvlData, Rectangle2D.Float enemyHitbox,Rectangle2D.Float playerHitbox,int tileY) {
        int firstXTile = (int) (enemyHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (playerHitbox.x / Game.TILES_SIZE);
        if (firstXTile > secondXTile) {
            return isAllTileWalkable(secondXTile, firstXTile, tileY, lvlData);
        } else {
            return isAllTileWalkable(firstXTile, secondXTile, tileY, lvlData);
        }
    }
    public static int[][] GetLevelData(BufferedImage img){

        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for(int j = 0;j<img.getHeight();j++){
            for(int i = 0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int value = color.getRed();
                if(value >= 14)
                    value = 13;
                lvlData[j][i] = value;
            }
        }
        //System.out.println(lvlData[5][6]);
        return lvlData;
    }
    public static Point GetPlayerSpawn(BufferedImage img){

        for(int j = 0;j<img.getHeight();j++){
            for(int i = 0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int value = color.getGreen();
                if(value == 100){
                    return new Point(i*Game.TILES_SIZE,j*Game.TILES_SIZE);
                }
            }

        }
        return new Point(2*Game.TILES_SIZE,8*Game.TILES_SIZE);
    }
    public static ArrayList<Coin> GetCoins(BufferedImage img){
        ArrayList<Coin> coins = new ArrayList<>();
        for(int j = 0;j<img.getHeight();j++){
            for(int i = 0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int value = color.getRed();
                if(value == 250){
                    coins.add(new Coin(i*Game.TILES_SIZE,j*Game.TILES_SIZE));
                }
            }
        }
        return coins;
    }
    public static ArrayList<Ghoul> GetGhouls(BufferedImage img){

        ArrayList<Ghoul> ghouls = new ArrayList<>();
        for(int j = 0;j<img.getHeight();j++){
            for(int i = 0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int value = color.getGreen();
                if(value == GHOUL && color.getRed()> 14){
                    ghouls.add(new Ghoul(i* Game.TILES_SIZE,j*Game.TILES_SIZE));
                }
            }
        }
        return ghouls;
    }
    public static ArrayList<Skeleton> GetSkeletons(BufferedImage img){

        ArrayList<Skeleton> skeletons = new ArrayList<>();
        for(int j = 0;j<img.getHeight();j++){
            for(int i = 0;i<img.getWidth();i++){
                Color color = new Color(img.getRGB(i,j));
                int value = color.getGreen();
                if(value == SKELETON && color.getRed()==100){
                    skeletons.add(new Skeleton(i* Game.TILES_SIZE,j*Game.TILES_SIZE));
                }
            }
        }
        return skeletons;
    }
}
