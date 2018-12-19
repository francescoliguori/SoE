/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import gioco.prova.display.Score;
import gioco.prova.entities.Boss;
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
    private Font font;
    private int bossLife;
    private Score score;
    private Player player;

    private ControllerEntities c;

    public HudManager(String path, String path2, String path3, int currLife, ControllerEntities c) {
       
        life = ImageLoader.loadImage(path);
        powerInactive = ImageLoader.loadImage(path2);
        powerActive = ImageLoader.loadImage(path3);
        kunai = ImageLoader.loadImage("/hudBg/kunai.png");
        bossImage = ImageLoader.loadImage("/hudBg/oro.png");
        this.currLife = currLife;
        currPower = 0;
        maxPower = 210;
        bossLife = 30;
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

    public void render(Graphics g) {
        int margin = 40;
        int offset = 20;

        font = FontLoader.load("res/fonts/naruto.ttf", 40);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString(Integer.toString(score.getCount()), 600, 80);

//        switch (currLife) {
//            case 1:
//                g.drawImage(life, (life.getWidth() + margin), margin, null);
//                break;
//            case 2:
//                g.drawImage(life, (2 * life.getWidth() + margin + offset), margin, null);
//                g.drawImage(life, (life.getWidth() + margin), margin, null);
//                break;
//            case 3:
//                g.drawImage(life, (3 * life.getWidth() + margin + offset * 2), margin, null);
//                g.drawImage(life, (2 * life.getWidth() + margin + offset), margin, null);
//                g.drawImage(life, (life.getWidth() + margin), margin, null);
//                break;
//        }
        if (currLife > 0) {
            for (int i = 1; i <= currLife; i++) {
                g.drawImage(life, ((i - 1) * life.getWidth() + margin + offset * (i - 1)), margin, null);
            }
        }

        g.setColor(Color.orange);
        g.drawRoundRect(margin + powerInactive.getWidth() + 10, margin + 10 + 55, maxPower + 10, powerInactive.getHeight() - 20, 25, 35);

        if (currPower == maxPower) {
            g.drawImage(powerActive, margin, margin + 55, null);
            g.fillRoundRect(margin + powerInactive.getWidth() + 10 + 5, margin + 5 + 10 + 55, maxPower, powerInactive.getHeight() - 30, 15, 25);
        } else {
            g.drawImage(powerInactive, margin, margin + 50, null);
            g.fillRoundRect(margin + powerInactive.getWidth() + 10 + 5, margin + 5 + 10 + 55, currPower, powerInactive.getHeight() - 30, 15, 25);
        }

        font = FontLoader.load("res/fonts/naruto.ttf", 18);
        g.setFont(font);
        g.drawImage(kunai, margin, margin + 120, null);
        g.drawString("x " + Integer.toString(Player.getCount()), 80, 185);

        nowTime = System.nanoTime(); //used for time generation of enemies        
        if (nowTime - lastTime > 0.25f * 1000000000 && currPower < maxPower) {
            currPower += 3;
            lastTime = System.nanoTime();
        }
        if (c.getFinalBoss() && c.getBoss()!=null ) {          
            //boss life
            Color purple = new Color(122, 33, 152);
            g.setColor(purple);
            g.drawImage(bossImage, 1200 - (margin + bossImage.getWidth()), margin, null);
            g.drawRoundRect(1200 - (margin + bossImage.getWidth() + 10 + 5 + 150+50), margin + 10, 210, powerInactive.getHeight() - 20, 25, 35);
            g.fillRoundRect(1200 - (margin + bossImage.getWidth() + 10 + 150+50), margin + 10 + 5, bossLife*2, powerInactive.getHeight() - 30, 15, 25);
            if(bossLife!=c.getBoss().getHealth()){
                 g.fillRoundRect(margin + powerInactive.getWidth() + 10 + 5, margin + 5 + 10 + 55, c.getBoss().getHealth(), powerInactive.getHeight() - 30, 15, 25);
                 bossLife=c.getBoss().getHealth();
            }            

        }
    }
}
