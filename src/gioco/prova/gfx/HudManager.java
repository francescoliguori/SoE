/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Vincenzo
 */
public class HudManager {
    private BufferedImage life;
    private int currLife;
    
    public HudManager(String path, int currLife) {
        life = ImageLoader.loadImage(path);
        this.currLife = currLife;
    }

    public int getCurrLife() {
        return currLife;
    }

    public void setCurrLife(int currLife) {
        this.currLife = currLife;
    }
    
    public void render(Graphics g) {
        int margin = 40;
        int offset = 20;
        
        switch(currLife) {
            case 1:
                g.drawImage(life, 1200 - (life.getWidth() + margin), margin, null);
                break;
            case 2:
                g.drawImage(life, 1200 - (2*life.getWidth() + margin + offset), margin, null);
                g.drawImage(life, 1200 - (life.getWidth() + margin), margin, null);
                break;
            case 3:
                g.drawImage(life, 1200 - (3*life.getWidth() + margin + offset*2), margin, null);
                g.drawImage(life, 1200 - (2*life.getWidth() + margin + offset), margin, null);
                g.drawImage(life, 1200 - (life.getWidth() + margin), margin, null);
                break;
        }
    }
    
}
