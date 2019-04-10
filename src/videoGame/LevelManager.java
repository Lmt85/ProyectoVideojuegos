/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import maths.Vector2;

/**
 *
 * @author Adrián Marcelo Suárez Ponce
 */
public class LevelManager {
    LinkedList<Sprite> level = new LinkedList<>();
    
    public void render(Graphics g) {
        for(int i = 0; i < level.size(); i++) {
            level.get(i).render(g);
        }
    }
    
    public void addTile(Block b) {
        level.add(b);
    }
    
    public void loadLevel(BufferedImage map) {
        for(int i = 0; i < map.getWidth(); i++) {
            for(int j = 0; j < map.getHeight(); j++) {
                
                int pixel = map.getRGB(i,j);
                int red = (pixel >> 16) & 0xff;
                       
                if(red == 255) level.add(new Block(new Vector2(i * 32, j * 32), new Vector2(0,0),true, 32,32,Assets.wall));
            }
        }
    }
}
