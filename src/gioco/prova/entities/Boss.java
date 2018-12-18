/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import gioco.prova.bullets.Fireball;
import gioco.prova.bullets.Kunai;
import gioco.prova.gfx.Animation;
import gioco.prova.gfx.Assets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Giovanni
 */
public class Boss extends Enemies {

    private ControllerEntities c;

    private Animation oroHit;
    private Animation oroDead;
    private Animation oroJump;
    private Animation oroAttack;
    private Animation oroLongAttack;

    protected double gravity;

    private boolean jumping = false;
    private boolean falling = true;
    private boolean longAttack = false;
    private boolean attack = false;
    private float jumpStrength = 200;
    private float jumpStep = 15;

    private float groundHeight;
    private float bossSpeed = 10;

    private boolean forward = true;
    private static Boss instance = null;
    private long timeLongAttack = 130000000 * 16;
    private long startLongAttack = 0;
    private long timeAttack = 100000000 * 19;
    private long startAttack = 0;
    private boolean hit = false;
    private int lastIndex = 0;

    public static Boss getBossInstance(Handler handler, float x, float y, ControllerEntities c) {
        if (instance == null) {
            instance = new Boss(handler, x, y, c);
        }
        return instance;
    }

    //serve per il singleton, o posso farne a meno?
    public static void restartBoss() {
        instance = null;
    }

    private Boss(Handler handler, float x, float y, ControllerEntities c) {
        super(handler, x, y);
        oroHit = new Animation(200, Assets.oroHit);
//        oroDead = new Animation(200, Assets.oroDead);
        oroJump = new Animation(200, Assets.oroJump);
        oroAttack = new Animation(100, Assets.oroAttack);
        oroLongAttack = new Animation(130, Assets.oroLongAttack);
        gravity = 0.5;
        groundHeight = y;
        health = 100;

        setWidth(100);
        setHeight(160);
        bounds.x = 10;
        bounds.y = 100;
        bounds.height = 160;
        bounds.width = 80;
        this.c = c;
    }

    @Override
    public void tick() {
        hit = false;

        if (checkCollsionFireballPlayer(0, 0)) {
            hit = true;
            System.out.println("fuoccoooo");
        }
        if (checkCollsionKunaiPlayer(0, 0)) {
            hit = true;
            System.out.println("kuunai");
        }
        if (longAttack) {
            if (System.nanoTime() - startLongAttack > timeLongAttack) {
                longAttack = false;
            } else {
                oroLongAttack.tick();
            }
        }
        if (attack) {
            if (System.nanoTime() - startAttack > timeAttack) {
                attack = false;

            } else {
                oroAttack.tick();
            }
        } else {
            jump(jumpStep);
            fall();
        }
    }

    public void attack() {
        x -= 250;
        bounds.x = 10 + 250;
        bounds.width = 80;
        attack = true;
        lastIndex = 0;
        startAttack = System.nanoTime();
        oroAttack.setIndex(0);
        c.setAttackSequence(false);
    }

    public void longAttack() {
        x -= 351;
        bounds.x = 10 + 351;
        bounds.width = 80;
        lastIndex = 0;
        longAttack = true;
        startLongAttack = System.nanoTime();
        oroLongAttack.setIndex(0);
        c.setLongAttackSequence(false);
    }

    private void fall() {
        if (falling) {
            bounds.height = 140;
            bounds.y = 20;
            y += gravity;
            gravity += 1;
            if (this.forward) {
                walkForward();
            } else {
                walkBackward();
            }
            if (collisionWithGround(y)) {
                y = groundHeight;
                falling = false;
                gravity = 0.5;
                bounds.y = 45;
                bounds.height = 115;
            }
        }

    }

    private void walkBackward() {
        if (x >= handler.getWidth() - width) {
            x = (handler.getWidth() - width);
        } else {
            x += bossSpeed;
        }
    }

    private void walkForward() {
        if (x <= 0) {
            x = 0;
        } else {
            x -= bossSpeed;
        }
    }

    private void jump(float step) {
        if (jumping) {
            bounds.height = 150;
            bounds.y = 10;
            y -= step;
            jumpStep -= 0.6; //impiega 25 step per raggiungere il punto più alto
            if (this.forward) {
                walkForward();
            } else {
                walkBackward();
            }
            if (y <= (groundHeight - jumpStrength)) {//perchè cazzo ci vuole il 6??????
                jumping = false;
                falling = true;

                jumpStep = 15;
            }
        }
    }

    public void jumpOne(boolean forward) {
        this.forward = forward;
        y -= jumpStep;
        jumping = true;
        bounds.x = 10;
        bounds.width = 80;
    }

    public boolean collisionWithGround(float y) {
        return y >= groundHeight;
    }

    public boolean bossOnTheGround() {
        return y == groundHeight;
    }

    public boolean reachedB() {
        if (x <= (handler.getWidth() - width)) {
            return true;
        } else {
            xMove = -speed;
            move();
            return false;
        }
    }

    public boolean checkCollsionKunaiPlayer(float xOffset, float yOffset) {
        for (Kunai k : c.getListKunaiPlayer()) {
            if (k.getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(xOffset, yOffset)) && !longAttack && !attack) {
                c.removeKunaiPlayer(k);
                return true;
            }
        }
        return false;
    }

    public boolean checkCollsionFireballPlayer(float xOffset, float yOffset) {
        for (Fireball f : c.getF()) {
            if (f.getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(xOffset, yOffset)) && !longAttack && !attack) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.getCurrentAnimationFrame(), (int) x, (int) y, null);
//        g.setColor(Color.red);
//        g.fillRect((int) x + bounds.x, (int) y + bounds.y, bounds.width, bounds.height);
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (checkCollsionKunaiPlayer(0, 0) || checkCollsionFireballPlayer(0, 0)) {
            //System.out.println("Colpito");
            return this.oroJump.getFrame(0);
        }
        if (longAttack) {
            if (lastIndex != oroLongAttack.getIndex()) {
//                if (oroLongAttack.getIndex() > 6 && oroLongAttack.getIndex() < 9) {
//                    bounds.width += 156;
//                    bounds.x -= 156;
//                    System.out.println(bounds.width);
//                    System.out.println(bounds.x);
                if (oroLongAttack.getIndex() == 6){
                    bounds.width += 160;
                    bounds.x -= 160;
                } else if (oroLongAttack.getIndex() == 7){
                    bounds.width += 158;
                    bounds.x -= 158;
                } else if (oroLongAttack.getIndex() == 8){
                    bounds.width += 50;
                    bounds.x -= 50;
                }else if(oroLongAttack.getIndex() == 9) {
                    bounds.x += 59;
                    bounds.width -= 59;
                }else if(oroLongAttack.getIndex() == 10){
                    bounds.x += 171;
                    bounds.width -= 171;
                }
                else if(oroLongAttack.getIndex() == 11){
                    bounds.x += 100;
                    bounds.width -= 100;
                }
                else if(oroLongAttack.getIndex() == 12){
                    bounds.x += 36;
                    bounds.width -= 36;
                }
                lastIndex = oroLongAttack.getIndex();
            }
            return oroLongAttack.getCurrentFrame();

        }
        if (attack) {
            if (lastIndex != oroAttack.getIndex()) {
                if (oroAttack.getIndex() == 6){
                    bounds.width += 31;
                    bounds.x -= 31;
                }
                else if (oroAttack.getIndex() == 7){
                    bounds.width += 68;
                    bounds.x -= 68;
                }
                else if (oroAttack.getIndex() == 8){
                    bounds.width += 127;
                    bounds.x -= 127;
                }
                else if (oroAttack.getIndex() == 11){
                    bounds.x += 127;
                    bounds.width -= 127;
                }
                else if (oroAttack.getIndex() == 12){
                    bounds.x += 68;
                    bounds.width -= 68;
                }
                else if (oroAttack.getIndex() == 13){
                    bounds.x += 31;
                    bounds.width -= 31;
                }
//                if (oroAttack.getIndex() > 6 && oroAttack.getIndex() < 10) {
//                    bounds.x -= 78;
//                    bounds.width += 78;
//
//                } else if (oroAttack.getIndex() > 11 && oroAttack.getIndex() < 15) {
//                    bounds.x += 78;
//                    bounds.width -= 78;
//
//                }
                lastIndex = oroAttack.getIndex();
            }

            return oroAttack.getCurrentFrame();
        }
        if (jumping) {
            return this.oroJump.getFrame(0);
        }
        if (falling == true && !jumping) {
            return this.oroJump.getFrame(1);
        }

        return this.oroJump.getFrame(2);
    }

    public boolean isLongAttack() {
        return longAttack;
    }

    public boolean isAttack() {
        return attack;
    }
}
