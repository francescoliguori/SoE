/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Handler;
import gioco.prova.bullets.Kunai;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Enemy2;
import gioco.prova.entities.Player;
import gioco.prova.input.KeyManager;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class StateKunai extends State implements StateDemo {
    private Player player;
    private ControllerEntities controller;
    private String infoMsg, scoreMsg, succMsg;
    private KeyManager k;
    private Context ctx;
    private boolean cpltAction;
    private long lastTime, nowTime;
    private int toKillEnemies, killedEnemies;

    public StateKunai(Handler handler, Context ctx) {
        super(handler);

        toKillEnemies = 3;
        killedEnemies = 0;
        infoMsg = "Press V to throw a kunai";
        scoreMsg = killedEnemies + "/" + toKillEnemies;
        succMsg = "Well done!";
        lastTime = System.nanoTime();
        cpltAction = false;
        k = handler.getKeyManager();
        this.ctx = ctx;
        player = handler.getGame().getGameState().getPlayer();
        initControllerEntities();
    }

    private void initControllerEntities() {
        controller = handler.getGame().getGameState().getController();
        controller.addEnemy(new Enemy2(handler, handler.getWidth(), 400));
        controller.setKunaiPlayer(new LinkedList<Kunai>());
    }
    
    public boolean isCpltAction() {
        return cpltAction;
    }

    @Override
    public void tick() {
        getInput();
        controller.enemyMovement();
        controller.kunaiPlayerMovement();
         
        if (!isCpltAction()) {
            player.setHealth(3); // ripristino vita player per non perdere.
            
            if (!controller.isEmptyEnemies() && controller.getE().getFirst().checkKunaiCollisions(0, 0)) {
                if (!controller.getE().getFirst().isDead()){
                    killedEnemies++;
                }
                controller.getE().getFirst().setDead(true);
            }
            
            if (controller.isEmptyEnemies() && killedEnemies <= toKillEnemies) {
                controller.addEnemy(new Enemy2(handler, handler.getWidth(), 400));
            }
        } else {
            nextState();
        }
    }

    @Override
    public void render(Graphics g) {
        controller.renderEnemy(g);
        controller.renderKunaiPlayer(g);
        
        if (!isCpltAction()) {
            g.setColor(Color.white);
            g.drawString(infoMsg, 100, 50);
            scoreMsg = killedEnemies + "/" + toKillEnemies;
            g.drawString(scoreMsg, 1000, 50);
        } else {
            g.setColor(Color.red);
            g.drawString(succMsg, 100, 50);        
        }
    }
    
    public void getInput() {
        if (killedEnemies == toKillEnemies) {
            cpltAction = true;
        }
    }
    
    private void nextState() {
        nowTime = System.nanoTime();
        if (nowTime - lastTime > 1000000000 * 2.5f && controller.isEmptyEnemies()) {
            ctx.setState(new StateFireball(handler, ctx));
        }
    }

}
