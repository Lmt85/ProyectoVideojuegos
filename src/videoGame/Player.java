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


    private Animation current;
    private Animation leftAnim;
    private Animation rightAnim;
    private Animation upAnim;
    private Animation downAnim;

    private int hp;
    private int lives;
    private boolean canTakeDmg;
    private int invencibilityFrames;

    
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
        
        setShoot(true);
        setOrientation(Sprite.Orientation.EAST);
        this.leftAnim = new Animation(Assets.swimtoleft, 100);
        this.rightAnim = this.current = new Animation(Assets.swimtoright, 100);
        this.upAnim = new Animation(Assets.swimup, 100);
        this.downAnim = new Animation(Assets.swimdown, 100);

        hp = Commons.PLAYER_HP;
        lives = 3;

    }
    
    public class PlayerBullet extends Projectile implements GameObject{
            public PlayerBullet(maths.Vector2 position, maths.Vector2 speed, boolean visible, int width, int height, BufferedImage image, Game game){ 
            super(position,speed,visible,width,height,image,Commons.PLAYER_BULLET_DAMAGE,game);
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
         * This method ticks the Bomb, only if visible, which spawns it in it's alien's position and moves
         * it straight down until it reaches the bottom
         */
        public void render(Graphics g) {
            g.drawRect((int)position.getX(), (int)position.getY(), width, height);
            g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), Commons.PLAYER_BULLET_WIDTH, Commons.PLAYER_BULLET_HEIGHT, null);
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
    }

    @Override
    public void tick() {
        if(getHp() >= 0) {
            // moves player
            setPosition(getPosition().add(getSpeed()));
            switch(getOrientation()) {
                case NORTH:
                    current = upAnim;
                    upAnim.tick(); 
                break;
                case EAST:
                    current = rightAnim;
                    rightAnim.tick();
                break;
                case SOUTH:
                    current = downAnim;
                    downAnim.tick();
                break;
                case WEST:
                    current = leftAnim;
                    leftAnim.tick();
            }
            // every tick it restores shoot cooldown
            if(!canShoot()) {
                shotcd--;
                if(shotcd < 0) {
                    setShoot(true);
                }
            }
            if(!canTakeDmg()) {
                invencibilityFrames--;
                if(invencibilityFrames <= 0) {
                    setCanTakeDmg(true);
                    resetInvencibility();
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
            g.drawImage(current.getCurrentFrame(), (int)position.getX(), (int) position.getY(), width, height, null);
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
        this.shotcd = Commons.PLAYER_SHOT_COOLDOWN;
    }
    
    public void resetInvencibility() {
        this.invencibilityFrames = Commons.INVENCIBILITY_FRAMES;
    }
    
    public void shoots() {
        bullets.addFirst(new PlayerBullet(new Vector2(getPosition().getX() + Commons.PLAYER_WIDTH/2, getPosition().getY() + Commons.PLAYER_HEIGHT/2),
                        getOrientation().speed(6), true, Commons.PLAYER_BULLET_WIDTH,
                        Commons.PLAYER_BULLET_HEIGHT, Assets.bubble, game));
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
    
    public void takeDamage(int dmg) {
        if(canTakeDmg()) {
            setHp(getHp() - dmg);
            setCanTakeDmg(false);
        }
    }

    public boolean canTakeDmg() {
        return canTakeDmg;
    }

    public void setCanTakeDmg(boolean canTakeDmg) {
        this.canTakeDmg = canTakeDmg;
    }
    
    
    
}

