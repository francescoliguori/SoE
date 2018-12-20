/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gioco.prova.gfx;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Soundtrack {

    private final String soundsPath;
    private File[] tracks;
    private Clip clip;

    public Soundtrack() {
        soundsPath = "res/sounds";
        tracks = this.loadTracksList();
    }

    private File[] loadTracksList() {
        return new File(soundsPath).listFiles();
    }

    private File getRandomTrack() {
        return tracks[new Random().nextInt(tracks.length)];
    }

    private void open() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(this.getRandomTrack()));
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
