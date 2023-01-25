package business;

import application.MP3PlayerApp;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import exceptions.SongNotFoundException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;

/*
 * Thread und Playerverwaltung, um Einstellungen auf Musik vorzunehmen (skip, play, pause..)
 */

public class MP3 extends Thread {

    private final SimpleMinim minim;
    private SimpleAudioPlayer audioPlayer;
    private final SimpleIntegerProperty currTime;
    private final SimpleStringProperty songName;
    boolean shuffle = false;
    boolean repeat = false;
    PlaylistManager manager;
    Song prevSong;
    Song nextSong;
    Song aktSong;
    boolean isPaused;
    Thread timerThread;
    Thread playThread;

    public MP3(MP3PlayerApp application) throws IOException {
        minim = new SimpleMinim();
        currTime = new SimpleIntegerProperty(0);
        manager = application.getManager();
        songName = new SimpleStringProperty();

    }

    /*
     * playSong setzt unsere Merkvariable für Pause auf false und setzt letzen, aktuellen und naechsten Song
     * Falls der Audioplayer noch keine Musik spielt, laed er das MP3-File
     * Falls playSong aber schon Musikspielt, wird er pausiert, da sonst die Lieder in verschiedenen Threads uebereinander spielen
     */

    public void playSong(Song song) throws SongNotFoundException, IOException {
        isPaused = false;
        aktSong = manager.getAktPlaylist(shuffle).getAktSong(song);
        prevSong = manager.getAktPlaylist(shuffle).getPrevSong(song);
        nextSong = manager.getAktPlaylist(shuffle).getNextSong(song);

        if (audioPlayer == null) {
            audioPlayer = minim.loadMP3File(aktSong.getFilename());
        } else if (audioPlayer.isPlaying()) {
            audioPlayer.pause();
            timerThread.interrupt();
        }

        timerThread = createTimerThread();

        play();
    }

    /*
     * erstellt einen neuen Thread um den Timer eines Liedes zu merken
     */

    public Thread createTimerThread() {
        return new Thread(() -> {
            while (playThread.isAlive()) {
                try {
                    Thread.sleep(1000);
                    currTime.setValue(currTime.getValue() + 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if(aktSong.getDuration() == currTime.getValue() / 1000){
                    try {
                        skip();

                    } catch (SongNotFoundException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    /*
     * Play fragt ab, ob das Lied pausiert ist, wenn ja spielt er ab dem pausierten Zeitpunkt weiter.
     * Ansonsten laedt er den neuen Song und setzt ihn auf einen neuen Pfad
     */

    public void play() throws IOException, SongNotFoundException {
        if (isPaused) {
            playAtTime();
            return;
        }
        songName.setValue(aktSong.getTitle());
        audioPlayer = minim.loadMP3File(aktSong.getFilename());
        currTime.setValue(0);
        playThread = new Thread(() -> audioPlayer.play());

        timerThread = createTimerThread();
        playThread.start();
        timerThread.start();
    }

    /*
     * Laedt aktuellen Song und spielt ihn mit der letzten gemerkten Zeit ab und startet den Thread
     */

    public void playAtTime() {
        audioPlayer = minim.loadMP3File(aktSong.getFilename());
        playThread = new Thread(() -> audioPlayer.play(currTime.getValue()));
        timerThread = createTimerThread();
        playThread.start();
        timerThread.start();
    }

    public void pause() throws InterruptedException {
        isPaused = true;
        audioPlayer.pause();
        timerThread.interrupt();
    }

    public void volume(float value) {
        audioPlayer.setGain(value);
    }

    /*
     * Wenn repeat an ist, stoppt er den alten Thread und spielt den aktuellen Song einfach erneut ab
     * Wenn repeat aus ist unterbricht er den alten Pfad, setzt den previous, aktuellen und next Song neu und startet einen neuen Thread mit play
     */

    public void skip() throws SongNotFoundException, IOException {
        if(repeat) {
            audioPlayer.pause();
            timerThread.interrupt();
            play();
            return;
        }
        audioPlayer.pause();
        timerThread.interrupt();
        prevSong = aktSong;
        aktSong = nextSong;

        nextSong = manager.getAktPlaylist(shuffle).getNextSong(aktSong);
        play();
    }

    /*
     * selbe Logik wie skip, nur dass der previous Song der aktuelle wird
     */

    public void skipBack() throws SongNotFoundException, IOException {
        if(repeat) {
            audioPlayer.pause();
            timerThread.interrupt();
            play();
            return;
        }

        audioPlayer.pause();
        timerThread.interrupt();

        nextSong = aktSong;
        aktSong = prevSong;

        prevSong = manager.getAktPlaylist(shuffle).getPrevSong(aktSong);
        play();
    }

    public void shuffle() {
        shuffle = !shuffle;

    }

    public void repeat() {
        repeat = !repeat;
    }

    public SimpleIntegerProperty currTimeProperty() {
        return currTime;
    }

    public SimpleStringProperty songNameProperty() {
        return songName;
    }

    public Song getAktSong() {
        if (aktSong != null) {
            return aktSong;
        }
        return null;
    }
}

