package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import videoGame.GameObject;
import maths.Vector2;
import videoGame.Player;
import videoGame.Sprite;

/**
 * This class is stores all the methods and variables related to the alien
 * @author Adrián Marcelo Suárez Ponce A01197108
 * @date 28/01/2019
 * @version 1.0
 */
public class Wheel extends Enemy implements GameObject{
    
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

    private Animation wheelAnim;
    private Random gen;



    public Wheel(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game, int hp){  
        super(position,speed,visible,width,height,image,game,hp);
        
        gen = new Random();
        mass = gen.nextGaussian();
        mass *= 0.25;
        mass += 1;
        this.width = (int)(mass*width);
        this.height = (int)(mass*height);
        this.wheelAnim = new Animation(Assets.wheels, 100);
        topSpeed = 4;
        //this.maxHp = hp;
        
    }
    
    @Override
    public void tick() {
            Vector2 force = getPosition().sub(game.getPlayer().getPosition());
            force.set(force.norm().scalar(0.5));
            this.applyForce(force);
            
            setSpeed(getSpeed().add(getAcceleration()));
            getSpeed().limit(topSpeed);
            setPosition(getPosition().add(getSpeed()));
            setAcceleration(getAcceleration().scalar(0));
            
            wheelAnim.tick();
//        }
    }
    
    /**
     * Renders alien if visible(not hit by laser)
     * @param g graphics parameter to paint
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(wheelAnim.getCurrentFrame(), (int)position.getX(), (int)position.getY(), width, height, null);
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