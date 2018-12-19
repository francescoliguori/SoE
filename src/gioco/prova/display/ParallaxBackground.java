/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.display;

import java.awt.Graphics;

/**
 *
 * @author Vincenzo Magna
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
    
    public static ParallaxBackground randomParallaxBg(int random,  int speed) {
        
        //Setting background image paths
        String[] pathsNight = new String[3];
        pathsNight[0] = "/background/mountain.png";
        pathsNight[1] = "/background/trees.png";
        pathsNight[2] = "/background/path.png";
        
        String[] pathsDay = new String[3];
        pathsDay[0] = "/background/mountain_day.png";
        pathsDay[1] = "/background/trees_day.png";
        pathsDay[2] = "/background/path_day.png";
        
        if (random == 0) {
            return new ParallaxBackground(pathsNight, speed);
        } else {
            return new ParallaxBackground(pathsDay, speed);
        }
    }
}
