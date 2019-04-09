package videoGame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import static videoGame.Commons.ALIEN_HEIGHT;
import static videoGame.Commons.ALIEN_INIT_X;
import static videoGame.Commons.ALIEN_INIT_Y;
import static videoGame.Commons.ALIEN_WIDTH;
import static videoGame.Commons.BOARD_WIDTH;
import static videoGame.Commons.BORDER_LEFT;
import static videoGame.Commons.BORDER_RIGHT;
import static videoGame.Commons.CHANCE;
import static videoGame.Commons.GO_DOWN;
import static videoGame.Commons.GROUND;

/**
 * This class manages the aliens as a whole, moving them in the same direction, encapsulating them
 * and ticking them in a single method, initializes each alien, which consequently initializes each
 * alien's bomb
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class EnemyManager implements GameObject, Commons{
    public ArrayList<Enemy> enemies; //array to store the aliens
    Game game;                      //reference to the game
    int direction = 1;              //direction of the block of aliens
    int destroyed = 0;              //counts the number of destroyed aliens
    int odds = 500;                 //to claculate the odds of dropping a bomb
    
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
        
    }
    
    /**
     * Ticks the manager, which generates a chance for each alien to drop a bomb,
     * detects when a visible alien touches the walls, and detects when the block
     * reaches the bottom.
     */
    @Override
    public void tick() {
        // moves aliens uniformly
        Random generator = new Random();
        for (Enemy enemy: enemies) { 
            
            int shot = generator.nextInt(odds);
            Enemy.Bomb b = enemy.getBomb();
            if (shot == CHANCE && enemy.isVisible() && !b.isVisible()) {

                b.setVisible(true);
                b.position.setX(enemy.position.getX());
                b.position.setY(enemy.position.getY());
            }
            
            b.tick();
            
            int x = (int)enemy.position.getX();
            
            if (x  >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {

                direction = -1;
                Iterator i1 = enemies.iterator();

                while (i1.hasNext()) {

                    Enemy a2 = (Enemy) i1.next();
                    a2.position.setY(a2.position.getY() + GO_DOWN);
                }
            }
            
            if (x <= BORDER_LEFT && direction != 1) {

                direction = 1;
                Iterator i2 = enemies.iterator();

                while (i2.hasNext()) {

                    Enemy a = (Enemy) i2.next();
                    a.position.setY(a.position.getY() + GO_DOWN);
                }
            }
        }

        Iterator it = enemies.iterator();

    }

    /**
     * This renders each alien of the array which hasn't yet been killed and consequently
     * each alien's bomb, if visible.
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        for (Enemy enemy: enemies) {
            Enemy.Bomb b = enemy.getBomb();
            enemy.render(g);
            b.render(g);
        }
    }
    
    /**
     * Converts the object to a string containing its most important attributes
     * @return a string containing its most important attributes.
     */
    @Override
    public String toString(){
        return String.valueOf(direction + " " + destroyed + "\n");
    }

    /**
     * Returns the direction of the current block
     * @return 1 or -1, positive being right, negative being left.
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Sets the direction to the param's value
     * @param direction 
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * Sets the destroyed aliens to the param's amount, used for the load file
     * @param destroyed 
     */
    public void setDestroyed(int destroyed) {
        this.destroyed = destroyed;
    }
}
