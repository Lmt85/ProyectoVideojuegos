package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.LinkedList;
import maths.Vector2;
import videoGame.GameObject;

/**
 * This class is stores all the methods and variables related to the alien
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class Can extends Enemy implements GameObject{
    private boolean shoot = true;
    private int shotcd;
    
    
    /**
     * Constructor with parameters, these parameters are passed directly to the Sprite class
     * @param position
     * @param speed
     * @param x
     * @param y
     * @param visible
     * @param width
     * @param height
     * @param image 
     * @param player 
     */
    public Can(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game,int hp){  
        super(position,speed,visible,width,height,image,game,hp);
        bullets = new LinkedList<>();
    }
    
        public class CanBullet extends Projectile implements GameObject{
            boolean first=true;
            Vector2 vector = new Vector2(0,0);
            
            public CanBullet(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image,Game game){ 
                super(position,speed,visible,width,height,image,50,game);
            }
        
        @Override
        public void init() {
        }

        /**
         * This method ticks the Bomb, only if visible, which spawns it in it's alien's position and moves
         * it straight down until it reaches the bottom
         */
        @Override
        public void tick() {
            if(first){
                double angulo = (Math.atan2(getSpeed().getY()-getPosition().getY(),getSpeed().getX()-getPosition().getX()));
                vector.set(cos(angulo), sin(angulo));
                first=false;
           } 
            vector.set(vector.scalar(1.075));
            setPosition(getPosition().add(vector));
        }
        
        /**
         * This method ticks the Bomb, only if visible, which spawns it in it's alien's position and moves
         * it straight down until it reaches the bottom
         */
        @Override
        public void render(Graphics g) {
            g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), width, height, null);
            g.drawRect((int) position.getX(), (int) position.getY(), width, height);
        }
        
        /**
         * Converts the object to a string with most important attributes.
         * @return a string with most important attributes.
         */
        @Override
        public String toString() {
            return String.valueOf(position.getX() + " " + position.getY() + " " +  visible + "\n");
        }
    }
        
    @Override
     public void tick(){
        if(canShoot()) {
            if(onScreen()) {
                shoots(game.getPlayer().getPosition());
                setShoot(false);
                resetShotcd();
            }
         } else {
             shotcd--;
             if(shotcd < 0) {
                setShoot(true);
             }
         }
     }
    
    /**
     * Renders alien if visible(not hit by laser)
     * @param g graphics parameter to paint
     */
    @Override
    public void render(Graphics g) {
        if(onScreen()) {
            g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), width, height, null);
            g.drawRect((int) position.getX(), (int) position.getY(), width, height);
        }
        for(int i = 0; i < getBullets().size(); i++) {
            getBullets().get(i).render(g);
        }
    }

    @Override
    public void init() {
    }
    
    /**
     * Returns a string of the an object's important variables. Used in game's save method.
     * @return a String with all the variables converted to string
     */
    @Override
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + visible + "\n");    
    }
    
    public boolean canShoot() {
        return shoot;
    }
    
    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public void resetShotcd() {
        this.shotcd = 10;
    }
    
    public void shoots(Vector2 player) {
        Vector2 vector = new Vector2(getPosition().getX()+Commons.ALIEN_WIDTH/2,getPosition().getY()+Commons.ALIEN_HEIGHT/2);
        bullets.addFirst(new CanBullet(vector, player, true, Commons.BOMB_WIDTH,
                        Commons.BOMB_HEIGHT, Assets.bomb,game));
    }

    public int getShotcd() {
        return shotcd;
    }

    public void setShotcd(int shotcd) {
        this.shotcd = shotcd;
    }
}