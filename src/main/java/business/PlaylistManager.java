package business;
import java.io.File;
import java.io.IOException;
import java.util.*;
import exceptions.PlaylistNotFoundException;

/*
 * Manager fuer unsere Playlistobjekte. Verwaltet sie und setzt aktuelle Playlist und Shuffleplaylist
 */

public class PlaylistManager {
	public Map<String, Playlist> playlistManager;
	Playlist aktPlaylist;
	Playlist shufflePlaylist;
	Einstellungen settings;

	public PlaylistManager (Einstellungen settings) throws IOException {
		this.settings = settings;
		getPlaylists();
	}

	/*
	 * laed alle Playlisten als File aus dem gegebenen Verzeichnis aus
	 */

	public void getPlaylists() throws IOException {
		playlistManager = new HashMap<String, Playlist>();
		String playlistDir = settings.getPlaylistsDir();
		File folder = new File(playlistDir);

		for(File fileEntry: Objects.requireNonNull(folder.listFiles())) {
			playlistManager.put(fileEntry.getName(), new Playlist(fileEntry));
		}
	}

	public Map<String, Playlist> getPlaylistManager() {
		return playlistManager;
	}

	/*
	 * gibt aktuelle Playlist zurueck
	 * @return aktPlaylist
	 * @return shufflePlaylist
	 */

	public Playlist getAktPlaylist(boolean shuffle) {
		if(!shuffle) {
			return aktPlaylist;
		} else {
			return shufflePlaylist;
		}

	}

	/*
	 * setzt aktuelle Playlist
	 */

	public void setPlaylist(String name) throws PlaylistNotFoundException, IOException {
		this.aktPlaylist = getPlaylist(name);
		shufflePlaylist = aktPlaylist.clone();
		setShufflePlaylist();
	}

	public void setShufflePlaylist() {
		Collections.shuffle((List<?>) shufflePlaylist.getSongs());
	}

	/*
	 * liest Playlist mit mitgegebenem Name
	 * @param String nameVonPlaylist
	 * @return Playlist gefundenePlaylist
	 * @exception Playlist nicht gefunden
	 */

	public Playlist getPlaylist(String name) throws PlaylistNotFoundException, IOException {
		for (String key : playlistManager.keySet()) {
			if (key.equals(name)){
				return playlistManager.get(name);
			}
		}
		throw new PlaylistNotFoundException(this);
	}

	/*
	 * @param Playliste wessen Name man herausfinden moechte
	 * @return Name von Playlist
	 */

	public String getPlaylistName (Playlist playlist) throws PlaylistNotFoundException, IOException {
		for(String key : playlistManager.keySet()) {
			if(playlistManager.get(key) == playlist) {
				return key;
			}
		}

		throw new PlaylistNotFoundException(this);
	}
}


