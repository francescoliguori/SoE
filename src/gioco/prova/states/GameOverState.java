/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.states;

import gioco.prova.Game;
import gioco.prova.Handler;
import gioco.prova.entities.ControllerEntities;
import gioco.prova.entities.Creature;
import gioco.prova.gfx.Assets;
import gioco.prova.input.KeyManager;
import java.awt.Graphics;

public class GameOverState extends State {

    private GameState gameState;
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
            System.exit(0);
        }
        if (k.enter) {
//                gameState=new GameState(handler);               

            handler.getGame().getGameState().getController().setCountDifficulty(0);
            Creature.setDEAFULT_SPEED();
            handler.getGame().getGameState().getController().setTimeEnemyGenerator(2.0f);
            handler.getGame().setGameState(new GameState(handler));
            //handler.getGame().getGameState().getController().setTimeToUp(System.nanoTime());

            State.setState(handler.getGame().getGameState());
        }
    }
}
