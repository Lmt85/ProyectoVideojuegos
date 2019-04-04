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
        Game g = new Game("Juego", 1500, 900); //creates a game which  contains it's own thread
        g.start(); //starts the game thread and thus the game itself
    } 
    
}
