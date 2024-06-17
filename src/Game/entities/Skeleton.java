package Game.entities;

import Game.Main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static Game.utils.constants.Directions.LEFT;
import static Game.utils.constants.Directions.RIGHT;
import static Game.utils.constants.EnemyConstants.*;
public class Skeleton extends Enemy{
    private Rectangle2D.Float attackBox;

    public Skeleton(float x, float y) {
        super(x, y,GHOUL_WIDTH_DEFAULT,GHOUL_HEIGHT_DEFAULT,GHOUL);
        initHitbox(x,y-15,42,58);
        initAttackBox();
    }
    public void update(int[][] lvlData,Player player){
        updateBehaviour(lvlData,player);
        updateAttackBox();
        updateAnimationTick();
    }
    private void updateAttackBox(){

        if(walkDir == RIGHT)
            attackBox.x = hitbox.x+hitbox.width + (int)(10*Game.scale);
        else
            attackBox.x = hitbox.x-attackBox.width - (int)(10*Game.scale);
        attackBox.y = hitbox.y+(int)(10*Game.scale);
    }
    protected void drawAttackBox(Graphics g, int xLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)(attackBox.x-xLvlOffset),(int)attackBox.y,(int)attackBox.width,(int)attackBox.height);
    }
    private void initAttackBox(){
        attackBox = new Rectangle2D.Float(hitbox.x,hitbox.y,(int)(20* Game.scale),(int)(20*Game.scale));
    }
    private void updateBehaviour(int[][] lvlData,Player player){
        if(firstUpdate)
        {
            firstUpdateCheck(lvlData);
        }
        if(inAir)
            updateInAir(lvlData);
        else{
            //patroling
            switch (enemyState){
                case IDLE:
                    newState(WALKING);
                    break;
                case WALKING:
                    if(canSeePlayer(lvlData,player))
                        turnTowardsPlayer(player);
                    if(isPlayerCloseEnoughForAttack(player))
                        newState(ATTACKING);
                    move(lvlData);
                    break;
                case ATTACKING:
                    if(aniIndex == 0)
                        attackChecked = false;
                    if(aniIndex == 3 && !attackChecked)
                        checkEnemyHit(attackBox,player);
                    break;
                case HIT:
                    break;
            }
        }
    }
    public int flipX(){
        if(walkDir==LEFT)
            return width;
        else
            return 0;
    }
    public int flipW(){

        if(walkDir==RIGHT)
            return 1;
        else
            return -1;
    }
}
