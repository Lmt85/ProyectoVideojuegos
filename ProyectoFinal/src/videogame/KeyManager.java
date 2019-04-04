/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author luismariotrujillo
 */
public class KeyManager  implements KeyListener{

    /**
     *
     */
    public boolean up;

    /**
     *
     */
    public boolean down;

    /**
     *
     */
    public boolean left;

    /**
     *
     */
    public boolean right;
    //Aqui agregamos un space, para que pueda ser usado en Player

    /**
     *
     */
    public boolean space;
    public boolean q;
    public boolean e;
    public boolean a;
    public boolean d;
    public boolean p;
    public boolean r;
    public boolean g;
    public boolean c;

    /**
     *
     */
    public boolean keys[];
    
    /**
     *
     */
    public KeyManager(){
        keys = new boolean[256];
    }
    
    /**
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        
        
            keys[e.getKeyCode()] = true;
        
        
    }

    /**
     * Metodo para hacer que se mueva el player, a traves de un true
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
       
        
            keys[e.getKeyCode()] = false;
        
        
    }
    
    /**
     *
     */
    public void tick(){
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        //agregando Space
        space = keys[KeyEvent.VK_SPACE];
        //agregando QEAD
        q = keys[KeyEvent.VK_Q];
        e = keys[KeyEvent.VK_E];
        a = keys[KeyEvent.VK_A];
        d = keys[KeyEvent.VK_D];
        //P de pausa
        p = keys[KeyEvent.VK_P];
        //R de reiniciar
        r = keys[KeyEvent.VK_R];
        //G de guardar
        g = keys[KeyEvent.VK_G];
        //C de cargar
        c = keys[KeyEvent.VK_C];
    }
    
    /**
     *
     * @param keyCode
     * @param bool
     * Metodo creado para cambiar de true a false despues de key released
     */
    public void keyDone(int keyCode, boolean bool){
        keys[keyCode] = bool;
    }
}