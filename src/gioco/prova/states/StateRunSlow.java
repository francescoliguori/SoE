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
import gioco.prova.gfx.ImageLoader;
import gioco.prova.input.KeyManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class StateRunSlow extends State implements StateDemo {

    private Player player;
    private String infoMsg, succMsg;
    private boolean cpltAction;
    private KeyManager k;
    private Context ctx;
    private long lastTime, nowTime;
    private BufferedImage arrow;
    private Animation animRunSlow;

    public StateRunSlow(Handler handler, Context ctx) {
        super(handler);

        infoMsg = "Press LEFT to RUN SLOWER";
        succMsg = "Well done!";
        cpltAction = false;
        k = handler.getKeyManager();
        this.ctx = ctx;
        player = handler.getGame().getGameState().getPlayer();
        arrow = ImageLoader.loadImage("/demo/arrowDemo1.png");
        animRunSlow = new Animation(100, Assets.demoLeft);
    }

    public boolean isCpltAction() {
        return cpltAction;
    }

    @Override
    public void tick() {
        animRunSlow.tick();
        if (!isCpltAction()) {
            getInput();
        } else {
            nextState();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animRunSlow.getCurrentFrame(), handler.getWidth() - (animRunSlow.getCurrentFrame().getWidth() + 40), 40, null);
        if (!isCpltAction()) {
            g.setColor(Color.white);
            g.drawString(infoMsg, 100, 50);
            g.drawImage(arrow, 5, 450, null);
        } else {
            g.setColor(Color.red);
            g.drawString(succMsg, 100, 50);
        }
    }

    public void getInput() {
        if (k.left) {
            if (player.getX() <= arrow.getWidth() + 20) {
                cpltAction = true;
                lastTime = System.nanoTime();
            }
        }
    }
    
    private void nextState() {
        nowTime = System.nanoTime();
        if (nowTime - lastTime > 1000000000 * 2.5f) {
            ctx.setState(new StateKunai(handler, ctx));
        }
    }
    
}
