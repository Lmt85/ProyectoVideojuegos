package videoGame;

import java.awt.Graphics;
import maths.Vector2;
import maths.IntVector2;


/**
 * Abstract class to be used as supper class of all game objects
 * such that they have a x and y position.
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 27/01/2019
 * @version 1.0
 */
public abstract class Item {
    
    //position of an item 
    protected Vector2 position;
    
    /**
     * Set the initial values to create the item
     * @param x <b>x</b> position of the object
     * @param y <b>y</b> position of the object
    */
    public Item(double x, double y){
        this.position = new Vector2(x, y);
    }
    
    public Item(Vector2 position){
        this.position = position;
    }
    
    /**
     * Set the x value of an Item
     * @param x double <b>x</b> of the position of the Item
     */
    public void setX(double x) {
        this.position.setX(x);
    }
    
    /**
     * Set the y value of an Item
     * @param y double <b>y</b> of the position of the Item
     */
    public void setY(double y) {
        this.position.setY(y);
    }
    
    /**
     * set the item position as a DouVector instance;
     * @param position 
     */
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    
    
    
    /**
     * Returns the x value of an Item
     * @return x Integer <b>x</b> of the position of the Item
     */
    public double getX() {
        return position.getX();
    }
    
    /**
     * Returns the y value of an Item
     * @return y Integer <b>y</b> of the position of the Item
     */
    public double getY() {
        return position.getY();
    }

    public Vector2 getPosition() {
        return position;
    }
    
    /**
     * Abstract Method to update the Item every tick, to be override in subclass.
     */
    public abstract void tick();
    
    /**
     * Abstract Method To paint the item, to be override in subclass.
     * @param g <b>Graphics</b> object to paint the item
     */
    public abstract void render(Graphics g);
    
    /**
     * Abstract method that converts an item's properties to string
     */
    public abstract String toString();
}
