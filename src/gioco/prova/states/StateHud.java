/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Handler;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Player;
import gioco.prova.entities.Ramen;
import gioco.prova.gfx.FontLoader;
import gioco.prova.gfx.HudManager;
import gioco.prova.gfx.ImageLoader;
import gioco.prova.input.KeyManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Vincenzo Magna, Laura Fusco
 */
public class StateHud extends State implements StateDemo {

    private Player player;
    private Context ctx;
    private ControllerEntities controller;
    private HudManager hud;
    private KeyManager k;
    private Ramen ramen;
    private Font font;
    private String infoMsg, infoMsgLife, infoMsgKunai1, infoMsgKunai2, infoMsgFireball1, infoMsgFireball2, infoMsgRamenBowl, infoMsgFinal;
    private boolean addLifeBar, addKunaiBar, addFireballBar, addRamenBowl, showMsgLifeBar, showMsgKunaiBar, showMsgFireballBar, showMsgRamenBowl, showMsgFinal;
    private long nowTime, lastTime, lastTime2;
    
    public StateHud(Handler handler, Context ctx) {
        super(handler);
        this.ctx = ctx;
        player = handler.getGame().getGameState().getPlayer();
        hud = handler.getGame().getGameState().getHudmngr();
        controller = handler.getGame().getGameState().getController();
        controller.getListRamen().add(new Ramen(handler, handler.getWidth(), 550));
        k = handler.getKeyManager();
        font = FontLoader.load("res/fonts/naruto.ttf", 30);
        
        infoMsg = "Let's have a look at the HUD";
        lastTime = System.nanoTime();
        
        infoMsgLife = "This is your life counter";
        addLifeBar = false;
        showMsgLifeBar = false;
        
        infoMsgKunai1 = "Hey, this is your kunai reserve. If they finish wait for a second\n";
        infoMsgKunai2 =  "and a new kunai will be ready to be thrown!";
        addKunaiBar = false;
        showMsgKunaiBar = false;
        
        infoMsgFireball1 = "Did you like the fireball? Wait for bar fullfill and....";
        infoMsgFireball2 = "when you are on the ground and the fireball is available...FIRE.";
        addFireballBar = false;
        showMsgFireballBar = false;
        
        infoMsgRamenBowl = "Ops, you lost 1 life. Take the Ramen bowl to recovery.";
        addRamenBowl = false;
        showMsgRamenBowl = false;
        
        infoMsgFinal = "Well done! You are ready to START!";
        showMsgFinal = false;
    }
    
    @Override
    public void tick() {
        
        if (!addLifeBar && !addKunaiBar && !addFireballBar && !addRamenBowl) {
            updateHud(1, 3.0f);
        }
        
        if (addLifeBar && !addKunaiBar && !addFireballBar && !addRamenBowl && !showMsgFinal) {
            updateHud(2, 7.0f);
            controller.kunaiPlayerMovement();
        }
        
        if (addLifeBar && addKunaiBar && !addFireballBar && !addRamenBowl && !showMsgFinal) {
          updateHud(3, 9.0f);
          hud.updateFireballBar();
          controller.kunaiPlayerMovement();
          controller.fireballMovement();
        }
        
        if (addLifeBar && addKunaiBar && addFireballBar && !addRamenBowl && !showMsgFinal) {
            updateHud(4, 9.0f);
            hud.updateFireballBar();
            controller.kunaiPlayerMovement();
            controller.fireballMovement();
        } 
        
        if (addLifeBar && addKunaiBar && addFireballBar && addRamenBowl && !showMsgFinal) {
            hud.updateFireballBar();
            hud.updateLifeBar();
            controller.kunaiPlayerMovement();
            controller.fireballMovement();
            
            if (controller.getRamenBowl().size() == 0) {
                updateHud(5, 3.0f);
            }
        }
        
        if (showMsgFinal) {
            hud.updateFireballBar();
            controller.kunaiPlayerMovement();
            controller.fireballMovement();
            updateHud(6, 4.0f);
        }
        
    }
    
    @Override
    public void render(Graphics g) {
       //Set font type and color operations.
       g.setFont(font);
       g.setColor(Color.white);
       
       if (addLifeBar) {
            if (showMsgLifeBar) {
                g.drawString(infoMsgLife, 600, 250);
                g.drawImage(ImageLoader.loadImage("/demo/arrowDemo3.png"), 900, 50, null);
           } 
           
           hud.renderLifeBar(g);
       }
           
       if (addKunaiBar) {
            if (showMsgKunaiBar) {
                g.drawString(infoMsgKunai1, 100, 350);
                g.drawString(infoMsgKunai2, 100, 410);
                g.drawImage(ImageLoader.loadImage("/demo/arrowDemo4.png"), 120, 180, null);
            }
           
            hud.rendereKunaiBarText(g);
            controller.renderKunaiPlayer(g);
       }
           
       if (addFireballBar) {
           if (showMsgFireballBar) {
                g.drawString(infoMsgFireball1, 50, 250);
                g.drawString(infoMsgFireball2, 50, 310);
                g.drawImage(ImageLoader.loadImage("/demo/arrowDemo4.png"), 330, 60, null);
           }
           
           hud.renderFireballBar(g);
           controller.renderFireball(g);
       }
       
       if (addRamenBowl) {
            if (showMsgRamenBowl) {
               g.setColor(Color.white);
               g.drawString(infoMsgRamenBowl, 400, 450);
            }
           
            if (controller.getRamenBowl().size() != 0) {
                controller.getRamenBowl().getFirst().render(g);
            }
       }
       
       if (!addLifeBar && !addKunaiBar && !addFireballBar && !addRamenBowl) {
           g.drawString(infoMsg, 100, 50);
       }
       
       if (showMsgFinal) {
           g.drawString(infoMsgFinal, 400, 350);
       }
        
    }
    
    private void updateHud(int state, float waitTime) {
        nowTime = System.nanoTime();
        
        if (nowTime - lastTime > waitTime * 1000000000) {
            lastTime = System.nanoTime();
            
            switch(state) {
                case 1:
                    addLifeBar = true;
                    showMsgLifeBar = true;
                    break;
                case 2:
                    addKunaiBar = true;
                    showMsgLifeBar = false;
                    showMsgKunaiBar = true;
                    break;
                case 3:
                    addFireballBar = true;
                    showMsgKunaiBar = false;
                    showMsgFireballBar = true;
                    break;
                case 4:
                    player.setHealth(2);
                    controller.getRamenBowl().getFirst().setX(1000);
                    addRamenBowl = true;
                    showMsgFireballBar = false;
                    showMsgRamenBowl = true;
                    break;
                case 5:
                    showMsgRamenBowl = false;
                    showMsgFinal = true;
                break;
                case 6:
                    nextState();
                    break;
            }
        }
    }
    
    private void nextState() {
        ctx.setState(new StateEnd(handler, ctx));
    }
}
