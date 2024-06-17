package Game.UI;

import Game.Database.DatabaseHandler;
import Game.gamestates.Playing;

import java.awt.*;

public class Score {
    private int score;
    private Playing playing;
    public Score(Playing playing){
        this.playing = playing;
        score = DatabaseHandler.getScore();
    }
    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,20));
        g.drawString("Score: "+score,250,40);
    }
    public void increaseScore(){
        ++score;
    }
    public void updateScore(int score){
        this.score = score;
    }
    public void update(){

    }
    public void setMaxScore(int score){
        this.score = score;
    }
    public void reset(){
        score = 0;
    }
    public void addScore(int score){
        this.score += score;
    }

    public int getScore(){
        return score;
    }
}
