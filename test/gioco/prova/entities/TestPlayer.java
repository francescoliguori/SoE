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
import gioco.prova.states.GameState;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Chris
 */
public class TestPlayer {

    private Game game;
    private Handler handler;
    private Player player;
    private ControllerEntities controller;

    public TestPlayer() {
        game = Game.getGameIstance();
        handler = Handler.getHandlerInstance(game);
        handler.getGame().setGameState(new GameState(handler));
        controller = handler.getGame().getGameState().getController();
        player = handler.getGame().getGameState().getPlayer();
    }

    @After
    public void tearDown() {
        Player.restartPlayer();
    }

    @Test
    public void testPlayerMoveRight() {
        handler.getKeyManager().right = true;
        float lastPosition = player.getX();
        player.getInput();
        player.move();
        
        assertTrue(player.getX() > lastPosition);
        lastPosition = player.getX();
        player.getInput();
        player.move();
       
        assertTrue(player.getX() > lastPosition);
        handler.getKeyManager().right = false;
    }

    @Test
    public void testPlayerMoveLeft() {
        handler.getKeyManager().left = true;
        float lastPosition = player.getX();
        player.getInput();
        player.move();
        ;
        assertTrue(player.getX() < lastPosition);
        lastPosition = player.getX();
        player.getInput();
        player.move();
        
        assertTrue(player.getX() < lastPosition);
        handler.getKeyManager().left = false;
    }

    @Test
    public void testPlayerJump() {
        handler.getKeyManager().up = true;
        player.getInput();
        player.move();
        handler.getKeyManager().up = false;
        assertFalse(player.collisionWithGround(player.getY()));
        //System.out.println(player.getY());
        long lastTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - lastTime < 5) {
            player.getInput();
            player.move();
           
        }
        assertTrue(player.collisionWithGround(player.getY()));
    }

    @Test
    public void testPlayerSlide() {
        handler.getKeyManager().down = true;
        float groundheight = player.getY();
        float lastX = player.getX();
        player.getInput();
        player.move();
        handler.getKeyManager().down = false;
        assertTrue(player.getY() > groundheight);
        assertTrue(player.getX() > lastX);
        lastX = player.getX();
        long lastTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - lastTime < 5) {
            player.getInput();
            player.move();
            assertTrue(player.getX() > lastX);

        }
        assertTrue(player.collisionWithGround(player.getY()));
    }

    @Test
    public void testPlayerShootFireball() {
        handler.getKeyManager().space = true;
        player.getInput();
        LinkedList<Fireball> listFireball = controller.getF();
        assertTrue(listFireball.size() == 0);
        handler.getGame().getGameState().getHudmngr().setCurrPower(210); //set the fireball bar to full
        player.getInput();
        assertTrue(listFireball.size() == 1);
        handler.getKeyManager().space = false;
    }

    @Test
    public void testPlayerShootKunai() {
        handler.getKeyManager().v = true;
        player.getInput();
        LinkedList<Kunai> listKunai = controller.getListKunaiPlayer();
        assertTrue(listKunai.size() == 1);
        player.getInput();
        assertTrue(listKunai.size() == 1);
        handler.getKeyManager().v = false;
    }

    @Test
    public void testPlayerStayInScreenX() {
        handler.getKeyManager().right = true;
        long lastTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - lastTime < 50) {
            player.getInput();
            player.move();

        }
        assertTrue(player.getX() == handler.getWidth() - 155);
        handler.getKeyManager().right = false;
        handler.getKeyManager().left = true;
        lastTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - lastTime < 50) {
            player.getInput();
            player.move();

        }
        assertTrue(player.getX() == 0);
        handler.getKeyManager().left = false;
    }
}
