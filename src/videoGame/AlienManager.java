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
public class AlienManager implements GameObject, Commons{
    public ArrayList<Alien> aliens; //array to store the aliens
    Game game;                      //reference to the game
    int direction = 1;              //direction of the block of aliens
    int destroyed = 0;              //counts the number of destroyed aliens
    int odds = 500;                 //to claculate the odds of dropping a bomb
    
    /**
     * This constructor with params recieves a reference to the game and initializes an empty list
     * where the aliens will be stored.
     * @param game 
     */
    public AlienManager(Game game) {
        aliens = new ArrayList<>();
        this.game = game;
    }
    
    /**
     * This initializes each alien and adds it to the existing list, stores the
     * position of each one so they form a block
     */
    @Override
    public void init() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                Alien alien = new Alien(new maths.Vector2(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i), new maths.Vector2(), true, ALIEN_WIDTH, ALIEN_HEIGHT, Assets.alien);
                aliens.add(alien);
            }
        }
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
        for (Alien alien: aliens) { 
            
            int shot = generator.nextInt(odds);
            Alien.Bomb b = alien.getBomb();
            if (shot == CHANCE && alien.isVisible() && !b.isVisible()) {

                b.setVisible(true);
                b.position.setX(alien.position.getX());
                b.position.setY(alien.position.getY());
            }
            
            b.tick();
            
            int x = (int)alien.position.getX();
            
            if (x  >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {

                direction = -1;
                Iterator i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = (Alien) i1.next();
                    a2.position.setY(a2.position.getY() + GO_DOWN);
                }
            }
            
            if (x <= BORDER_LEFT && direction != 1) {

                direction = 1;
                Iterator i2 = aliens.iterator();

                while (i2.hasNext()) {

                    Alien a = (Alien) i2.next();
                    a.position.setY(a.position.getY() + GO_DOWN);
                }
            }
        }

        Iterator it = aliens.iterator();

        while (it.hasNext()) {
            
            Alien alien = (Alien) it.next();
            
            if (alien.isVisible()) {
                if (alien.position.getY() > GROUND - ALIEN_HEIGHT) {
                    game.setGameState(-1);
                    game.message = "Invasion!";
                } else alien.act(direction);
            }
        }
    }

    /**
     * This renders each alien of the array which hasn't yet been killed and consequently
     * each alien's bomb, if visible.
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        for (Alien alien: aliens) {
            Alien.Bomb b = alien.getBomb();
            alien.render(g);
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
