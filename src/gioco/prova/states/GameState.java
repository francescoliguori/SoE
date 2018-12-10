/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.display.Background;
import gioco.prova.display.ParallaxBackground;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Player;
import gioco.prova.gfx.HudManager;
import gioco.prova.gfx.Assets;
import java.awt.Graphics;

/**
 *
 * @author marcoruggiero
 */
public class GameState extends State {

    private Player player;

    private ParallaxBackground parallax;
    private Background fixBg;
    private ControllerEntities controller;
    
    private HudManager hudmngr;

    public GameState(Handler handler) {
        super(handler);

        controller = new ControllerEntities(handler);
        player = new Player(handler, 100, 400, controller);

        //Setting background image paths
        String[] pathsNight = new String[3];
        pathsNight[0] = "/background/mountain.png";
        pathsNight[1] = "/background/trees.png";
        pathsNight[2] = "/background/path.png";
        
        String[] pathsDay = new String[3];
        pathsDay[0] = "/background/mountain_day.png";
        pathsDay[1] = "/background/trees_day.png";
        pathsDay[2] = "/background/path_day.png";

        if ((int) (Math.random() * 2) == 0) {
            parallax = new ParallaxBackground(pathsNight, (int) (player.getSpeed() / 2));
            fixBg = new Background(0, "/background/bg_sky_night.png");
        } else {
            parallax = new ParallaxBackground(pathsDay, (int) (player.getSpeed() / 2));
            fixBg = new Background(0, "/background/bg_sky_day.png");
        }
        
        //Setting HUD
        String pathHud = "/hudBg/lifeicon.png";
        hudmngr = new HudManager(pathHud, player.getHealth());
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void tick() {
        hudmngr.setCurrLife(player.getHealth());
        parallax.tick();
        player.tick();
        controller.tick();
    }

    public ControllerEntities getController() {
        return controller;
    }

    @Override
    public void render(Graphics g) {
        fixBg.render(g);
        parallax.render(g);
        hudmngr.render(g);
        player.render(g);
        controller.render(g);
    }

    public void setController(ControllerEntities controller) {
        this.controller = controller;
    }
    
    
}
