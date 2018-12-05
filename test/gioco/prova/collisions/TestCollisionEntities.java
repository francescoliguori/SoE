/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.collisions;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.bullets.Fireball;
import gioco.prova.bullets.Kunai;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Enemies;
import gioco.prova.entities.Enemy1;
import gioco.prova.entities.Enemy2;
import gioco.prova.entities.Enemy3;
import gioco.prova.entities.Player;
import java.util.LinkedList;
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
public class TestCollisionEntities {

    private Game game;
    private Handler handler;
    private Enemy2 enemy1;
    private Enemy3 enemy2;
    private Player player;
    private ControllerEntities controller;

    public TestCollisionEntities() {
        game = new Game("Itachi's Rush", 1200, 700);
        handler = new Handler(game);
    }

    @Before
    public void setUp() {
        controller = new ControllerEntities(handler);
        enemy1 = new Enemy2(handler, game.getWidth(), 325);
        enemy1.setX(enemy1.getX() + enemy1.getWidth());
        enemy2 = new Enemy3(handler, game.getWidth(), 325, controller);
        enemy2.setX(enemy2.getX() + enemy2.getWidth());
        controller.addEnemy(enemy1);
        controller.addEnemy(enemy2);
        player = new Player(handler, 100, 325, controller);
    }

    @After
    public void tearDown() {
        enemy1 = null;
        enemy2 = null;
        player = null;
    }

    @Test
    public void testCollisionPlayerEnemy() {
        assertFalse(checkEnemyCollision(0, 0));
        enemy1.setX(150);
        assertTrue(checkEnemyCollision(0, 0));
        enemy1.setX(30);
        assertFalse(checkEnemyCollision(0, 0));
        enemy1.setX(31);
        assertTrue(checkEnemyCollision(0, 0));
        enemy1.setX(214);
        assertTrue(checkEnemyCollision(0, 0));
        enemy1.setX(215);
        assertFalse(checkEnemyCollision(0, 0));

        enemy1.setX(400);
        assertFalse(checkEnemyCollision(0, 0));

        enemy2.setX(100);
        assertTrue(checkEnemyCollision(0, 0));
        enemy2.setX(41);
        assertTrue(checkEnemyCollision(0, 0));
        enemy2.setX(40);
        assertFalse(checkEnemyCollision(0, 0));
        enemy1.setX(214);
        assertTrue(checkEnemyCollision(0, 0));
        enemy1.setX(215);
        assertFalse(checkEnemyCollision(0, 0));
    }

    @Test
    public void testKunaiPlayerEnemyCollision() {
        handler.getKeyManager().v = true;
        player.getInput();
        LinkedList<Kunai> listKunai = controller.getListKunaiPlayer();
        assertTrue(listKunai.size() == 1);
        Kunai kunai = null;
        for (Kunai k : controller.getListKunaiPlayer()) {
            kunai = k;
        }
        enemy1.setX(500);
        //System.out.println(enemy1.getX());
        while (!collisionKunaiPlayerEnemy(0, 0)) {
            kunai.tick();
            //System.out.println(kunai.getX());
        }
        assertFalse(collisionKunaiPlayerEnemy(0, 0));
        assertEquals(0, controller.getListKunaiPlayer().size());

    }

    @Test
    public void testKunaiEnemyPlayerCollision() {
        // Player vita non disponibile
        enemy2.setX(500);
        controller.addKunaiEnemies(new Kunai(handler, enemy2.getX(),
                enemy2.getY(), enemy2.getWidth(), enemy2.getHeight(), false));
        LinkedList<Kunai> listKunai = controller.getListKunaiEnemies();
        assertTrue(listKunai.size() == 1);
        Kunai kunai = null;
        for (Kunai k : controller.getListKunaiEnemies()) {
            kunai = k;
        }
        while (!collisionKunaiEnemyPlayer(0, 0)) {
            kunai.tick();
            //System.out.println(kunai.getX());
        }
        assertFalse(collisionKunaiEnemyPlayer(0, 0));
        assertEquals(0, controller.getListKunaiEnemies().size());

    }

    @Test
    public void testFireballPlayerEnemyCollision() {
        enemy2.setX(500);
        enemy1.setX(350);
        handler.getKeyManager().space = true;
        player.getInput();
        //controller.addFireball(new Fireball(handler, player.getX(), player.getY(),
        //        player.getWidth(), player.getHeight()));
        LinkedList<Fireball> listFireball = controller.getF();
        assertTrue(listFireball.size() == 1);
        Fireball fireball = null;
        for (Fireball f : controller.getF()) {
            fireball = f;
        }
        while(!checkFireballCollisions(0, 0)){
            fireball.tick();
            //System.out.println(fireball.getX());
        }
        assertTrue(controller.getEnemies().isEmpty());
        System.out.println("\n");
        while(!listFireball.isEmpty()){
            controller.tick();
            //System.out.println(fireball.getX());
        }
        assertTrue(listFireball.isEmpty());

    }

    private boolean checkEnemyCollision(float xOffset, float yOffset) {
        for (Enemies e : controller.getEnemies()) {
            if (e.getCollisionBounds(0f, 0f).intersects(player.getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    private boolean collisionKunaiPlayerEnemy(float xOffset, float yOffset) {
        for (Kunai k : controller.getListKunaiPlayer()) {
            if (k.getCollisionBounds(0f, 0f).intersects(enemy1.getCollisionBounds(xOffset, yOffset))) {
                controller.removeKunaiPlayer(k);
                return true;
            }
        }
        return false;
    }

    private boolean collisionKunaiEnemyPlayer(float xOffset, float yOffset) {
        for (Kunai k : controller.getListKunaiEnemies()) {
            if (k.getCollisionBounds(0f, 0f).intersects(player.getCollisionBounds(xOffset, yOffset))) {
                controller.removeKunaiEnemies(k);
                return true;
            }
        }
        return false;
    }

    private boolean checkFireballCollisions(float xOffset, float yOffset) {
        for (Fireball f : controller.getF()) {
            for (Enemies e : controller.getEnemies()) {
                if (e.getCollisionBounds(0f, 0f).intersects(f.getCollisionBounds(xOffset, yOffset))) {
                    controller.removeEnemy(e);
                    //System.out.println("Colpito");
                }
            }
        }
        if (controller.getEnemies().isEmpty()){
            return true;
        }
        return false;
    }
}
