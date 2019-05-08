/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import maths.Vector2;

/**
 * Implements the player of the game, which is only able to move
 * @author Adrián Marcelo Suárez Ponce A01197108
 */
public class Player extends Sprite implements GameObject {
    private PlayerBullet p;
    private LinkedList<PlayerBullet> bullets;
    private boolean shoot;                      //stores whether or not the player can shoot;
    private int shotcd;
    private int hp;
    private int lives;
    
    /**
     * Constructor with parameters, these parameters are passed directly to the Sprite class
     * @param x
     * @param y
     * @param visible
     * @param width
     * @param height
     * @param image
     * @param game 
     */
    public Player(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game) {
        super(position, speed, visible, width, height, image, game);
        bullets = new LinkedList<>();
        hp = 10;
        lives = 3;
    }
    
    public class PlayerBullet extends Projectile implements GameObject{
            public PlayerBullet(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game){ 
            super(position,speed,visible,width,height,image,1,game);
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
            Vector2 vector = new Vector2(0,0);
            vector.set(getSpeed());
            vector.set(vector.scalar(1.75));
            setPosition(getPosition().add(vector));
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
    public void init() {
        setShoot(true);
        setOrientation(Sprite.Orientation.NORTH);
        hp = 50;
    }

    @Override
    public void tick() {
        if(getHp() >= 0) {
            // moves player
            setPosition(getPosition().add(getSpeed()));

            // every tick it restores shoot cooldown
            if(!canShoot()) {
                shotcd--;
                if(shotcd < 0) {
                    setShoot(true);
                }
            }
        } else {
            getGame().setGameState(-1);
        }
    }

    /**
     * Renders the player if not hit by a bomb
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(getImage(), (int)position.getX(), (int) position.getY(), width, height, null);
    }
    
    /**
    * Converts the object to a string with most important attributes.
    * @return a string with most important attributes.
    */
    @Override
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + visible + " " + speed.getX() + "\n");
    }

    public PlayerBullet getP() {
        return p;
    }

    public void setP(PlayerBullet p) {
        this.p = p;
    }

    public LinkedList<PlayerBullet> getBullets() {
        return bullets;
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
    
    public void shoots() {
        bullets.addFirst(new PlayerBullet(getPosition(),
                        getOrientation().speed(6), true, Commons.BOMB_WIDTH,
                        Commons.BOMB_HEIGHT, Assets.bomb,game));
        System.out.println(bullets.size());
    }

    public int getShotcd() {
        return shotcd;
    }

    public void setShotcd(int shotcd) {
        this.shotcd = shotcd;
    }

    public int getHp() {
        return hp;
    }

    public int getLives() {
        return lives;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
    
}

