/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import maths.Vector2;

/**
 * Implements the player of the game, which is only able to move
 * @author Adrián Marcelo Suárez Ponce A01197108
 */
public class Player extends Sprite implements GameObject {
    public PlayerBullet p;
    
    
    /**
     * Constructor with parameters, these parameters are passed directly to the Sprite class
     * @param x
     * @param y
     * @param visible
     * @param width
     * @param height
     * @param image
     * @param game 
     */
    public Player(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image) {
        super(position, speed, visible, width, height, image);
        
    }
    
    public class PlayerBullet extends Projectile implements GameObject{
            public PlayerBullet(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image){ 
            super(position,speed,visible,width,height,image,50);
        }

        @Override
        public void init() {
            
        }

        /**
         * This method ticks the Bomb, only if visible, which spawns it in it's alien's position and moves
         * it straight down until it reaches the bottom
         */
        @Override
        public void tick() {
            setPosition(getPosition().add(getSpeed()));
            if (getPosition().getX() >= Commons.BOARD_WIDTH + BOMB_WIDTH || 
                getPosition().getX() < 0 - Commons.BOMB_WIDTH ||
                getPosition().getY() < 0 - Commons.BOMB_HEIGHT ||
                getPosition().getY() >= Commons.BOARD_HEIGHT + Commons.BOMB_HEIGHT) 
                    setVisible(false);
        }

        /**
         * Renders the bomb if visible
         * @param g 
         */
        @Override
        public void render(Graphics g) {
            if (isVisible()) {
                g.drawRect((int)position.getX(),(int) position.getY(), width, height);
                g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), null);
            }
        }
        
        /**
         * Converts the object to a string with most important attributes.
         * @return a string with most important attributes.
         */
        @Override
        public String toString() {
            return String.valueOf(position.getX() + " " + position.getY() + " " +  visible + "\n");
        }
    }
    
    @Override
    public void init() {
        p = new PlayerBullet(new Vector2(), new Vector2(), false, Commons.BOMB_WIDTH, Commons.BOMB_HEIGHT,Assets.bomb);
    }

    @Override
    public void tick() {
        
        // moves player
        setPosition(getPosition().add(getSpeed()));
        
        // player boundaries
        if(getPosition().getX() >= Commons.BOARD_WIDTH - Commons.PLAYER_WIDTH) setPosition(Commons.BOARD_WIDTH - Commons.PLAYER_WIDTH,getPosition().getY());
        if(getPosition().getX() < 0) setPosition(0,getPosition().getY());    
        if(getPosition().getY() < 0) setPosition(getPosition().getX(),0);
        if(getPosition().getY() >= Commons.BOARD_HEIGHT - Commons.PLAYER_HEIGHT) setPosition(getPosition().getX(),Commons.BOARD_HEIGHT - Commons.PLAYER_HEIGHT);
        
        System.out.println(o);
        
    }

    /**
     * Renders the player if not hit by a bomb
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        if (isVisible()) { 
            g.drawImage(getImage(), (int)position.getX(), (int) position.getY(), null);
        }
    }
    
    /**
    * Converts the object to a string with most important attributes.
    * @return a string with most important attributes.
    */
    @Override
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + visible + " " + speed.getX() + "\n");
    }

    public PlayerBullet getP() {
        return p;
    }

    public void setP(PlayerBullet p) {
        this.p = p;
    }
    
    
}
