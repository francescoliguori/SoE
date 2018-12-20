/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova;

import gioco.prova.input.KeyManager;

//anche l'handler viene reso singleton
public class Handler {
    private Game game;
    static private Handler instance = null;
    
    public static Handler getHandlerInstance(Game game){
        if(instance == null){
            instance = new Handler(game);
        }
        return instance;
    }
    private Handler(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
        
    }

    public void setGame(Game game) {
        this.game = game;
        
    }
    
    public int getWidth(){
        return game.getWidth();
    }
    
    public int getHeight(){
        return game.getHeight();
    }
    
    public KeyManager getKeyManager(){
        return game.getKeyManager();
    }
}
