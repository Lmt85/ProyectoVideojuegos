package videoGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import static java.lang.Math.abs;
import maths.Vector2;

/**
 * The ball that collides with the bricks and player.
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 05/02/2019
 * @version 1.0
 */
public class Ball extends Item{
    public  Vector2 velocity;
    private Rectangle collider;
    public Vector2 previousPosition; //the previous position of the ball. Used in collsisions
    private Animation animation;
    
    private Game game;
    private Player player;
    
    private int width;
    private int height;
    
    /**
     * Ball constructor, takes the ball's dimensions and a reference to Game
     * @param width ball width
     * @param height ball height
     * @param game reference to game
     */
    public Ball(int width, int height, Game game) {
        super(0, 0);
        //creates objects
        velocity = new Vector2();
        previousPosition = new Vector2();
        animation = new Animation(Assets.ballAnim, 120);
        //sets values of constructor
        this.game = game;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Executes the ball initialization. Adds a reference to a Player in the game
     * and sets the ball's position on top of the player.
     * @param player the player in the game
     */
    public void init(Player player){
        this.player = player;
        setPositionRelativeToPlayer();
    }
    
    /**
     * Executes every frame of the game, contains all the ball's logic, 
     * including movement, collisions, states, etc.
     */
    @Override
    public void tick() {
        animation.tick();
       previousPosition.set(position); //updates the previous position of the ball before any changes to the actual position.
       
    //Wall collisions
       //Side walls collsions
       if(position.getX() <= 0){
           velocity.setX(abs(velocity.getX()));
       }else if(position.getX() + width >= game.getWidth()){
           velocity.setX(-abs(velocity.getX()));
       }
       //Top wall collsion
       if(position.getY() <= 0){
           velocity.setY(abs(velocity.getY()));
       //When the ball exits from the bottom of the game window, the player loses a life and the lauched state changes to flse, and the ball resets its position to on top of the player
       }else if(position.getY() + height >= game.getHeight()){
           player.lives--;
           reboot();
       }
       
       //update the ball's position from its velocity
       position.set(position.add(velocity));
       //Update the collider's position
       collider.x = (int)position.getX();
       collider.y = (int)position.getY();
       //update the collider's dimesnions
       collider.width = width;
       collider.height = height;
    }
    
    /**
     * renders the ball
     * @param g Graphics object to render in
     */
    @Override
    public void render(Graphics g) {
       g.drawImage(animation.getCurrentFrame(), (int)position.getX(), (int)position.getY(), width, height, null);
       //g.drawRect(collider.x, collider.y, collider.width, collider.height);
    }
    
    /**
     * returns the velocity of the ball
     * @return Vector2 ball's velocity
     */
    public Vector2 getVelocity() {
        return velocity;
    }
    
    /**
     * sets the velocity of the ball
     * @param velocity 
     */
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    
    /**
     * Returns the collider of the ball
     * @return Rectangle collider of the ball
     */
    public Rectangle getCollider() {
        return collider;
    }
    
    /**
     * updates the ball's position to be on top of the player
     */
    public void setPositionRelativeToPlayer(){
        position.setX(player.position.getX() + player.getWidth() / 2 - width / 2);
        position.setY(player.getY() - height - 10 + player.margin);
        collider = new Rectangle((int)position.getX(), (int)position.getY(), width, height);
    }
    
    /**
     * Returns the height of the ball
     * @return interger with the ball's height
     */
    public int getHeight() {
        return height;
    }

     /**
     * Returns the width of the ball
     * @return interger with the ball's width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * reboots the ball so that it is no longer launched
     */
    public void reboot(){
        player.launched = false;
        setPositionRelativeToPlayer();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    @Override
    /**
     * Returns all the attributes needed to save the current ball state
     * @return a string that describes the ball
     */
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + velocity.getX() + " " + velocity.getY() + " " + width + " " + height + "\n");
    }
    
}
