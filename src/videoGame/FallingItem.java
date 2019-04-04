/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import com.sun.glass.ui.Size;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author carlo
 */
public class FallingItem extends Item{
    
    private int type;
    private int speed;
    public static int size = 60;
    private Rectangle collider;
    private Game game;
    private Player player;
    
    /**
     * 
     * @param x
     * @param y
     * @param type
     * @param game 
     */
    public FallingItem(double x, double y, int type, Game game) {
        super(x, y);
        this.speed = 4;
        this.type = type;
        this.game = game;
        collider = new Rectangle((int)x, (int)y, size, size);
    }
    
    public void init(Player player){
        this.player = player;
    }
    
    @Override
    public void tick() {
        position.setY(position.getY() + speed);
        
        collider.x = (int)position.getX();
        collider.y = (int)position.getY();
    }

    @Override
    public void render(Graphics g) {
        if(type == 1)
            g.drawImage(Assets.br, (int)position.getX(), (int)position.getY(), collider.width, collider.height, null);
        if(type == 2)
            g.drawImage(Assets.ba, (int)position.getX(), (int)position.getY(), collider.width, collider.height, null);
        g.drawRect(collider.x, collider.y, collider.width, collider.height);
    }

    @Override
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + type + " " + speed + "\n");
    }

    public Rectangle getCollider() {
        return collider;
    }    

    public int getType() {
        return type;
    }   
}
