/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.display.ScrollBackground;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Enemies;
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
    private ScrollBackground scrollbg;
    private ControllerEntities controller;
    
    public GameState(Handler handler)
    {
        super(handler);
       
        controller= new ControllerEntities(handler);
        player = new Player(handler, 100, 300,controller);
     
        //Setting background image paths
        String[] paths = new String[2];
        paths[0] = "/texture/pattern.jpg";
        paths[1] = "/texture/pattern2.jpg";
        //Instantiate ScrollBackground with the image
        scrollbg = new ScrollBackground(paths, 2);
       
    }
    @Override
    public void tick() 
    {
        scrollbg.tick();
        player.tick();
        controller.tick();
    }

    @Override
    public void render(Graphics g)
    {
        scrollbg.render(g);
        player.render(g);
        controller.render(g);
    }
    
}
