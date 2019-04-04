/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.image.BufferedImage;

/**
 * Class that deals with the Animations
 * @author Adrián Marcelo Suárez Ponce A00823198
 * @date 27/02/2019
 * @version 1.0
 */
public class Animation {
    private int time;      // sets the time of every frame
    private int index;      // to get the index of the next frame to paing
    private long lastTime;      // sets the time of every frame
    private long timer;     // to get the index of the next frame to paing
    private BufferedImage[] frames; // to store every frame of the animation
    
    /**
     * Constructor for the animation class
     * @param frames an <code>array</code> of images
     * @param time an <code>int</code> value that states the speed of every frame in milliseconds
     */
    public Animation(BufferedImage[] frames, int time) {
        this.time = time;  
        // stores the time
        
        this.frames = frames;                   // stores frames
        
        index = 0;                              // initializes the index
        timer = 0;                              // initializes the timer
        lastTime = System.currentTimeMillis();  // getting the initial time in milliseconds
    }
    /**
     * Gets the current frame of the animation to paint
     * @return the <code>BufferedImage</code> of the corresponding frame to paint
     */
    public BufferedImage getCurrentFrame() {
        return frames[index];
    }
    
    /**
     * To update the animation in order to get the correct frame
     */
    public void tick() {
        // acumulates the time from the previous tick to the current one
        timer += System.currentTimeMillis() - lastTime;
        // updates last Time to store the current time;
        lastTime = System.currentTimeMillis();
        // checks the timer in order to increase the index
        if(timer > time) {
            index++;
            timer = 0; //resets the timer
            
            if(index >= frames.length) {
                index = 0;
            }
        }
    }
}
