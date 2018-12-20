/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Handler;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Enemies;
import gioco.prova.entities.Enemy2;
import gioco.prova.entities.Player;
import gioco.prova.gfx.HudManager;
import gioco.prova.input.KeyManager;
import java.awt.Color;
import java.awt.Graphics;

public class StateFireball extends State implements StateDemo {

    private Player player;
    private ControllerEntities controller;
    private String infoMsg, warningMsg, succMsg;
    private KeyManager k;
    private HudManager hud;
    private Context ctx;
    private boolean cpltAction;
    private long lastTime1, lastTime2, nowTime;
    private int toKillEnemies, killedEnemies;

    public StateFireball(Handler handler, Context ctx) {
        super(handler);

        infoMsg = "Press SPACEBAR to shot a fireball";
        warningMsg = "Hey, watch out! A large group of enemies is coming.";
        succMsg = "Well done!";
        cpltAction = false;
        k = handler.getKeyManager();
        this.ctx = ctx;
        lastTime1 = System.nanoTime();
        lastTime2 = System.nanoTime();
        player = handler.getGame().getGameState().getPlayer();
        controller = handler.getGame().getGameState().getController();
        hud = handler.getGame().getGameState().getHudmngr();
        hud.setCurrPower(hud.getMaxPower());
        killedEnemies = 0;
        toKillEnemies = 3;
    }

    private void enemyGenerator() {
        nowTime = System.nanoTime();
        if (controller.getE().size() < toKillEnemies && nowTime - lastTime1 > 1000000000 * 0.2f) {
            lastTime1 = System.nanoTime();
            controller.addEnemy(new Enemy2(handler, handler.getWidth(), 400));
        }
    }

    public boolean isCpltAction() {
        return cpltAction;
    }

    @Override
    public void tick() {
        getInput();
        
        controller.fireballMovement();
        controller.enemyMovement();
        
        if (!isCpltAction()) {
            enemyGenerator();
            
            for(Enemies enemy: controller.getE()) {
                if (player.getHealth() <= 2) {
                    player.setHealth(3);
                }
                
                if (enemy.checkFireballCollisions(0, 0)) {
                    enemy.setDead(true);
                    killedEnemies++;
                }    
            }
        } else {
            nextState();
        }
    }

    @Override
    public void render(Graphics g) {
        controller.renderEnemy(g);
        controller.renderFireball(g);

        if (!isCpltAction()) {
            g.setColor(Color.white);
            g.drawString(infoMsg, 100, 50);
        } else {
            g.setColor(Color.red);
            g.drawString(succMsg, 100, 50);
        }
    }

    public void getInput() {
        if (killedEnemies == toKillEnemies) {
            hud.setCurrPower(hud.getMaxPower());
            cpltAction = true;
        }
    }

    private void nextState() {
            nowTime = System.nanoTime();
            if (nowTime - lastTime2 > 1000000000 * 2.5f && controller.isEmptyEnemies()) {
                ctx.setState(new StateHud(handler, ctx));
            }
    }
}
