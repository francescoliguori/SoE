/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.input.KeyManager;
import java.awt.Graphics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vincenzo
 */
public class Enemy1Test {
    
    private Game game;
    private Handler handler;
    private Enemy1 enemy;
    private ControllerEntities controller;
    
    public Enemy1Test() {
        game = Game.getGameIstance();
        handler = new Handler(game);
        controller = new ControllerEntities(handler);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        enemy = new Enemy1(handler, game.getWidth(), 0, controller);
        enemy.setX(enemy.getX() + enemy.getWidth());
    }
    
    @After
    public void tearDown() {
        enemy = null;
    }
    
    /**
     * Check if enemy has been instantiated and if it is positioned
     * on the right side, out of the screen.
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
        float before = 0;
        float expBefore = 0;
        float after = 0;
        float expAfter = 0;
        float deltaMove = 0;
        float expDeltaMove = 0;
        float endPoint = enemy.getWidth();
        
        //Simulate left movement.
        while (enemy.getX() > -endPoint) {
            before = enemy.getX();
            expBefore = enemy.getX();
            enemy.tick();
            after = enemy.getX();
            expAfter = expBefore + enemy.getxMove();
            
            //Check if enemy is moving left.
            deltaMove = Math.abs(after - before);
            expDeltaMove = Math.abs(expAfter - expBefore);
            
            assertTrue(expDeltaMove == deltaMove);
        }
        
        //Check enemy, while moving left, goes out of the screen.
        assertTrue(enemy.getX() <= -endPoint);
    }
}
