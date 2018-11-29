/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Game;
import gioco.prova.Handler;

/**
 *
 * @author marcoruggiero
 */
public abstract class Creature extends Entity
{
    public static final int DEFAULT_HEALTH = 10;
    protected int health;
    public static final float DEAFULT_SPEED = 5.0f;
    protected float speed;
    public static final int DEFAULT_CREATURE_WIDTH = 155;
    public static final int DEFAULT_CREATURE_HEIGHT = 187;
    
    protected float xMove, yMove;
    public Creature(Handler handler, float x,float y, int width, int height)
    {
        super(handler,x,y,width,height);
        
        health = DEFAULT_HEALTH;
        speed = DEAFULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    public void move()
    {
        x += xMove;
        y += yMove;
        
    }
    public float getxMove() {
        return xMove;
    }

    public void setxMove(float xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(float yMove) {
        this.yMove = yMove;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
}
