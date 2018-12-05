/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import gioco.prova.bullets.Kunai;
import gioco.prova.gfx.Animation;
import gioco.prova.gfx.Assets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Utente
 */
public class Enemy3 extends Enemies {

    private ControllerEntities c;

    private Animation enemyRunning3;
    private Animation enemyDead3;
    private Animation enemyJump3;
    private boolean lastDeadFrame = false;

    //varibili per il tempo
    private long lastTimeJump = System.currentTimeMillis();
    private long lastTime = System.nanoTime();
    private long lastTimeKunai = System.currentTimeMillis();

    private long now;

    protected double gravity;
    private boolean jumping = false;
    private boolean falling = true;
    private float jumpStrength = 200;
    private float jumpStep = 15;

    private float groundHeight;
    
    private static float timeBehavior = 1.5f;

    
    public Enemy3(Handler handler, float x, float y, ControllerEntities c) {
        super(handler, x, y);
        enemyRunning3 = new Animation(100, Assets.enemies3);
        enemyDead3 = new Animation(50, Assets.enemies3Dead);
        enemyJump3 = new Animation(90, Assets.enemies3Jump);

        this.c = c;
        groundHeight = y;
        bounds.x = 20;
        bounds.y = 80;
        bounds.height = 80;
        bounds.width = 80;
    }

    @Override
    public void tick() {
        enemyRunning3.tick();
        if (isDead()) {
            enemyDead3.tick();
        }
        getInput();
        move();
    }

    private void fall() {
        if (falling) {
            bounds.height = 90;
            bounds.y = 100;
            y += gravity;
            gravity += 1;
            if (collisionWithGround(y)) {
                y = groundHeight;
                falling = false;
                gravity = 0.5;
            }
        }

    }

    public void jump(float step) {
        if (jumping) {
            bounds.height = 120;
            bounds.y = 70;
            y -= step;
            jumpStep -= 0.6;
            if (y <= groundHeight - jumpStrength) {
                jumping = false;
                falling = true;

                jumpStep = 15;
            }
        }
    }

    public boolean collisionWithGround(float y) {
        return y >= groundHeight;
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        xMove -= speed;

        //if (!isDead()) {
        //if (!isDead() || (isDead() && !this.collisionWithGround(this.getY()))) {
        fall();
        if (!isDead()) {
            jump(jumpStep);
        }

        now = System.nanoTime(); //used for time generation of enemies        

        //System.out.println(yDelta);
        if (!isDead()) {
            enemyBehavior();
        }
//            now = System.currentTimeMillis();
//            if (y == groundHeight && now - lastTimeJump > Math.random()) {
//                yMove -= jumpStep;
//                jumping = true;
//                lastTimeJump = System.currentTimeMillis();
//            }
//
//            //sparare dopo un certo tempo
//            if (now - lastTimeKunai > 2000) {
//                c.addKunaiEnemies(new Kunai(handler, this.getX() - this.width, this.getY(), width, height, false));
//                lastTimeKunai = System.currentTimeMillis();
//            }
        //}
    }

   public static void setTimeBehavior(float time) {
        timeBehavior = time;
    }

    private void enemyBehavior() {
        if (now - lastTime > timeBehavior*1000000000) {           //every 2 seconds at the moment
//            System.out.println(timeBehavior);
            if ((int) (Math.random() * 2) == 1) {
                if (y == groundHeight) {
                    yMove -= jumpStep;
                    jumping = true;
                    //lastTimeJump = System.currentTimeMillis();
                    lastTime = System.nanoTime();
                }

            } else {
                c.addKunaiEnemies(new Kunai(handler, this.getX() - this.width, this.getY(), width, height, false));

                //lastTimeKunai = System.currentTimeMillis();
                lastTime = System.nanoTime();
            }
        }
        /* if (y == groundHeight) {
                    yMove -= jumpStep;
                    jumping = true;
         }*/
    }

//    public boolean checkPlayerColliion(float xOffset, float yOffset) {
//        if (.getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(xOffset, yOffset))) {
//            if (e.isDead()) {
//                    //jumping = true;
//                //jump(jumpStep, groundHeight+100);
//                return false;
//            }
//            return true;
//        }
//
//        return false;
//
//    }
    @Override
    public void render(Graphics g) {

        if(lastDeadFrame){
            g.drawImage(this.getCurrentAnimationFrame(), (int) x, (int) y+12, null);
        }
        else{
            g.drawImage(this.getCurrentAnimationFrame(), (int) x, (int) y, null);
        }
            
        /* else if (jumping == true) {
            //g.drawImage(enemyJump3.getFrame(3), (int) x, (int) y, null);
            
                g.drawImage(this.getCurrentAnimationFrame(), (int) x, (int) y, null);
            
           
        } else if (falling == true && jumping == false) {
            //g.drawImage(enemyJump3.getFrame(2), (int) x, (int) y, null);
                g.drawImage(this.getCurrentAnimationFrame(), (int) x, (int) y, null);
            
            //g.drawImage(this.getCurrentAnimationFrame(), (int) x, (int) y, null);
        }*/
        //g.setColor(Color.red);
        //g.fillRect((int)x+bounds.x , (int)y+bounds.y , bounds.width, bounds.height);
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (!dead && (this.checkKunaiCollisions(0, 0) || this.checkFireballCollisions(0, 0))) {
            dead = true;
            falling = true;
            //System.out.println("Nemico colpito");
            //handler.getGame().getGameState().getController().removeEnemy(this);
            return this.enemyDead3.getCurrentFrame();
        }

        if (dead && lastDeadFrame) {
            return this.enemyDead3.getFrame(7);
        }

        if (dead) {
            if (enemyDead3.getCurrentFrame() == enemyDead3.getFrame(6)) {
                lastDeadFrame = true;
            }
            return this.enemyDead3.getCurrentFrame();
        }
        if (jumping == true) {
            return this.enemyJump3.getFrame(3);
        }
        if (falling == true && jumping == false) {
            return this.enemyJump3.getFrame(2);

        }

        return this.enemyRunning3.getCurrentFrame();
    }
}
