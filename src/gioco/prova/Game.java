/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova;

import gioco.prova.display.Display;
import gioco.prova.gfx.Assets;
import gioco.prova.gfx.ImageLoader;
import gioco.prova.gfx.SpriteSheet;
import gioco.prova.input.KeyManager;
import gioco.prova.states.GameState;
import gioco.prova.states.MenuState;
import gioco.prova.states.State;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import gioco.prova.display.ScrollBackground;
import gioco.prova.gfx.ImageLoader;
import gioco.prova.states.GameOverState;

import sun.audio.*;
import java.io.*;
import java.nio.file.Path;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author marcoruggiero
 */
public class Game implements Runnable {

    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    //SCROLLBACKGROUND
    private ScrollBackground sbg;

    //static background
    private BufferedImage background;

    private GameState gameState;
    private MenuState menuState;
    private GameOverState gameOverState;

    private static Clip clip;

    // Input
    private KeyManager keyManager;

    // Handler
    private Handler handler;
    
    //istanza di Game, privata e statica. E' utilizzata per rendere Game singleton
    static private Game instance = null;
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    //metodo che restituisce l'istanza di game
    public static Game getGameIstance(){
        if (instance == null){
            instance = new Game("Itachi's Rush", 1200, 700);
        }
        return instance;
    }
    //il costruttore viene reso privato in modo tale da poter essere invocato solo 
    //dall'interno della classe. 
    private Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();

    }

    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();
        handler = Handler.getHandlerInstance(this);
        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        State.setState(gameState);

        try {
            File ost = new File("res/ost.wav");
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(ost));
            this.playSoundtrack();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    private void tick() {
        keyManager.tick();
        if (State.getState() != null) {
            State.getState().tick();
        }
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //Clear here

        g.clearRect(0, 0, width, height);
        //Draw here

        //Start Drawing
        //Draw the background image into the Graphic referenc g
        //drawImage prendee in ingresso l'immagine da disegnare, le coordinate 
        //x ed y e infine un observer, in questo caso posto a null.
        //An asynchronous update interface for receiving notifications about 
        //Image information as the Image is constructed.
        if (State.getState() != null) {
            State.getState().render(g);
        }
        //End drawing
        bs.show();
        g.dispose();

    }

    public void run() {
        init();

        //frame, o tick, per second: quante volte al secondo vengono invocate
        //tick e render
        int fps = 60;
        //un secondo, espresso in nanosecondi, diviso il numero di fps. E' la 
        //massima quantità di tempo che si può dedicare all'esecuzione di tick e render
        //per avere un numero di fps pari a 60.
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        //tempo passato in nanosecondi, non ha una risoluzione in nanosecondi.
        //Returns the current value of the running Java Virtual Machine's high-resolution 
        //time source, in nanoseconds.
        long lastTime = System.nanoTime();
        //si vuole creare un contatore dei frame al secondo
        long timer = 0;
        int ticks = 0;
        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += (now - lastTime);
            lastTime = now;

            if (delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }

            if (timer >= 1000000000) {
//                System.out.println("Tick e frame:" + ticks);
                ticks = 0;
                timer = 0;

            }

        }
        stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setMenuState(MenuState menuState) {
        this.menuState = menuState;
    }

    public void setGameoverState(GameOverState gameoverState) {
        this.gameOverState = gameoverState;
    }

    public GameOverState getGameoverState() {
        return gameOverState;
    }

    public MenuState getMenuState() {
        return menuState;
    }

    public static void playSoundtrack() {
        clip.setMicrosecondPosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stopSountrack() {
        clip.stop();
    }
}
