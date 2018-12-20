/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.display;

import java.awt.Graphics;

public class ScrollBackground {
    private Background[] bg;
    private int scrolling;
    
    public ScrollBackground(String[] path, int scrolling) {
        this.scrolling = scrolling;
        bg = addBackgrounds(path);
    }

    public Background[] getBg() {
        return bg;
    }

    public int getScrolling() {
        return scrolling;
    }
    
    public void tick() {
        for(int i = 0; i < bg.length; i++) {
            if (bg[i].getX() > -bg[i].getImage().getWidth()) {
                bg[i].setX(bg[i].getX() - scrolling);
            } else {
                bg[i].setX(((bg.length - 1) * (bg[i].getImage().getWidth())) - scrolling);
            }
            
        }
    }
    
    public void render(Graphics g) {
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
