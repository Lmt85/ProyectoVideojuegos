package videoGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import static java.lang.Math.abs;
import maths.Vector2;

/**
 * The player of the game. the bar at the bottom
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 05/02/2019
 * @version 1.0
 */
public class Player extends Item{
    
    private Game game;
    Rectangle collider;
    Ball ball; //reference to the ball  
    private Animation animation; //stores the animation of the bar
    
    private double speed;
    private int width;
    private int height;
    public int lives;
    private int initLives; //saves the initial lives of the player at construction, so they can be restored at reboot
    private int launchSpeed;
    public int margin;
    //intertnal state
    public boolean launched; //if the ball has been launched. if not the player would launch the ball by pressing space
    
    /**
     * The player constructor receives the bar dimensions, an initial position 
     * , lives, and a reference to the game were the player recides.
     * @param x x position at init
     * @param width the bar width
     * @param height the bar height
     * @param lives the lives at init
     * @param game a reference to the game
     */
    public Player(double x, int width, int height, int lives, Game game) {
        super(x, game.getHeight()- height);
        //defined at constructor
        this.speed = 20;
        this.launchSpeed = 10;
        this.margin = 50;
        this.launched = false;
        //defined ny constructor
        this.game = game;
        this.width = width;
        this.height = height;
        this.lives = lives;
        this.initLives = lives;
        //initialising objects
        animation = new Animation(Assets.playerAnim,150);
        collider = new Rectangle((int)position.getX(), (int)position.getY() + margin, width, height - margin);
    }
    
    /**
     * Executes the player initialization. Adds a reference to a Ball in the game
     * @param ball the ball in the game
     */
    public void init(Ball ball){
        this.ball = ball;
    }
    
    /**
     * Executes every frame of the game, contains all the player's logic, 
     * including movement, collisions, states, etc.
     */
    @Override
    public void tick() {
        animation.tick();
        /**
         * if the ball collides with the player
         */
        if(ball.getCollider().intersects(collider)){
            //Vertical collision
            if((ball.previousPosition.getX() > collider.x || ball.previousPosition.getX() + ball.getWidth() > collider.x )
            && (ball.previousPosition.getX() < collider.x + width || ball.previousPosition.getX() + ball.getWidth() < collider.x + width)){ 
                //top wall
                if(ball.previousPosition.getY() + ball.getHeight() <= collider.y){
                    ball.velocity.setY(-abs(ball.velocity.getY()));
                    //Adds horizontal speed to the bal depedning on were it collided in the player
                    ball.velocity.setX((ball.velocity.getX()) + (ball.position.getX() - (collider.x + (width/2) - (ball.getWidth()/2) ) ) / width * 6);
                //bottom wall
                }else if(ball.previousPosition.getY() >= collider.y + height){
                    ball.velocity.setY(abs(ball.velocity.getY()));
                }
            //Horizontal colision
            }else if((ball.previousPosition.getY() >=  collider.y - ball.getWidth()  )){
               //left wall
               if(ball.previousPosition.getX() + ball.getWidth()<= collider.y){
                   ball.velocity.setX(-abs(ball.velocity.getX()));
                   //adds horizontal speed to the ball if collided while moving
                   //if(game.getKeyManager().left)
                        //ball.velocity.setX(ball.velocity.getX() - speed);
               //right wall
               }else if(ball.previousPosition.getX() >= collider.x + width ){
                    ball.velocity.setX(abs(ball.velocity.getX()));
                    //adds horizontal speed to the ball if collided while moving
                    //if(game.getKeyManager().right)
                       // ball.velocity.setX(ball.velocity.getX() + speed);
               }
           }else{
                System.out.println("Ball: " + ball.position.getX() + ", " + ball.position.getY());
                System.out.println("Player: " + collider.x + ", " + collider.y);
           }
        }
        
        //if the user presses the right key or 'D' the player moves to the right
        if(game.getKeyManager().right){
            position.setX(position.getX() + speed);
        }
        //if the user presses the left key or 'A' the player moves to the left
        if(game.getKeyManager().left){
            position.setX(position.getX() - speed);
        }
        
        //Collisions with the walls
        if(position.getX() < -(width / 2)){
            position.setX((-width) / 2);
        }else if(position.getX() + width / 2 > game.getWidth()){
            position.setX(game.getWidth() - (width / 2));
        }
        
        //controls the ball if  it has'nt launched and  launches it when the user presses space
        if(!launched){
            ball.setPositionRelativeToPlayer();
            if(game.getKeyManager().space){
                ball.setVelocity(new Vector2(0,-launchSpeed));
                launched = true;
            } 
        }
        
        //Update the collider's position
        collider.x = (int)position.getX();
        collider.y = (int)position.getY() + margin;
        
        //updates the collider width
        collider.width = width;
        collider.height = height - margin;
    }
    
    /**
     * renders the player
     * @param g Graphics object to render in
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), (int)position.getX(), (int)position.getY(), width, height, null);
        
        //g.drawRect(collider.x, collider.y, collider.width, collider.height);
        //g.drawRect( (int)((position.getX() + (width/2) - (ball.getWidth()/2) )), (int)position.getY() , 10, 10);
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void reboot() {
        lives = initLives;
    }
    
    public void setHeight(int height) {
       this.height = height;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    public Rectangle getCollider() {
        return collider;
    }
    
    public void setModifier(int type){
        if(type == 1){
            width += 40;
        }else if(type == 2){
            ball.setVelocity(ball.getVelocity().add(ball.getVelocity().norm().scalar(5)));
        }
    }
    
    /**
     * Returns all the attributes needed to save the current player state
     * @return a string that describes the player
     */
    @Override
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + width + " " + height + " " +  lives + " " + launched + "\n");
    }
    
}
