/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.bullets.Kunai;
import gioco.prova.states.GameState;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Chris
 */
public class Enemy3Test {

    private Game game;
    private Handler handler;
    private Enemy3 enemy;
    private ControllerEntities controller;

    public Enemy3Test() {
        game = Game.getGameIstance();
        handler = Handler.getHandlerInstance(game);
        handler.getGame().setGameState(new GameState(handler));
        controller = handler.getGame().getGameState().getController();
    }

    @Before
    public void setUp() {
        enemy = new Enemy3(handler, game.getWidth(), 0, controller);
        enemy.setX(enemy.getX() + enemy.getWidth());
        controller.addEnemy(enemy);
    }

    /**
     * Check if enemy has been instantiated and if it is positioned on the right
     * side, out of the screen.
     */
    @Test
    public void enemyCreationTest() {
        //Check if enemy has been instanciated.
        assertTrue(enemy != null);
        //Check if enemy is positioned on the right side, out of the screen.
        assertTrue(enemy.getX() > game.getWidth());
    }

    @Test
    public void enemyLeftMovementTest() {
        float lastPosition = enemy.getX();
        controller.tick();
        assertTrue(enemy.getX() < lastPosition);
        lastPosition = enemy.getX();
        controller.tick();
        assertTrue(enemy.getX() < lastPosition);
    }

    @Test
    public void testEnemyArrowShot() {
        controller.addKunaiEnemies(new Kunai(handler, enemy.getX(),
                enemy.getY(), enemy.getWidth(), enemy.getHeight(), false));
        assertTrue(controller.getListKunaiEnemies().size() == 1);
        Kunai kunai = null;
        for (Kunai k : controller.getListKunaiEnemies()) {
            kunai = k;
        }

        while (!controller.getListKunaiEnemies().isEmpty()) {
            controller.tick();
        }
        assertTrue(controller.getListArrowEnemies().isEmpty());
    }

    @Test
    public void testEnemyDeleteOuterScreen() {
        assertTrue(!controller.getE().isEmpty());
        while (!controller.getE().isEmpty()) {
            controller.tick();
        }
        assertTrue(controller.getE().isEmpty());
    }
}
