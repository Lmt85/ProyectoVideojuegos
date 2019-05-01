/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.LinkedList;
import maths.Vector2;

/**
 *
 * @author luismariotrujillo
 */
public class Boss extends Sprite implements GameObject{
        private Boss.BossBullet b;
        private LinkedList<Boss.BossBullet> bullets;
        private boolean shoot =false;                      //stores whether or not the player can shoot;
        private int shotcd;

    public Boss(Vector2 position, Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game) {
        super(position, speed, visible, width, height, image, game);
        bullets = new LinkedList<>();
    }

    
    public class BossBullet extends Projectile implements GameObject{
            boolean first=true;
            Vector2 vector = new Vector2(0,0);
    
            public BossBullet(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image,Game game){ 
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
    @Override
    public void render(Graphics g) {
        g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), width, height, null);
        g.drawRect((int) position.getX(), (int) position.getY(), width, height);
    }

    @Override
    public void init() {
    }
    @Override
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + visible + "\n");    
    }

    public LinkedList<BossBullet> getBullets() {
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
        bullets.addFirst(new BossBullet(vector,
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
