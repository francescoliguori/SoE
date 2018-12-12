/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.score;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Utente
 */
public class ReadScore {

    private FileReader fin = null;
    private BufferedReader b = null;

    public ReadScore() {
        try {
            fin = new FileReader("res/score.txt");
            b = new BufferedReader(fin);
        } catch (IOException ex) {
            System.out.println("sono qui");
            Logger.getLogger(WriteScore.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }

//    public String[] read() {
////        String str;
////
////       List<String> list = new ArrayList<String>();
////        try {
////            while((str = b.readLine()) != null){
////                list.add(str);
////           }
////            closeScore();
////        return list.toArray(new String[0]);
////        } catch (IOException ex) {
////            Logger.getLogger(ReadScore.class.getName()).log(Level.SEVERE, null, ex);
////        }
////       closeScore();
////        return null;
//
//
//    }
    public String read(){
        try {
            return b.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ReadScore.class.getName()).log(Level.SEVERE, null, ex);
        }finally{        
            closeScore();
        }

        return null;
    }

    public void closeScore() {
        try {
            fin.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteScore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
