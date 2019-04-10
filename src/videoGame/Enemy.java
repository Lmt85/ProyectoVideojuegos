package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class is stores all the methods and variables related to the alien
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class Enemy extends Sprite implements GameObject{
    
    /**
     * Constructor with parameters, these parameters are passed directly to the Sprite class
     * @param x
     * @param y
     * @param visible
     * @param width
     * @param height
     * @param image 
     */
    public Enemy(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image){  
        super(position,speed,visible,width,height,image);
//        bomb = new Bomb(position,speed,false,width,height,Assets.bomb); //For each instance, it initializes a bomb, which isn't visible at first
    }

    /**
     * This method ticks the Alien, according to alienManager's current direction.
     * @param direction 
     */
    public void act(int direction) {
        position.setX(position.getX() + direction);
    }

    /**
     * This method sets a bomb as the instance's bomb
     * @param bomb 
     */
//    public void setBomb(Bomb bomb) {
////        this.bomb = bomb;
//    }

    /**
     * This method returns the bomb
     * @return instance's bomb.
     */
//    public Bomb getBomb() {
////        return bomb; return null
//    }

    @Override
    public void tick() {
       
    }
    
    /**
     * Renders alien if visible(not hit by laser)
     * @param g graphics parameter to paint
     */
    @Override
    public void render(Graphics g) {
        if (isVisible()) {
            g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), null);
        }  
    }

    @Override
    public void init() {
    }
    
    /**
     * Returns a string of the an object's important variables. Used in game's save method.
     * @return a String with all the variables converted to string
     */
    @Override
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + visible + "\n");    
    }
    
    /**
    * This class is stores all the methods and variables related to an alien's bomb
    * @author Adrián Marcelo Suárez Ponce
    * @date 9/03/2019
    * @version 1.0
    */
    public class Bomb extends Sprite implements GameObject{
            public Bomb(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image){ 
            super(position,speed,visible,width,height,image);
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
}