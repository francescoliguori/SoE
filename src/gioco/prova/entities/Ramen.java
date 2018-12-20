/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import gioco.prova.gfx.Assets;
import java.awt.Graphics;

public class Ramen extends Entity {

    public Ramen(Handler handler, float x, float y) {
        super(handler, x, y, 187, 155);
        bounds.height = 42;
        bounds.width = 55;
    }

    @Override
    public void tick() {
        move();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ramen, (int) this.x, (int) this.y, null);

    }

    public boolean checkPlayerCollisions(float xOffset, float yOffset) {
        if (handler.getGame().getGameState().getPlayer().getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(xOffset, yOffset))) {
            return true;
        }
        return false;
    }

    public void move() {
        x -= 10.0f;
    }
}
