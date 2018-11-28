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
import java.awt.Graphics;

/**
 *
 * @author Utente
 */
public abstract class Enemies extends Creature {
    
    public Enemies(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
       
    }

    private void destroyEnemy(Graphics g){
       if((x-xMove) <= 0) {
            g.clearRect(0, 300, width, height);
         
        }
    }
     //questo metodo è utilizzato per controllare se un nemico 
    //entra in collisione con un kunai lanciato dal personaggio
    public boolean checkKunaiColliions(float xOffset,float yOffset)
    {
       for (Kunai k : handler.getGame().getGameState().getController().getK()){
           if (k.getCollisionBounds(0f,0f).intersects(this.getCollisionBounds(xOffset, yOffset))){
               handler.getGame().getGameState().getController().removeKunai(k);
               return true;
           }
               
       }
       return false;
       
    }
     public boolean checkFireballCollisions(float xOffset,float yOffset)
    {
       for (Fireball f : handler.getGame().getGameState().getController().getF()){
           if (f.getCollisionBounds(0f,0f).intersects(this.getCollisionBounds(xOffset, yOffset))){
                return true;
           }
               
       }
       return false;
       
    }
    
    
}
