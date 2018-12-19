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

/**
 *
 * @author Vincenzo Magna, Laura Fusco
 */
public class StateSlide extends State implements StateDemo {

    private Player player;
    private String infoMsg, succMsg;
    private boolean cpltAction;
    private KeyManager k;
    private Context ctx;
    private long lastTime, nowTime;
    private Animation animSlide;

    public StateSlide(Handler handler, Context ctx) {
        super(handler);

        infoMsg = "Press DOWN to SLIDING DOWN";
        succMsg = "Well done!";
        cpltAction = false;
        k = handler.getKeyManager();
        this.ctx = ctx;
        player = handler.getGame().getGameState().getPlayer();
        animSlide = new Animation(100, Assets.demoDown);
    }

    public boolean isCpltAction() {
        return cpltAction;
    }

    @Override
    public void tick() {
        animSlide.tick();
        
        if (!isCpltAction()) {
            getInput();
        } else {
            if (player.getY() == player.getGroundHeight()) {
                nextState();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animSlide.getCurrentFrame(), handler.getWidth() - (animSlide.getCurrentFrame().getWidth() + 40), 40, null);
        if (!isCpltAction()) {
            g.setColor(Color.white);
            g.drawString(infoMsg, 100, 50);
        } else {
            g.setColor(Color.red);
            g.drawString(succMsg, 100, 50);
        }
    }

    public void getInput() {
        if (k.down && player.getY() > player.getGroundHeight()) {
            cpltAction = true;
            lastTime = System.nanoTime();
        }
    }
    
    private void nextState() {
        nowTime = System.nanoTime();
        if (nowTime - lastTime > 1000000000 * 2.5f) {
            ctx.setState(new StateRunFast(handler, ctx));
        }
    }

}
