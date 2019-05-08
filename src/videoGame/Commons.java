package videoGame;

public interface Commons {
    public static int scale = 2;
    
    //Game constants
    public static final String GAME_NAME = "trashedy";
    public static final int BOARD_WIDTH = 358 * scale;
    public static final int BOARD_HEIGHT = 350 * scale;
    public static final int GRID_WIDTH = 32 * scale;
    public static final int GRID_HEIGHT = 32 * scale;
            
    
    public static final int BLOCK_WIDTH = 32 * scale;
    public static final int BLOCK_HEIGHT = 32 * scale;
    public static final int BORDER_RIGHT = 30 * scale;
    public static final int BORDER_LEFT = 5 * scale;

    //Bullet constants
    public static final int PLAYER_BULLET_HEIGHT = 6 * scale;
    public static final int PLAYER_BULLET_WIDTH = 6 * scale;
    public static final int CAN_BULLET_HEIGHT = 6 * scale;
    public static final int CAN_BULLET_WIDTH = 6 * scale;
    public static final int BOSS_BULLET_HEIGHT = 6 * scale;
    public static final int BOSS_BULLET_WIDTH = 6 * scale;
    
    //Player constants
    public static final int PLAYER_WIDTH = 15 * scale;
    public static final int PLAYER_HEIGHT = 10 * scale;
    public static final int START_Y = 280* scale;
    public static final int START_X = 270* scale;
    public static final int PLAYER_HP = 10;
    public static final int PLAYER_SHOT_COOLDOWN = 5;
    public static final int INVENCIBILITY_FRAMES = 30;
    
    //Enemy constants
    public static final int CAN_HEIGHT = 30* scale;
    public static final int CAN_WIDTH = 30* scale;
    public static final int CAN_HP = 10;
    public static final int WHEEL_HEIGHT = 30* scale;
    public static final int WHEEL_WIDTH = 30* scale;
    public static final int WHEEL_HP = 10;
    public static final int BOSS_HEIGHT = 30* scale;
    public static final int BOSS_WIDTH = 30* scale;
    public static final int BOSS_HP = 10;
    
    //HUD Constants
    public static final int HEART_SIZE = 10 * scale;
    public static final int TAB_WIDTH = 60 * scale;
    public static final int TAB_HEIGHT = 20 * scale;
    public static final int HEART_MAX = 10;
    public static final int SPACE_WIDTH = 160 * scale;
    public static final int SPACE_HEIGHT = 60 * scale;
    

    public static final int FONT_WIDTH = 10 * scale;
    
    //GameState Constants
    public static final int PLAYING_GAMESTATE = 0;
    public static final int LOST_GAMESTATE = -1;
    public static final int WON_GAMESTATE = 1;
    public static final int LEVEL_PASSED_GAMESTATE = 2;
    
    //Game Messages
    public static final String LOST_GAME_MESSAGE = "You Lost";
    public static final String WON_GAME_MESSAGE = "You Won";
    
    //Damage Constants
    public static final int PLAYER_BULLET_DAMAGE = 1;
    public static final int CAN_BULLET_DAMAGE = 1;
    public static final int WHEEL_DAMAGE = 1;
    public static final int BOSS_BULLET_DAMAGE = 1;
    
    
}