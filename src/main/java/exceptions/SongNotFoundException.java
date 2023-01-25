package exceptions;
import business.Playlist;

import java.io.IOException;

/*
 * Exception falls Song nicht existiert
 */

public class SongNotFoundException extends Exception {
        Playlist playlist;

    public SongNotFoundException (Playlist playlist) throws IOException {
        super("Song wurde nicht gefunden. Playlist wird neugeladen.");
        this.playlist = playlist;
        playlist.loadPlaylist();
    }
}
