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
public class Enemies extends Creature {
    
    private Animation enemyRunning;
    
    public Enemies(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        enemyRunning = new Animation(50, Assets.enemies);
    }

    @Override
    public void tick() {
       enemyRunning.tick();
       getInput();
       move();
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(enemyRunning.getCurrentFrame(), (int) x, (int) y, null);
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
    private void destroyEnemy(Graphics g){
        if((x-xMove) <= 0) {
            g.clearRect(0, 300, width, height);
         
        }
    }
    
}
