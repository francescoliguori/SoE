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
import java.awt.image.BufferedImage;

public class Enemy2 extends Enemies {

    private Animation enemyRunning2;
    private Animation enemyDead2;

    //inizialmente è possibile fare una collisione con il nemico, una volta colpito 
    //si annulla la possibilità di collidere con esso.
    public Enemy2(Handler handler, float x, float y) {
        super(handler, x, y);
        enemyRunning2 = new Animation(100, Assets.enemies2);
        enemyDead2 = new Animation(70, Assets.enemies2Dead);
        
        bounds.x = 25;
        bounds.y = 80;
        bounds.height = 90;
        bounds.width = 90;


    }

    @Override
    public void tick() {
        enemyRunning2.tick();
        if(dead)
            enemyDead2.tick();
        getInput();
        move();
    }

    @Override
    public void render(Graphics g) {
        if(lastDeadFrame){
            g.drawImage(this.getCurrentAnimationFrame(), (int) x, (int) y+15, null);
        }
        else{
            g.drawImage(this.getCurrentAnimationFrame(), (int) x, (int) y, null);
        }
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;
        xMove -= speed;          
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (!dead && (this.checkKunaiCollisions(0, 0) || this.checkFireballCollisions(0, 0))) {
            dead = true;
            return this.enemyDead2.getCurrentFrame();
        }

        if (dead && lastDeadFrame) {
            return this.enemyDead2.getFrame(4);
        }

        if (dead) {
            if (enemyDead2.getCurrentFrame() == enemyDead2.getFrame(3)) {
                lastDeadFrame = true;
            }
            return this.enemyDead2.getCurrentFrame();
        }

        return this.enemyRunning2.getCurrentFrame();
    }
}
