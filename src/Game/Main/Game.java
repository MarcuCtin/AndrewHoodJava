package Game.Main;

import Game.Database.PlayerData;
import Game.UI.LevelCompleted;
import Game.UI.Pause;
import Game.UI.Score;
import Game.gamestates.Gamestate;
import Game.gamestates.Menu;
import Game.gamestates.Playing;

import java.awt.*;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    private Thread gameThread;

    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Playing playing;
    private Menu menu;
    private Pause pause;
    private LevelCompleted levelCompleted;
    private Score score;
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float scale = 1.5f;
    public final static int TILES_IN_WIDTH = 26 ; // width and height = game screen size
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE*scale);
    public final static int GAME_WIDTH = TILES_SIZE*TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE*TILES_IN_HEIGHT;
    public PlayerData playerData;
    public Game (){

        initClasses();
        menu = new Menu(this);
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();//last call
    }
    private void initClasses(){

        playing = new Playing(this);
        if(score==null)
            score = new Score(playing);
        pause = new Pause(Game.GAME_WIDTH/2,Game.GAME_HEIGHT/3,this,getPlaying());
        if(playing!=null){
            levelCompleted = new LevelCompleted(playing);
        }

    }
    public LevelCompleted getCompleted() {
        return levelCompleted;
    }
    public Pause getPause(){
        return pause;
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void update(){

        //System.out.println("Game update + THE GAME STATE: " + Gamestate.state);
        switch(Gamestate.state){
            case COMPLETED:
                    levelCompleted.update();
                break;
            case PAUSE:
                if(getPlaying().getPaused())
                    pause.update();
                break;
            case MENU:
                menu.update();
                break;
            case PLAYING:
                    playing.update();
                    score.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }
    public void render(Graphics g){

        switch(Gamestate.state){
            case COMPLETED:
                    playing.render(g);
                    levelCompleted.draw(g);

                break;
            case PAUSE:
                if(getPlaying().getPaused()) {
                    playing.render(g);
                    pause.render(g);
                }
                break;
            case MENU:
                menu.render(g);
                break;
            case PLAYING:
                    playing.render(g);
                    score.draw(g);
//                if(getPlaying().getLevelCompletedStatus())
//                    levelCompleted.draw(g);
//                if(!getPlaying().getPaused()) {
//
//                }
                break;
            default:
                break;
        }

    }
    public Score getScore(){
        return score;
    }
    public void setScore(int score){
        this.score.updateScore(score);
    }
    public void setMaxScore(int score){
        this.score.setMaxScore(score);
    }
    @Override
    public void run() {
        double timePerFrame = 1000000000.0/FPS_SET;
        double timePerUpdate = 1000000000.0/UPS_SET;
        long prevTime = System.nanoTime();

        int frames = 0 ;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU=0;
        double deltaF=0 ;
        while(true){
            long currentTime = System.nanoTime();
            deltaU = deltaU + (currentTime-prevTime)/timePerUpdate;
            deltaF = deltaF + (currentTime-prevTime)/timePerFrame;
            prevTime = currentTime;
            if(deltaU>=1){
                update();
                updates++;
                deltaU--;
            }
            if(deltaF>=1){
                gamePanel.repaint();
                frames++;
                deltaF--;
            }
            if(System.currentTimeMillis()-lastCheck >=1000){
                lastCheck = System.currentTimeMillis();
                frames = 0;
                updates = 0;
            }
        }
    }
    public void windowFocusLost(){
        if(Gamestate.state == Gamestate.PLAYING){
            playing.getPlayer().resetDirBooleans();
        }
    }
    public Menu getMenu(){
        return menu;
    }
    public Playing getPlaying(){
        return playing;
    }

    public void setPaused(boolean b) {
        playing.setPaused(b);
    }


}