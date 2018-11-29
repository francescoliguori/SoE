/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova;
import gioco.prova.display.Display;
/**
 *
 * @author marcoruggiero
 */
public class Launcher
{
    public static void main (String[] args)
    {
        Game game = new Game("Itachi's Rush", 1200, 700);
        game.start();
        
    }
}
