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
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Chris
 */
public class ControllerEntities {
    private LinkedList <Enemies> e = new LinkedList<Enemies>();
    private Enemies tempEnemy;
    private Handler handler;
    private LinkedList<Fireball> f = new LinkedList<Fireball>();
    private LinkedList ball;
    private LinkedList<Kunai> k = new LinkedList<Kunai>();
    private LinkedList kuna;
    private long lastTime=System.nanoTime() ; //used for generation of enemies 
    private Fireball fireball;
    private Kunai kunai;
    
    public ControllerEntities(Handler handler) {
        this.handler = handler;
    }
    
    public void tick(){
    /*    for(int i = 0; i < e.size(); i++){
            tempEnemy = e.get(i);
            tempEnemy.tick();
        }
        for(int i = 0; i < f.size(); i++)
        {
            
            fireball = f.get(i);
            fireball.tick();
        
            
       }*/
    for(int i = 0; i < e.size(); i++){
      
            tempEnemy = e.get(i);
            if(tempEnemy.getX() < -tempEnemy.getWidth())
                removeEnemy(tempEnemy);
            tempEnemy.tick();
        }
    for(int i = 0; i < f.size(); i++){
            System.out.println(f.size());
           fireball = f.get(i);          
            if(fireball.getX()> handler.getWidth()){               
                removeFireball(fireball);
            }
            fireball.tick();   
         }
    for(int i = 0; i < k.size(); i++){
                System.out.println(k.size());
           kunai =k.get(i);          
            if(kunai.getX()> handler.getWidth()){               
                removeKunai(kunai);
            }
            kunai.tick();   
         }
    
        enemyGenerator();
    }
    
    public void render(Graphics g){
        for(int i = 0; i < e.size(); i++){
            tempEnemy = e.get(i);
            tempEnemy.render(g);
        }
        for(int i = 0; i < f.size(); i++){
            fireball=f.get(i);
            fireball.render(g);
        }
        for(int i = 0; i < k.size(); i++){
            kunai = k.get(i);
            kunai.render(g);
        }
        
    }
    
    public boolean isNotShooting(){
        return f.isEmpty() ;
    }
    public void addFireball(Fireball fireball){
        f.add(fireball);
    }
    
    public void removeFireball(Fireball fireball){       
       f.remove(fireball);    
    }
    
    public void addKunai(Kunai kunai){
        k.add(kunai);
    }
    
    public void removeKunai(Kunai kunai){       
       k.remove(kunai);
    }
    public void addEnemy(Enemies enemy){
        e.add(enemy);
    }
    
    public void removeEnemy(Enemies enemy){
        e.remove(enemy);
    }
    
    public LinkedList<Fireball> getF(){
        return f;
    }
    
    public LinkedList<Kunai> getK(){
        return k;
    }

    public LinkedList<Enemies> getE() {
        return e;
    }
    
    private void enemyGenerator(){
         long now=System.nanoTime() ; //used for time generation of enemies        
         if(now - lastTime > 2000000000){           //every 2 seconds at the moment
             addEnemy(chooseEnemy((int)(Math.random()*3)));
             lastTime=System.nanoTime();
         }
    }  
   private Enemies chooseEnemy(int n){
       if(n==1){
           return new  Enemy1(handler,handler.getWidth(), 150);
       }
        else if(n == 0)return new Enemy2(handler,handler.getWidth(), 300);
       return new Enemy3(handler,handler.getWidth(), 300);
   }
   
}
