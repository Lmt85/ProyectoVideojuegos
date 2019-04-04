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
    public static BufferedImage br;
    public static BufferedImage ba;
    
    public static BufferedImage Amphetamines[];
    
    public static BufferedImage sprites;    //to store the sprites
    public static BufferedImage barAnim[];  //array that stores each frame of the animation
    
    public static BufferedImage ballSpriteSheet;
    public static BufferedImage ballAnim[];
    
    public static BufferedImage playerSpriteSheet;
    public static BufferedImage playerAnim[];
    //public static int frames;
    
    
    /**
     * loads all of the game assets as images
     */
    public static void init(){
        int barFrames = 3;     //states the amount of frames in the animation
        int ballFrames = 13;
        int playerFrames = 5;
                
        sprites = ImageLoader.loadImage("/images/bar_spritesheet.png"); //stores the spritesheet
        SpriteSheet spritesheet = new SpriteSheet(sprites);
                
        ballSpriteSheet = ImageLoader.loadImage("/images/cucurbitSprites.png");
        SpriteSheet ballSheet = new SpriteSheet(ballSpriteSheet);
        
        playerSpriteSheet = ImageLoader.loadImage("/images/beakerSprites.png");
        SpriteSheet playerSheet = new SpriteSheet(playerSpriteSheet);
        
        barAnim = new BufferedImage[barFrames];
        Amphetamines = new BufferedImage[4];
        ballAnim = new BufferedImage[ballFrames];
        playerAnim = new BufferedImage[playerFrames];
        
        for(int i = 0; i < barFrames; i++) {
            barAnim[i] = spritesheet.crop(i * 497/3, 0, 497/3,52); //IMPORTANT: THESE MEASUREMENTS CHANGE WITH EACH SPRITESHEET
        }
        
        for(int i = 0; i < ballFrames; i++) {
            ballAnim[i] = ballSheet.crop(i * 16, 0, 16,16); //IMPORTANT: THESE MEASUREMENTS CHANGE WITH EACH SPRITESHEET
        }
        
        for(int i = 0; i < playerFrames; i++) {
            playerAnim[i] = playerSheet.crop(i * 32, 0, 32,32); //IMPORTANT: THESE MEASUREMENTS CHANGE WITH EACH SPRITESHEET
        }
        
        background = ImageLoader.loadImage("/images/walter.png"); 
        br = ImageLoader.loadImage("/images/br.png"); 
        ba = ImageLoader.loadImage("/images/ba.png"); 
        Amphetamines[0] = ImageLoader.loadImage("/images/AmphetamineTab_1.png");
        Amphetamines[1] = ImageLoader.loadImage("/images/AmphetamineTab_2.png");
        Amphetamines[2] = ImageLoader.loadImage("/images/AmphetamineTab_3.png");
        Amphetamines[3] = ImageLoader.loadImage("/images/AmphetamineTab_4.png");
    }    
}
