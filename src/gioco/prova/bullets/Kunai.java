/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.bullets;

import gioco.prova.Handler;
import gioco.prova.entities.Creature;
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
public class Kunai extends Entity {

    private Animation kunaiThrowForward;
    private Animation kunaiThrowBackward;
    private boolean forward;

    public Kunai(Handler handler, float x, float y, int width, int height, boolean forward) {
        super(handler, x, y, width, height);
        kunaiThrowForward = new Animation(100, Assets.kunaiThrowForward);
        kunaiThrowBackward = new Animation(100, Assets.kunaiThrowBackward);
        //creazione del quadrato di collisione per il kunai
        if(!forward){
            bounds.x = 128;
            bounds.width = 23;
        }
        else{
            bounds.x = 143;
            bounds.width = 23;
        }
        bounds.y = 125;
        bounds.height = 10;
        this.forward = forward;
    }

    @Override
    public void tick() {
        move(forward);
        //System.out.println(forward);
        //move();
        if (forward) {
            kunaiThrowForward.tick();
        } else {
            kunaiThrowBackward.tick();
        }
        //else

    }

    public boolean isForward() {
        return forward;
    }

    //@Override
    public void render(Graphics g) {
        if(isForward()){
        g.drawImage(getCurrentAnimationFrame(), (int) x + 57, (int) y +1, null);
            
        }else{
        g.drawImage(getCurrentAnimationFrame(), (int) x +35, (int) y +6, null);
            
        }
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (forward) {
            return kunaiThrowForward.getCurrentFrame();
        } else {
            return kunaiThrowBackward.getCurrentFrame();
        }
    }

    public void move(boolean forward) {
        if (forward) {
            x += 17;
        } else {
            x -= 17 + (Creature.getDEAFULT_SPEED()-10);
        }
    }
//    public void move() {
//        x += 17;
//    }
}
