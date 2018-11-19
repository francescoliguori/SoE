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
public class Enemy2 extends Enemies {
    private Animation enemyRunning2;     

    public Enemy2(Handler handler, float x, float y) {
        super(handler, x, y);
         enemyRunning2 = new Animation(90, Assets.enemies2);
    }
         @Override
    public void tick() {
       enemyRunning2.tick();
       getInput();
       move();
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(enemyRunning2.getCurrentFrame(), (int) x, (int) y, null);
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
