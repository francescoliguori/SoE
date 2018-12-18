/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import gioco.prova.bullets.Arrow;
import gioco.prova.bullets.Fireball;
import gioco.prova.bullets.Kunai;
import gioco.prova.display.Score;
import gioco.prova.gfx.Animation;
import gioco.prova.gfx.Assets;
import gioco.prova.score.HighScores;
import gioco.prova.states.GameOverState;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

/**
 *
 * @author marcoruggiero
 */
public class Player extends Creature {

    private ControllerEntities c;
    private Handler handler;
    private Animation animRunningLeft;
    private Animation animRunningRight;
    private Animation animRunning;
    private Animation animStop;
    private Animation animJump;
    private Animation animDown;

    private Score score;

    private long lastTime = System.nanoTime(); //used for generation of kunai 
    private final int MAX_KUNAI = 10;
    private static int count;
    private float TimeKunaiGenerator; //15 secondi

    protected double gravity;
    protected boolean falling = true;
    protected boolean jumping = false;
    protected boolean canSlide = true; //added
    protected boolean slidingUp = false;
    protected boolean slidingDown = true;
    float xStart; //addddd
    float xFin;
    private float jumpStrength = 200;

    private float jumpStep = 15;
    private float slideStepY = 8; //80
    private float slideStepX = 5; //50

    private float groundHeight;
    private float collisionTime = System.nanoTime();
    private boolean isCollision = false;
    private static Player instance = null;

    public static Player getPlayerInstance(Handler handler, float x, float y, ControllerEntities c) {
        if (instance == null) {
            instance = new Player(handler, x, y, c);
        }
        return instance;
    }

    public static void restartPlayer() {
        instance = null;
    }

    private Player(Handler handler, float x, float y, ControllerEntities c) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        gravity = 0.5;

        //Animazione
        animRunningLeft = new Animation(90, Assets.playerRunning);
        animRunningRight = new Animation(50, Assets.playerRunning);
        animRunning = new Animation(70, Assets.playerRunning);
        animStop = new Animation(70, Assets.playerStop);
        animJump = new Animation(270, Assets.playerJump);
        animDown = new Animation(200, Assets.playerDown); //my add to define the millisecond
        groundHeight = y;
        //si aggiunge un controller entities: sarà usato per sparare
        this.c = c;
        this.handler = handler;

        bounds.x = 40;
        bounds.y = 100;
        bounds.width = 95;
        bounds.height = 90;

        count = MAX_KUNAI;
        TimeKunaiGenerator = 5; // 5s
    }

    @Override
    public void tick() {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //in questo metodo si aggiornano tutte le variabili per ciascun oggetto
        //si invoca tick su animRunning così da cambiare i frame
        animRunningLeft.tick();
        animRunningRight.tick();
        animRunning.tick();
        animDown.tick();
        //animJumping.tick();
        //movimento
        getInput();
        move();
        checkRamenCollisions(0, 0);
        if (System.nanoTime() - collisionTime > 2000000000) { //2 sec
            isCollision = false;
            //collisionTime = System.nanoTime();
        }
        if (count < MAX_KUNAI) {
            KunaiGenerator();
        }
    }

    public int getScore() {
        return handler.getGame().getGameState().getHudmngr().getScore().getCount();
    }

    private void fall() {

        if (falling) {
            bounds.y = 100;
            bounds.height = 90;
            y += gravity;
            gravity += 1;
            if (collisionWithGround(y)) {

                y = groundHeight;
                falling = false;
                gravity = 0.5;
            }
        }

    }

    private void jump(float step, float groundH) {
        if (jumping) {
            bounds.height = 120;
            bounds.y = 70;
            y -= step;
            jumpStep -= 0.6;
            if (y <= groundH - jumpStrength) {
                jumping = false;
                falling = true;

                jumpStep = 15;
            }
        }
    }

    private void slide(float stepY, float stepX) {  //funzione per lo sliding in discesa
        xFin = xStart + 90; //adddddd
        if (!slidingUp && !slidingDown) {
            yMove += stepY;
            xMove += stepX;

            if (y > (groundHeight + getHeight() * 0.55)) { // /3
                y = (float) (groundHeight + getHeight() * 0.55);
                yMove = 0;//addddddd
                xMove += stepX;
                //slidingUp = true;

            }
            if (this.getX() > xFin || x > handler.getWidth() - 200 || this.getX() < xStart) {//
                //                    System.out.println(this.getX() < xstart);
                slidingUp = true;
            }
        } else if (slidingUp && !slidingDown) {
            yMove -= stepY;
            xMove += stepX;
            if (y <= groundHeight) {
                yMove = 0;

                //System.out.println("Entrato nell'if");
                yMove += groundHeight - y;
                slidingUp = false;
                slidingDown = true;
                canSlide = true;
                //                System.out.println(slidingUp + " " + slidingDown);
            }
        }

    }

    //questo metodo è utilizzato per controllare se il player
    //entra in collisione con un nemico
    public boolean checkEnemyCollisions(float xOffset, float yOffset) {
        for (Enemies e : handler.getGame().getGameState().getController().getEnemies()) {
            if (e.getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(xOffset, yOffset)) && canSlide) {
                if (e.isDead()) {
                    //jumping = true;
                    //jump(jumpStep, groundHeight+100);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public boolean checkKunaiEnemyCollisions(float xOffset, float yOffset) {
        for (Kunai k : handler.getGame().getGameState().getController().getListKunaiEnemies()) {
            if (k.getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(xOffset, yOffset)) && canSlide) {
                //            System.out.println("Collisione con kunai nemico");
                c.removeKunaiEnemies(k);
                return true;
            }
        }
        return false;
    }

    /*public void checkRamenCollisions(float xOffset, float yOffset) {
     for (Ramen ramen : handler.getGame().getGameState().getController().getRamen()) {
     if (health != 3 && ramen.checkPlayerCollisions(xOffset, yOffset)) {
     health += 1;
     }
     }
     }*/
    public boolean checkArrowEnemyCollisions(float xOffset, float yOffset) {
        for (Arrow a : handler.getGame().getGameState().getController().getListArrowEnemies()) {
            if (a.getCollisionBounds(0f, 0f).intersects(this.getCollisionBounds(xOffset, yOffset)) && canSlide) {
                //            System.out.println("Collisione con kunai nemico");
                c.removeArrowEnemies(a);

                return true;
            }
        }
        return false;

    }

    public void checkRamenCollisions(float xOffset, float yOffset) {
        for (Ramen ramen : handler.getGame().getGameState().getController().getListRamen()) {
            if (health != 3 && ramen.checkPlayerCollisions(xOffset, yOffset)) {
                health += 1;
                handler.getGame().getGameState().getController().removeRamen(ramen);
            }

        }
    }

    public void getInput() {
        xMove = 0;
        yMove = 0;

        fall();
        jump(jumpStep, groundHeight);
        slide(slideStepY, slideStepX);
        //System.out.println(yDelta);
        if (y == groundHeight && handler.getKeyManager().up) {
            yMove -= jumpStep;
            jumping = true;

        }
        if (handler.getKeyManager().right && canSlide) {
            if ((x + xMove) >= handler.getWidth() - 155) {
                //x = 375;
                x = handler.getWidth() - 155;
            } else {
                xMove += speed;
            }

        }
        if (handler.getKeyManager().left && canSlide) {

            if ((x - xMove) <= 0) {
                x = 0;
            } else {
                xMove -= speed;
            }
        }

        if (handler.getKeyManager().down && canSlide && y == groundHeight
                && !handler.getKeyManager().left && x < handler.getWidth() - 330) { // 330 = xpersonaggio 155 + grandezza slide 175
            canSlide = false;

            if (slidingDown) {
                xStart = this.getX();
                //System.out.println(xstart);
                yMove += slideStepY;
                xMove += slideStepX;
                if ((x + xMove) >= handler.getWidth() - 250) {  //400
                    //x = 375;
                    x = handler.getWidth() - 250;
                }
                slidingDown = false;
                jumping = false;
            }
        }

        if (handler.getKeyManager().space && y == groundHeight && c.getF().isEmpty() && c.getListKunaiPlayer().isEmpty() && handler.getGame().getGameState().getHudmngr().getCurrPower() == handler.getGame().getGameState().getHudmngr().getMaxPower()) {
            //canShoot=false;
            c.addFireball(new Fireball(handler, this.getX(), this.getY(), width, height));
            handler.getGame().getGameState().getHudmngr().setCurrPower(0);
        }

        //lo facciamo sparare solo se premiamo V e non ci sono altri kunai
        if (handler.getKeyManager().v && c.getListKunaiPlayer().isEmpty() && c.getF().isEmpty()) {
            if (count > 0) {
                c.addKunaiPlayer(new Kunai(handler, this.getX(), this.getY(), width, height, true));
                count -= 1;
            }
            System.out.println(count);
        }

    }

    public boolean collisionWithGround(float y) {
        return y >= groundHeight;
    }

    @Override
    public void render(Graphics g) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //si disegna ogni volta il frame corrente dell'animazione
        g.drawImage(getCurrentAnimationFrame(), (int) x, (int) y, null);

        //g.clearRect((int)x,(int) y, 187, 155);
        //g.setColor(Color.red);
        //g.fillRect(100, 300, Creature.DEFAULT_CREATURE_WIDTH / 2, Creature.DEFAULT_CREATURE_HEIGHT);
        //        g.setColor(Color.red);
        //        g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (isCollision) {
            return animDown.getCurrentFrame();
        }
        if (jumping) {
            if (this.checkEnemyCollisions(0, 0) || this.checkKunaiEnemyCollisions(0, 0) || checkArrowEnemyCollisions(0, 0)) {
                checkCollision();
                //isCollision = true;
                //return animDown.getCurrentFrame();
            }
            if (y >= groundHeight - (jumpStrength * 2 / 4)) {
                return animJump.getFrame(3);
            } else {
                return animJump.getFrame(4);
            }

            //        } else if (!slidingDown) {
            //            return animDown.getCurrentFrame();
        } else if (falling && y >= (groundHeight - jumpStrength)) {
            if (this.checkEnemyCollisions(0, 0) || this.checkKunaiEnemyCollisions(0, 0) || checkArrowEnemyCollisions(0, 0)) {
                checkCollision();
                //isCollision = true;
                //return animDown.getCurrentFrame();
            }
            return animJump.getFrame(6);

        }
        if (xMove < 0) {
            if (this.checkEnemyCollisions(0, 0) || this.checkKunaiEnemyCollisions(0, 0) || checkArrowEnemyCollisions(0, 0)) {
                checkCollision();
                //isCollision = true;
                //return animDown.getCurrentFrame();
            }

            return animRunningLeft.getCurrentFrame();
        }
        if (xMove > 0) {
            if (this.checkEnemyCollisions(0, 0) || this.checkKunaiEnemyCollisions(0, 0) || checkArrowEnemyCollisions(0, 0)) {
                checkCollision();
                //isCollision = true;
                //return animDown.getCurrentFrame();
            }
            return animRunningRight.getCurrentFrame();
        }
        if (this.checkEnemyCollisions(0, 0) || this.checkKunaiEnemyCollisions(0, 0) || checkArrowEnemyCollisions(0, 0)) {
            checkCollision();
            //isCollision = true;
            //return animDown.getCurrentFrame();
            return animDown.getFrame(2);

        }

        return animRunning.getCurrentFrame();
    }

    private void checkCollision() {
        health -= 1;
        if (health <= 0) {
            handler.getGame().setGameoverState(new GameOverState(handler));
            handler.getGame().setState(handler.getGame().getGameoverState());
            handler.getGame().st.stop();
            HighScore();
        }
        isCollision = true;
        collisionTime = System.nanoTime();
    }

    private void HighScore() {

        HighScores hs = new HighScores();
        String name = JOptionPane.showInputDialog("Enter your name: ");
        hs.write(name, getScore());
    }

    private void KunaiGenerator() {
        long now = System.nanoTime(); //used for time generation of kunai
        if (now - lastTime > TimeKunaiGenerator * 1000000000) {
            count += 1;
            lastTime = System.nanoTime();
            //System.out.println(count);
        }
    }

    public void setTimeKunaiGenerator(float TimeKunaiGenerator) {
        this.TimeKunaiGenerator = TimeKunaiGenerator;
    }

    public static int getCount() {
        return count;
    }
}
