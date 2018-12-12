/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Handler;
import gioco.prova.gfx.Assets;
import java.awt.Font;
import java.awt.Graphics;
import gioco.prova.score.ReadScore;
import gioco.prova.gfx.FontLoader;
import java.awt.Color;

/**
 *
 * @author Utente
 */
public class ScoreState extends State {

    private Font font;
    private ReadScore r;
    private String s;
    private int i = 0;

    public ScoreState(Handler handler) {
        super(handler);
        font = FontLoader.load("res/fonts/naruto.ttf", 100);
        r = new ReadScore();
        s = r.read();
        if(s==null)
            s="";
               
    }

    @Override
    public void tick() {
        getInput();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.score, 0, 0, null);
        g.setFont(font);
        
        g.setColor(new Color(166, 20, 20));
//        for (int j = 0; j < s.length; j++) {
//            g.drawString(s[j], 20, 100 + j * 50);
        g.drawString(s, 250, 300);
//        }
    }

    private void getInput() {
        if (handler.getKeyManager().esc) {
            State.setState(handler.getGame().getMenuState());
        }

    }

}
