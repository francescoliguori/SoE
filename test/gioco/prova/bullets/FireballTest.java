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
public class FireballTest {

    private Game game;
    private Handler handler;
    private Fireball f;

    public FireballTest() {
        game = Game.getGameIstance();
        handler = Handler.getHandlerInstance(game);
        f = new Fireball(handler, 200, 200, 50, 60);
    }

    /**
     * Test of move method, of class Fireball.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        //la fireball è un oggetto che si muove parallelo al terreno, quindi, il test di 
        //move consiste nel vedere che chiamare move incrementi x e lasci invariato y
        double temp = f.getX();
        double temp1 = f.getY();
        f.move();
        assertTrue(f.getX() > temp);
        assertTrue(f.getY() == temp1);
    }

}
