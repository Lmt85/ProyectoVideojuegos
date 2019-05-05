/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import maths.Vector2;
import java.util.Random;


/**
 * This class sets the standards for a tangible game object.
 * @author Adrian Marcelo Suárez Ponce A01197108
 * @date 28/01/2019
 * @version 1.0
 */
public abstract class Sprite implements GameObject {
    public enum Orientation {

        NORTH(1), EAST(2), SOUTH(3), WEST(4), NO_O(0);
        
        private int dir;
        private Orientation(final int dir) {
            this.dir = dir;
        }
        

        protected Vector2 speed(int value) {
            Vector2 vec = new Vector2();
            switch(this) {
                case NORTH: vec.set(0, -value);
                break;
                case EAST: vec.set(value,0);
                break;
                case SOUTH: vec.set(0,value);
                break;
                case WEST: vec.set(-value,0);
                break;
                case NO_O: vec.set(0,0);
            }
            return vec;
        }
        
       public static Orientation getRandomOrientation(){
           Random random = new Random();
           return values()[random.nextInt(values().length)];
       }       
                
    }
    protected boolean visible;      //to store the visibility
    protected BufferedImage image;  //to store the image
    protected Vector2 position;
    protected Vector2 speed;        //to store the speed vector
    protected Vector2 acceleration;
    protected int width;            //to store the object's width
    protected int height;           //to store the object's width
    protected Orientation o;        //to store the object's orientation
    protected Game game;            //to store a reference to the game
    
    public Sprite(Vector2 position, Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game) {
        this.position = position;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.image = image;
        this.visible = visible;
        this.game = game;
        this.acceleration = new Vector2(0,0);
    }
    
    /**
     * Returns if the object is visible, if it exists
     * @return the visibility of the object
     */
    public boolean onScreen() {
        if(game.getCamera().getBounds().intersects(this.getBounds()) || game.getCamera().getBounds().contains(this.getBounds())){
            return true;
        } else return false;
    }

    /**
     * Sets the visibility of the object to the param's value
     * @param visible 
     */
    protected void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Sets the object's image to the param's value
     * @param image 
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Gets the image of the object.
     * @return 
     */
    public BufferedImage getImage() {
        return image;
    }
    
    /**
     * Sets the position of the object as a vector
     * @param pos 
     */
    public void setPosition(Vector2 pos){
        this.position = pos;
    }
    
    public void setPosition(double x,double y) {
        this.position = new Vector2(x,y);
    }
    
    /**
     * Sets the speed of the object as the received vector
     * @param speed 
     */
    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }
    
    public void setSpeed(double x,double y) {
        this.speed = new Vector2(x,y);
    }
    /**
     * Returns the speed as a vector
     * @return Vector2
     */
    public Vector2 getSpeed() {
        return speed;
    }
    
    /**
     * Gets the position of the object as a vector
     * @return Vector2 position
     */
    public Vector2 getPosition(){
        return position;
    }
    
    public void setOrientation(Orientation o) {
        this.o = o;
    }
    
    public Orientation getOrientation() {
        return o;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Orientation getO() {
        return o;
    }

    public void setO(Orientation o) {
        this.o = o;
    }
        
    public Rectangle getBounds() {
        return new Rectangle((int)this.getPosition().getX(),(int)this.getPosition().getY(),this.getWidth(),this.getHeight());
    }
    
    public boolean isVisible() {
        return visible;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }
    
}
