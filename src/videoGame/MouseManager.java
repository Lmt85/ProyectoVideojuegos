/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import maths.IntVector2;

/**
 * class inn charge of managing mouse inputs from a given window
 * implements MouseListener and MouseMotionListener so that it overrides them
 * with the wanted callbacks.
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class MouseManager implements MouseListener , MouseMotionListener {
    //booleans indicating if a button is pressed
    private boolean izquierdo; 
    private boolean derecho;
    
    //the position of the mouse as a vector
    IntVector2 position;
    
    /**
     * constructor, assigns a mouse position of (0,0)
     */
    public MouseManager(){
        position = new IntVector2();
    }
    
    /**
     * get mouse's x position
     * @return 
     */
    public int getX() {
        return position.getX();
    }
    
    /**
     * get mouse's y position
     * @return 
     */
    public int getY() {
        return position.getY();
    }
    
    /**
     * get mouse's  position as an IntVector
     * @return 
     */
    public IntVector2 getPosition() {
        return position;
    }
   
    /**
     * true if the left button is pressed
     * @return 
     */
    public boolean isIzquierdo(){
        return izquierdo;
    }
    
    /**
     * true if the right button is pressed
     * @return 
     */
    public boolean isDerecho(){
        return derecho;
    }
    
    /**
     * set izquierdo boolean
     * @param izquierdo 
     */
    public void setIzquierdo( boolean izquierdo){
        this.izquierdo = izquierdo;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }
    
    /**
     * Executes when a mouse button is pressed.
     * Updates buttons and position when so
     * @param e mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            izquierdo = true;
            position.setX(e.getX());
            position.setY(e.getY());
        }
        //System.out.println(e);
        if(e.getButton() == MouseEvent.BUTTON2){
            derecho = true;
        }
    }

    /**
     * Executes when a mouse button is released.
     * Updates buttons and position when so
     * @param e mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            izquierdo = false;
            position.setX(e.getX());
            position.setY(e.getY());
        }
        if(e.getButton() == MouseEvent.BUTTON2){
            derecho = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    /**
     * Executes when a mouse button is dragged.
     * Updates buttons and position when so
     * @param e mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
         if(e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK){
            izquierdo = true;
            position.setX(e.getX());
            position.setY(e.getY());
        }
        if(e.getButton() == MouseEvent.BUTTON2){
            derecho = true;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
}
