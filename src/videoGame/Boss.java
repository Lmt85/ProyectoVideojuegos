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
public class Boss extends Enemy implements GameObject{
        private boolean shoot =false;                      //stores whether or not the player can shoot;
        private int firstattack =50;
        private int shotcd;
        private int shotattack=50;
        double angle = 0;
        double angle2 = 3.14;
        double angle3 = 1.57;
        double angle4 = 4.71;
        int cambiar = 0;
        private boolean seen = false;


    public Boss(Vector2 position, Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game, int hp) {
        super(position, speed, visible, width, height, image, game,hp);
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
            
            if(firstattack>1){
                if(first){
                    if(cambiar==0){
                        vector.set(cos(angle), sin(angle));
                     cambiar=1;
                    }else if(cambiar==1){
                        vector.set(cos(angle2), sin(angle2));
                        cambiar=0;
                    }
                first=false;
                } 
            }else{
                if(getShotAttack()>1){
                if(first){
                    if(cambiar==0){
                        vector.set(cos(angle), sin(angle));
                        cambiar=1;
                    }else if(cambiar==1){
                        vector.set(cos(angle2), sin(angle2));
                        cambiar=2;
                    }else if(cambiar==2){
                        vector.set(cos(angle3), sin(angle3));
                        cambiar=3;
                    }else{
                        vector.set(cos(angle4), sin(angle4));
                        cambiar=0;
                    }             
                        first=false;
                    } 
                }else{
                    if(first){
                        if(cambiar==0){
                            vector.set(cos(angle), sin(angle));
                            cambiar=1;
                        }else if(cambiar==1){
                            vector.set(cos(angle2), sin(angle2));
                            cambiar=0;
                        }
                        first=false;
                    } 
                }
            }

           vector.set(vector.scalar(1.05));
           setPosition(getPosition().add(vector));

        }
        
        /**
         * Renders the bomb if visible
         * @param g 
         */
//        @Override
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
         if(onScreen() && !seen) {
             seen = true;
         }
        if(canShoot() && seen) {
                if(firstattack>1){
                    shoots(game.getPlayer().getPosition());
                    angle=angle-.2;
                    angle2=angle2-.2;
                    setShoot(false);
                    resetShotcd();
                    //System.out.println(bullets.size());
                    setFirstAttack(getFirstAttack()-1);
                }else{
                    if(getShotAttack()>1){
                        setShotcd(0);
                        shoots(game.getPlayer().getPosition());
                        angle=angle+.2;
                        angle2=angle2+.2;
                        angle3=angle3+.2;
                        angle4=angle4+.2;
                        setShoot(false);
                     //   System.out.println(bullets.size());
                       // System.out.println(getShotcd());
                        setShotAttack(getShotAttack()-1);
                    }else{   
                        shoots(game.getPlayer().getPosition());
                        angle=angle+.2;
                        angle2=angle2+.2;
                        setShoot(false);
                        resetShotcd();
                    }
                }
        } else {
             shotcd--;
             if(shotcd < 0) {
                setShoot(true);
             }
        }
     }
     
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
        this.shotcd = 5;
    }
    
    public void shoots(Vector2 player) {
        Vector2 vector = new Vector2(getPosition().getX() + Commons.BOSS_WIDTH/2, getPosition().getY()+Commons.BOSS_HEIGHT/2);
        bullets.addFirst(new Boss.BossBullet(vector, player, true, Commons.BOSS_BULLET_WIDTH,
                        Commons.BOSS_BULLET_HEIGHT, Assets.bomb,game));
    }

    public int getShotcd() {
        return shotcd;
    }

    public void setShotcd(int shotcd) {
        this.shotcd = shotcd;
    }
    
    public int getShotAttack() {
        return shotattack;
    }
    public void setShotAttack(int shotcd) {
        this.shotattack = shotcd;
    }
    public void resetShotAttack() {
        this.shotattack = 50;
    }
    public int getFirstAttack() {
        return firstattack;
    }
    public void setFirstAttack(int firsta) {
        this.firstattack = firsta;
    }
}
