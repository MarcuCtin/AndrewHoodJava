package Game.entities;

import Game.Main.Game;

import java.awt.geom.Rectangle2D;

import static Game.utils.HelpMethods.*;
import static Game.utils.constants.Directions.LEFT;
import static Game.utils.constants.Directions.RIGHT;
import static Game.utils.constants.EnemyConstants.*;

public abstract class Enemy extends Entity{
    protected int aniIndex,enemyState=IDLE,enemyType;
    protected boolean inAir;
    protected float fallSpeed;
    protected float gravity=0.04f* Game.scale;
    protected int aniTick,aniSpeed = 15;
    protected final float walkSpeed= Game.scale * 0.35f;
    protected int walkDir = RIGHT;
    protected boolean firstUpdate = true;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected int maxHealth ;
    protected int currentHealth;
    protected boolean active = true;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height,int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x,y,width,height);
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
    }
    protected void firstUpdateCheck(int[][] lvlData){
            if(!isEntityOnGround(hitbox,lvlData))
                inAir = true;
            firstUpdate = false;
    }
    protected void updateInAir(int [][] lvlData){
        if(CanMoveHere(hitbox.x,hitbox.y+fallSpeed,hitbox.width,hitbox.height,lvlData)){
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        }
        else{
            inAir = false;
            hitbox.y=GetEntityYPosUnderRoofOrAboveGround(hitbox,fallSpeed);
            tileY = (int)(hitbox.y/Game.TILES_SIZE);
        }
    }
    protected void move(int [][] lvlData){
        float xSpeed = 0;
        if(walkDir == LEFT){
            xSpeed = -walkSpeed;
        }
        else{
            xSpeed = walkSpeed;
        }
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width,hitbox.height,lvlData)){
            if(isFloor(hitbox,xSpeed,lvlData)){
                hitbox.x += xSpeed;
                return;
            }

        }
        changeWalkDir();
    }
    protected void setWalkDir(int walkDir){
        this.walkDir = walkDir;
    }
    protected void newState(int enemyState){
        this.enemyState = enemyState;
        aniIndex = 0;
        aniTick =0;
    }
    public void takeDamage(int damage){
        if(currentHealth<=0 && enemyState!=DYING){
            currentHealth = 0;
            newState(DYING);
        }
        else if(enemyState!=DYING && enemyState!=HIT && enemyState!=ATTACKING){
            currentHealth -= damage;
            newState(HIT);
        }
    }
    protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player){
        if(attackBox.intersects(player.getHitbox())){
            player.changeHealth(-GetEnemyDmg(enemyType));
        }
        attackChecked=true;
    }
    protected void turnTowardsPlayer(Player player){
        if(player.hitbox.x>hitbox.x){
            walkDir = RIGHT;
        }
        else{
            walkDir = LEFT;
        }
    }

    protected boolean canSeePlayer(int [][] lvlData,Player player  ){
        int playerTileY = (int)(player.getHitbox().y/Game.TILES_SIZE);
        if(playerTileY == tileY)
            if(isPlayerInRange(player)){
                if(isSightClear(lvlData,hitbox,player.hitbox,tileY))//adica nu avem niciun obstacol in range
                {
                    //System.out.println("Player in range");
                    return true;
                }
            }
        return false;
    }
    private boolean isPlayerInRange(Player player){
        int absVal = Math.abs((int)(player.getHitbox().x - hitbox.x));
        return absVal <= attackDistance*5;
    }
    protected boolean isPlayerCloseEnoughForAttack(Player player){
        int absVal = Math.abs((int)(player.getHitbox().x - hitbox.x));
        return absVal <= attackDistance;
    }
    protected void updateAnimationTick(){
        aniTick++;

        if(aniTick>=aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex>=GetSpriteAmount(enemyType,enemyState)){
                aniIndex = 0;

                switch (enemyState){
                    case ATTACKING, HIT -> newState(IDLE);
                    case DYING -> active = false;
                }
//
//                if(enemyState== ATTACKING)
//                    newState(IDLE);
//                else if(enemyState == HIT)
//                    newState(IDLE);
//                else if(enemyState == DYING)
//                    active = false;
            }
        }
    }

    protected void changeWalkDir(){
        if(walkDir == LEFT){
            walkDir = RIGHT;
        }
        else{
            walkDir = LEFT;
        }
    }

    public boolean isActive() {
        return active;
    }
    public int getAniIndex() {
        return aniIndex;
    }
    public int getEnemyState() {
        return enemyState;
    }
    public void reset(){
        active = true;
        currentHealth = maxHealth;
        firstUpdate = true;
        inAir = false;
        aniIndex = 0;
        aniTick = 0;
        enemyState = IDLE;
        walkDir = RIGHT;
        hitbox.x = x;
        hitbox.y = y-30;
        fallSpeed = 0;
    }

}
