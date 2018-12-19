/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import gioco.prova.bullets.Fireball;
import gioco.prova.bullets.Kunai;
import gioco.prova.bullets.Arrow;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Chris
 */
public class ControllerEntities {

    private LinkedList<Enemies> e = new LinkedList<Enemies>();
    private Enemies tempEnemy;
    private Handler handler;
    private LinkedList<Fireball> f = new LinkedList<Fireball>();
    private LinkedList ball;
    private LinkedList<Kunai> kunaiPlayer = new LinkedList<Kunai>();
    private LinkedList<Kunai> kunaiEnemies = new LinkedList<Kunai>();
    private LinkedList<Arrow> arrowEnemies = new LinkedList<Arrow>();
    private LinkedList<Ramen> ramenBowl = new LinkedList<Ramen>();
    private long lastTime = System.nanoTime(); //used for generation of enemies 
    private long lastTime2 = System.nanoTime();
    private Fireball fireball;
    private Kunai kunai;
    private Arrow arrow;
    private Ramen ramen;
    private Player player;

    private int countDifficulty = 0;
    private float TimeEnemyGenerator = 2.0f;
    private float TimeRamenGenerator = 5.0f;
    private float TimeToUp = System.nanoTime();

    //This variables regard the Boss
    private boolean finalBoss = false;
    private Boss boss;
    private boolean bossMoveSx;
    private boolean jumpSequence;

    private boolean longAttackSequence = false;
    private boolean longAttackJumpSequence = false;
    private int countLongAttackJump = 0;

    private boolean attackSequence = true;
    private boolean attackJumpSequence = true;
    private int countAttackJump = 0;


    public ControllerEntities(Handler handler) {
        this.handler = handler;
    }

    /**
     * ************************CONTROL ENTITY TICK****************************
     */
    public void enemyMovement() {
        for (int i = 0; i < e.size(); i++) {
            tempEnemy = e.get(i);
            if (tempEnemy.getX() < -tempEnemy.getWidth()) {
                removeEnemy(tempEnemy);
            }
            tempEnemy.tick();
        }
    }

    public void fireballMovement() {
        for (int i = 0; i < f.size(); i++) {
            fireball = f.get(i);
            if (fireball.getX() > handler.getWidth()) {
                removeFireball(fireball);
            }
            fireball.tick();
        }
    }

    public void kunaiPlayerMovement() {
        for (int i = 0; i < kunaiPlayer.size(); i++) {
            kunai = kunaiPlayer.get(i);
            if (kunai.getX() > handler.getWidth()) {
                removeKunaiPlayer(kunai);
            }
            kunai.tick();
        }
    }

    public void kunaiEnemyMovement() {
        for (int i = 0; i < kunaiEnemies.size(); i++) {
            kunai = kunaiEnemies.get(i);
            if (kunai.getX() < -kunai.getWidth()) {
                removeKunaiEnemies(kunai);
            }
            kunai.tick();
        }
    }

    public void arrowEnemyMovement() {
        for (int i = 0; i < arrowEnemies.size(); i++) {
            arrow = arrowEnemies.get(i);
            if (arrow.getX() < -arrow.getWidth()) {
                removeArrowEnemies(arrow);
            }
            arrow.tick();
        }
    }

    public void ramenBowlMovement() {
        for (int i = 0; i < ramenBowl.size(); i++) {
            ramen = ramenBowl.get(i);
            if (ramen.getX() < -ramen.getWidth()) {
                removeRamen(ramen);
            }
            ramen.tick();
        }
    }

    /**
     * ***********************************************************************
     */
    
    public void tick() {
        //Control Entities Movement 
        enemyMovement();
        fireballMovement();
        kunaiPlayerMovement();
        kunaiEnemyMovement();
        arrowEnemyMovement();
        ramenBowlMovement();
        
        if (!finalBoss) {
            
            Boss.restartBoss();
            enemyGenerator();
            ramenGenerator();
            if (System.nanoTime() - TimeToUp > 5 * 1000000000L) { //every 5 sec
                checkDifficulty();
                countDifficulty += 1;
                TimeToUp = System.nanoTime();
            }
        } else if (e.isEmpty()) {
            player = handler.getGame().getGameState().getPlayer();
            boss = handler.getGame().getGameState().getBoss();
            addEnemy(boss);
            bossMoveSx = true;
        } else if (boss != null) {
            bossBattle();
            double rand = (Math.random() * 3);

            if (!longAttackJumpSequence && !attackJumpSequence && !jumpSequence) {
                if (rand <= 1) {
                    longAttackJumpSequence = true;
                    longAttackSequence = true;
                    System.out.println("luuuingo");
                } else if (rand <= 2) {
                    System.out.println("avvio corto");
                    attackJumpSequence = true;
                    attackSequence = true;
                } else if (rand <= 3) {
                    jumpSequence = true;
                    System.out.println("Juuuummpo");
                }
            }

        }

    }

    public void setFinalBoss(boolean finalBoss) {
        this.finalBoss = finalBoss;
    }
    
    public boolean getFinalBoss(){
        return finalBoss;
    }

    private void bossBattle() {
        if (boss.reachedB() && !boss.isDead()) {

            if (jumpSequence) {
                bossJumpSequence();
            }

            if (longAttackJumpSequence) {
                bossLongAttackSequence();
            }
            if (attackJumpSequence) {
                bossAttackSequence();
            }
        }
    }

    private void bossLongAttackJumpSequence() {
        if (countLongAttackJump < 2 && boss.bossOnTheGround() && !boss.isLongAttack()) {
            longAttackSequence = true;
            boss.setX(boss.getX() + 351);
            countLongAttackJump++;
            boss.jumpOne(true);
        } else if (countLongAttackJump > 1 && boss.bossOnTheGround() && !boss.isLongAttack()) {
            boss.setX(boss.getX() + 351);
            countLongAttackJump++;
            longAttackSequence = true;
            boss.jumpOne(false);
            if (countLongAttackJump > 3) {
                countLongAttackJump = 0;
                longAttackJumpSequence = false;
            }
        }
    }

    private void bossLongAttackSequence() {
        if (longAttackSequence && boss.bossOnTheGround()) {
            boss.longAttack();
        } else {
            bossLongAttackJumpSequence();
        }
    }

    private void bossJumpSequence() {
        if (boss.getX() >= 0 && boss.bossOnTheGround() && bossMoveSx && jumpSequence) {
            if (boss.getX() <= 0) {
                bossMoveSx = false;
            }
            boss.jumpOne(bossMoveSx);
        } else if (boss.getX() <= handler.getWidth() - boss.getWidth() && boss.bossOnTheGround() && !bossMoveSx) {
            if (boss.getX() >= handler.getWidth() - boss.getWidth()) {
                bossMoveSx = true;
                jumpSequence = false;
                return;
            }
            boss.jumpOne(bossMoveSx);
        }
    }

    private void bossAttackJumpSequence() {
        if (countAttackJump < 2 && boss.bossOnTheGround() && !boss.isAttack()) {
            attackSequence = true;
            boss.setX(boss.getX() + 250);
            countAttackJump++;
            boss.jumpOne(true);
        } else if (countAttackJump > 1 && boss.bossOnTheGround() && !boss.isAttack()) {
            boss.setX(boss.getX() + 250);
            countAttackJump++;
            attackSequence = true;
            boss.jumpOne(false);
            if (countAttackJump > 3) {
                countAttackJump = 0;
                attackJumpSequence = false;
            }
        }
    }

    private void bossAttackSequence() {
        if (attackSequence && boss.bossOnTheGround()) {
            boss.attack();
        } else {
            bossAttackJumpSequence();
        }
    }

    public void setAttackSequence(boolean attackSequence) {
        this.attackSequence = attackSequence;
    }

    public void setLongAttackSequence(boolean longAttackSequence) {
        this.longAttackSequence = longAttackSequence;
    }
    
    public void endBossBattle(){
        boss = null;
    }

    /**
     * *********************CONTROL ENTITY RENDER*****************************
     */
    public void renderEnemy(Graphics g) {
        for(Enemies enemy : e) {
            enemy.render(g);
        }
    }

    public void renderFireball(Graphics g) {
        for(Fireball fb : f) {
            fb.render(g);
        }
    }

    public void renderKunaiPlayer(Graphics g) {
        for(Kunai k : kunaiPlayer) {
            k.render(g);
        }
    }

    public void renderKunaiEnemy(Graphics g) {
        for(Kunai k : kunaiEnemies) {
            k.render(g);
        }
    }

    public void renderKunaiArrow(Graphics g) {
        for(Arrow a : arrowEnemies) {
            a.render(g);
        }
    }

    public void renderRamenBowl(Graphics g) {
        for(Ramen r : ramenBowl) {
            r.render(g);
        }
    }

    /**
     * ***********************************************************************
     */
    public void render(Graphics g) {
        renderEnemy(g);
        renderFireball(g);
        renderKunaiPlayer(g);
        renderKunaiEnemy(g);
        renderKunaiArrow(g);
        renderRamenBowl(g);
    }
    
    private void checkDifficulty() {
        //check the difficulty
//        if (handler.getKeyManager().j) {
        switch (countDifficulty) {
            case 0:
                Creature.changeDefaultSpeed();
//              countDifficulty++;
//              System.err.println("diffuculty" + countDifficulty);
                break;
            case 1:
                Creature.changeDefaultSpeed();
                this.TimeEnemyGenerator -= 0.5f; //1.5 sec
//              countDifficulty++;
//              System.err.println("diffuculty" + countDifficulty);
                break;
            case 2:
                Creature.changeDefaultSpeed();
                Enemy3.setTimeBehavior(1.0f); //1 sec
//              countDifficulty++;
//              System.err.println("diffuculty" + countDifficulty);
                break;
            case 3:
                Creature.changeDefaultSpeed();
                this.TimeEnemyGenerator -= 0.7f; // 0.8 sec
//              countDifficulty++;
//              System.err.println("diffuculty" + countDifficulty);
                break;
            case 4:
                Creature.changeDefaultSpeed();
                Enemy3.setTimeBehavior(0.5f); //1 sec
//              countDifficulty++;
//              System.err.println("diffuculty" + countDifficulty);
                break;
            default:
//              System.err.println("diffuculty" + countDifficulty);
                break;

        }
//    }
    }

    //public boolean collisionKunai(Kunai kunai){
    // metodo che implementerà la collisione del kunai nemico con il player
    //}
    public LinkedList<Enemies> getEnemies() {
        return e;
    }

    public LinkedList<Ramen> getRamenBowl() {
        return ramenBowl;
    }
    
//    public LinkedList<Kunai> getKunaiPlayer() {
//        return kunaiPlayer;
//    }
//    
//    public LinkedList<Kunai> getKunaiEnemies() {
//        return kunaiEnemies;
//    }
    public boolean isNotShooting() {
        return f.isEmpty();
    }

    public void addFireball(Fireball fireball) {
        f.add(fireball);
    }

    public void removeFireball(Fireball fireball) {
        f.remove(fireball);
    }

    public void addKunaiPlayer(Kunai kunai) {
        kunaiPlayer.add(kunai);
    }

    public void addKunaiEnemies(Kunai kunai) {
        kunaiEnemies.add(kunai);
    }

    public void removeKunaiPlayer(Kunai kunai) {
        kunaiPlayer.remove(kunai);
    }

    public void removeKunaiEnemies(Kunai kunai) {
        kunaiEnemies.remove(kunai);
    }

    public void addArrowEnemies(Arrow arrow) {
        arrowEnemies.add(arrow);
    }

    public void removeArrowEnemies(Arrow arrow) {
        arrowEnemies.remove(arrow);
    }

    public void addRamen(Ramen ramen) {
        ramenBowl.add(ramen);
    }

    public void removeRamen(Ramen ramen) {
        ramenBowl.remove(ramen);
    }

    public void addEnemy(Enemies enemy) {
        e.add(enemy);
    }

    public void removeEnemy(Enemies enemy) {
        e.remove(enemy);
    }

    public LinkedList<Fireball> getF() {
        return f;
    }

    public LinkedList<Kunai> getListKunaiPlayer() {
        return kunaiPlayer;
    }

    public LinkedList<Kunai> getListKunaiEnemies() {
        return kunaiEnemies;
    }

    public LinkedList<Arrow> getListArrowEnemies() {
        return arrowEnemies;
    }

    public LinkedList<Enemies> getE() {
        return e;
    }

    public LinkedList<Ramen> getListRamen() {
        return ramenBowl;
    }

    public void setKunaiPlayer(LinkedList<Kunai> kunaiPlayer) {
        this.kunaiPlayer = kunaiPlayer;
    }
   
    public boolean isEmptyEnemies() {
        return e.size() == 0;
    }
    
    private void enemyGenerator() {
        long now = System.nanoTime(); //used for time generation of enemies        
        if (now - lastTime > TimeEnemyGenerator * 1000000000) {           //every 2 seconds at the moment
            addEnemy(chooseEnemy((int) (Math.random() * 3)));
            lastTime = System.nanoTime();
        }
    }

    private void ramenGenerator() {         
        long now = System.nanoTime(); //used for time generation of ramen
        if (handler.getGame().getGameState().getPlayer().getHealth() < 2) {
            if (now - lastTime2 > TimeRamenGenerator * 1000000000) {
                 double rand = (Math.random() * 3);          
                if (rand <= 1) { // altezza ground
                      addRamen(new Ramen(handler, handler.getWidth(), 550));
                } else if (rand <= 2) { //jump
                    addRamen(new Ramen(handler, handler.getWidth(), 350));                  
                } else if (rand <= 3) { //slide
                    addRamen(new Ramen(handler, handler.getWidth(), 620));       
            }
                lastTime2 = System.nanoTime();
            }
        }
    }

    private Enemies chooseEnemy(int n) {
        switch (n) {
            case 1:
                return new Enemy3(handler, handler.getWidth(), 400, this);
            case 0:
                return new Enemy2(handler, handler.getWidth(), 400);
            default:
                return new Enemy1(handler, handler.getWidth(), 400, this);
        }
    }

    public int getCountDifficulty() {
        return countDifficulty;
    }

    public void setCountDifficulty(int countDifficulty) {
        this.countDifficulty = countDifficulty;
    }

    public void setTimeEnemyGenerator(float TimeEnemyGenerator) {
        this.TimeEnemyGenerator = TimeEnemyGenerator;
    }

    public void setTimeRamenGenerator(float TimeRamenGenerator) {
        this.TimeRamenGenerator = TimeRamenGenerator;
    }

    public void setTimeToUp(float TimeToUp) {
        this.TimeToUp = TimeToUp;
    }

    public Boss getBoss() {
        return boss;
    }
    
}
