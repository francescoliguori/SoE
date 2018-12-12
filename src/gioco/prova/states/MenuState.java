/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.entities.Player;
import gioco.prova.gfx.Assets;
import gioco.prova.gfx.FontLoader;
import gioco.prova.input.KeyManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author marcoruggiero
 */
public class MenuState extends State {

    private String[] menu = {
        "START",
        "DEMO",
        "HIGH SCORE",
        "THE STORY",
        "COMMANDS",
        "CREDITS",
        "QUIT"
    };
    private Color noSelected = new Color(166, 20, 20);
    private Color selected = new Color(238, 116, 7);
    private Font font;
    private int choice = 0;
    //private GameState gameState;
    private KeyManager k = handler.getKeyManager();

    public MenuState(Handler handler) {
        super(handler);
        font = FontLoader.load("res/fonts/naruto.ttf", 40);
        handler.getGame().setFps(12);

    }

    @Override
    public void tick() {
        getInput();
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(Assets.menu, 0, 0, null);
        g.setFont(font);
        for (int i = 0; i < menu.length; i++) {           
            if (i == choice) {
                //g.setColor(Color.RED);
                g.setColor(selected);
            } else {
                //g.setColor(Color.ORANGE);
                g.setColor(noSelected);
            }
            g.drawString(menu[i], 20, 250 + i * 50);
        }
    }

    public void getInput() {
        if (k.enter) {
            select();
        }
        if (k.up) {
            choice--;
            if (choice == -1) {
                choice = menu.length - 1;
            }
        }
        if (k.down) {
            choice++;
            if (choice == menu.length) {
                choice = 0;
            }
        }
    }

    private void select() {
        switch (choice) {
            case 0: //start the game              
                Player.restartPlayer();
                handler.getGame().setGameState(new GameState(handler));
                handler.getGame().setFps(60);
                State.setState(handler.getGame().getGameState());
                break;
            case 1: //demostate
                //demoState=new DemoState(handler);
                //handler.getGame().setFps(60);
                //handler.getGame().setGameState(demoState);
                //State.setState(demoState);
                break;
            case 2: //score
                State.setState(new ScoreState(handler));
                break;
            case 3: //the story
                State.setState(new StoryState(handler));
                break;
            case 4: //commands
                State.setState(new CommandsState(handler));
                break;
            case 5: //credits
                State.setState(new CreditsState(handler));
                break;
            case 6: //exit
                System.exit(0);
                break;
        }
    }

}
