package Game.Levels;

import Game.Database.DatabaseHandler;
import Game.Main.Game;
import Game.UI.Coin;
import Game.gamestates.Gamestate;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    private ArrayList<Level> levels;
    public int lvlIndex = 0;
    private BufferedImage[] levelSprite;
    private boolean gameCompleted = false;
    private ArrayList<Coin> coins;
    public LevelManager(Game game) {
       if(game!=null) this.game = game;
        //load all levels
//        levelSprite = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        levels = new ArrayList<>();
        setCoins();
        buildAllLevels();
        lvlIndex = DatabaseHandler.loadCurrentLevel();
        resetAll();
    }

    public void setLvlIndex(int lvlIndex) {
        this.lvlIndex = lvlIndex;
    }
    public int getLevelIndex(){
        return lvlIndex;
    }
    private void buildAllLevels(){

        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for(BufferedImage img:allLevels){
            levels.add(new Level(img));
        }
    }
    public void setGameCompleted(boolean gameCompleted){
        this.gameCompleted = gameCompleted;
    }

    public void loadNextLevel(){
            if(game.getPlaying().getLevelCompletedStatus())
                lvlIndex++;
            System.out.println("Loading level: "+lvlIndex);
            if(lvlIndex>=levels.size()){
                lvlIndex = 0;
                System.out.println("GAME COMPLETED");
                setGameCompleted(true);
                Gamestate.state = Gamestate.MENU;
            }

            Level newLevel = levels.get(lvlIndex);
            game.getPlaying().getEnemyManager().loadEnemies(newLevel);
            game.getPlaying().getPlayer().loadLvlData(newLevel.getLvlData());
            game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());

    }

    public void importOutsideSprites() {
        BufferedImage img = LoadSave.LoadImage(LoadSave.LEVEL_ONE_ATLAS);
        levelSprite = new BufferedImage[14];
        for(int j=0;j<2;j++){
            for(int i=0;i<7;i++){
                levelSprite[j*7+i] = img.getSubimage(i*32,j*32,32,32);
            }
        }

    }
    public void setCoins(){
        if (!levels.isEmpty() && levels.get(lvlIndex) != null) {
            coins = levels.get(lvlIndex).getCoins();
            if (coins == null) {
                coins = new ArrayList<>();
            }
        } else {
            coins = new ArrayList<>();
        }
    }
    public ArrayList<Coin> getCoins(){
        return coins;
    }
    public void drawCoins (Graphics g,int lvlOffset) {
        coins = levels.get(lvlIndex).getCoins();
        for(Coin coin:coins){
            if(!coin.isCollected())
                coin.draw(g,lvlOffset);
        }
    }

    public void render(Graphics g,int lvlOffset) {
        if(gameCompleted){
                g.setColor(Color.RED);
                g.setFont(new Font("Arial",Font.BOLD,50));
                g.drawString("CONGRATULATIONS! GAME COMPLETED",Game.GAME_WIDTH/2-200,Game.GAME_HEIGHT/2);
        }
        if(!game.getPlaying().getEnemyManager().isLevelFinished){
            drawCoins(g,lvlOffset);
            for(int j = 0;j<Game.TILES_IN_HEIGHT;j++) {
                for(int i = 0; i< levels.get(lvlIndex).getLvlData()[0].length; i++) {
                    int index = levels.get(lvlIndex).getSpriteIndex(i, j);//return index of the sprite(from the BufferedImage[spritesheet.length]) at position i,j

                    g.drawImage(levelSprite[index], Game.TILES_SIZE*i-lvlOffset, j * Game.TILES_SIZE,Game.TILES_SIZE,Game.TILES_SIZE, null);

                    //for each pixel in the level_metadata, draw the corresponding sprite
                }
            }
        }
    }
    public void update() {
        Rectangle2D.Float playerHitbox = game.getPlaying().getPlayer().getHitbox();
        if(coins!=null){
            for(Coin coin:coins){
                if(coin.getHitbox().intersects(playerHitbox)&&!coin.isCollected()){
                    coin.setCollected(true);
                    game.getScore().increaseScore();
                    DatabaseHandler.saveCurrentScore(game.getScore().getScore());
                }
            }
        }
    }
    public void resetAll(){
        if(game.getPlaying()!=null) game.getPlaying().resetAll();
    }
    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }
    public int getAmountOfLevels() {
        return levels.size();
    }
}
