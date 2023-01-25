package exceptions;
import business.Playlist;
import business.PlaylistManager;
import java.io.IOException;

/*
 *  Exception falls Playlist leer ist
 */

public class PlaylistEmptyException extends Exception {
    PlaylistManager manager;

    public PlaylistEmptyException (PlaylistManager manager, Playlist playlist) throws IOException, PlaylistNotFoundException {
        super("Playlist: " + manager.getPlaylistName(playlist) + "ist leer.");
        this.manager = manager;

    }
}
