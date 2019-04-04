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
public class Player  extends Item {

    private int direction;
    private int width;
    private int height;
    private Game game;
    //creamos dos variables, velocidad en x y velocidad en y
    private int iSpeedX;
    private int iSpeedY;
    //creamos una variable que define cuantos frames se vera hurt
    private int iHurtFrames;
    //finalmente definimos nuestro sprite, el cual modificaremos
    private BufferedImage sprite;
    private Rectangle rectangle;
    private boolean dragging;

    //private Animation animation;

    /**
     *
     * @param x
     * @param y
     * @param direction
     * @param width
     * @param height
     * @param game
     */
    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        sprite = Assets.player;
        iHurtFrames = 0;
        this.rectangle = new Rectangle(x, y, width, height);
        
        //animation = new Animation(Assets.alienSprite, 100);

    }

    /**
     *
     * @param dragging
     */
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    /**
     *
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     *
     * @param rectangle
     */
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     *
     * @param x
     * @param y
     */
    public Player(int x, int y) {
        super(x, y);
    }

    /**
     *
     */
    @Override
    public void tick() {

        //animation.tick();
//        this.animationRight.tick();
        //Mis condicionales del movimiento del jugador
        //De por si deberian explicarse por si mismas

        if (game.getKeyManager().left) {
            setX(getX() - 6);

        } else if (game.getKeyManager().right) {
            setX(getX() + 6);

        }
        

        //Mis condicionales para que el player no salga de la pantalla
        if (getX() >= game.getWidth() - this.width) {
            setX(getX() - 15);
        }
        if (getX() <= 0) {
            setX(getX() + 15);
        }
        if (getY() >= game.getHeight() - this.height) {
            setY(getY() - 15);
        }
        if (getY() <= 0) {
            setY(getY() + 15);
        }

    }

    /**
     *
     * @param g
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, getX(), getY(), getWidth(), getHeight(), null);
        //g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);

    }

    /**
     *
     * @return
     */
    @Override
    public int getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    @Override
    public void setX(int x) {
        this.x = x;
        this.rectangle.setLocation(this.x, this.y);
    }

    /**
     *
     * @return
     */
    public int getDirection() {
        return direction;
    }

    /**
     *
     * @param direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
        this.rectangle.setSize(this.width, this.height);
    }

    /**
     *
     * @return
     */
    @Override
    public int getY() {
        return y;

    }

    /**
     *
     * @param y
     */
    @Override
    public void setY(int y) {
        this.y = y;
        this.rectangle.setLocation(this.x, this.y);
    }

    /**
     *
     * @return
     */
    public int getiSpeedX() {
        return iSpeedX;
    }

    /**
     *
     * @return
     */
    public int getiSpeedY() {
        return iSpeedY;
    }

    /**
     *
     * @param iSpeedY
     */
    public void setiSpeedY(int iSpeedY) {
        this.iSpeedY = iSpeedY;
    }

    /**
     *
     * @param iSpeedX
     */
    public void setiSpeedX(int iSpeedX) {
        this.iSpeedX = iSpeedX;
    }

}
