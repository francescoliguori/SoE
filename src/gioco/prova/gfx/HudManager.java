/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import gioco.prova.display.Score;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class HudManager {

    private BufferedImage life;
    private BufferedImage powerActive;
    private BufferedImage powerInactive;
    private BufferedImage kunai;
    private BufferedImage bossImage;
    private long lastTime;
    private long nowTime;
    private int currLife;
    private int currPower;
    private int maxPower;
    private Font fontScore, fontKunai;
    private Score score;
    private Player player;

    private ControllerEntities c;

    public HudManager(Player player, ControllerEntities c) {
        this.player = player;
        fontScore = FontLoader.load("res/fonts/naruto.ttf", 40);
        fontKunai = FontLoader.load("res/fonts/naruto.ttf", 18);
        life = ImageLoader.loadImage("/hudBg/lifeicon.png");
        powerInactive = ImageLoader.loadImage("/hudBg/powinactive.png");
        powerActive = ImageLoader.loadImage("/hudBg/powactive.png");
        kunai = ImageLoader.loadImage("/hudBg/kunai.png");
        currLife = player.getHealth();
        bossImage = ImageLoader.loadImage("/hudBg/oro.png");
        currPower = 0;
        maxPower = 210;
        lastTime = System.nanoTime();
        score = new Score();
        this.c = c;
    }

    public int getCurrLife() {
        return currLife;
    }

    public void setCurrLife(int currLife) {
        this.currLife = currLife;
    }

    public int getCurrPower() {
        return currPower;
    }

    public void setCurrPower(int currPower) {
        this.currPower = currPower;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public Score getScore() {
        return score;
    }

    public void updateLifeBar() {
        currLife = player.getHealth();
    }
    
    public void updateFireballBar() {
        nowTime = System.nanoTime(); //used for time generation of enemies        
        if (nowTime - lastTime > 0.25f * 1000000000 && currPower < maxPower) {
            currPower += 3;
            lastTime = System.nanoTime();
        }
    }
    
    public void tick() {
        updateLifeBar();
        updateFireballBar();
    }
    
    public void renderLifeBar(Graphics g) {
        int margin = 40;
        int offset = 20;
        
        switch(currLife) {
            case 1:
                g.drawImage(life, 1200 - (life.getWidth() + margin), margin, null);
                break;
            case 2:
                g.drawImage(life, 1200 - (2*life.getWidth() + margin + offset), margin, null);
                g.drawImage(life, 1200 - (life.getWidth() + margin), margin, null);
                break;
            case 3:
                g.drawImage(life, 1200 - (3*life.getWidth() + margin + offset*2), margin, null);
                g.drawImage(life, 1200 - (2*life.getWidth() + margin + offset), margin, null);
                g.drawImage(life, 1200 - (life.getWidth() + margin), margin, null);
                break;
        }
    }
    
    public void renderFireballBar(Graphics g) {
        int margin = 40, margin1 = 50, margin2 = 55;
        int offset1 = 20, offset2 = 30;
        int arcWidth1 = 25, archHeight1 = 35;
        int arcWidth2 = 15, archHeight2 = 25;
        int fill = 10;
        
        g.setColor(Color.orange);
        g.drawRoundRect(margin1 + powerInactive.getWidth(), margin1, maxPower + fill, powerInactive.getHeight() - offset1, arcWidth1, archHeight1);
        
        if(currPower == maxPower){
           g.drawImage(powerActive, margin, margin, null);
           g.fillRoundRect(margin2 + powerInactive.getWidth(), margin2, maxPower, powerInactive.getHeight() - offset2, arcWidth2, archHeight2);    
        }else{
           g.drawImage(powerInactive, margin, margin, null);
           g.fillRoundRect(margin2 + powerInactive.getWidth(), margin2, currPower, powerInactive.getHeight() - offset2, arcWidth2, archHeight2);
        }
    }
    
    public void renderScoreText(Graphics g) {
        g.setFont(fontScore);
        g.setColor(Color.white);
        g.drawString(Integer.toString(score.getCount()), 600, 80);
    }
    
    public void rendereKunaiBarText(Graphics g) {
        g.setFont(fontKunai);
        g.drawImage(kunai, 50, 100, null);
        g.drawString("x " + Integer.toString(Player.getCount()), 90, 125);
    }
    
    public void render(Graphics g) {
        renderLifeBar(g);
        renderFireballBar(g);
        rendereKunaiBarText(g);
        renderScoreText(g);
    }
    
}
