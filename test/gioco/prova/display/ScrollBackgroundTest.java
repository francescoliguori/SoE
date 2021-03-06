/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.display;

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
public class ScrollBackgroundTest {

    private String[] paths;
    private int pathsLength;
    private ScrollBackground scrollbg;

    public ScrollBackgroundTest() {
        pathsLength = 3;
        paths = new String[pathsLength];
        paths[0] = "/background/mountain_day.png";
        paths[1] = "/background/trees_day.png";
        paths[2] = "/background/path_day.png";
        //Instantiate ScrollBackground with the image.
        scrollbg = new ScrollBackground(paths, pathsLength);
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
     * Test of tick method, of class ScrollBackground.
     */
    @Test
    public void testTick() {

        ScrollBackground instance = scrollbg;
        int scrolling = scrollbg.getScrolling();
        Background[] bg = scrollbg.getBg();
        Integer[] before = new Integer[bg.length];
        Integer[] after = new Integer[bg.length];

        for (int i = 0; i < bg.length; i++) {
            before[i] = bg[i].getX();
        }

        instance.tick();

        for (int i = 0; i < bg.length; i++) {
            after[i] = bg[i].getX();
        }

        //Check if the background is scrolling
        assertNotSame(before, after);
    }
}
