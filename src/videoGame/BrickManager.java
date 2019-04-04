package videoGame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * A container for the bricks of the game.
 * Creates the brick wall and executes the render and tick methods for all bricks.
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 05/02/2019
 * @version 1.0
 */
public class BrickManager {
    public ArrayList<Brick> bricks; //List containig all bricks in the game
    private LinkedList<Brick> removeQueue; //queue of bricks to be removed
    
    public ArrayList<FallingItem> items;
    private LinkedList<FallingItem> removeItemQueue;
    
    private Game game;
    private Ball ball;
    private Player player;
    
    Random rng;
    
    private int width; //width of each brick to be created
    private int height; //height of each brick to be created
    private int layers; //amount of layers of the wall
    private int topMargin; //empty space at the top of the wall
    
    /**
     * Constructor of the brick manager. Sets the dimensions for each brick, 
     * the amount of layers, and the top margin
     * @param game A reference to the game
     * @param width width of each brick 
     * @param height height of each brick 
     * @param layers Amount of layers of the wall
     * @param topMargin top margin
     */
    public BrickManager(Game game, int width, int height, int layers, int topMargin) {  
        this.game = game;
        this.width = width;
        this.height = height; 
        this.layers = layers;
        this.topMargin = topMargin;
        //create objjects
        bricks = new ArrayList<>();
        removeQueue = new LinkedList<>(); 
        items = new ArrayList<>();
        removeItemQueue = new LinkedList<>();
        rng = new Random(System.nanoTime());
    }
    
    /**
     * initializes the brick manager setting a reference to ball, and creates 
     * the wall of bricks
     * @param ball 
     */
    public void init(Ball ball, Player player){
        this.ball = ball;
        this.player = player;
        createBricks(layers);
    }
    
    /**
     * Creates and adds a brick to the bricks list at a specific position, 
     * with an specific amount of lives.
     * @param x x position
     * @param y y position
     * @param lives amount of lives
     */
    private void addBrick(int x, int y, int lives){
        bricks.add(new Brick(x, y, width, height, lives, ball));
    }    
    
    /**
     * Creates the brick wall such that it fills the width of the screen, and 
     * adds a layer below for each layer set. also gives an amount of lives to 
     * each brick depending on the  layer it lives in. 
     * @param layers the mount of layers
     */
    public void createBricks(int layers){
        for (int i = 0; i < game.getWidth() / width; i ++){ //i is the index of horizontal brick
            for(int j = 0; j < layers; j++){                //j is the index of vertical brick (layer of the brick)
                addBrick(i * width, j * height + topMargin, layers - j);
            }
        }
    }
    
    /**
     * Each frame it executes the tick method for each brick each in the brick list and if a brick's 
     * lives go  below or equal to 0, it adds them to the remove queue.
     * If a brick is in the remove queue, it removes it.
     */
    public void tick(){
        for(Brick b : bricks){
            b.tick(); //executes  .tick() for each brick
            if(b.getLives() <= 0){ //adds a brick to the remove queue if it's lives go below or equal to 0
               removeQueue.add(b);
                if(rng.nextInt(5 - b.initialLives) == 0){ 
                    items.add(new FallingItem(b.position.getX() + b.getWidth() / 2 - FallingItem.size / 2, b.position.getY(), rng.nextInt(2) + 1, game));
                }
            }
        }
        for(FallingItem fi : items){
            fi.tick(); //executes  .tick() for each falling item
            if(fi.getCollider().intersects(player.getCollider())){
                removeItemQueue.add(fi);
                player.setModifier(fi.getType());
            }
        }
        //removes from the bricks list, all the brick in the remove queue
        /*for(Brick b : removeQueue){ 
            bricks.remove(b);
        }*/
        bricks.removeAll(removeQueue);
        
        items.removeAll(removeItemQueue);
        
        /*for(FallingItem fi : removeItemQueue){
            items.remove(fi);
        }*/
        
        //The remove queue is cleared after flushing.
        removeQueue = new LinkedList<>();
        removeItemQueue = new LinkedList<>();
    }
    
    /**
     * renders each  brick in the brick list
     * @param g the graphics where the bricks will be rendered.
     */
    public void render(Graphics g){
        for(Brick b : bricks){
            b.render(g);
        }
        for(FallingItem fi : items){
            fi.render(g);
        }
    }
    
    /**
     * reboots the brick manager by flushing its bricks list and creating a new wall
     */
    public void reboot(){
        bricks = new ArrayList<>();
        createBricks(layers);
    }
}
