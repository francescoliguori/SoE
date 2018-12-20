/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Soundtrack {

    private final String soundsPath;
    private String[] tracks;
    private Clip clip;

    public Soundtrack() {
        soundsPath = "/sounds";
        tracks = new String[2];
        tracks[0] = "/sounds/ost.wav";
        tracks[1] = "/sounds/ost2.wav";
    }

    /*private File[] loadTracksList() {
        return new File("res" + soundsPath).listFiles();
    }*/

    private String getRandomTrack() {
        return tracks[new Random().nextInt(tracks.length)];
    }

    private void open() {
        try {
            URL url = getClass().getResource(this.getRandomTrack());
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void play() {
        this.open();
        clip.setMicrosecondPosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

}
