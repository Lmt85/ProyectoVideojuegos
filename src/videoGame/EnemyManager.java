package videoGame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * This class manages the aliens as a whole, moving them in the same direction, encapsulating them
 * and ticking them in a single method, initializes each alien, which consequently initializes each
 * alien's bomb
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class EnemyManager implements GameObject{
    private ArrayList<Wheel> enemies; //array to store the aliens
    private ArrayList<Can> cans; 
    private Game game;
    
    /**
     * This constructor with params recieves a reference to the game and initializes an empty list
     * where the aliens will be stored.
     * @param game 
     */
    public EnemyManager(Game game) {
        enemies = new ArrayList<>();
        cans = new ArrayList<>();
        this.game = game;
    }
    
    /**
     * This initializes each alien and adds it to the existing list, stores the
     * position of each one so they form a block
     */
    @Override
    public void init() {
        for (int i = 0; i < enemies.size(); i++){
            enemies.get(i).setOrientation(Sprite.Orientation.getRandomOrientation());
            enemies.get(i).setSpeed(enemies.get(i).getOrientation().speed(3));
            //enemies.get(i).setSpeed(n.randomEnum().speed(3));      
        }
    }
    
    /**
     * Ticks the manager, which generates a chance for each alien to drop a bomb,
     * detects when a visible alien touches the walls, and detects when the block
     * reaches the bottom.
     */
    @Override
    public void tick() {
        for(int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i).isVisible()) {
                enemies.get(i).tick(game.getPlayer());
                //System.out.println("Hello");
            } 
            else{
                enemies.get(i).tick();
                
            }
        }
        for(int i = 0; i < cans.size(); i++) {
            if(cans.get(i).isVisible() && game.getCamera().getBounds().contains(cans.get(i).getBounds())) {
                cans.get(i).tick(game.getPlayer().getPosition(),game);
            } else{
                cans.get(i).tick();
                
            }
            //else  cans.remove(cans.get(i));
        }
    }

    /**
     * This renders each alien of the array which hasn't yet been killed and consequently
     * each alien's bomb, if visible.
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).render(g);
        }
        for(int i = 0; i < cans.size(); i++) {
            cans.get(i).render(g);
            if(!cans.get(i).getBullets().isEmpty()){
                for(int x = 0; x < cans.get(i).getBullets().size(); x++) {
                    cans.get(i).getBullets().get(x).render(g);
                }
            }
        }
    }

    
    public ArrayList<Wheel> getEnemies() {
        return enemies;
    }
    public ArrayList<Can> getCans() {
        return cans;
    }

    public void setEnemies(ArrayList<Wheel> enemies) {
        this.enemies = enemies;
    }
    
    public void setCans(ArrayList<Can> cans) {
        this.cans = cans;
    }
    
}
