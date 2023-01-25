package presentation.scenes.playerView;

import application.MP3PlayerApp;
import business.MP3;
import business.PlaylistManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import presentation.uicomponents.ImageViewPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

/*
 * Controller für die PlayerView
 */

public class PlayerController {
    private final MP3PlayerApp application;
    private final MP3 player;
    PlayerView view;
    String coverDir = "src/main/resources/application/music/album_artworks/";
    ImageView coverView;

    public PlayerController(MP3PlayerApp app, MP3 player, PlaylistManager manager){
        this.application = app;
        this.player = player;
        this.view = new PlayerView(application, player, manager);

        initialize();
    }

    /*
     * Listener, falls neuer Song abgespielt wird per Skip beispielsweise
     */

    public void initialize() {
        player.songNameProperty().addListener((observableValue, oldValue, newValue) -> Platform.runLater(() ->{
            try {

                view.coverView.setImage(new Image(new FileInputStream(coverDir + player.getAktSong().getTitle() + "album_artworks.jpg")));
                view.coverViewPane = new ImageViewPane(coverView);
                view.songName.setText(player.getAktSong().getTitle());
                view.songArtist.setText(player.getAktSong().getArtist());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }));
    }

    public PlayerView getView(){return view;}
}
