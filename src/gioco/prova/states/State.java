/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Handler;
import java.awt.Graphics;

public abstract class State {

    //inizialemente lo stato corrente è null

    private static State currentState = null;

    public static void setState(State state) {
        currentState = state;
    }

    public static State getState() {
        return currentState;
    }

    protected Handler handler;

    public State(Handler handler) {
        this.handler = handler;
    }

    //Ogni stato in cui si troverà il gioco avrà sicuramente i metodi tick e render

    public abstract void tick();

    public abstract void render(Graphics g);

}
