/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.gfx.Animation;
import gioco.prova.gfx.Assets;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author marcoruggiero
 */
public class Player extends Creature {

    private Animation animRunningLeft;
    private Animation animRunningRight;
    private Animation animRunning;
    private Animation animStop;
    private Animation animJump;
     private Animation animDown;

    protected double gravity;
    protected boolean falling = true;
    protected boolean jumping = false;
    protected boolean down = false; //added
    protected boolean slidingUp = false;
    protected boolean slidingDown = true;
    private float jumpStrength = 200;

    private float jumpStep = 15;
    private float slideStepY = 80; //10
    private float slideStepX=15;

    private float groundHeight;
    private float maxGroundSlide=400;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        gravity = 0.5;

        //Animazione
        animRunningLeft = new Animation(90, Assets.playerRunning);
        animRunningRight = new Animation(50, Assets.playerRunning);
        animRunning = new Animation(70, Assets.playerRunning);
        animStop = new Animation(70, Assets.playerStop);
        animJump= new Animation(270, Assets.playerJump);
        animDown = new Animation(20, Assets.playerDown); //my add to define the millisecond
        groundHeight = y;
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

        /*if (y < 100){
         //y -= jumpStrength/3;
         y += weight;
         }*/
        System.out.println("ground: height" + groundHeight);
        System.out.println(" y " + y);

    }

    private void fall() {

        if (falling) {
            y += gravity;
            gravity += 1;
            if (collisionWithGround(y)) {
                y = groundHeight;
                falling = false;
                gravity = 0.5;
            }
        }

    }

    private void jump(float step) {
        if (jumping) {
            y -= step;
            jumpStep -= 0.6;
            if (y <= groundHeight - jumpStrength) {
                jumping = false;
                falling = true;
                
                jumpStep = 15;
            }
        }
    }

    private void slide(float stepY,float stepX) {  //funzione per lo sliding in discesa 
        
        if (!slidingUp && !slidingDown) {  
            yMove += stepY;
            xMove+=stepX;
            if (y > (groundHeight + getHeight()/2) ){ 
                y = groundHeight + getHeight()/2;
                slidingUp = true;
                
                //down=false;
            }
        } else if (slidingUp && !slidingDown) {
            yMove -= stepY;
            xMove+=stepX;
            if (y <= groundHeight  ) {
                yMove = 0;
                
                //System.out.println("Entrato nell'if");
                yMove += groundHeight - y;
                slidingUp = false;
                slidingDown = true;
                
               // down=true;
                
                System.out.println(slidingUp + " " + slidingDown);
            }
        }

    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        fall();
        jump(jumpStep);
        slide(slideStepY,slideStepX);
        //System.out.println(yDelta);
        if (y == groundHeight && handler.getKeyManager().up) {
            yMove -= jumpStep;
            jumping = true;

        }
        if (handler.getKeyManager().right) {
            if ((x + xMove) >= handler.getWidth() - 155) {
                //x = 375;
                x = handler.getWidth() - 155;
            } else {
                xMove += speed;
            }

        }
        if (handler.getKeyManager().left) {
            if ((x - xMove) <= 0) {
                x = 0;
            } else {
                xMove -= speed;
            }
        }
        if (handler.getKeyManager().down && y == groundHeight) {
            if (slidingDown) {
                yMove += slideStepY;
                xMove+=slideStepX;
                if ((x + xMove) >= handler.getWidth() - 250) {
                //x = 375;
                x = handler.getWidth() - 250;
                }
               // down=true;
                slidingDown = false;
                jumping=false;

            }
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
//        g.setColor(Color.red);
//        g.fillRect(100, 300, Creature.DEFAULT_CREATURE_WIDTH / 2, Creature.DEFAULT_CREATURE_HEIGHT);
    }

    private BufferedImage getCurrentAnimationFrame() {
        if (jumping) {
            if (y >= groundHeight - (jumpStrength * 2 / 4)) {
                return animJump.getFrame(3);
            } else {
                return animJump.getFrame(4);
            }
            
        }else if(!slidingDown ){
            return animDown.getCurrentFrame();
        
        } else {

            if (falling && y >= (groundHeight - jumpStrength)) {
                return animJump.getFrame(6);

            }

        }
        if (xMove < 0) {
            return animRunningLeft.getCurrentFrame();
        }
        if (xMove > 0) {
            return animRunningRight.getCurrentFrame();
        }
        return animRunning.getCurrentFrame();
    }

}
