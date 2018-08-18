package sound;

import parameters.Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Song {

    private File sound;
    private Clip clip;
    private Sound event;

    Song(File sound, Sound event) {
        this.sound = sound;
        this.event = event;
        play();
    }

    Sound getEvent() {
        return event;
    }

    public void play() {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem
                    .getAudioInputStream(sound);
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    void stop() {
        clip.stop();
    }
}
