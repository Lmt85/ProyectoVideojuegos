/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.Graphics;
import maths.IntVector2;

/**
 * Provides the means to check collisions with other colliders
 * Contains a position and a radius
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class CircleCollider{
    
    private IntVector2 position;
    private int radious;
    private boolean collided; //true if at collision
    
    /**
     * Constructor, requires an position in x and y and a radius for the circle 
     * collider.
     * @param x
     * @param y
     * @param r 
     */
    public CircleCollider(int x, int y, int r) {
       position = new IntVector2(x, y);
       this.radious = r;
    }
    
    /**
     * Constructor, requires a IntVexor for the collider's position and a radius
     * @param position
     * @param r 
     */
    public CircleCollider(IntVector2 position, int r) {
       this.position = position;
       this.radious = r;
    }
    
    /**
     * returns the collider's position
     * @return 
     */
    public IntVector2 getPosition() {
        return position;
    }
    
    /**
     * returns the circle radius
     * @return 
     */
    public int getRadious() {
        return radious;
    }
    
    /**
     * returns the collided status of the collider
     * @return 
     */
    public boolean isCollided() {
        return collided;
    }
    
    /**
     * sets the collider's position to a IntVector
     * @param position 
     */
    public void setPosition(IntVector2 position) {
        this.position.setX(position.getX());
        this.position.setY(position.getY());
    }
    
    /**
     * sets the radius of the circle collider
     * @param radious 
     */
    public void setRadious(int radious) {
        this.radious = radious;
    }
    
    /**
     * checks if the collider collides with other circle collider, if so, 
     * returns true.
     * @param other the other circle collider witch which collision will be 
     * checked
     * @return 
     */
    public boolean collideWith(CircleCollider other){
        if(position.dist(other.position) <= radious + other.getRadious()){
            collided = true;
            return true;
        }
            return  false;
    }
    
    /**
     * Checks if the circle colliders contains a point
     * @param otherPos the IntVector of the  point to be checked
     * @return 
     */
    public boolean conatins(IntVector2 otherPos){
        return position.dist(otherPos) <= radious;
    }
    
    /**
     * resets the collided status of the collider to false
     */
    public void resetCollided(){
        collided = false;
    }
}
