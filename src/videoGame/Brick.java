package videoGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import static java.lang.Math.abs;
/**
 * The bricks in the game.
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 05/02/2019
 * @version 1.0
 */
public class Brick extends Item{
    Ball ball;
    private Rectangle collider;
    
    private int lives;
    public int initialLives;
    private int width;
    private int height;    

    /**
     * Brick constructor, sets the position, dimensions, lives (internal state),
     * and a reference to the ball with which it will collide
     * @param x x position
     * @param y y position
     * @param width the brick's width
     * @param height the brick's height
     * @param lives the brick's lives
     * @param ball a reference to the ball
     */
    public Brick(double x, double y,int width, int height, int lives, Ball ball) {
        super(x, y);
        this.lives = lives;
        this.initialLives = lives;
        this.width = width;
        this.height = height;
        this.ball = ball;
        //creates objects
        collider = new Rectangle( (int)position.getX(), (int)position.getY(), width, height);
    }
    
    /**
     * Executes every frame of the game, contains all the bricks logic, 
     * including collisions, states, etc.
     */
    @Override
    public void tick() {
       //When colliding with the ball
       if(ball.getCollider().intersects(collider)){
           //reduce the brikc lives if the ball collides with it
           lives--;
           //Vertical collision
           if((ball.previousPosition.getX() > position.getX() || ball.previousPosition.getX() + ball.getWidth() > position.getX() )
           && (ball.previousPosition.getX() < position.getX() + width || ball.previousPosition.getX() + ball.getWidth() < position.getX() + width)){
               //top wall
               if(ball.previousPosition.getY() + ball.getHeight() <= position.getY()){
                   ball.velocity.setY(-abs(ball.velocity.getY()));
               //bottom wall
               }else if(ball.previousPosition.getY() >= position.getY() + height ){
                    ball.velocity.setY(abs(ball.velocity.getY()));
               }
           //Horizontal collision
           }else if((ball.previousPosition.getY() > position.getY() || ball.previousPosition.getY() + ball.getHeight()> position.getY() ) 
           && (ball.previousPosition.getY() < position.getY() + height || ball.previousPosition.getY() + ball.getHeight() < position.getY() + height)){
               //left wall
               if(ball.previousPosition.getX() + ball.getWidth()<= position.getX()){
                   ball.velocity.setX(-abs(ball.velocity.getX()));
               //right wall
               }else if(ball.previousPosition.getX() >= position.getX() + width ){
                    ball.velocity.setX(abs(ball.velocity.getX()));
               }
           }
       }
    }
    
    /**
     * Renders the Brick
     * @param g Graphics object to render in
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.Amphetamines[lives-1], (int)position.getX(), (int)position.getY(), width, height, null);
        //g.drawRect(collider.x, collider.y, collider.width, collider.height);
        //g.drawString(Integer.toString(lives),(int)position.getX(), (int)position.getY() + height);
    }
    
    /**
     * returns the brick remaining lives
     * @return lives
     */
    public int getLives() {
        return lives;
    }

    public int getWidth() {
        return width;
    }   
    
    /**
     * Returns all the attributes needed to save the current Brick state
     * @return a string that describes the brick
     */
    @Override
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + width + " " + height + " " + lives + "\n");
    }
}
