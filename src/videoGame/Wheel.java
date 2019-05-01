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



    public Wheel(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game, int hp){  
        super(position,speed,visible,width,height,image,game,hp);
        this.wheelAnim = new Animation(Assets.wheels, 100);        
        //this.maxHp = hp;
        
    }
    
    @Override
    public void tick() {
        if(isVisible()){
            if(game.getPlayer().getPosition().getX() > this.getPosition().getX()) this.setSpeed(this.getSpeed().getX() + .2, this.getSpeed().getY());
            if(game.getPlayer().getPosition().getX() < this.getPosition().getX()) this.setSpeed(this.getSpeed().getX() - .2, this.getSpeed().getY());
            if(game.getPlayer().getPosition().getY() > this.getPosition().getY()) this.setSpeed(this.getSpeed().getX(), this.getSpeed().getY() + .2);
            if(game.getPlayer().getPosition().getY() < this.getPosition().getY()) this.setSpeed(this.getSpeed().getX(), this.getSpeed().getY() - .2);
            wheelAnim.tick();
        } else {
            this.setSpeed(getSpeed().scalar(.9));
        }
        this.setPosition(this.getPosition().getX() + this.getSpeed().getX(), this.getPosition().getY() + this.getSpeed().getY());
    }
    
    /**
     * Renders alien if visible(not hit by laser)
     * @param g graphics parameter to paint
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(wheelAnim.getCurrentFrame(), (int)position.getX(), (int)position.getY(), width, height, null);
        //g.drawRect((int) position.getX(), (int) position.getY(), width, height);
        /*
        if(maxHp != hp){
            g.fillRect((int) (position.getX() + 5), (int)position.getY() - 10,  (int) (width - 10) * (this.hp / maxHp), 5);
        }
        g.fillRect((int) (position.getX() + 5), (int)position.getY() - 10,  (int) ((width - 10) * (this.hp / maxHp)), 5);
        */
        
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