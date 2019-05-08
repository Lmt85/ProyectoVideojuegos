/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import maths.Vector2;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


/**
 *
 * @author marcelosuarez
 */
public class Block extends Sprite implements GameObject{
    
    private Vector2 normal;
    
    public Block(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image,Game game) {
            super(position,speed,visible,width,height,image,game);
    }
    

    @Override
    public void init() {
        
        
        
    }

    @Override
    public void tick() {
        
        
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getImage(),(int)getPosition().getX(),(int)getPosition().getY(),width,height, null);
        
    }
}
