package videoGame;

import java.awt.Graphics;
import java.util.ArrayList;

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
            enemies.get(i).init();
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
            if(!enemies.get(i).isVisible() && enemies.get(i).getBullets().size() == 0) { // Removes enemies completely from list if their bullets are empty and is destroyed 
                if(enemies.get(i).getClass().equals(Wheel.class)) {
                    game.setScore(game.getScore() + 100);
                } else if(enemies.get(i).getClass().equals(Can.class)) {
                    game.setScore(game.getScore() + 200);
                }
                enemies.remove(i);
                System.out.println(game.getScore());
            } else {
                if(enemies.get(i).getHp() > 0 && enemies.get(i).isVisible()){
                    enemies.get(i).tick();
                } else{
                    enemies.get(i).setVisible(false);
                }
                for(int j = 0; j < getEnemies().get(i).getBullets().size(); j++) {  //
                    if(getEnemies().get(i).getBullets().get(j).isVisible()) {   //If the bullet exists and doesnt collision with wall or p
                        getEnemies().get(i).getBullets().get(j).tick();
                    } else {
                        getEnemies().get(i).getBullets().remove(j);
                    }
                }
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
        for(int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i).onScreen() && enemies.get(i).isVisible()) {
                enemies.get(i).render(g);
            }
            for(int j = 0; j < enemies.get(i).getBullets().size(); j++) {
                if(enemies.get(i).getBullets().get(j).onScreen()) {
                    enemies.get(i).getBullets().get(j).render(g);
                }
            }
        }
        
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
}
