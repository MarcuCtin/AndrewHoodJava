package Game.UI;

import Game.Main.Game;
import Game.gamestates.Gamestate;
import Game.gamestates.Playing;
import Game.gamestates.State;
import Game.gamestates.StateMethods;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static Game.utils.constants.LevelConstants.GetStarAmountForCurrentLevel;

public class LevelCompleted extends State implements StateMethods {
    private Playing playing;
    private BufferedImage img;
    public PauseMenuOptionBtn nextBtn;
    private PauseMenuOptionBtn menuBtn;
    private int bgX,bgY,bgW,bgH;
    private float scale = 1.5f;
    private float starScale = 0.5f;
    private float starX =  Game.GAME_WIDTH/2-120;
    private float starY = (int)(Game.GAME_HEIGHT*0.35);
    public LevelCompleted(Playing playing) {
        super(playing.getGame());
        this.playing = playing;
        initImg();
        initButtons();
     }
    private void initImg() {
        img = LoadSave.LoadImage(LoadSave.COMPLETED_LVL_IMG);
        bgW = (int)(img.getWidth()* Game.scale*scale);
        bgH = (int)(img.getHeight()*Game.scale*scale);
        bgX = Game.GAME_WIDTH/2-bgW/2;
        bgY = (int)(75*Game.scale);
    }
    private void initButtons() {
         int menuX = Game.GAME_WIDTH/2-100;
         int nextX = Game.GAME_WIDTH/2+70;
         int y = (int)(Game.GAME_HEIGHT*0.5);
         nextBtn = new PauseMenuOptionBtn(nextX,y,4, Gamestate.PLAYING,playing);
         menuBtn = new PauseMenuOptionBtn(menuX,y,0,Gamestate.MENU,playing);
    }
    public void starDraw(Graphics g){
        int score = game.getScore().getScore();
        int starAmountToShow = GetStarAmountForCurrentLevel(game.getPlaying().getLevelIndex(),score);
        for(int i = 0 ; i<starAmountToShow;i++) {
//            System.out.println("star amount to show: " + starAmountToShow);
            Star star = new Star((int) starX+i*100, (int) starY, 50, 50);
            star.draw(g);
        }

    }
    public void draw(java.awt.Graphics g) {
        g.drawImage(img,bgX,bgY,bgW,bgH,null);
        g.setColor(Color.red);
        g.setFont(new Font("Roboto",Font.BOLD,15));
        g.drawString("NextLevel", (int) (nextBtn.getBtnHitbox().getX()-12*Game.scale), (int) (nextBtn.getBtnHitbox().getY()-20*Game.scale));
        nextBtn.draw(g);
        g.drawString("Main Menu", (int) (menuBtn.getBtnHitbox().getX()-10*Game.scale), (int) (menuBtn.getBtnHitbox().getY()-20*Game.scale));
        menuBtn.draw(g);
        starDraw(g);
    }
    public void update() {

        nextBtn.update();
        menuBtn.update();
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public boolean mouseClicked(MouseEvent e) {
        return false;
    }


    public void mouseMoved(MouseEvent e) {

        nextBtn.setMouseOver(false);
        menuBtn.setMouseOver(false);
        if(isMouseInOptions(e, menuBtn))
            menuBtn.setMouseOver(true);
        else if(isMouseInOptions(e, nextBtn))
            nextBtn.setMouseOver(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void mousePressed(MouseEvent e) {

        if(isMouseInOptions(e, menuBtn)) {
            System.out.println("Mouse pressed");
           menuBtn.setMousePressed();
        }
        else if(isMouseInOptions(e, nextBtn))
            nextBtn.setMousePressed();
    }
    public void mouseReleased(MouseEvent e) {

        if(isMouseInOptions(e, menuBtn))
        {

            if(menuBtn.isMousePressed()) {

                playing.resetAll();
                playing.setPaused(false);
            }
        }
        else if(isMouseInOptions(e, nextBtn))
                if(nextBtn.isMousePressed()) {

                    //urmatorul level se va incarca din functia update() din cadrul nextBtn
                    System.out.println("gamestate after nexting to new level: " + Gamestate.state+" and lvl index is now " + playing.getLevelIndex());
                }
        menuBtn.resetBooleans();
        nextBtn.resetBooleans();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
