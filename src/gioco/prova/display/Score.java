/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.display;

/**
 *
 * @author Umberto
 */
public class Score {

    private int GROWING_DELTA = 1;
    private int count;

    public Score() {
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public void incrementCount(int multiplier) {
        this.count += GROWING_DELTA * (multiplier + 1);
    }

}
