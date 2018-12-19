/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.states.GameState;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Utente
 */
public class RamenTest {

    private Game game;
    private Handler handler;
    private Ramen r;
    private ControllerEntities controller;

    public RamenTest() {
        game = Game.getGameIstance();
        handler = Handler.getHandlerInstance(game);
        handler.getGame().setGameState(new GameState(handler));
        controller = handler.getGame().getGameState().getController();

    }

    @Before
    public void setUp() {
        r = new Ramen(handler, 200, 200);
        controller.addRamen(r);
    }

    @Test
    public void testMove() {
        double temp = r.getX();
        double temp1 = r.getY();
        controller.tick();

        assertTrue(r.getX() < temp);
        assertTrue(r.getY() == temp1);

    }
}
