/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author luismariotrujillo
 */
public class Shot extends Item {

    private BufferedImage sprite;
    private int width;
    private int height;
    private Game game;
    private Player player;
    private Animation animation;
    
    private Rectangle rectangle;
    
    private int speed;

    /**
     *
     * @return
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     *
     * @param x
     */
    @Override
    public void setX(int x) {
        this.x = x;
        this.rectangle.setLocation(this.x, this.y);
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *
     * @return
     */
    @Override
    public int getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    @Override
    public void setY(int y) {
        this.y = y;
        //this.rectangle.setLocation(this.x, this.y);
        this.rectangle.setLocation(this.x, this.y);
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return
     */

    /**
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game
     * @param player
     */
    public Shot(int x, int y, int width, int height, int speed, Game game, Player player) {
        super(x, y);
        sprite = Assets.shot;
        this.width = width;
        this.height = height;
        this.game = game;
        this.player = player;
        this.rectangle = new Rectangle(x, y, width, height);
        this.animation = new Animation(Assets.shotSprite, 100);
        //le definimos speed para poder incrementarla luego
       this.speed = speed;
      
    }

    /**
     *
     */
    @Override
    public void tick() {
                //Mis condicionales para que el player no salga de la pantalla
   
        setY(getY()-speed);
       animation.tick();
       
        if (getY() < -50) {
            setSpeed(0);
            
        }
        

    }
    
    
    public boolean collision(Rectangle colisionero){
        if(colisionero.intersects(rectangle)){
            hitEnemy();
            return true;
        }
        return false;
    }
    
    
    /**
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
    public void hitEnemy(){
        setX(-100);
        setY(-100);
        setSpeed(0);
    }
}
