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
import java.util.Random;

/**
 *
 * @author Adrián Marcelo Suárez Ponce
 */
public class LevelManager {
    LinkedList<Sprite> level;
    Game game;
    Random gen = new Random();
    
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
                
//                double h = gen.nextGaussian();
//                double w = h;
//                w = h *= 2;
                
                if(red == 255 && blue == 255) {
//                    w += Commons.ALIEN_WIDTH;
//                    h += Commons.ALIEN_HEIGHT;
                    game.getEnemyManager().getEnemies().add(new Boss(new Vector2(i * Commons.GRID_WIDTH, j * Commons.GRID_HEIGHT), new Vector2(0,0),true,(int) Commons.ALIEN_WIDTH,Commons.ALIEN_HEIGHT,Assets.pirate,game,20));
                }     
                else if(red == 255) level.add(new Block(new Vector2(i * Commons.GRID_WIDTH, j * Commons.GRID_HEIGHT), new Vector2(0,0),true, Commons.BLOCK_WIDTH,Commons.BLOCK_HEIGHT,Assets.wall,game));
                else if(blue == 255) {
//                    w += Commons.ALIEN_WIDTH;
//                    h += Commons.ALIEN_HEIGHT;
                    game.getEnemyManager().getEnemies().add(new Wheel(new Vector2(i * Commons.GRID_WIDTH, j * Commons.GRID_HEIGHT), new Vector2(0,0),true, Commons.ALIEN_WIDTH,Commons.ALIEN_HEIGHT,Assets.alien,game,5));
                }
                else if(green == 255) game.getEnemyManager().getEnemies().add(new Can(new Vector2(i * Commons.GRID_WIDTH, j * Commons.GRID_HEIGHT), new Vector2(0,0),true, Commons.ALIEN_WIDTH,Commons.ALIEN_HEIGHT,Assets.can,game,10));
                else if(green == 100) game.setPlayer(new Player(new Vector2(i * Commons.GRID_WIDTH, j * Commons.GRID_HEIGHT), new Vector2(), true, Commons.PLAYER_WIDTH, Commons.PLAYER_HEIGHT, Assets.player,game));
                else if(red == 100) level.add(new Block(new Vector2(i * Commons.GRID_WIDTH, j * Commons.GRID_HEIGHT), new Vector2(0,0),true, Commons.BLOCK_WIDTH * 2,Commons.BLOCK_HEIGHT * 2,Assets.movement,game));
                else if(red == 101) level.add(new Block(new Vector2(i * Commons.GRID_WIDTH, j * Commons.GRID_HEIGHT), new Vector2(0,0),true, Commons.BLOCK_WIDTH * 2,Commons.BLOCK_HEIGHT * 2,Assets.shooting,game));
                
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
