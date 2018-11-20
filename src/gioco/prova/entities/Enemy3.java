/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import gioco.prova.gfx.Animation;
import gioco.prova.gfx.Assets;
import java.awt.Graphics;

/**
 *
 * @author Utente
 */ 
public class Enemy3 extends Enemies {
    private Animation enemyFlying;     

    public Enemy3(Handler handler, float x, float y) {
        super(handler, x, y);
         enemyFlying = new Animation(100, Assets.enemies3);
    }
         @Override
    public void tick() {
       enemyFlying.tick();
       getInput();
       move();
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(enemyFlying.getCurrentFrame(), (int) x, (int) y, null);
    }
    
    private void getInput() {
        xMove = 0;
        yMove = 0;
          /* if ((x - xMove) <= 0) {
                x = 0;
            } else {*/
                xMove -= speed;
            //}                 
    }
}
