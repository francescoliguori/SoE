/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.collisions;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.bullets.Arrow;
import gioco.prova.bullets.Fireball;
import gioco.prova.bullets.Kunai;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Enemies;
import gioco.prova.entities.Enemy1;
import gioco.prova.entities.Enemy2;
import gioco.prova.entities.Enemy3;
import gioco.prova.entities.Player;
import gioco.prova.entities.Ramen;
import gioco.prova.states.GameState;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Chris
 */
public class TestCollisionEntities {

    private Game game;
    private Handler handler;
    private Enemy1 enemy1;
    private Enemy2 enemy2;
    private Enemy3 enemy3;
    private Ramen ramen;
    private Player player;
    private ControllerEntities controller;

    public TestCollisionEntities() {
        game = Game.getGameIstance();
        handler = Handler.getHandlerInstance(game);
        handler.getGame().setGameState(new GameState(handler));
        controller = handler.getGame().getGameState().getController();
    }

    @Before
    public void setUp() {
        enemy1 = new Enemy1(handler, game.getWidth(), 400, controller);
        enemy1.setX(enemy1.getX() + enemy1.getWidth());
        enemy2 = new Enemy2(handler, game.getWidth(), 400);
        enemy2.setX(enemy2.getX() + enemy2.getWidth());
        enemy3 = new Enemy3(handler, game.getWidth(), 400, controller);
        enemy3.setX(enemy3.getX() + enemy3.getWidth());

        controller.addEnemy(enemy1);
        controller.addEnemy(enemy2);
        controller.addEnemy(enemy3);
        player = Player.getPlayerInstance(handler, 100, 400, controller);

    }

    @After
    public void tearDown() {
        enemy1 = null;
        enemy2 = null;
        enemy3 = null;
        Player.restartPlayer();
    }

    @Test
    public void testCollisionPlayerEnemy() {
        //most assert for limit collision
        assertFalse(checkEnemyCollision(0, 0));
        enemy2.setX(150);
        assertTrue(checkEnemyCollision(0, 0));
        enemy2.setX(25);
        assertFalse(checkEnemyCollision(0, 0));
        enemy2.setX(26);
        assertTrue(checkEnemyCollision(0, 0));
        enemy2.setX(209);
        assertTrue(checkEnemyCollision(0, 0));
        enemy2.setX(210);
        assertFalse(checkEnemyCollision(0, 0));

        enemy2.setX(400);
        assertFalse(checkEnemyCollision(0, 0));

        enemy3.setX(100);
        assertTrue(checkEnemyCollision(0, 0));
        enemy3.setX(36);
        assertTrue(checkEnemyCollision(0, 0));
        enemy3.setX(35);
        assertFalse(checkEnemyCollision(0, 0));
        enemy2.setX(209);
        assertTrue(checkEnemyCollision(0, 0));
        enemy2.setX(210);
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
        enemy2.setX(500);

        while (!collisionKunaiPlayerEnemy(0, 0)) {
            kunai.tick();

        }
        assertFalse(collisionKunaiPlayerEnemy(0, 0));
        assertEquals(0, controller.getListKunaiPlayer().size());
        handler.getKeyManager().v = false;
    }

    @Test
    public void testKunaiEnemyPlayerCollision() {
        // Player vita non disponibile
        enemy3.setX(500);
        controller.addKunaiEnemies(new Kunai(handler, enemy3.getX(),
                enemy3.getY(), enemy3.getWidth(), enemy3.getHeight(), false));
        LinkedList<Kunai> listKunai = controller.getListKunaiEnemies();
        assertTrue(listKunai.size() == 1);
        Kunai kunai = null;
        for (Kunai k : controller.getListKunaiEnemies()) {
            kunai = k;
        }
        while (!collisionKunaiEnemyPlayer(0, 0)) {
            kunai.tick();

        }
        assertFalse(collisionKunaiEnemyPlayer(0, 0));
        assertEquals(0, controller.getListKunaiEnemies().size());

    }

    @Test
    public void testArrowEnemyPlayerCollision() {
        // Player vita non disponibile
        enemy1.setX(500);
        controller.addArrowEnemies(new Arrow(handler, enemy1.getX(),
                enemy1.getY(), enemy1.getWidth(), enemy1.getHeight(), false));
        LinkedList<Arrow> listArrow = controller.getListArrowEnemies();
        assertTrue(listArrow.size() == 1);
        Arrow arrow = null;
        for (Arrow a : controller.getListArrowEnemies()) {
            arrow = a;
        }
        while (!collisionArrowEnemyPlayer(0, 0)) {
            arrow.tick();

        }
        assertFalse(collisionArrowEnemyPlayer(0, 0));
        assertEquals(0, controller.getListArrowEnemies().size());

    }

    @Test
    public void testFireballPlayerEnemyCollision() {
        enemy3.setX(1100);
        enemy2.setX(1000);
        controller.addFireball(new Fireball(handler, player.getX(), player.getY(),
                player.getWidth(), player.getHeight()));

        LinkedList<Fireball> listFireball = controller.getF();
        assertTrue(listFireball.size() == 1);
        Fireball fireball = null;
        for (Fireball f : controller.getF()) {
            fireball = f;
        }
        while (!checkFireballCollisions(0, 0)) {

            fireball.tick();

        }
        assertTrue(controller.getE().isEmpty());

        while (!listFireball.isEmpty()) {
            controller.tick();

        }
        assertTrue(listFireball.isEmpty());

    }

    @Test
    public void testPlayerRamenCollision() {
        player.setHealth(2);
        ramen = new Ramen(handler, handler.getWidth(), 550);
        controller.addRamen(ramen);
        assertTrue(controller.getListRamen().size() == 1);

        while (!checkRamenPlayerCollision(0, 0)) {
            ramen.tick();

        }
        assertTrue(controller.getListRamen().isEmpty());
        assertTrue(player.getHealth() == 3);
    }

    private boolean checkEnemyCollision(float xOffset, float yOffset) {
        for (Enemies e : controller.getE()) {
            if (e.getCollisionBounds(0f, 0f).intersects(player.getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRamenPlayerCollision(float xOffset, float yOffset) {
        for (Ramen ramen : controller.getListRamen()) {
            if (player.getHealth() != 3 && ramen.getCollisionBounds(0f, 0f).intersects(player.getCollisionBounds(xOffset, yOffset))) {
                player.setHealth(player.getHealth() + 1);
                controller.removeRamen(ramen);
                return true;
            }
        }
        return false;
    }

    private boolean collisionKunaiPlayerEnemy(float xOffset, float yOffset) {
        for (Kunai k : controller.getListKunaiPlayer()) {
            if (k.getCollisionBounds(0f, 0f).intersects(enemy2.getCollisionBounds(xOffset, yOffset))) {
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

    private boolean collisionArrowEnemyPlayer(float xOffset, float yOffset) {
        for (Arrow a : controller.getListArrowEnemies()) {
            if (a.getCollisionBounds(0f, 0f).intersects(player.getCollisionBounds(xOffset, yOffset))) {
                controller.removeArrowEnemies(a);
                return true;
            }
        }
        return false;
    }

    private boolean checkFireballCollisions(float xOffset, float yOffset) {
        for (Fireball f : controller.getF()) {
            for (Enemies e : controller.getE()) {
                if (e.getCollisionBounds(0f, 0f).intersects(f.getCollisionBounds(xOffset, yOffset))) {
                    controller.removeEnemy(e);

                }
            }
        }
        if (controller.getE().isEmpty()) {
            return true;
        }
        return false;
    }
}
