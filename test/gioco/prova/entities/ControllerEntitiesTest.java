/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.bullets.Fireball;
import gioco.prova.bullets.Kunai;
import java.awt.Graphics;
import java.util.LinkedList;
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
public class ControllerEntitiesTest {
    private Game game;
    private Handler handler;
    private ControllerEntities controller;
    private Enemy1 enemy;
    
    public ControllerEntitiesTest() {
        game = new Game("Itachi's Rush", 1200, 700);
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
        enemy = new Enemy1(handler, game.getWidth(), 0);
        enemy.setX(enemy.getX() + enemy.getWidth());
    }
    
    @After
    public void tearDown() {
    }
    
     @Test
    public void controllerCreationTest() {
        System.out.println("controllerCreationTest");
        //Check if controller has been instanciated.
        assertTrue(controller != null);
    }
    
    @Test
    public void enemyAutomaticMovementTest() {
        System.out.println("enemyAutomaticMovementTest");
        int numEnemies = 0;
        int expNumEnemies = 1;
        float beforeEnemy = 0;
        float afterEnemy = 0;
        float expBeforeEnemy = 0;
        float expAfterEnemy = 0;
        float deltaEnemy = 0;
        float expDeltaEnemy = 0;
        
        
        controller.addEnemy(enemy);
        LinkedList<Enemies> enemies = controller.getE();
        numEnemies = enemies.size();
        
        Enemy1 e = enemy;
        Enemy1 enemyTmp;
        
        
        //Check if controller has the expected number of enemeies in the list.
        assertEquals(expNumEnemies, numEnemies);

        enemyTmp = (Enemy1)enemies.getFirst();

        beforeEnemy = enemyTmp.getX();
        expBeforeEnemy = e.getX();
        assertTrue(expBeforeEnemy == beforeEnemy);

        controller.tick();
        e.tick();

        afterEnemy = enemyTmp.getX();
        expAfterEnemy = e.getX();
        assertTrue(expAfterEnemy == afterEnemy);

        //Check if the enemy is moving left.
        deltaEnemy = Math.abs(afterEnemy - beforeEnemy);
        expDeltaEnemy = Math.abs(expAfterEnemy - expBeforeEnemy);
        assertTrue(expDeltaEnemy == deltaEnemy);
       
    }
    
    @Test
    public void enemyDestructionTest() {
        System.out.println("enemyDestructionTest");
        int numEnemies = 0;
        int expNumEnemies = 1;
        Enemy1 enemyTmp;
        
        controller.addEnemy(enemy);
        LinkedList<Enemies> enemies = controller.getE();
        numEnemies = enemies.size();
        
        //Check if one enemy has been generated and add to the Enemies' list.
        assertEquals(numEnemies, expNumEnemies);
        
        //Starting left movement.
        while (numEnemies > 0) {
            enemyTmp = (Enemy1)enemies.getFirst();
            controller.tick();
            numEnemies = enemies.size();
        }
        
        expNumEnemies = 0;
        
        //Check if the enemy has been deleted.
        assertEquals(expNumEnemies, numEnemies);
    }
    
    /**
     * Test of isNotShooting method, of class ControllerEntities.
     */
    @Test
    public void testIsNotShooting() {
        System.out.println("isNotShooting");
        int expNumFireballs = 0;
        ControllerEntities instance = controller;
        boolean expResult = true;
        boolean result = instance.isNotShooting();
        assertEquals(expResult, result);
    }

    /**
     * Test of addFireball method, of class ControllerEntities.
     */
    @Test
    public void testAddFireball() {
        System.out.println("addFireball");
        int expNumFireballs = 1;
        int numFireballs = 0;
        Fireball fireball = new Fireball(handler, 40, 40, 155, 187);
        ControllerEntities instance = controller;
        instance.addFireball(fireball);
        numFireballs = instance.getF().size();
        //Check if the fireball has been added to the controller's fireballs list.
        assertEquals(expNumFireballs, numFireballs);
    }

    /**
     * Test of removeFireball method, of class ControllerEntities.
     */
    @Test
    public void testRemoveFireball() {
        System.out.println("removeFireball");
        int expNumFireballs = 0;
        int numFireballs = 0;
        Fireball fireball = new Fireball(handler, 40, 40, 155, 187);
        ControllerEntities instance = controller;
        
        //Check if the fireball has been added to the controller's fireballs list.
        expNumFireballs = 1;
        instance.addFireball(fireball);
        numFireballs = instance.getF().size();
        assertEquals(expNumFireballs, numFireballs);
        
        //Check if the fireball has been removed from the controller's fireballs list.
        expNumFireballs = 0;
        instance.removeFireball(fireball);
        numFireballs = instance.getF().size();
        assertEquals(expNumFireballs, numFireballs);
    }

    /**
     * Test of addKunai method, of class ControllerEntities.
     */
    @Test
    public void testAddKunai() {
        System.out.println("addKunai");
        int expNumKunai = 1;
        int numKunai = 0;
        Kunai kunai = new Kunai(handler, 40, 40, 155, 187, true);
        ControllerEntities instance = controller;
        instance.addKunaiPlayer(kunai);
        numKunai = instance.getListKunaiPlayer().size();
        //Check if the kunai has been added to the controller's kunai list.
        assertEquals(expNumKunai, numKunai);
    }

    /**
     * Test of removeKunai method, of class ControllerEntities.
     */
    @Test
    public void testRemoveKunai() {
        System.out.println("removeKunai");
        int expNumKunai = 0;
        int numKunai = 0;
        Kunai kunai = new Kunai(handler, 40, 40, 155, 187, true);
        ControllerEntities instance = controller;
        
        //Check if the kunai has been added to the controller's kunai list.
        expNumKunai = 1;
        instance.addKunaiPlayer(kunai);
        numKunai = instance.getListKunaiPlayer().size();
        assertEquals(expNumKunai, numKunai);
        
        //Check if the kunai has been removed from the controller's kunai list.
        expNumKunai = 0;
        instance.removeKunaiPlayer(kunai);
        numKunai = instance.getListKunaiPlayer().size();
        assertEquals(expNumKunai, numKunai);
    }

    /**
     * Test of addEnemy method, of class ControllerEntities.
     */
    @Test
    public void testAddEnemy() {
        System.out.println("addKunai");
        int expNumEnemies = 1;
        int numEnemies = 0;
        Enemy1 e = enemy;
        ControllerEntities instance = controller;
        instance.addEnemy(e);
        numEnemies = instance.getE().size();
        //Check if the enemy has been added to the controller's enemies list.
        assertEquals(expNumEnemies, numEnemies);
    }

    /**
     * Test of removeEnemy method, of class ControllerEntities.
     */
    @Test
    public void testRemoveEnemy() {
        System.out.println("removeEnemy");
        int expNumEnemies = 0;
        int numEnemies = 0;
        Enemy1 e = enemy;
        ControllerEntities instance = controller;
        
        //Check if the kunai has been added to the controller's kunai list.
        expNumEnemies = 1;
        instance.addEnemy(e);
        numEnemies = instance.getE().size();
        assertEquals(expNumEnemies, numEnemies);
        
        //Check if the kunai has been removed from the controller's kunai list.
        expNumEnemies = 0;
        instance.removeEnemy(e);
        numEnemies = instance.getE().size();
        assertEquals(expNumEnemies, numEnemies);
    }

    /**
     * Test of getF method, of class ControllerEntities.
     */
    @Test
    public void testGetF() {
        System.out.println("getF");
        ControllerEntities instance = controller;
        LinkedList<Fireball> expResult = new LinkedList<>();
        Fireball tempFireball;
        
        for (int i=0; i<2; i++) {
            tempFireball = new Fireball(handler, 40, 40, 155, 187); 
            expResult.add(tempFireball);
            instance.addFireball(tempFireball);
        }
        
        LinkedList<Fireball> result = instance.getF();
        
        //Check if the fireballs list are the same.
        assertEquals(expResult, result);
    }

    /**
     * Test of getK method, of class ControllerEntities.
     */
    @Test
    public void testGetK() {
        System.out.println("getK");
        ControllerEntities instance = controller;
        LinkedList<Kunai> expResult = new LinkedList<>();
        Kunai tempKunai;
        
        for (int i=0; i<2; i++) {
            tempKunai = new Kunai(handler, 40, 40, 155, 187, true); 
            expResult.add(tempKunai);
            instance.addKunaiPlayer(tempKunai);
        }
        
        LinkedList<Kunai> result = instance.getListKunaiPlayer();
        
        //Check if the fireballs list are the same.
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getE method, of class ControllerEntities.
     */
    @Test
    public void testGetE() {
        System.out.println("getE");
        ControllerEntities instance = controller;
        LinkedList<Enemies> expResult = new LinkedList<>();
        Enemy1 tempEnemy;
        
        for (int i=0; i<2; i++) {
            tempEnemy = new Enemy1(handler, game.getWidth(), 0);; 
            expResult.add(tempEnemy);
            instance.addEnemy(tempEnemy);
        }
        
        LinkedList<Enemies> result = instance.getE();
        
        //Check if the fireballs list are the same.
        assertEquals(expResult, result);
    }
    
}
