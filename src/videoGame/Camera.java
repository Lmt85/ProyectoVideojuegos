/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoGame;

/**
 * Class that manages the game camera
 * @author Adrián Marcelo Suárez Ponce
 */
public class Camera {
    float x, y;
    Game game;
    
    public Camera(float x, float y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;
    }
    
    public void tick(Player player) {
        
        x += ((player.getPosition().getX() - x) - game.getWidth()/2) * 0.05f;
        y += ((player.getPosition().getY() - y) - game.getHeight()/2) * 0.05f;
        
        if(x < 0 - Commons.PLAYER_WIDTH) x = -Commons.PLAYER_WIDTH;
        if(x > Commons.BOARD_WIDTH + Commons.PLAYER_WIDTH) x = Commons.BOARD_WIDTH + Commons.PLAYER_WIDTH;
        if(y < 0 - Commons.PLAYER_HEIGHT) y = -Commons.PLAYER_HEIGHT;
        if(y > Commons.BOARD_HEIGHT + Commons.PLAYER_HEIGHT) y = Commons.BOARD_HEIGHT + Commons.PLAYER_HEIGHT;
        
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    
}
