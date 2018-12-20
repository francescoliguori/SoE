/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova;

public class Launcher
{
    public static void main (String[] args)
    {
        //La classe Game è singleton
        Game game = Game.getGameIstance();
        game.start();
        
    }
}
