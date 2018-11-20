/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.display;

import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vincenzo
 */
public class ScrollBackground {
    private Background[] bg;
    private int scrolling, delay;
    
    public ScrollBackground(String[] path, int scrolling) {
        this.scrolling = scrolling;
        bg = addBackgrounds(path);
        delay = scrolling * (path.length - 1);
    }

    public int getDelay() {
        return delay;
    }
    
    public void tick() {
        for(int i = 0; i < bg.length; i++) {
            if (bg[i].getX() > -bg[i].getImage().getWidth()) {
                bg[i].setX(bg[i].getX() - scrolling);
            } else {
                bg[i].setX((bg.length - 1) * (bg[i].getImage().getWidth() - (scrolling * (bg.length - 1))));
            }
        }
    }
    
    public void render(Graphics g) {
        try {
            Thread.currentThread().sleep(delay);
        } catch (InterruptedException ex) {
            Logger.getLogger(ScrollBackground.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i = 0; i < bg.length; i++) {
            g.drawImage(bg[i].getImage(), bg[i].getX(), 0, null);
        }
    }
    
    private Background[] addBackgrounds(String[] path) {
        bg = new Background[path.length];
       
        for(int i = 0; i < path.length; i++) {
            bg[i] = new Background(i, path[i]);
        }
        
        return bg;
    }
}
