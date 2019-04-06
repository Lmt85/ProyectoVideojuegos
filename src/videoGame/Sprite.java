/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.Image;
import java.awt.image.BufferedImage;
import maths.Vector2;


/**
 * This class sets the standards for a tangible game object.
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class Sprite {
    protected boolean visible;      //to store the visibility
    protected BufferedImage image;  //to store the image
    protected Vector2 position;     //to store the position
    protected Vector2 speed;        //to store the speed vector
    protected int width;            //to store the object's width
    protected int height;           //to store the object's width

    
    public Sprite(Vector2 position, Vector2 speed, boolean visible, int width, int height, BufferedImage image) {
        this.position = position;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.image = image;
        this.visible = visible;
    }
    
    /**
     * Returns if the object is visible, if it exists
     * @return the visibility of the object
     */
    public boolean isVisible() {
        return visible;
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
    
    
}
