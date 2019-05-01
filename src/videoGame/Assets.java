package videoGame;

import java.awt.image.BufferedImage;
/**
 * encapsulates the game assets and provides the means to statically access them.
 * Also loads the assets.
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class Assets {
    public static BufferedImage background; // to store background image

    // Stores each sprite
    public static BufferedImage alien;
    public static BufferedImage shot;
    public static BufferedImage bomb;
    public static BufferedImage player;
    public static BufferedImage explosion;
    public static BufferedImage map;
    public static BufferedImage wall;
    
    public static BufferedImage heartFull;
    public static BufferedImage heartHalf;
    public static BufferedImage heartEmpty;
    public static BufferedImage hud;
    public static BufferedImage sand;
    public static BufferedImage wheel;
    public static BufferedImage can;

    
    /**
     * loads all of the game assets as images
     */
    public static void init(){
        background = ImageLoader.loadImage("/images/background.png"); 
        map = ImageLoader.loadImage("/images/Map.png");
        
        wall = ImageLoader.loadImage("/images/Wall.png");
        alien = ImageLoader.loadImage("/images/alien.png"); 
        shot = ImageLoader.loadImage("/images/shot.png"); 
        bomb = ImageLoader.loadImage("/images/bomb.png"); 
        player = ImageLoader.loadImage("/images/player.png");
        explosion = ImageLoader.loadImage("/images/explosion.png");
        
        heartFull = ImageLoader.loadImage("/images/heartFull.png");
        heartHalf = ImageLoader.loadImage("/images/HeartHalf.png");
        heartEmpty = ImageLoader.loadImage("/images/HeartEmpty.png");
        hud = ImageLoader.loadImage("/images/hud.png");
        sand = ImageLoader.loadImage("/images/Sand.png");
        can = ImageLoader.loadImage("/images/can.png");
        wheel = ImageLoader.loadImage("/images/wheel.png");
    }    
}
