/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.display;

import java.awt.image.BufferedImage;
import gioco.prova.gfx.ImageLoader;
import java.awt.Graphics;

public class Background {
    private int x;
    private BufferedImage image;

    public Background(int x, String path) {
        image = ImageLoader.loadImage(path);
        this.x = x * image.getWidth();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public BufferedImage getImage() {
        return image;
    }
    
    public void render(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
    
    public static Background randomBg(int random) {
        if (random == 0) {
            return new Background(0, "/background/bg_sky_night.png");
        } else {
            return new Background(0, "/background/bg_sky_day.png");
        }
    }
}
