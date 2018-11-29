/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.display;

import gioco.prova.states.GameState;
import java.awt.Graphics;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 *
 * @author Vincenzo
 */
public class ParallaxBackground {
    
    private ScrollBackground[] sbgs;

    public ParallaxBackground(String[] path, int speed) {
        
        sbgs = new ScrollBackground[path.length];
        String[] paths = new String[2];
        
        int speedSbgs[] = {(speed - 1), speed, (speed + 1)};        
        
        for (int i = 0; i < path.length; i++) {
            paths[0] = path[i];
            paths[1] = path[i];
            sbgs[i] = new ScrollBackground(paths, speedSbgs[i]);
        }
    }
    
    public void tick() {
        for (int i = 0; i < sbgs.length; i++)
            sbgs[i].tick();
    }
    
    public void render(Graphics g) {
        for (int i = 0; i < sbgs.length; i++)
            sbgs[i].render(g);
    }
}
