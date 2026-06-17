package audio;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/** Service de lecture audio — joue des fichiers OGG depuis le classpath. */
public class AudioService {

    private AudioService() {}

    /** Joue un fichier audio de façon asynchrone (thread démon). */
    public static Thread jouer(String nomFichier) {
        Thread t = new Thread(() -> lire(nomFichier), "audio-" + nomFichier);
        t.setDaemon(true);
        t.start();
        return t;
    }

    /** Joue un fichier audio et bloque jusqu'à la fin (au plus 5 secondes). */
    public static void jouerEtAttendre(String nomFichier) {
        try {
            jouer(nomFichier).join(5000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    private static void lire(String nomFichier) {
        URL url = AudioService.class.getResource("/audio/" + nomFichier);
        if (url == null) return;
        try {
            AudioInputStream source = AudioSystem.getAudioInputStream(url);
            AudioFormat fmt = source.getFormat();
            AudioFormat pcmFmt = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    fmt.getSampleRate(), 16,
                    fmt.getChannels(), fmt.getChannels() * 2,
                    fmt.getSampleRate(), false);
            AudioInputStream pcm = AudioSystem.getAudioInputStream(pcmFmt, source);
            Clip clip = AudioSystem.getClip();

            // Attendre l'événement STOP plutôt que drain(), qui retourne immédiatement pour un Clip
            CountDownLatch fin = new CountDownLatch(1);
            clip.addLineListener(e -> {
                if (e.getType() == LineEvent.Type.STOP) fin.countDown();
            });

            clip.open(pcm);
            clip.start();
            fin.await();
            clip.close();
            pcm.close();
            source.close();
        } catch (Exception ignored) {
            // Les erreurs audio ne doivent pas bloquer l'application
        }
    }
}
