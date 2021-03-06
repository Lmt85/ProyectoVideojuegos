package videoGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * class in charge of managing keyboard inputs from a given window
 * implements KeyListener so that it overrides them with the wanted callbacks.
 * Contains an array with boolean values for each key pressed true if pressed, 
 * false if not.
 * Contain an array with boolean values for each key that was released, after
 * the key got released its value will be true up until been read by using the
 * getWasPresed(key) function at which point it will be reseted to false.
 * 
 * @author Carlos Adrian Guerra Vazquez A00823198
 * @date 28/01/2019
 * @version 1.0
 */
public class KeyManager implements KeyListener{
    //self explanatory flags that stores if the respective key is pressed
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    
    public boolean north;
    public boolean east;
    public boolean south;
    public boolean west;
    
    public boolean space;
    public boolean paused;
    public boolean save;
    public boolean load;
    public boolean restart;
    
    //key array for pressed and released keys
    public boolean keys[];
    private boolean wasPressed[];
    
    /**
     * Constructor initializes the array to 256 as it is the limit to which 
     * KeyEvent constants are registered.
     */
    public KeyManager(){
        keys = new boolean[256];
        wasPressed = new boolean[256];
    }
    
    //pls dont use this
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
       
    /**
     * callback that sets the key array value of the key pressed to true if pressed
     * @param e parameter used by implementer
     */
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }
    
    /**
     * callback that sets the key array value of the key released to false if pressed, and 
     * sets the key WasPressed array value of the key released to true when released
     * @param e parameter used by implementer 
     */
    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        wasPressed[e.getKeyCode()] = true;
        
    }
    
    /**
     * code to be executed once every frame. updates the axis pressed values
     */
    public void tick(){
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left =  keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D]; 
        
        north = keys[KeyEvent.VK_UP];
        east = keys[KeyEvent.VK_RIGHT];
        south = keys[KeyEvent.VK_DOWN];
        west = keys[KeyEvent.VK_LEFT];
                
        space = keys[KeyEvent.VK_SPACE];
        paused = keys[KeyEvent.VK_P];
        save = keys[KeyEvent.VK_G];
        load = keys[KeyEvent.VK_C];
        restart = keys[KeyEvent.VK_R];
    }
    
    /**
     * returns if a key was pressed in any moment in the past before this method
     * was called. resets the wasPressed status of the key to false.
     * @param VK_Value A <b>KeyEvent</b> integer value for the wanted key.
     * @return Boolean indicating if the key was pressed.
     */
    public boolean getWasPresed(int VK_Value){
         boolean pressed = wasPressed[VK_Value];
         wasPressed[VK_Value] = false;
         return pressed;
    }
}
