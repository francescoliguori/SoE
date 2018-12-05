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
import java.util.LinkedList;
import org.junit.Before;
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
        this.game = new Game("Itachi's Rush", 1200, 700);
        handler = new Handler(game);
    }

    @Before
    public void setUp() {
        controller = new ControllerEntities(handler);
        player = new Player(handler, 100, 325, controller);
    }

    @Test
    public void testPlayerMoveRight() {
        handler.getKeyManager().right = true;
        float lastPosition = player.getX();
        player.getInput();
        player.move();
        //System.out.println(player.getX());
        assertTrue(player.getX() > lastPosition);
        lastPosition = player.getX();
        player.getInput();
        player.move();
        //System.out.println(player.getX());
        assertTrue(player.getX() > lastPosition);
    }

    @Test
    public void testPlayerMoveLeft() {
        handler.getKeyManager().left = true;
        float lastPosition = player.getX();
        player.getInput();
        player.move();
        //System.out.println(player.getX());
        assertTrue(player.getX() < lastPosition);
        lastPosition = player.getX();
        player.getInput();
        player.move();
        //System.out.println(player.getX());
        assertTrue(player.getX() < lastPosition);
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
            //System.out.println(player.getY());
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
            //System.out.println("y = " + player.getY() + " x = " + player.getX());
        }
        assertTrue(player.collisionWithGround(player.getY()));
    }

    @Test
    public void testPlayerShootFireball() {
        handler.getKeyManager().space = true;
        player.getInput();
        LinkedList<Fireball> listFireball = controller.getF();
        assertTrue(listFireball.size() == 1);
        player.getInput();
        assertTrue(listFireball.size() == 1);
    }

    @Test
    public void testPlayerShootKunai() {
        handler.getKeyManager().v = true;
        player.getInput();
        LinkedList<Kunai> listKunai = controller.getListKunaiPlayer();
        assertTrue(listKunai.size() == 1);
        player.getInput();
        assertTrue(listKunai.size() == 1);
    }

    @Test
    public void testPlayerStayInScreenX() {
        handler.getKeyManager().right = true;
        long lastTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - lastTime < 50) {
            player.getInput();
            player.move();
            //System.out.println(player.getX());
        }
        assertTrue(player.getX() == handler.getWidth() - 155);
        handler.getKeyManager().right = false;
        handler.getKeyManager().left = true;
        lastTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - lastTime < 50) {
            player.getInput();
            player.move();
            //System.out.println(player.getX());
        }
        assertTrue(player.getX() == 0);
    }
}
