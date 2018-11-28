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
import java.awt.Color;
import java.awt.image.BufferedImage;


/**
 *
 * @author marcoruggiero
 */
public class Fireball extends Entity {
    
    private Animation fireballJutsu;
    
    public Fireball(Handler handler, float x, float y, int width, int height){
       super(handler,  x,  y,  width,  height);
       fireballJutsu = new Animation(240, Assets.fireballJutsu);
       bounds.x = 100;
       bounds.y = 60;
       bounds.width = 123;
       bounds.height = 120;
      
    }

    @Override
    public void tick(){
      
       move();
       
        fireballJutsu.tick();
    }

    //@Override
    public void render(Graphics g) {
 
         g.drawImage(getCurrentAnimationFrame(), (int) x+40, (int) y-15, null);
         //g.setColor(Color.red);
         //g.fillRect((int)x+bounds.x , (int)y+bounds.y , bounds.width, bounds.height);
       
    }
    
    private BufferedImage getCurrentAnimationFrame(){
        return fireballJutsu.getCurrentFrame();   
    }
    
    public void move(){
        x+=10;
    }
     
    
}
