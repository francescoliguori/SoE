/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import gioco.prova.bullets.Arrow;
import gioco.prova.gfx.Animation;
import gioco.prova.gfx.Assets;
import static gioco.prova.gfx.Assets.enemies1Shot;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Utente
 */
public class Enemy1 extends Enemies {

    private Animation enemyRunning1;
    private Animation enemyDead1;
    private Animation enemyShot1;
    private ControllerEntities c;

    //varibili per il tempo
    private long lastTime = System.nanoTime();
    private long now;
    private static float timeBehavior = 2f;
    private boolean shot = false;

    //inizialmente è possibile fare una collisione con il nemico, una volta colpito 
    //si annulla la possibilità di collidere con esso.
    public Enemy1(Handler handler, float x, float y, ControllerEntities c) {
        super(handler, x, y);
        enemyRunning1 = new Animation(200, Assets.enemies1);
        enemyShot1 = new Animation(150, Assets.enemies1Shot);
        enemyDead1 = new Animation(70, Assets.enemies1Dead);
        this.c = c;

        bounds.x = 50;
        bounds.y = 80;
        bounds.height = 90;
        bounds.width = 65;
    }

    @Override
    public void tick() {
        enemyRunning1.tick();
        
        if (dead) {
            enemyDead1.tick();
        } 
        
        if (shot) {
            enemyShot1.tick();
        } else {
            move();
        }
        
        getInput();
    }

    @Override
    public void render(Graphics g) {
        if (lastDeadFrame) {
            g.drawImage(this.getCurrentAnimationFrame(), (int) x, (int) y + 15, null);
        } else {
            g.drawImage(this.getCurrentAnimationFrame(), (int) x, (int) y, null);
        }
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;
        
        if(dead){
            xMove-=speed;
        }else{
            xMove -= speed-5;
        }
        now = System.nanoTime();

        if (!dead) {
            enemyBehavior();
        }
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (!dead && (this.checkKunaiCollisions(0, 0) || this.checkFireballCollisions(0, 0))) {
            dead = true;
            //handler.getGame().getGameState().getController().removeEnemy(this);
            return this.enemyDead1.getCurrentFrame();
        }

        if (dead && lastDeadFrame) {
            return this.enemyDead1.getFrame(7);
        }

        if (dead) {
            if (enemyDead1.getCurrentFrame() == enemyDead1.getFrame(3)) {
                lastDeadFrame = true;
            }
            return this.enemyDead1.getCurrentFrame();
        }

        return this.enemyRunning1.getCurrentFrame();
    }

    public static void setTimeBehavior(float time) {
        timeBehavior = time;
    }

    private void enemyBehavior() {
        if (now - lastTime > timeBehavior * 500000000) {
            c.addArrowEnemies(new Arrow(handler, this.getX() - this.width, this.getY(), width, height, false));
            lastTime = System.nanoTime();
        }
    }

}
