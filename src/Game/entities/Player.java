package Game.entities;

import Game.Database.DatabaseHandler;
import Game.Main.Game;
import Game.gamestates.Playing;
import Game.utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static Game.utils.HelpMethods.*;
import static Game.utils.constants.PlayerConstants.*;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick,aniIndex,aniSpeed=10;
    private BufferedImage image,subImage;
    private int playerAction =  IDLE;
    private boolean up,down,left,right,jump;
    private int playerDirection = -1; //not moving
    private final float pSpeed= Game.scale;
    private boolean moving = false,attacking=false;
    private int[][] lvlData;
    private float xDrawOffset=(int)(16*Game.scale);
    private float yDrawOffset=(int)(9*Game.scale); //hitboxul caracterului incepe la ((64*scale-48)/2,(64*scale-64)/2/scale) xy
    //jumping / gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f*Game.scale;
    private float jumpSpeed = -2.7f*Game.scale;
    private float fallSpeedAfterCollision = 0.5f*Game.scale;
    private boolean inAir = false;
    //StatusBar ui
    private BufferedImage healthBarImg;
    private float healthBarScale = 0.2f;
    private int actualHealthBarWidth;
    private  int actualHealthBarHeight;

    private int healthBarXStart = (int) (healthBarScale*150*Game.scale);
    private int healthBarYStart = (int) (healthBarScale*80*Game.scale);
    private int healthBarWidth ;
    private int healthBarHeight ;
    private int healthBarX = (int) (10*Game.scale);
    private int healthBarY = (int) (10*Game.scale);
    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;
    private Rectangle2D.Float attackBox;
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;
    private Playing playing;
    public Player(int x, int y,int width, int height,Playing playing) {
        super(x, y,width,height);
        this.playing = playing;
        loadAnimations();
        initHitbox(  x,y,(int)(32*Game.scale),(int)(42.6666667f*Game.scale));
        initAttackBox();
        int ok=0;
        ok++;
        //if(ok==1)
            //System.out.println("XoffSet: "+xDrawOffset+" YoffSet: "+yDrawOffset+" playerWidth "+width+" playerHeight "+height+" hitboxWidth "+hitbox.width+" hitboxHeight "+hitbox.height+" scale "+Game.scale+"playerWidth " + width+" playerHeight "+height);

    }
    public void setSpawn(Point spawn){
        this.x = spawn.x;
        this.y = spawn.y-height/5;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox(){
        attackBox = new Rectangle2D.Float(hitbox.x-20,hitbox.y,(int)(80*Game.scale),(int)(20*Game.scale));
    }
    public void update(){
        updateHealthBar();
        if(currentHealth<=0) {
            playing.setGameOver(true);
            return;
        }
        updatePos();
        if(attacking)
            checkAttack();
        updateAttackBox();
        updateAnimationTick();
        setAnimation();
    }
    private void checkAttack(){
        if(attackChecked || aniIndex!=1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);

    }
    private void updateAttackBox(){

        if(right)
            attackBox.x = hitbox.x+hitbox.width + (int)(10*Game.scale);
        else if(left)
            attackBox.x = hitbox.x-attackBox.width - (int)(10*Game.scale);
        attackBox.y = hitbox.y+(int)(10*Game.scale);
    }
    private void updateHealthBar(){
        healthWidth = (int)((currentHealth/(float)maxHealth)*healthBarWidth);
    }
    public void render(Graphics g,int xLvlOffset){
        g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x-xDrawOffset)-xLvlOffset+flipX, (int)(hitbox.y-yDrawOffset) ,100*flipW,100,null);
        //drawHitbox(g,xLvlOffset);
        //drawAttackBox(g,xLvlOffset);
        drawUI(g);
    }
    private void drawAttackBox(Graphics g,int xLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)(attackBox.x-xLvlOffset),(int)attackBox.y,(int)attackBox.width,(int)attackBox.height);
    }
    private void drawUI(Graphics g){
        //healthBar
        g.setColor(Color.RED);
        //System.out.println("HealthWidth: "+healthWidth);
        g.fillRect(healthBarXStart+10,healthBarYStart,healthWidth,healthBarHeight+10);
        g.drawImage(healthBarImg,healthBarX,healthBarY,actualHealthBarWidth,actualHealthBarHeight,null);
    }

    private void setAnimation(){
        int startAni = playerAction;
        if(moving){
            playerAction = RUNNING;
        }
        else playerAction = IDLE;
        if(inAir){
            if(airSpeed<0){
                playerAction = JUMPING;
            }
            else{
                playerAction = FALLING;
            }
        }
        if(attacking){
            playerAction = ATTACK_1;
            if(startAni!=ATTACK_1){
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }
        if(startAni != playerAction){
            resetAniTick();
            resetAniIndex();
        }
    }
    private void resetAniTick(){
        aniTick = 0;
    }
    private void resetAniIndex(){
        aniIndex = 0;
    }
    private void updateAnimationTick(){
        aniTick++;
        if(aniTick>=aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex>=GetSpriteAmount(playerAction)){
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }
    public void resetAll(){
        resetAniIndex();
        resetAniTick();
        resetDirBooleans();

        if(!isEntityOnGround(hitbox,lvlData))
            inAir = true;
        hitbox.x=x;
        hitbox.y=y;
        currentHealth = maxHealth;
        attacking = false;

        DatabaseHandler.saveCurrentLevel(0);
        DatabaseHandler.saveCurrentScore(0);
    }
    private void updatePos(){
        moving=false;
        if(jump)
            jump();
        if(!left &&!right && !inAir) return;
        float xSpeed = 0;
        if(left) {
            xSpeed = -pSpeed;
            flipX = width;
            flipW = -1;
        }
        if(right) {
            xSpeed = pSpeed;
            flipX = 0;
            flipW = 1;
        }
        if(!inAir)
            if(!isEntityOnGround(hitbox,lvlData))
                inAir = true;

        if(inAir){
            if(CanMoveHere(hitbox.x,hitbox.y+airSpeed,hitbox.width,hitbox.height,lvlData)) {
                //if jumped and no solid obj stopped player update Y of player
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }
            else{
                //if solid obj stoped player update Y of player to be exactly next to roof or ground
                hitbox.y = GetEntityYPosUnderRoofOrAboveGround(hitbox,airSpeed);
                if(airSpeed>0)
                    //if player touched ground reset airSpeed
                    resetInAir();
                else
                    //if player touched roof set airSpeed to fallSpeedAfterCollision(falling faser)
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed); //update X of player
            }
        }else{
            //if player is not in air update X of player anyway
            updateXPos(xSpeed);
        }
        moving = true;
    }
    private void jump(){
        if(inAir)
            //if already in air return
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }
    private void resetInAir(){
        inAir = false;
        airSpeed = 0;
    }
    public void changeHealth(int amount){
        currentHealth += amount;
        if(currentHealth>=maxHealth)
            currentHealth = maxHealth;
        if(currentHealth<=0)
        {
            currentHealth = 0;
            //game over
        }
    }
    private void updateXPos(float xSpeed){
        if(CanMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width,hitbox.height,lvlData)){
            hitbox.x+=xSpeed;
        }
        else{
            hitbox.x = GetEntityXPosNextToWall(hitbox,xSpeed,lvlData);
            //System.out.println("Collision"+hitbox.x);
        }
    }
    private void loadAnimations() {
            BufferedImage image = LoadSave.LoadImage(LoadSave.PLAYER_ATLAS);
            animations = new BufferedImage[8][19];
            for(int i=0;i<animations.length;i++){
                for(int j=0;j<animations[i].length;j++){
                    animations[i][j] = image.getSubimage(j*100,i*100, 100, 100);
                    //tbd animations[i][j] = image.getSubimage(j*100,i*100, (int) (64*Game.scale), (int) (64*Game.scale));
                }
            }
            healthBarImg = LoadSave.LoadImage(LoadSave.HEALTH_BAR);
            actualHealthBarWidth = (int) (healthBarImg.getWidth()*healthBarScale);
            actualHealthBarHeight = (int) (healthBarImg.getHeight()*healthBarScale);
            healthBarWidth =actualHealthBarWidth-healthBarXStart;
            healthBarHeight = actualHealthBarHeight-healthBarYStart;
    }
    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
        if(!isEntityOnGround(hitbox,lvlData))
            inAir = true;
    }
    public void resetDirBooleans(){
        setDown(false);
        setUp(false);
        setLeft(false);
        setRight(false);
    }



    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }
    public void setJump(boolean jump){
        this.jump = jump;
    }
}
