package Game.entities;

import Game.Levels.Level;
import Game.gamestates.Gamestate;
import Game.gamestates.Playing;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Game.utils.constants.EnemyConstants.GHOUL_HEIGHT_DEFAULT;
import static Game.utils.constants.EnemyConstants.GHOUL_WIDTH_DEFAULT;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] ghoulArr;
    private BufferedImage[][] skeletonsArr;
    private ArrayList<Ghoul> ghouls = new ArrayList<>();
    private ArrayList<Skeleton> skeletons = new ArrayList<>();
    public boolean isLevelFinished = false;
    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();

    }
    public void loadEnemies(Level level){
        setIsLevelFinished(false);
        ghouls = level.getGhouls();
        skeletons = level.getSkeletons();
        //System.out.println("Size of ghouls: "+ghouls.size());
    }
    public void update(int[][] lvlData,Player player){
        boolean isAnyActive = false;
        for(Ghoul ghoul: ghouls){
            if(ghoul.isActive()) {
                ghoul.update(lvlData, player);
                isAnyActive = true;
            }
        }
        for(Skeleton skeleton: skeletons){
            if(skeleton.isActive()) {
                skeleton.update(lvlData, player);
                isAnyActive = true;
            }
        }
        if(!isAnyActive){

            setIsLevelFinished(true);

            System.out.println("LEVEL COMPLETED + state is now" + Gamestate.state);
        }
    }
    public void setIsLevelFinished(boolean isLevelFinished){
        this.isLevelFinished = isLevelFinished;
    }
    public void checkEnemyHit(Rectangle2D.Float playerAttackBox){
        for(Ghoul ghoul: ghouls){
            if(ghoul.hitbox.intersects(playerAttackBox) && ghoul.isActive()){
                ghoul.takeDamage(8); //PlayerData.attackPower
                if(ghoul.currentHealth<=0)
                    playing.getPlayer().setAttacking(false);
                return;
            }
        }
        for(Skeleton skeleton: skeletons){
            if(skeleton.hitbox.intersects(playerAttackBox) && skeleton.isActive()){
                skeleton.takeDamage(6); //PlayerData.attackPower
                if(skeleton.currentHealth<=0)
                    playing.getPlayer().setAttacking(false);
                return;
            }
        }
    }
    public void draw(Graphics  g,int xLvlOffset){
        drawGhouls(g,xLvlOffset);
        drawSkeletons(g,xLvlOffset);
    }
    private void drawGhouls(Graphics g,int xLvlOffset){
        for(Ghoul ghoul: ghouls){
            if(ghoul.isActive()) {
                g.drawImage(ghoulArr[ghoul.getEnemyState()][ghoul.getAniIndex()],
                        (int)ghoul.getHitbox().x-xLvlOffset-30+ghoul.flipX(),
                        (int)ghoul.getHitbox().y-16,
                        GHOUL_WIDTH_DEFAULT*ghoul.flipW(),
                        GHOUL_HEIGHT_DEFAULT,
                        null);
                //ghoul.drawAttackBox(g,xLvlOffset);
            }
//            g.drawRect((int)ghoul.getHitbox().x+xLvlOffset,(int)ghoul.getHitbox().y,42,58);
        }
    }
    private void drawSkeletons(Graphics g,int xLvlOffset){
        for(Skeleton skeleton: skeletons){
            if(skeleton.isActive()) {
                g.drawImage(skeletonsArr[skeleton.getEnemyState()][skeleton.getAniIndex()],
                        (int)skeleton.getHitbox().x-xLvlOffset-30+skeleton.flipX(),
                        (int)skeleton.getHitbox().y-25,
                        GHOUL_WIDTH_DEFAULT*skeleton.flipW(),
                        GHOUL_HEIGHT_DEFAULT,
                        null);
                //skeleton.drawAttackBox(g,xLvlOffset);
            }
//            g.drawRect((int)skeleton.getHitbox().x+xLvlOffset,(int)skeleton.getHitbox().y,42,58);
        }
    }
    private void loadEnemyImgs(){
        ghoulArr = new BufferedImage[6][15];
        BufferedImage temp = LoadSave.LoadImage(LoadSave.GHOUL_SPRITE);
        //System.out.println("Width: "+ghoulArr.length+" Height: "+ghoulArr[0].length);
        for(int i = 0; i<ghoulArr.length;i++){
            for(int j = 0; j< ghoulArr[i].length;j++){
                ghoulArr[i][j] = temp.getSubimage(j*GHOUL_WIDTH_DEFAULT,i*GHOUL_HEIGHT_DEFAULT,GHOUL_WIDTH_DEFAULT,GHOUL_HEIGHT_DEFAULT);
            }
        }
        skeletonsArr = new BufferedImage[6][16];
        temp = LoadSave.LoadImage(LoadSave.SKELETON_SPRITE);
        //System.out.println("Width: "+ghoulArr.length+" Height: "+ghoulArr[0].length);
        for(int i = 0; i<skeletonsArr.length;i++){
            for(int j = 0; j< skeletonsArr[i].length;j++){
                skeletonsArr[i][j] = temp.getSubimage(j*GHOUL_WIDTH_DEFAULT,i*GHOUL_HEIGHT_DEFAULT,GHOUL_WIDTH_DEFAULT,GHOUL_HEIGHT_DEFAULT);
            }
        }


    }
    public void resetAllEnemies(){
        for(Ghoul ghoul: ghouls){
            ghoul.reset();
        }
        for(Skeleton skeleton: skeletons){
            skeleton.reset();
        }
    }
}
