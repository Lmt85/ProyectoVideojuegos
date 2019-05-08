package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import videoGame.GameObject;
import videoGame.Sprite;

/**
 * This class is stores all the methods and variables related to the alien
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public abstract class Enemy extends Sprite implements GameObject{
    protected LinkedList<Projectile> bullets;
    protected int hp;
    protected int maxHp;
    protected int explotionseconds;
    protected Animation explotion;
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
    public Enemy(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game, int hp){  
        super(position,speed,visible,width,height,image,game);
        bullets = new LinkedList<>();
        this.hp = hp;
        this.maxHp = hp;
        this.explotionseconds=20;
        this.explotion = new Animation(Assets.explosions, 100);
    }

    @Override
     public void tick(){
         
     }
    
    /**
     * Renders alien if visible(not hit by laser)
     * @param g graphics parameter to paint
     */
    @Override
    public void render(Graphics g) {
        if(getExplotionSec()>0){
            g.drawImage(explotion.getCurrentFrame(), (int)position.getX(), (int)position.getY(), width, height, null);            
        }else{
            g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), width, height, null);

        }
        g.drawRect((int) position.getX(), (int) position.getY(), width, height);
        
        //g.fillRect((int) (position.getX() + 5), (int)position.getY() - 10,  (int) ((width - 10)), 5);
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

    public LinkedList<Projectile> getBullets() {
        return bullets;
    }

    public void setBullets(LinkedList<Projectile> bullets) {
        this.bullets = bullets;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    
    public int getExplotionSec() {
        return explotionseconds;
    }

    public void setExplotionSec(int es) {
        this.explotionseconds = es;
    }
    
}