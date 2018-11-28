/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Game;
import gioco.prova.Handler;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author marcoruggiero
 */
public abstract class Entity 
{   
    //protected: tutte le classi che estendono Entity avranno anche x e y
    protected Handler handler;
    protected float x, y;
    protected int width, height;
    //rettangolo per le collisioni
    protected Rectangle bounds;
    public Entity(Handler handler, float x, float y, int width, int height)
    {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        //creazione del rettangolo per le collisioni al momento della crazione 
        //dell'entità. 
        bounds = new Rectangle(0,0, width, height);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
    //funzione per ricavare i bound del rettangolo di collisione
    public Rectangle getCollisionBounds(float xOffset, float yOffset){
        return new Rectangle((int)(x + bounds.x + xOffset), (int)(y + bounds.y + yOffset), bounds.width, bounds.height);
    }
    
    public abstract void tick();
    
    public abstract void render(Graphics g);
}
