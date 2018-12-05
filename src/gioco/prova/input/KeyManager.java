/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author marcoruggiero
 */
public class KeyManager implements KeyListener{
    
    private boolean [] keys;
    public boolean up, down, left, right,space, v, j;
    
    public KeyManager()
    {
        keys = new boolean[256];
    }
    
    public void tick()
    {
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        space = keys[KeyEvent.VK_SPACE];
        v = keys[KeyEvent.VK_V];
        j = keys[KeyEvent.VK_J];
        
    }
    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    keys[e.getKeyCode()] = true;
//    System.out.println("Premuto");
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
     keys[e.getKeyCode()] = false;
    }
    
}
