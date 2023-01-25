package presentation.scenes.playlistView;

import application.MP3PlayerApp;
import business.MP3;
import business.PlaylistManager;
import business.Song;
import exceptions.PlaylistNotFoundException;
import exceptions.SongNotFoundException;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presentation.uicomponents.songBox.SongBoxController;

import java.io.IOException;
import java.util.List;

/*
 * Controller für die PlaylistView, zeigt die Songs an und verbindet sie mit einem Playbutton, um Songs direkt zu spielen
 */

public class PlaylistViewController {
    PlaylistView view;
    MP3 player;
    PlaylistManager manager;
    ListView<Song> tracklistView;
    String playlistName;
    HBox songContainer;
    VBox centerPane;

    public PlaylistViewController(MP3PlayerApp application, MP3 player, PlaylistManager manager, String playlistName) throws PlaylistNotFoundException, IOException {
        view = new PlaylistView(application, player, manager, playlistName);
        this.manager = manager;
        this.playlistName = playlistName;
        this.player = player;
        tracklistView = view.trackListView;
        centerPane = view.centerPane;
        initialize();
    }

    /*
     * Erstellt einen "Kasten" für Daten des Songs und einen unique Playbutton
     */

    public void initialize() throws PlaylistNotFoundException, IOException {
        List<Song> songListe = manager.getPlaylist(playlistName).getSongs();
        for(Song song : songListe) {
            songContainer = new HBox();
            SongBoxController songAnzeige = new SongBoxController(song);
            Button playButton = new Button();
            playButton.getStyleClass().addAll("icon-button", "playButton");
            playButton.setId(song.getFilename());
            playButton.setOnMouseClicked(e -> {
                try {
                    manager.setPlaylist(playlistName);
                    player.playSong(song);
                } catch (SongNotFoundException | IOException | PlaylistNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            });
            songContainer.getChildren().addAll(songAnzeige.getRoot(), playButton);
            centerPane.getChildren().add(songContainer);
        }
    }

    public PlaylistView getRoot() {
        return view;
    }

}
