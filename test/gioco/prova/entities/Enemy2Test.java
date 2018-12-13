/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Game;
import gioco.prova.Handler;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Chris
 */
public class Enemy2Test {
    private Game game;
    private Handler handler;
    private Enemy2 enemy;
    private ControllerEntities controller;

    public Enemy2Test() {
        game = Game.getGameIstance();
        handler = Handler.getHandlerInstance(game);
        controller = new ControllerEntities(handler);
    }

    @Before
    public void setUp() {
        enemy = new Enemy2(handler, game.getWidth(), 0);
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
        for (Enemies e : controller.getEnemies()) {
            enemy = (Enemy2) e;
        }
        float lastPosition = enemy.getX();
        controller.tick();
        assertTrue(enemy.getX() < lastPosition);
        lastPosition = enemy.getX();
        controller.tick();
        assertTrue(enemy.getX() < lastPosition);
    }

    @Test
    public void testEnemyDeleteOuterScreen() {
        assertTrue(!controller.getEnemies().isEmpty());
        while (!controller.getEnemies().isEmpty()) {
            controller.tick();
        }
        assertTrue(controller.getEnemies().isEmpty());
    }
}
