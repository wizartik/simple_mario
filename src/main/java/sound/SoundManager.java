package sound;

import parameters.FileLoader;
import parameters.Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {
    private static Song song;

    public static synchronized void playSound(Sound event) {
        play(FileLoader.getResourceAsFile(event.getPath()));
    }

    public static void playBackground(Sound event) {
        if (song != null) {
            song.stop();
        }
        song = new Song(FileLoader.getResourceAsFile(event.getPath()), event);
    }

    private static void play(File sound) {

        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem
                    .getAudioInputStream(sound);
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void stopSoundtrack() {
        song.stop();
    }
}
