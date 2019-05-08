package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class defines each of the methods of the shot
 * @author Adrián Marcelo Suárez Ponce A01197108
 * @date 9/03/2019
 * @version 1.0
 */
public class Projectile extends Sprite implements GameObject, Commons {
    protected int damage;
    public Projectile(maths.Vector2 position, maths.Vector2 speed,  boolean visible, int width, int height, BufferedImage image, int damage,Game game){ 
        super(position, speed, visible,width,height,image, game);
        this.damage = damage;
    }

    @Override
    public void init() {
    }

    /**
     * Method which ticks the Shot, moving it upwards to the screen.
     * If it is not visible and the space key is pressed, it sets it visible
     * sets it to the center of the player and plays a laser sound
     * If it is already visible it moves it upwards until it reaches the upper
     * border or an alien
     */
    @Override
    public void tick() {
        
    }

    /**
     * Renders the shot with the current positions.
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        
    }
    
    
    /**
     * Returns a string of the an object's important variables. Used in game's save method.
     * @return a String with all the variables converted to string
     */
    @Override
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + visible + "\n");
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    
}
