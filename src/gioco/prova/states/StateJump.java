/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Handler;
import gioco.prova.entities.Player;
import gioco.prova.gfx.Animation;
import gioco.prova.gfx.Assets;
import gioco.prova.input.KeyManager;
import java.awt.Color;
import java.awt.Graphics;

public class StateJump extends State implements StateDemo {

    private Player player;
    private String infoMsg, succMsg;
    private boolean cpltAction;
    private KeyManager k;
    private Context ctx;
    private long nowTime, lastTime;
    private Animation animJump;

    public StateJump(Handler handler, Context ctx) {
        super(handler);

        infoMsg = "Press UP to JUMP";
        succMsg = "Well done!";
        cpltAction = false;
        k = handler.getKeyManager();
        this.ctx = ctx;
        player = handler.getGame().getGameState().getPlayer();
        animJump = new Animation(100, Assets.demoUp);
    }

    public boolean isCpltAction() {
        return cpltAction;
    }

    @Override
    public void tick() {
        animJump.tick();
        
        if (!isCpltAction()) {
            getInput();
        } else {
            if (player.collisionWithGround(player.getY())) {
                nextState();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animJump.getCurrentFrame(), handler.getWidth() - (animJump.getCurrentFrame().getWidth() + 40), 40, null);
        
        if (!isCpltAction()) {
            g.setColor(Color.white);
            g.drawString(infoMsg, 100, 50);
        } else {
            g.setColor(Color.red);
            g.drawString(succMsg, 100, 50);
        }
    }

    public void getInput() {
        if (k.up && player.getY() < player.getGroundHeight()) {
            cpltAction = true;
            lastTime = System.nanoTime();
        }
    }
    
    private void nextState() {
        nowTime = System.nanoTime();
        if (nowTime - lastTime > 1000000000 * 2.5f) {
            ctx.setState(new StateSlide(handler, ctx));
        }
    }
    
}
