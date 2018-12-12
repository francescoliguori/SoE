/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.score;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Utente
 */
public class WriteScore {
    private FileWriter fout=null;
    private BufferedWriter b=null;
    

    public WriteScore(){
        try {
            //fout=new FileWriter("res/score.txt",true);
            fout=new FileWriter("res/score.txt");
            b=new BufferedWriter(fout);
        } catch (IOException ex) {
            Logger.getLogger(WriteScore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void write(String name,int score){
        try {
//           b.append(name);
//           b.append(": ");
//           b.append(score);
//           b.newLine();

          if(name==null)
               b.write("unknown");
           else
                b.write(name);
           b.write(":");           
           b.write(String.valueOf(score));
           b.flush();        
        } catch (IOException ex) {
            Logger.getLogger(WriteScore.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            closeScore();
        }
    }
    public void closeScore(){
        try {
           fout.close();              
           b.close();
        } catch (IOException ex) {
            Logger.getLogger(WriteScore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
