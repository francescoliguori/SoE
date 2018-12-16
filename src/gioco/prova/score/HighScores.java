/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.score;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Utente
 */
class PersonScore {

    String name;
    int score;

    public PersonScore(String name, int score) {
        this.name = name;
        this.score = score;
    }
}

class scoreCompare implements Comparator<PersonScore> {

    @Override
    public int compare(PersonScore s1, PersonScore s2) {
        return s2.score - s1.score;
    }
}

public class HighScores {

    private FileReader fin = null;
    private BufferedReader br = null;
    private FileWriter fout = null;
    private BufferedWriter bw = null;
    ArrayList<String> listRead = new ArrayList<>();
    ArrayList<PersonScore> list = new ArrayList<>();

    public HighScores() {
        try {
            fin = new FileReader("res/score.txt");
            br = new BufferedReader(fin);
        } catch (IOException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] read() {
        String str;
        try {
            while ((str = br.readLine()) != null) {
                listRead.add(str);
            }
            return listRead.toArray(new String[0]);
        } catch (IOException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void write(String name, int score) {
        int line=0;
        if (name == null) {
            name = "unknown";
        }
        orderScore(name, score);
        try {
            fout = new FileWriter("res/score.txt");
            bw = new BufferedWriter(fout);
            for (PersonScore person : list) {
                bw.write(person.name);
                bw.write(":" + person.score);
                bw.newLine();
                bw.flush();
                line++;
                if (line == 5) {
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeScore();
        }
    }

    private void orderScore(String name, int score) {
        readForOrder();
        list.add(new PersonScore(name, score));
        Collections.sort(list, new scoreCompare());
    }

    private void closeScore() {
        try {
            fout.close();
            br.close();
            fin.close();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readForOrder() {
        String str;
        try {
            while ((str = br.readLine()) != null) {
                String[] tmp = str.split(":");
                String name = tmp[0];
                int score = Integer.valueOf(tmp[1]);
                list.add(new PersonScore(name, score));
            }
        } catch (IOException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
