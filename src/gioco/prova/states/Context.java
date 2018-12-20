/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import java.awt.Graphics;

public class Context implements StateDemo{
    
    private StateDemo state;
    
    public Context() {
        state = null;
    }
    
    public StateDemo getState() {
        return state;
    }

    public void setState(StateDemo state) {
        this.state = state;
    }
    
    
    @Override
    public void tick() {
        state.tick();
    }
    
    @Override
    public void render(Graphics g) {
        state.render(g);
    }
    
}
