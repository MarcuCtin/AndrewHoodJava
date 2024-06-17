package Game.gamestates;

import Game.Database.DatabaseHandler;
import Game.Levels.LevelManager;
import Game.Main.Game;
import Game.UI.GameOverOverlay;
import Game.UI.LevelCompleted;
import Game.UI.Pause;
import Game.UI.PauseBtn;
import Game.entities.Enemy;
import Game.entities.EnemyManager;
import Game.entities.Player;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Playing extends State implements StateMethods{
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private Player player;
    private PauseBtn pauseButton;

    private int xLvlOffset;
    private int leftBorder = (int) ( 0.5 * Game.GAME_WIDTH);
    private int rightBorder = (int) ( 0.5 * Game.GAME_WIDTH);
    //private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    //private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX ;
    private boolean paused = false;
    private BufferedImage bgImage;
    private BufferedImage bgImage2;
    private boolean gameOver = false;
    private Enemy enemyHit;

    private GameOverOverlay gameOverOverlay;
    private LevelCompleted levelCompleted;
    private boolean levelCompletedStatus = false;
    Pause pauseMenu;
    public Playing(Game game){
        super(game);
        initClasses();
        bgImage = LoadSave.LoadImage(LoadSave.PLAYING_BG);
        bgImage2 = LoadSave.LoadImage(LoadSave.PLAYING_BG2);
        calcLvlOffset();
        loadStartLevel();
    }
    public void loadNextLevel(){
        levelManager.setLvlIndex(levelManager.getLevelIndex()+1);
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        resetAll();
        DatabaseHandler.saveCurrentLevel(levelManager.getLevelIndex());
    }
    private void loadStartLevel(){
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
    }
    public LevelManager getLevelManager(){
        return levelManager;
    }
    private void calcLvlOffset(){
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }
    private void initClasses(){
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        pauseMenu = game.getPause();
        pauseButton = new PauseBtn(Game.GAME_WIDTH-50,10, Gamestate.PAUSE);
        player = new Player(Game.TILES_SIZE*2,(int)(Game.TILES_SIZE*7+20*Game.scale),(int)(64*Game.scale),(int)(64*Game.scale),this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        gameOverOverlay = new GameOverOverlay(this);

        //levelCompleted = new LevelCompleted(this);
    }
    public Player getPlayer(){
        return player;
    }
    public void windowFocusLost(){
        player.resetDirBooleans();
    }
    public void setLevelCompletedStatus(boolean status){
        levelCompletedStatus = status;
    }

    @Override
    public void update() {
        if(enemyManager.isLevelFinished){
            System.out.println("Playing update + THE LEVEL FINISHED ?: " + enemyManager.isLevelFinished + " THE LEVEL COMPLETED ?:" + levelCompletedStatus);
            levelCompletedStatus = true;
            Gamestate.state = Gamestate.COMPLETED;
            levelManager.update();


        }else{
            if(levelCompletedStatus){
                System.out.println("Playing update + THE LEVEL FINISHED ?: " + enemyManager.isLevelFinished + " THE LEVEL COMPLETED ?:" + levelCompletedStatus);
            }
            if(!paused && !gameOver && !levelCompletedStatus){
                levelManager.update();
                enemyManager.update(levelManager.getCurrentLevel().getLvlData(),player);
                pauseButton.update();
                checkCloseToBorder();
                player.update();
            } else gameOverOverlay.update();
        }

    }
//    public void update() {
//        if(!paused && !gameOver && !levelCompletedStatus){
//                levelManager.update();
//
//                enemyManager.update(levelManager.getCurrentLevel().getLvlData(),player);
//                pauseButton.update();
//                checkCloseToBorder();
//                player.update();
//        }
//        if(gameOver){
//            gameOverOverlay.update();
//        }
//    }


    public void render(Graphics g) {

        if(!paused && !levelCompletedStatus && !gameOver){
            g.drawImage(bgImage,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
            g.drawImage(bgImage2,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
            levelManager.render(g,xLvlOffset);
            pauseButton.draw(g);
            player.render(g,xLvlOffset);
            enemyManager.draw(g,xLvlOffset);
        }
        if(gameOver){
            gameOverOverlay.draw(g);
        }

    }

    private void checkCloseToBorder(){
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;
        if(diff>rightBorder){
            xLvlOffset += diff - rightBorder;
        }
        else if(diff<leftBorder){
            xLvlOffset += diff - leftBorder;
        }
        if(xLvlOffset > maxLvlOffsetX)
            xLvlOffset = maxLvlOffsetX;
        else if(xLvlOffset < 0)
            xLvlOffset = 0;
    }
    public int getLevelIndex(){
        return levelManager.lvlIndex;
    }
    public void resetAll(){
        //reset all entities enemy,level tbd
        gameOver = false;
        paused = false;
        enemyManager.isLevelFinished = false;
        game.getScore().reset();
        player.resetDirBooleans();
        player.resetAll();
        enemyManager.resetAllEnemies();
        //levelManager.resetAll();

        enemyManager.setIsLevelFinished(false);

    }
    public void checkEnemyHit(Rectangle2D.Float attackBox){
         enemyManager.checkEnemyHit(attackBox);
    }
    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }
    public void setPaused(boolean paused){
        this.paused = paused;
    }

    public LevelCompleted getLevelCompleted(){
        return levelCompleted;
    }

    public boolean getPaused(){
        return paused;
    }
    public boolean getLevelCompletedStatus(){
        return levelCompletedStatus;
    }
    //
    @Override
    public boolean mouseClicked(MouseEvent e) {
        if(!gameOver){
            if(e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
        }
        return false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver)
            if(isMouseInPause(e, pauseButton)){
                pauseButton.setMousePressed(true);
            }
    }

    @Override

    public void mouseReleased(MouseEvent e) {
        if(!gameOver)
            if(isMouseInPause(e, pauseButton)){
                if(pauseButton.isMousePressed()){
                    pauseButton.applyGameState();
                    setPaused(true);
                    if (pauseMenu == null) {
                        pauseMenu = game.getPause(); // Initialize pauseMenu if it's null
                    }
                    pauseMenu.resetButtons(); // Reset all PauseMenuOptionBtn instances
                    System.out.println("Mouse released pause button and gamestate is now: "+Gamestate.state);
                }
            }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver)
        {
            pauseButton.setMouseOver(false);
            if (isMouseInPause(e, pauseButton))
                pauseButton.setMouseOver(true);
            if (!pauseButton.isMouseOver())
                pauseButton.resetBooleans();
        }
    }
    //
    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e);
        else
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    if(paused) {
                        setPaused(false);
                        Gamestate.state = Gamestate.PLAYING;
                    }
                    else
                    {
                        setPaused(true);
                        Gamestate.state = Gamestate.PAUSE;
                    }
                    break;
            }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
        }
    }
    public void setMaxLvlOffset(int lvlOffset){
        this.maxLvlOffsetX = lvlOffset;
    }
    public EnemyManager getEnemyManager(){
        return enemyManager;
    }

}
