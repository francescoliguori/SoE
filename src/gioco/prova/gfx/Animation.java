/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import java.awt.image.BufferedImage;

/**
 *
 * @author marcoruggiero
 */
public class Animation {
    private int index, speed;
    private long lastTime, timer;
    private BufferedImage[] frames;

    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        //index = 0 per mostrare il primo frame dell'animazione
        index = 0;
        timer = 0;
        lastTime = System.currentTimeMillis();
       
        
        
    }
    
    public void tick()
    {
        //la differenza sottostante indica l'intervallo di tempo tra due tick
        timer +=  System.currentTimeMillis() -lastTime;
        lastTime = System.currentTimeMillis();
        
        if (timer > speed)
        {
            index++;
            timer = 0;
            //una volta arriavati all'ultimo frame, il successivo sarà il primo
            if (index >= frames.length)
            {
                index = 0;
            }
        }
    }
    public BufferedImage getCurrentFrame()
    {
      return frames[index];   
    }
    
    public BufferedImage getFrame(int i)
    {
      return frames[i];   
    }
}
