/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.entities;

import gioco.prova.Handler;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Chris
 */
public class ControllerEntities {
    private LinkedList <Enemies> e = new LinkedList<Enemies>();
    private Enemies tempEnemy;
    private Handler handler;

    public ControllerEntities(Handler handler) {
        this.handler = handler;
    }
    
    public void tick(){
        for(int i = 0; i < e.size(); i++){
            tempEnemy = e.get(i);
            tempEnemy.tick();
        }
    }
    
    public void render(Graphics g){
        for(int i = 0; i < e.size(); i++){
            tempEnemy = e.get(i);
            tempEnemy.render(g);
        }
    }
    
    public void addEnemy(Enemies enemy){
        e.add(enemy);
    }
    
    public void removeEnemy(Enemies enemy){
        e.remove(enemy);
    }
}
