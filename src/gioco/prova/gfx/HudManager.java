/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import gioco.prova.display.Score;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class HudManager {

    private BufferedImage life;
    private BufferedImage powerActive;
    private BufferedImage powerInactive;
    private BufferedImage kunai;
    private long lastTime;
    private long nowTime;
    private int currLife;
    private int currPower;
    private int maxPower;

    private Font font;
    private Score score;

    public HudManager(String path, String path2, String path3, int currLife) {
        life = ImageLoader.loadImage(path);
        powerInactive = ImageLoader.loadImage(path2);
        powerActive = ImageLoader.loadImage(path3);
        kunai = ImageLoader.loadImage("/hudBg/kunai.png");
        this.currLife = currLife;
        currPower = 0;
        maxPower = 210;
        lastTime = System.nanoTime();

        score = new Score();
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

    public void render(Graphics g) {
        int margin = 40;
        int offset = 20;

        font = FontLoader.load("res/fonts/naruto.ttf", 40);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString(Integer.toString(score.getCount()), 600, 80);
        
        switch (currLife) {
            case 1:
                g.drawImage(life, 1200 - (life.getWidth() + margin), margin, null);
                break;
            case 2:
                g.drawImage(life, 1200 - (2 * life.getWidth() + margin + offset), margin, null);
                g.drawImage(life, 1200 - (life.getWidth() + margin), margin, null);
                break;
            case 3:
                g.drawImage(life, 1200 - (3 * life.getWidth() + margin + offset * 2), margin, null);
                g.drawImage(life, 1200 - (2 * life.getWidth() + margin + offset), margin, null);
                g.drawImage(life, 1200 - (life.getWidth() + margin), margin, null);
                break;
        }

        g.setColor(Color.orange);
        g.drawRoundRect(margin + powerInactive.getWidth() + 10, margin + 10, maxPower + 10, powerInactive.getHeight() - 20, 25, 35);

        if (currPower == maxPower) {
            g.drawImage(powerActive, margin, margin, null);
            g.fillRoundRect(margin + powerInactive.getWidth() + 10 + 5, margin + 5 + 10, maxPower, powerInactive.getHeight() - 30, 15, 25);
        } else {
            g.drawImage(powerInactive, margin, margin, null);
            g.fillRoundRect(margin + powerInactive.getWidth() + 10 + 5, margin + 5 + 10, currPower, powerInactive.getHeight() - 30, 15, 25);
        }
        
        g.drawImage(kunai, margin, margin + 50, null);

        nowTime = System.nanoTime(); //used for time generation of enemies        
        if (nowTime - lastTime > 0.25f * 1000000000 && currPower < maxPower) {
            currPower += 3;
            lastTime = System.nanoTime();
        }

    }

}
