/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.bullets;

import gioco.prova.Game;
import gioco.prova.Handler;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcoruggiero
 */
public class KunaiTest {

    private Game game;
    private Handler handler;
    private Kunai kPlayer, kEnemy;

    public KunaiTest() {
        game = Game.getGameIstance();
        handler = Handler.getHandlerInstance(game);
        kPlayer = new Kunai(handler, 200, 200, 50, 60, true);
        kEnemy = new Kunai(handler, 200, 200, 50, 60, false);

    }

    /**
     * Test of move method, of class Kunai.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        double temp = kPlayer.getX();
        double temp1 = kPlayer.getY();
        kPlayer.move(true);
        assertTrue(kPlayer.getX() > temp);
        assertTrue(kPlayer.getY() == temp1);

        temp = kEnemy.getX();
        temp1 = kEnemy.getY();
        kEnemy.move(false);
        assertTrue(kEnemy.getX() < temp);
        assertTrue(kEnemy.getY() == temp1);

    }

}
