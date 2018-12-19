/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Handler;
import gioco.prova.input.KeyManager;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Vincenzo Magna, Laura Fusco
 */
public class StateEnd extends State implements StateDemo {

    private Context ctx;
    private boolean endVideo;
    private float videoTime;
    private long nowTime, lastTime;
    private Image image;
    private KeyManager k;
    
    public StateEnd(Handler handler, Context ctx) {
        super(handler);
        this.ctx = ctx;
        lastTime = System.nanoTime();
        endVideo = false;
        videoTime = 3.0f;
        URL url = this.getClass().getResource("/itachi_eyes.gif");
        image = new ImageIcon(url).getImage();
    }
    
    @Override
    public void tick() {
        if (!endVideo) {
            nowTime = System.nanoTime();
            if (nowTime - lastTime > videoTime * 1000000000) {
                endVideo = true;
            }
        }
   
    }
    
    @Override
    public void render(Graphics g) { 
        g.drawImage(image, 0, 0, null);
        
        if(endVideo) {
            nextState();
        }
    }
    
    private void nextState() {
        handler.getGame().setState(new MenuState(handler));
    }
}
