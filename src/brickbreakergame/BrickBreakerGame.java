/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreakergame;

import videoGame.Game;

/**
 *
 * @author carlo
 */
public class BrickBreakerGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl","true");
        Game g = new Game(); //creates a game which  contains it's own thread
        g.start(); //starts the game thread and thus the game itself
    } 
    
}
