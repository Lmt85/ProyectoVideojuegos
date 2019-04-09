package videoGame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 * Provides he necessary components to create a window.
 * It is composed of a JFrame and a canvas to accompany the JFrame.
 * 
 * @author Adrián Marcelo Suárez Ponce A01197108
 * @date 28/01/2019
 * @version 1.0
 */
public class Display {
    private JFrame jframe; //Java swing class for creating a window
    private Canvas canvas; //clas used to display the images
    
    private String title; //window title
    
    //size of the window in display pixels.
    private int width;
    private int height;
    
    /**
    * initializes the values for the application game
    * @param title to display the title of the window
    * @param width to set the width
    * @param height to set the height
    */
    Display(String title, int width, int height)
    {
        this.title = title; 
        this.width = width; 
        this.height = height;
        createDisplay(); //initialization of the frame and canvas
    }
    
    /**
    * create the frame and the canvas and add the canvas to the window frame
    */
    public void createDisplay(){
        jframe = new JFrame(title);
        jframe.setSize(width, height);
        
        //setting as not resizable, visible and posible to close
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null); //set location of the window at the center of the screen
        jframe.setVisible(true);
        
        //Canvas settings
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width,height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        
        // Adding the canvas to the frame window and packing to get the right dimension       
        jframe.add(canvas);
        jframe.pack();
    }
    
    /**
    * to get the canvas of the game
    * @return the <b>Canvas</b>
    */
    public Canvas getCanvas(){
        return this.canvas;
    }
    
    /**
    * to get the frame of the game
    * @return the <b>JFrame</b>
    */
    public JFrame getJframe() {
        return jframe;
    }
    
    /**
     * 
     * @param bf
     * @param g
     * @return 
     */
    public boolean render(BufferStrategy bf, Graphics g){
        return false;
    }
    
    
}
