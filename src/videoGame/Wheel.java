package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import videoGame.GameObject;
import videoGame.Player;
import videoGame.Sprite;

/**
 * This class is stores all the methods and variables related to the alien
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class Wheel extends Sprite implements GameObject{
    
    /**
     * Constructor with parameters, these parameters are passed directly to the Sprite class
     * @param position
     * @param speed
     * @param x
     * @param y
     * @param visible
     * @param width
     * @param height
     * @param image 
     * @param player 
     */
    public Wheel(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image){  
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
    
    public void tick(Player gamer) {
       if(gamer.getPosition().getX() > this.getPosition().getX()) this.setSpeed(this.getSpeed().getX() + .2, this.getSpeed().getY());
       if(gamer.getPosition().getX() < this.getPosition().getX()) this.setSpeed(this.getSpeed().getX() - .2, this.getSpeed().getY());
       if(gamer.getPosition().getY() > this.getPosition().getY()) this.setSpeed(this.getSpeed().getX(), this.getSpeed().getY() + .2);
       if(gamer.getPosition().getY() < this.getPosition().getY()) this.setSpeed(this.getSpeed().getX(), this.getSpeed().getY() - .2);
       this.setPosition(this.getPosition().getX() + this.getSpeed().getX(), this.getPosition().getY() + this.getSpeed().getY());
    }
    
    /**
     * Renders alien if visible(not hit by laser)
     * @param g graphics parameter to paint
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), width, height, null);
        g.drawRect((int) position.getX(), (int) position.getY(), width, height);
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
}