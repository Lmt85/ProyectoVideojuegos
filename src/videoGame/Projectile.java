package videoGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.System.console;
import javax.swing.ImageIcon;
import static videoGame.Commons.H_SPACE;
import static videoGame.Commons.V_SPACE;

/**
 * This class defines each of the methods of the shot
 * @author Adrián Marcelo Suárez Ponce A01197108
 * @date 9/03/2019
 * @version 1.0
 */
public class Projectile extends Sprite implements GameObject, Commons {
    double damage;
    
    public Projectile(maths.Vector2 position, maths.Vector2 speed,  boolean visible, int width, int height, BufferedImage image, double damage){ 
        super(position, speed, visible,width,height,image);
        this.damage = damage;
    }

    @Override
    public void init() {
    }

    /**
     * Method which ticks the Shot, moving it upwards to the screen.
     * If it is not visible and the space key is pressed, it sets it visible
     * sets it to the center of the player and plays a laser sound
     * If it is already visible it moves it upwards until it reaches the upper
     * border or an alien
     */
    @Override
    public void tick() {
    }

    /**
     * Renders the shot with the current positions.
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        if (isVisible()) {
            g.drawRect((int)position.getX(),(int) position.getY(), width, height);
            g.drawImage(getImage(), (int)position.getX(), (int)position.getY(), null);
        }
    }
    
    
    /**
     * Returns a string of the an object's important variables. Used in game's save method.
     * @return a String with all the variables converted to string
     */
    @Override
    public String toString() {
        return String.valueOf(position.getX() + " " + position.getY() + " " + visible + "\n");
    }

}


//            if (game.getKeyManager().space) {
//                setVisible(true);
//                setPositionRelativeToPlayer();
//                setOrientation(game.player.getOrientation());
//                
//                switch(getOrientation()) {
//                    case NORTH: setSpeed(new maths.Vector2(0,-4));
//                    break;
//                    case EAST: setSpeed(new maths.Vector2(4,0));
//                    break;
//                    case SOUTH: setSpeed(new maths.Vector2(0,4));
//                    break;
//                    case WEST: setSpeed(new maths.Vector2(-4,0));
//                }
//            }
//        } else {
//            if (getPosition().getY() < 0 || getPosition().getY() + Commons.BOMB_HEIGHT >= Commons.BOARD_HEIGHT || getPosition().getX() < 0 || getPosition().getX() + Commons.BOMB_WIDTH >= Commons.BOARD_WIDTH) {
//                setVisible(false);
//                setSpeed(new maths.Vector2(0,0));
//            }
//            else setPosition(getPosition().add(getSpeed()));
//        }

