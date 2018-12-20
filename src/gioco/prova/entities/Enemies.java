/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import gioco.prova.bullets.Fireball;
import gioco.prova.bullets.Kunai;
import gioco.prova.display.Score;

public abstract class Enemies extends Creature {

    protected boolean dead = false;
    private Score score;
    private int difficulty;
    protected boolean lastDeadFrame = false;

    public Enemies(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    //questo metodo è utilizzato per controllare se un nemico 
    //entra in collisione con un kunai lanciato dal personaggio
    public boolean checkKunaiCollisions(float xOffset, float yOffset) {
        for (Kunai k : handler.getGame().getGameState().getController().getListKunaiPlayer()) {
            if (k.getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(xOffset, yOffset))) {
                handler.getGame().getGameState().getController().removeKunaiPlayer(k);
                difficulty = handler.getGame().getGameState().getController().getCountDifficulty();
                score = handler.getGame().getGameState().getHudmngr().getScore();
                score.incrementCount(difficulty + 2);
                return true;
            }

        }
        return false;
    }

    public boolean checkFireballCollisions(float xOffset, float yOffset) {
        for (Fireball f : handler.getGame().getGameState().getController().getF()) {
            if (f.getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(xOffset, yOffset))) {
                difficulty = handler.getGame().getGameState().getController().getCountDifficulty();
                score = handler.getGame().getGameState().getHudmngr().getScore();
                score.incrementCount(difficulty + 2);
                return true;
            }
        }
        return false;

    }
}
