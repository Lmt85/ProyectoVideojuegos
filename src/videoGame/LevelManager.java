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
    LinkedList<Sprite> level;
    Game game;
    
    public LevelManager(Game game) {
        level = new LinkedList<>();
        this.game = game;
    }
    
    public void render(Graphics g) {
        for(int i = 0; i < level.size(); i++) {
            if(game.getCamera().getBounds().contains(level.get(i).getBounds())||game.getCamera().getBounds().intersects(level.get(i).getBounds()) ) level.get(i).render(g);
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
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                       
                if(red == 255) level.add(new Block(new Vector2(i * Commons.GRID_WIDTH, j * Commons.GRID_HEIGHT), new Vector2(0,0),true, Commons.BLOCK_WIDTH,Commons.BLOCK_HEIGHT,Assets.wall,game));
                if(blue == 255) game.getEnemyManager().getEnemies().add(new Wheel(new Vector2(i * Commons.GRID_WIDTH, j * Commons.GRID_HEIGHT), new Vector2(0,0),true, Commons.ALIEN_WIDTH,Commons.ALIEN_HEIGHT,Assets.alien,game));
                if(green == 255) game.getEnemyManager().getCans().add(new Can(new Vector2(i * Commons.GRID_WIDTH, j * Commons.GRID_HEIGHT), new Vector2(0,0),true, Commons.ALIEN_WIDTH,Commons.ALIEN_HEIGHT,Assets.alien,game));
                
            }
        }
    }

    public LinkedList<Sprite> getLevel() {
        return level;
    }

    public void setLevel(LinkedList<Sprite> level) {
        this.level = level;
    }
       
}
