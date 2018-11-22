/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.bullets;

import gioco.prova.Game;
import gioco.prova.Handler;
import java.awt.Graphics;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcoruggiero
 */
public class KunaiTest {
    private Game game;
    private Handler  handler;
    private Kunai k;
    private int width = 1200, height = 700;
    
    public KunaiTest() {
        game = new Game("game", width, height);
        handler = new Handler(game);
        k = new Kunai(handler,  200,  200,  50,  60);
    
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of move method, of class Kunai.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        double temp = k.getX();
        double temp1 = k.getY();
        k.move();
        
        assertTrue(k.getX() > temp);
        assertTrue(k.getY() == temp1);
        
    }
    
}
