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
import gioco.prova.score.ReadScore;
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
        //"THE STORY",
        "COMMANDS",
        "CREDITS",
        "QUIT"
    };
    private Color noSelected = new Color(166, 20, 20);
    private Color selected = new Color(238, 116, 7);
    private Font font,fontScore;
    private ReadScore r;
    private String s;
    
    private int choice = 0;
    //private GameState gameState;
    private KeyManager k = handler.getKeyManager();
    private boolean option = false;

    public MenuState(Handler handler) {
        super(handler);
        font = FontLoader.load("res/fonts/naruto.ttf", 40);
        fontScore = FontLoader.load("res/fonts/naruto.ttf", 100);
        handler.getGame().setFps(10);

    }

    @Override
    public void tick() {
        if (!option) {
            getInputClassic();
        } else {
            getInputOption();
        }
    }

    @Override
    public void render(Graphics g) {
        if (!option) {
            g.setFont(font);
            g.drawImage(Assets.menu, 0, 0, null);
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
        } else {
            switch (choice) {
                case 2: //score
                    g.drawImage(Assets.score, 0, 0, null);
                    g.setFont(fontScore);
                    g.setColor(noSelected);
                    g.drawString(s, 250, 300);
                    break;
//                case 3: //story
//                    g.drawImage(Assets.story, 0, 0, null);
//                    break;
                case 3: //commands
                    g.drawImage(Assets.commands, 0, 0, null);
                    break;
                case 4: //credits
                    g.drawImage(Assets.credits, 0, 0, null);
                    break;

            }
        }
    }

    public void getInputClassic() {
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

    public void getInputOption() {
        if (k.esc) {
            option = false;
        }
    }

    private void select() {
        switch (choice) {
            case 0: //start the game              
                Player.restartPlayer();
                handler.getGame().setGameState(new GameState(handler));
                handler.getGame().setFps(60);
                handler.getGame().setState(handler.getGame().getGameState());
                break;
//            case 1: //demostate
//                //demoState=new DemoState(handler);
//                //handler.getGame().setFps(60);
//                //handler.getGame().setGameState(demoState);
//                //State.setState(demoState);
//                break;
            case 2: //score
                //State.setState(new ScoreState(handler));
                r = new ReadScore();
                s = r.read();
                if (s == null) {
                    s = "";
                }
                option = true;
                break;
//            case 3: //the story
//                //State.setState(new StoryState(handler));
//
//                option = true;
//                break;
            case 3: //commands
                //State.setState(new CommandsState(handler));
                option = true;
                break;
            case 4: //credits
                //State.setState(new CreditsState(handler));
                option = true;
                break;
            case 5: //exit
                System.exit(0);
                break;
        }
    }

}
