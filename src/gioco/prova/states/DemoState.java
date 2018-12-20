/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Handler;
import gioco.prova.display.Background;
import gioco.prova.display.ParallaxBackground;
import gioco.prova.entities.Player;
import gioco.prova.gfx.FontLoader;
import gioco.prova.input.KeyManager;
import java.awt.Font;
import java.awt.Graphics;

public class DemoState extends State {

    private int random;
    private Player player;
    private Background fixBg;
    private ParallaxBackground parallax;
    private Context ctx;
    private StateJump statejump;
    private KeyManager k;
    private Font font;

    public DemoState(Handler handler) {
        super(handler);
        font = FontLoader.load("res/fonts/naruto.ttf", 40);
        player = Player.getPlayerInstance(handler, 100, 400, null);
        random = (int) (Math.random() * 2);
        fixBg = Background.randomBg(random);
        parallax = ParallaxBackground.randomParallaxBg(random, (int) player.getSpeed() / 2);
        k = handler.getKeyManager();
        ctx = new Context();
        statejump = new StateJump(handler, ctx);
        ctx.setState(statejump);
    }

    @Override
    public void tick() {
        parallax.tick();
        player.tick();
        ctx.tick();
    }

    @Override
    public void render(Graphics g) {
        g.setFont(font);
        fixBg.render(g);
        parallax.render(g);
        player.render(g);
        ctx.render(g);
    }

}
