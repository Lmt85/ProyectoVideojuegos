/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Implements the player of the game, which is only able to move
 * @author marcelosuarez
 */
public class Player extends Sprite implements GameObject {
    private Game game;      // References the game
    
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
    public Player(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game) {
        super(position, speed, visible, width, height, image);
        this.game = game;
        
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
            if (isVisible()) {
                position.setY(position.getY() + 1);
                if (position.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {
                    setVisible(false);
                }
            }
        }

        /**
         * Renders the bomb if visible
         * @param g 
         */
        @Override
        public void render(Graphics g) {
            if (isVisible()) {
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
    }

    @Override
    public void tick() {
        setSpeed(0,0);
        // To change player speed left 
        if (game.getKeyManager().left) {
            speed.setX(-2);
        }
        
        // To change player speed right
        if (game.getKeyManager().right) {
            speed.setX(2);
        }
        
        // To change player speed up
        if (game.getKeyManager().up) {
            speed.setY(-2);
        }
        
        // To change player speed down
        if (game.getKeyManager().down) {
            speed.setY(2);
        }
        
        //moves player
        setPosition(getPosition().add(getSpeed()));
        
        
        // So player doesn't go past the left border
        if (position.getX() <= 2) {
            position.setX(2);
        }
        
        // So the player doesn't go past the right border
        if (position.getX() >= Commons.BOARD_WIDTH - Commons.PLAYER_WIDTH) {
            position.setX(Commons.BOARD_WIDTH - Commons.PLAYER_WIDTH);
        }
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
}
