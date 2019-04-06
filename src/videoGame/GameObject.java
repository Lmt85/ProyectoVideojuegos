/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.Graphics;

/**
 * This is an interface that serves as a guideline for each of the videogame's
 * object
 * @author Adrián Marcelo Suárez POnce
 */
public interface GameObject {    
    public void init();
    public void tick();
    public void render(Graphics g);
    public String toString();
}
