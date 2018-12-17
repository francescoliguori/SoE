/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.entities.Boss;
import gioco.prova.entities.Creature;
import gioco.prova.entities.Player;
import gioco.prova.gfx.Assets;
import gioco.prova.input.KeyManager;
import java.awt.Graphics;

public class GameOverState extends State {

    private KeyManager k;

    public GameOverState(Handler handler) {
        super(handler);

        k = handler.getKeyManager();
    }

    @Override
    public void tick() {

        getInput();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.gameOver, 0, 0, 1200, 700, null);
        g.drawImage(Assets.gameOverLogo, 0, 0, 1200, 700, null);

    }

    public void getInput() {
        if (k.esc) {
            handler.getGame().setFps(10);
            handler.getGame().setState(handler.getGame().getMenuState());
            restoreGame();
        }
        if (k.enter) {
//                gameState=new GameState(handler);               
           restoreGame();
            handler.getGame().setGameState(new GameState(handler));
            handler.getGame().setState(handler.getGame().getGameState());
            //State.setState(handler.getGame().getGameState());
        }
    }
    
    private void restoreGame(){
         handler.getGame().getGameState().getController().setCountDifficulty(0);
            Creature.setDEAFULT_SPEED();
            //questo metodo è stato utilizzato per inizializzare nuovamente le variabili del player
            //una volta morti. Questo porta ad avere un'istanza di player per sessione di gioco
            Player.restartPlayer();
			Boss.restartBoss();
            handler.getGame().getGameState().getController().setTimeEnemyGenerator(2.0f);
            handler.getGame().getGameState().getController().setTimeRamenGenerator(5.0f);           
            handler.getGame().st.play();
           
    }
}
