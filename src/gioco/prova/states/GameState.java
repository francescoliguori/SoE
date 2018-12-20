/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Handler;
import gioco.prova.display.Background;
import gioco.prova.display.ParallaxBackground;
import gioco.prova.entities.Boss;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Player;
import gioco.prova.gfx.HudManager;
import gioco.prova.gfx.Assets;
import java.awt.Graphics;

public class GameState extends State {

    private Player player;
    private ParallaxBackground parallax;
    private Background fixBg;
    private ControllerEntities controller;
    private HudManager hudmngr;
    private Boss boss;
    private int random;
    private static boolean story = true;
    private boolean winBoss = false;

    public GameState(Handler handler) {
        super(handler);

        controller = new ControllerEntities(handler);
        player =  Player.getPlayerInstance(handler, 100, 400, controller);
        random = (int) (Math.random() * 2);
        fixBg = Background.randomBg(random);
        parallax = ParallaxBackground.randomParallaxBg(random, (int) player.getSpeed() / 2);
        hudmngr = new HudManager(player, controller);
    }

    public Player getPlayer() {
        return player;
    }

    public Boss getBoss() {
        return boss;
    }

    public HudManager getHudmngr() {
        return hudmngr;
    }

    @Override
    public void tick() {
        if (story) {
            getInput();
        } else {
            parallax.tick();
            hudmngr.tick();
            player.tick();
            controller.tick();
            if (player.getScore() >= 450 && !winBoss) {//450 def
                createBoss();
            }
        }
    }

    public ControllerEntities getController() {
        return controller;
    }

    @Override
    public void render(Graphics g) {
        if (story) {
            g.drawImage(Assets.story, 0, 0, null);
        } else {
            fixBg.render(g);
            parallax.render(g);
            hudmngr.render(g);
            player.render(g);
            controller.render(g);
        }
    }

    public void setController(ControllerEntities controller) {
        this.controller = controller;
    }

    private void createBoss() {
        boss = Boss.getBossInstance(handler, handler.getWidth(), 427, controller); //in modo da dare
        //la stessa altezza del player! Perchè gli sprite hanno altezze diverse
        controller.setFinalBoss(true);
    }
    
    public void destroyBoss(){
        boss = null;
        controller.setFinalBoss(false);
        winBoss = true;
        Player.setCount(player.MAX_KUNAI);
        player.setHealth(player.MAX_HEALTH);
    }

    public void getInput() {
        if (handler.getKeyManager().enter) {
            story = false;
        }
    }

}
