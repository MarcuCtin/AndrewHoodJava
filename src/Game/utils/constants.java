package Game.utils;

import Game.Main.Game;

public class constants {
    public static class LevelConstants{
        public static final int LEVEL_ONE = 0;
        public static final int LEVEL_TWO = 1;
        public static final int LEVEL_THREE = 2;
        public static int GetStarAmountForCurrentLevel(int levelIndex, int score){
            switch (levelIndex){
                case LEVEL_ONE:
                    if(score>=5){
                        return 3;
                    }
                    else if(score>=3){
                        return 2;
                    }
                    else if(score>=1){
                        return 1;
                    }
                    else return 0;

                case LEVEL_TWO:
                    if(score>=8){
                        return 3;
                    }
                    else if(score>=5){
                        return 2;
                    }
                    else if(score>=2){
                        return 1;
                    }
                    else return 0;
                case LEVEL_THREE:
                    if(score>=12){
                        return 3;
                    }
                    else if(score>=10){
                        return 2;
                    }
                    else if(score>=5){
                        return 1;
                    }
                    else return 0;
            }
            return 0;
        }
    }
    public static class EnemyConstants{
        public static final int GHOUL = 0;
        public static final int SKELETON = 1;
        public static final int IDLE = 2;
        public static final int WALKING = 4;
        public static final int ATTACKING = 5;
        public static final int DYING = 0;
        public static final int JUMPING = 3;
        public static final int FALLING = 1;
        public static final int GHOUL_WIDTH_DEFAULT = 100;
        public static final int GHOUL_HEIGHT_DEFAULT =100;
        public static final int GHOUL_WIDTH = (int)(GHOUL_WIDTH_DEFAULT* Game.scale);

        public static final int GHOUL_HEIGHT = (int)(GHOUL_HEIGHT_DEFAULT* Game.scale);
        public static final int GHOUL_DRAWOFFSET_X = (int)(30*Game.scale);
        public static final int GHOUL_DRAWOFFSET_Y = (int)(20*Game.scale);
        public static final int GHOUL_HITBOX_WIDTH = (int)(42*Game.scale);
        public static final int GHOUL_HITBOX_HEIGHT = (int)(58*Game.scale);
        public static final int HIT = 1;

        public static final int GetSpriteAmount(int enemy_type,int enemy_state){
            switch (enemy_type){
                case GHOUL:
                    switch (enemy_state){
                        case IDLE:
                            return 14;
                        case WALKING:
                            return 11;
                        case ATTACKING:
                            return 12;
                        case DYING:
                            return 15;
                        case JUMPING:
                            return 7;
                        case HIT:
                            return 6;
                    }

            }
            return 0;
        }
        public static int GetMaxHealth(int enemy_type){
            switch (enemy_type){
                case GHOUL:
                    return 20;
                case SKELETON:
                    return 40;
                default:
                    return 1;
            }

        }
        public static int GetEnemyDmg(int enemy_type){
            switch (enemy_type){
                case GHOUL:
                    return 20;
                case SKELETON:
                    return 30;
                default:
                    return 0;
            }
        }
    }
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 96;
            public static final int B_HEIGHT_DEFAULT = 32;
            public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT* Game.scale);
            public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT* Game.scale);
        }
    }
    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;

    }
    public static class PlayerConstants{
        public static final int CLIMBING = 0;
        public static final int DJUMP = 1;
        public static final int DYING =2;
        public static final int FALLING = 3;
        public static final int IDLE = 4;
        public static final int JUMPING = 5;
        public static final int RUNNING = 6;
        public static final int ATTACK_1 = 7;
        public static final int WALKING = 8;
        public static int GetSpriteAmount(int player_action){
            switch (player_action){
                case PlayerConstants.DJUMP:
                    return 6;
//                case PlayerConstants.ATTACK_2:
//                    return 10;
                case PlayerConstants.DYING:
                    return 15;
                case PlayerConstants.WALKING:
                    return 20;
                case PlayerConstants.RUNNING:
                    return 12;
                case PlayerConstants.IDLE:
                    return 15;
                case PlayerConstants.JUMPING:
                    return 6;
                case PlayerConstants.FALLING:
                    return 5;
                case PlayerConstants.ATTACK_1:
                    return 9; //bow attack
//                case PlayerConstants.ATTACK_JUMP_1:
//                    return 1;
                case PlayerConstants.CLIMBING:
                    return 18;
                default:
                    return 0;

            }
        }
    }
}
