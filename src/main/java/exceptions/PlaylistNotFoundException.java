package exceptions;
import business.PlaylistManager;

import java.io.IOException;

/*
 *  Exception falls Playlist nicht existiert
 */

public class PlaylistNotFoundException extends Exception {
        PlaylistManager manager;

    public PlaylistNotFoundException (PlaylistManager manager) throws IOException {
        super("Playlist wurde nicht gefunden. Playlisten werden neugeladen.");
        this.manager = manager;
        manager.getPlaylists();
    }
}
