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
import java.awt.image.BufferedImage;

/**
 *
 * @author Utente
 */
public class Ramen extends Entity {

    private long now;
    private long last = System.nanoTime();
    private boolean spawn = false;

    public Ramen(Handler handler, float x, float y) {
        super(handler, x, y, 187, 155);
        bounds.x = 70;
        bounds.y = 100;
        bounds.height = 30;
        bounds.width = 60;
    }

    @Override
    public void tick() {
       
    }

    @Override
    public void render(Graphics g) {
//         if (spawn){
        //g.drawImage(Assets.ramen, (int) this.x, (int) this.y, null);
        //g.setColor(Color.red);
        //g.fillRect((int)x+bounds.x , (int)y+bounds.y , bounds.width, bounds.height);
//        }
//         spawn=false;
    }

//    public BufferedImage spawn() {
//        if (spawn) {
//            return Assets.ramen;
//        }
//        return null;
//    }

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
}
