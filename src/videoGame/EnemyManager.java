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
    private ArrayList<Enemy> enemies;
    private Game game;
    
    /**
     * This constructor with params recieves a reference to the game and initializes an empty list
     * where the aliens will be stored.
     * @param game 
     */
    public EnemyManager(Game game) {
        enemies = new ArrayList<>();
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
            enemies.get(i).tick();
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
    }

    
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
