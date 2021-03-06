package videoGame;

public interface Commons {
    public static int scale = 2;
    
    //Game constants
    public static final int BOARD_WIDTH = 358 * scale;
    public static final int BOARD_HEIGHT = 350 * scale;
    public static final int GRID_WIDTH = 32 * scale;
    public static final int GRID_HEIGHT = 32 * scale;
            
    
    public static final int BLOCK_WIDTH = 32 * scale;
    public static final int BLOCK_HEIGHT = 32 * scale;
    public static final int BORDER_RIGHT = 30 * scale;
    public static final int BORDER_LEFT = 5 * scale;
    public static final int GO_DOWN = 15;
    public static final int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    public static final int CHANCE = 5;
    public static final int DELAY = 17;
    public static final int H_SPACE = 6* scale;
    public static final int V_SPACE = 1* scale;

    //Bomb constants
    public static final int BOMB_HEIGHT = 6* scale;
    public static final int BOMB_WIDTH = 6* scale;
    
    //Player constants
    public static final int PLAYER_WIDTH = 15 * scale;
    public static final int PLAYER_HEIGHT = 10 * scale;
    public static final int START_Y = 280* scale;
    public static final int START_X = 270* scale;
    
    //Alien constants
    public static final int ALIEN_HEIGHT = 30* scale;
    public static final int ALIEN_WIDTH = 30* scale;
    public static final int ALIEN_INIT_X = 150* scale;
    public static final int ALIEN_INIT_Y = 5* scale;    
    
    //Hud Constants
    public static final int HEART_SIZE = 10 * scale;
    public static final int TAB_WIDTH = 60 * scale;
    public static final int TAB_HEIGHT = 20 * scale;
    public static final int HEART_MAX = 10;
}