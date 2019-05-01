package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import java.util.LinkedList;
import maths.Vector2;
import videoGame.GameObject;
import videoGame.Player;
import videoGame.Sprite;

/**
 * This class is stores all the methods and variables related to the alien
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class Can extends Sprite implements GameObject{
    private CanBullet b;
    private LinkedList<CanBullet> bullets;
    private boolean shoot =false;                      //stores whether or not the player can shoot;
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
    public Can(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game){  

        super(position,speed,visible,width,height,image,game);
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

                /*
                double angulo = (1/ tan(abs((getSpeed().getY() - getPosition().getY())/(getSpeed().getX() - getPosition().getX()))));
               System.out.println(angulo);
*/
                vector.setX(cos(angulo));
                vector.setY(sin(angulo));
                first=false;

           }
            
            vector.setX(vector.getX()*1.025);
            vector.setY(vector.getY()*1.025);
          //  System.out.println(vector.getX());
          //  System.out.println(vector.getY());


            setPosition(getPosition().add(vector));
        
        }

        /**
         * Renders the bomb if visible
         * @param g 
         */
//        @Override
//        public void render(Graphics g) {
//            if (isVisible()) {
//                g.drawRect((int)position.getX(),(int) position.getY(), Commons.BOMB_WIDTH, Commons.BOMB_HEIGHT);
//                g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), null);
//            }
//        }
        
        /**
         * Converts the object to a string with most important attributes.
         * @return a string with most important attributes.
         */
        @Override
        public String toString() {
            return String.valueOf(position.getX() + " " + position.getY() + " " +  visible + "\n");
        }
    }
    /**
     * This method sets a bomb as the instance's bomb
     * @param bomb 
     */
 //    public void setBomb(Bomb bomb) {
 ////        this.bomb = bomb;
 //    }

    /**
     * This method returns the bomb
     * @return instance's bomb.
     */
 //    public Bomb getBomb() {
 ////        return bomb; return null
 //    }
    @Override
     public void tick(){
         
         if(!getBullets().isEmpty()) {
                for (int i = 0; i < getBullets().size(); i++) {
                    if(getBullets().get(i).isVisible()) {
                        getBullets().get(i).tick();
                    }
                    else getBullets().remove(i);
                }
                System.out.println(getBullets().size());

        }
         
     }
    
    public void tick(Vector2 position, Game game) {
        if (canShoot()) { 
                shoots(position);
                setShoot(false);
                resetShotcd();
        }else {
            shotcd--;
            if(shotcd < 0) {
                setShoot(true);
            }
        }
        
        if(!getBullets().isEmpty()) {
                for (int i = 0; i < getBullets().size(); i++) {
                    if(getBullets().get(i).isVisible()) {
                        getBullets().get(i).tick();
                    }
                    else getBullets().remove(i);
                }
                System.out.println(getBullets().size());

        }
    }
    
    /**
     * Renders alien if visible(not hit by laser)
     * @param g graphics parameter to paint
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), width, height, null);
        g.drawRect((int) position.getX(), (int) position.getY(), width, height);
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

    public LinkedList<CanBullet> getBullets() {
        return bullets;
    }
    
    public boolean canShoot() {
        return shoot;
    }
    
    public void setShoot(boolean shoot) {
        this.shoot = shoot;
    }

    public void resetShotcd() {
        this.shotcd = 15;
    }
    
    public void shoots(Vector2 player) {
        Vector2 vector = new Vector2(getPosition().getX()+Commons.ALIEN_WIDTH/2,getPosition().getY()+Commons.ALIEN_HEIGHT/2);
        bullets.addFirst(new CanBullet(vector,
                        player, true, Commons.BOMB_WIDTH,

                        Commons.BOMB_HEIGHT, Assets.bomb,game));
        System.out.println(bullets.size());
    }

    public int getShotcd() {
        return shotcd;
    }

    public void setShotcd(int shotcd) {
        this.shotcd = shotcd;
    }

}