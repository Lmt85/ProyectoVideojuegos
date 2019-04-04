/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author luismariotrujillo
 */
public class Assets {
    

    public static BufferedImage background;	//	to	store	background	image
    public static BufferedImage player;					//	to	store	the	player	image
    //Agregamos un bufferedimage para darle el efecto del rebote
    public static BufferedImage playerHurt;
    public static BufferedImage playerHurt2;
    public static BufferedImage enemy;
    public static BufferedImage bomb;
    public static BufferedImage shot;
    public static BufferedImage lifes;
    public static BufferedImage gameOver;
    public static BufferedImage win;
    //
    public static BufferedImage sprites;
    public static BufferedImage alienSprite[];
    public static BufferedImage bombSprite[];
    public static BufferedImage shotSprite[];
    //public static BufferedImage playerUp[];
    //public static BufferedImage playerDown[];
    //public static BufferedImage playerLeft[];
    //public static BufferedImage playerRight[];
    //
    
    public static SoundClip oof;
    public static SoundClip ding;
    public static BufferedImage life;

    /**
     * initializing	the	images	of	the	game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/space.jpg");
        player = ImageLoader.loadImage("/images/ship.png");
        enemy = ImageLoader.loadImage("/images/alien.png"); 
        shot = ImageLoader.loadImage("/images/shots2.png");
        lifes = ImageLoader.loadImage("/images/heart.png");
        bomb = ImageLoader.loadImage("/images/bombita.png");
        //definimos otra bufferedimage para hacerlo hurt
     
        oof = new SoundClip("/sounds/UGH HUH.wav");
        ding = new SoundClip("/sounds/ding.wav");
        win = ImageLoader.loadImage("/images/youwin.jpg");
        gameOver = ImageLoader.loadImage("/images/gameover.jpg");
        sprites = ImageLoader.loadImage("/images/ballSprite.png");
        

        
        
        
        SpriteSheet spritesheet = new SpriteSheet(sprites);
        alienSprite = new BufferedImage[9];
        bombSprite = new BufferedImage[9];
        shotSprite = new BufferedImage[9];

        for (int i = 0; i < 9; i++) {
            alienSprite[i] = spritesheet.crop(i * 64, 0, 64, 64);
            bombSprite[i] = spritesheet.crop(i * 64, 64, 64, 64);
            shotSprite[i] = spritesheet.crop(i * 64, 128, 64, 64);
        }


    }
    
}
