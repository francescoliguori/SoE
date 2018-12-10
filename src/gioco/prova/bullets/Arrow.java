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
 * @author stefano
 */
public class Arrow extends Entity {

    private Animation arrowThrowBackward;

    public Arrow(Handler handler, float x, float y, int width, int height, boolean forward) {
        super(handler, x, y, width, height);

        arrowThrowBackward = new Animation(100, Assets.arrowThrowBackward);
        //creazione del quadrato di collisione per la freccia
        bounds.x = 118;
        bounds.y = 125;
        bounds.width = 23;
        bounds.height = 10;
    }

    @Override
    public void tick() {
        move();
        arrowThrowBackward.tick();

    }

    //@Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) x + 35, (int) y + 20, null);
        //g.setColor(Color.red);
        //g.fillRect((int)x+bounds.x , (int)y+bounds.y , bounds.width, bounds.height)
    }

    private BufferedImage getCurrentAnimationFrame() {
        return arrowThrowBackward.getCurrentFrame();
    }

    public void move() {
        x -= 20;
    }

}
