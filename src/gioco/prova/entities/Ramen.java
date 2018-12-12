/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import gioco.prova.gfx.Assets;
import gioco.prova.gfx.ImageLoader;
import java.awt.Color;
import java.awt.Graphics;

public class Ramen extends Entity {

    private long now;
    private long last = System.nanoTime();
    private boolean spawn = false;

    public Ramen(Handler handler, float x, float y) {
        super(handler, x, y, 187, 155);
        bounds.height = 42;
        bounds.width = 55;
    }

    @Override
    public void tick() {
        move();
    }

    @Override
    public void render(Graphics g) {
           g.drawImage(Assets.ramen, (int) this.x, (int) this.y, null);
 
    }

   /* public BufferedImage spawn() {
        if (spawn) {
            return Assets.ramen;
        }
       return null;
   }
*/
    public boolean checkPlayerCollisions(float xOffset, float yOffset) {
        if (handler.getGame().getGameState().getPlayer().getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(xOffset, yOffset))) {
            //System.out.println("collision ramen");
            return true;
        }
        return false;      
    }
//    public int  posSpawnX(){
//        return (int) (Math.random() * 10);
//    }
//    
//    public int  posSpawnY(){
//        int pos=(int) (Math.random() * 6);
//           if(pos<=3)
//               pos=3;
//           //System.out.println(pos);
//           return pos;
//    }
        public void move() {
        x -= 10.0f;
    }
}