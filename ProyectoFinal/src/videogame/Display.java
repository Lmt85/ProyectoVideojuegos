/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author luismariotrujillo
 */
public class Display {
    private JFrame jframe;

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setJframe(JFrame jframe) {
        this.jframe = jframe;
    }

    public JFrame getJframe() {
        return jframe;
    }
    private Canvas canvas;

    private String title;
    private int width;
    private int height;

    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    public void createDisplay() {
        //create the app window
        jframe = new JFrame(title);

        jframe.setSize(width, height);

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

        //canvas setting and size
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        //adding the canvas to the app window and get right dimension
        jframe.add(canvas);
        jframe.pack();

    }

    public Canvas getCanvas() {
        return canvas;
    }

    
}
