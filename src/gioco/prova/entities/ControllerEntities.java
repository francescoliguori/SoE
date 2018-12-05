/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import gioco.prova.bullets.Fireball;
import gioco.prova.bullets.Kunai;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

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
    private long lastTime = System.nanoTime(); //used for generation of enemies 
    private Fireball fireball;
    private Kunai kunai;
    private Enemy3 enemy3;

    private int countDifficulty = 0;
    private float TimeEnemyGenerator = 2.0f;

    private float TimeToUp = System.nanoTime();

    public ControllerEntities(Handler handler) {
        this.handler = handler;
    }

    public void tick() {
        for (int i = 0; i < e.size(); i++) {

            tempEnemy = e.get(i);
            if (tempEnemy.getX() < -tempEnemy.getWidth()) {
                removeEnemy(tempEnemy);
            }
            tempEnemy.tick();
        }
        for (int i = 0; i < f.size(); i++) {
//            System.out.println(f.size());
            fireball = f.get(i);
            if (fireball.getX() > handler.getWidth()) {
                removeFireball(fireball);
            }
            fireball.tick();
        }
        for (int i = 0; i < kunaiPlayer.size(); i++) {
            //System.out.println(kunaiPlayer.size());
            kunai = kunaiPlayer.get(i);
            if (kunai.getX() > handler.getWidth()) {
                removeKunaiPlayer(kunai);
            }
            kunai.tick();
        }

        for (int i = 0; i < kunaiEnemies.size(); i++) {
            //System.out.println(kunaiEnemies.size());
            kunai = kunaiEnemies.get(i);
            if (kunai.getX() < -kunai.getWidth()) {
                removeKunaiEnemies(kunai);
            }
            kunai.tick();
        }

        enemyGenerator();
        
        if (System.nanoTime() - TimeToUp > 5000000000L) {
            System.out.println(TimeToUp+ countDifficulty);
            checkDifficulty();
            countDifficulty += 1;
            TimeToUp = System.nanoTime();
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < e.size(); i++) {
            tempEnemy = e.get(i);
            tempEnemy.render(g);
        }
        for (int i = 0; i < f.size(); i++) {
            fireball = f.get(i);
            fireball.render(g);
        }
        for (int i = 0; i < kunaiPlayer.size(); i++) {
            kunai = kunaiPlayer.get(i);
            kunai.render(g);
        }

        for (int i = 0; i < kunaiEnemies.size(); i++) {
            kunai = kunaiEnemies.get(i);
            kunai.render(g);
        }

    }

    private void checkDifficulty() {
        //check the difficulty
//        if (handler.getKeyManager().j) {
        switch (countDifficulty) {
            case 0:
//                    for (int i = 0; i <= e.size(); i++) {
//                        e.get(i).setSpeed(12.0f);
//                    }
                Creature.changeDefaultSpeed();
//                    countDifficulty++;
//                System.err.println("diffuculty" + countDifficulty);
                break;
            case 1:
                Creature.changeDefaultSpeed();
                this.TimeEnemyGenerator -= 0.5f; //1.5 sec
//                    countDifficulty++;
//                System.err.println("diffuculty" + countDifficulty);
                break;
            case 2:
                Creature.changeDefaultSpeed();
//                    for (Enemies e: getEnemies()) {
//                        if (e instanceof Enemy3) {
                Enemy3.setTimeBehavior(1.0f); //1 sec
//                        }
//                    }
//                    countDifficulty++;
//                System.err.println("diffuculty" + countDifficulty);
                break;
            case 3:
                Creature.changeDefaultSpeed();
                this.TimeEnemyGenerator -= 0.5f; // 1 sec
//                    countDifficulty++;
//                System.err.println("diffuculty" + countDifficulty);
                break;
            case 4:
                Creature.changeDefaultSpeed();
//                    for (Enemies e: getEnemies()) {
//                        if (e instanceof Enemy3) {
                Enemy3.setTimeBehavior(0.5f); //1 sec
//                        }
//                    }
//                    countDifficulty++;
//                System.err.println("diffuculty" + countDifficulty);
                break;
            default:
//                System.err.println("diffuculty" + countDifficulty);
                break;
        }
//        }
    }

    //public boolean collisionKunai(Kunai kunai){
    // metodo che implementerà la collisione del kunai nemico con il player
    //}
    public LinkedList<Enemies> getEnemies() {
        return e;
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

    public void addEnemy(Enemies enemy) {
        e.add(enemy);
    }

    public void removeEnemy(Enemies enemy) {
        e.remove(enemy);
    }

//    public void jumpEnemy(Enemies enemy) {
//        enemy3.jump(15);
//    }
    public LinkedList<Fireball> getF() {
        return f;
    }

    public LinkedList<Kunai> getListKunaiPlayer() {
        return kunaiPlayer;
    }

    public LinkedList<Kunai> getListKunaiEnemies() {
        return kunaiEnemies;
    }

    public LinkedList<Enemies> getE() {
        return e;
    }

//    private void enemyJump() {
//        jumpEnemy(chooseEnemy(1));
//    }
    private void enemyGenerator() {
        long now = System.nanoTime(); //used for time generation of enemies        
        if (now - lastTime > TimeEnemyGenerator * 1000000000) {           //every 2 seconds at the moment
            addEnemy(chooseEnemy((int) (Math.random() * 2)));
            lastTime = System.nanoTime();
        }
    }

    private Enemies chooseEnemy(int n) {
        if (n == 1) {
            return new Enemy3(handler, handler.getWidth(), 330, this);
        } else {
            return new Enemy2(handler, handler.getWidth(), 330);
        }
    }

}
