/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.bullets;

import gioco.prova.Handler;
import gioco.prova.entities.Entity;
import java.awt.Graphics;
import gioco.prova.gfx.Animation;
import gioco.prova.gfx.Assets;
import java.awt.image.BufferedImage;


/**
 *
 * @author marcoruggiero
 */
public class Kunai extends Entity {
    
    private Animation kunaiThrow;
    
    public Kunai(Handler handler, float x, float y, int width, int height){
       super(handler,  x,  y,  width,  height);
       kunaiThrow = new Animation(100, Assets.kunaiThrow);
    }

    @Override
    public void tick(){
      
       move();
       
        kunaiThrow.tick();
    }

    //@Override
    public void render(Graphics g) {
 
         g.drawImage(getCurrentAnimationFrame(), (int) x+40, (int) y-15, null);
    }
    
    private BufferedImage getCurrentAnimationFrame(){
        return kunaiThrow.getCurrentFrame();   
    }
    
    public void move(){
        x+=17;
    }
     
    
}
