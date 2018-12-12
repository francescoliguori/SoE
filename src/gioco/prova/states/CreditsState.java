/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Handler;
import gioco.prova.gfx.Assets;
import java.awt.Graphics;

/**
 *
 * @author Utente
 */
public class CreditsState extends State {

    public CreditsState(Handler handler) {
        super(handler);
    }

    @Override
    public void tick() {
        getInput();
    }

    @Override
    public void render(Graphics g) {
       g.drawImage(Assets.credits, 0, 0, null);
    }
     private void getInput(){
        if(handler.getKeyManager().esc)
            State.setState(handler.getGame().getMenuState());
        
    }
    
}
