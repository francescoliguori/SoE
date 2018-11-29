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
import gioco.prova.gfx.Assets;
import java.awt.Graphics;

/**
 *
 * @author marcoruggiero
 */
public class GameState extends State
{
    private Player player;
   
    private ParallaxBackground parallax;
    private Background fixBg;
    private ControllerEntities controller;
    
    public GameState(Handler handler)
    {
        super(handler);
       
        controller= new ControllerEntities(handler);
        player = new Player(handler, 100, 325,controller);
        
        //Setting background image paths
        String[] paths = new String[3];
        paths[0] = "/background/tree4.png";
        paths[1] = "/background/tree3.png";
        //paths[2] = "/background/tree2.png";
        paths[2] = "/background/0red.png";
        parallax = new ParallaxBackground(paths, (int)player.getSpeed());
        fixBg = new Background(0, "/background/bgMoon.png");
       
    }
    
    public Player getPlayer() {
        return player;
    }
    
    @Override
    public void tick() 
    {
        parallax.tick();
        player.tick();
        controller.tick();
    }
     public ControllerEntities getController() {
        return controller;
    }
     
    @Override
    public void render(Graphics g)
    {
        fixBg.render(g);
        parallax.render(g);
        player.render(g);
        controller.render(g);
    }
    
}
