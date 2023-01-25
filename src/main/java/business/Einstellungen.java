package business;
/*
* Pfadverwaltung für die Businesslogik
 */

public class Einstellungen {

    String playlistsDir;

    public Einstellungen() {
        playlistsDir = "src/main/resources/application/music/playlists/";
    }

    public void setPlaylistsDir(String playlistsDir) {
        this.playlistsDir = playlistsDir;
    }

    public String getPlaylistsDir() {
        return playlistsDir;
    }
}
