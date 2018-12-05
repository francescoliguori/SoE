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
    private BufferedImage[] lives;
    private int currLife;
    
    public HudManager(String[] path, int currLife) {
        lives = new BufferedImage[path.length];
        
        for (int i = 0; i < path.length; i++) {
            lives[i] = ImageLoader.loadImage(path[i]);
        }
        
        this.currLife = currLife;
    }

    public int getCurrLife() {
        return currLife;
    }

    public void setCurrLife(int currLife) {
        this.currLife = currLife;
    }
    
    public void render(Graphics g) {
        if (currLife > 0) {
            g.drawImage(lives[currLife], 1050, 15, null);
        } else {
            g.drawImage(lives[0], 1050, 15, null);
        }
    }
    
}
