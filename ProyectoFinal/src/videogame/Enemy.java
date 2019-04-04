/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author luismariotrujillo
 */
public class Enemy extends Item {

    private BufferedImage green, yellow, red;
    private BufferedImage sprite;

    private int width;
    private int height;
    private Game game;
    private Player player;
    private Rectangle rectangle;
    private int id;
    private boolean down;
    
    private Animation animation;
    
    private int trueX;
    private int trueY;
    
    

    @Override
    public int getX() {
        return x;
    }

    public Enemy(int x, int y, int width, int height, int id, Game game, Player player) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.trueX = x;
        this.trueY = y;
        this.width = width;
        this.height = height;
        this.game = game;
        this.player = player;
        this.rectangle = new Rectangle(x, y, width, height);
        this.sprite = green;
        this.id = id;
        this.down=false;
        
        animation = new Animation(Assets.alienSprite, 100);
        
    }

    @Override
    public void tick() {
        animation.tick();
        moveEnemies();
        
    }
    
    public void moveEnemies(){
       if(getX()+40==game.getWidth()){     
            game.setEnemiesDirectionX(-1);
            game.setEnemiesDirectionY(5);
            game.enemyWall();
            
        }else if(getX()==0){
            game.setEnemiesDirectionX(1);
            game.setEnemiesDirectionY(5);
            game.enemyWall();
        }
     
       setX(getX()+game.getEnemiesDirectionX());
    }
    
    public void hitWall(){
        setY(getY() + 6);
    }
    
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    public int getId(){
        return id;
    }

    @Override
    public int getY() {
        return super.getY(); //To change body of generated methods, choose Tools | Templates.
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    
    @Override
    public void setX(int x){
        this.x = x;
        this.rectangle.setLocation(this.x, this.y);
    }

    @Override
    public void setY(int y){
        this.y = y;
        this.rectangle.setLocation(this.x, this.y);
    }
    
    @Override
    public void render(Graphics g) {
        //g.drawImage(Assets.enemy, getX(), getY(), getWidth(), getHeight(), null);
        g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
    public void reset(){
        this.x = trueX;
        this.y = trueY;
    }
    
    public void getHit(){

        setX(-10000);
        setY(-10000);
    }

}
