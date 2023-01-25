package presentation.scenes.playlistView;

import application.MP3PlayerApp;
import business.MP3;
import business.PlaylistManager;
import business.Song;
import exceptions.PlaylistNotFoundException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import presentation.uicomponents.menu.MenuViewController;
import presentation.uicomponents.musicControls.MusicControlController;
import presentation.uicomponents.volume.VolumeSliderController;

import java.io.IOException;

/*
 * zeigt den Inhalt der Playliste in Songs an
 */

public class PlaylistView extends BorderPane {
    Text title;
    MP3 player;
    MP3PlayerApp application;
    PlaylistManager manager;
    VBox centerPane;
    ListView<Song> trackListView;
    public PlaylistView(MP3PlayerApp application, MP3 player, PlaylistManager manager, String playlistName) throws PlaylistNotFoundException, IOException {

        this.player = player;
        this.application = application;
        this.manager = manager;


        title = new Text(playlistName.split("\\.")[0]);
        title.getStyleClass().addAll("title", "center");
        centerPane = new VBox();
        centerPane.getChildren().add(title);
        centerPane.setSpacing(10);
        centerPane.setPadding(new Insets(25));
        centerPane.getStyleClass().add("center-container");
        /*
         * erstellt ein MenuView im linken Bereich, um schnellen Zugriff auf Player, Playlisten und EInstellungen zu erhalten
         */

        MenuViewController menu = new MenuViewController(application, player);

        Region fillerRegion1 = new Region();
        Region fillerRegion2 = new Region();
        HBox.setHgrow(fillerRegion1, Priority.ALWAYS);
        HBox.setHgrow(fillerRegion2, Priority.ALWAYS);

        MusicControlController controller = new MusicControlController(player, manager);
        VolumeSliderController volumeSlider = new VolumeSliderController(player, manager);
        HBox volumeSliderView = volumeSlider.getView();
        HBox controllerView = controller.getRoot();
        controllerView.setAlignment(Pos.CENTER);
        volumeSliderView.setAlignment(Pos.CENTER_RIGHT);
        controllerView.getStyleClass().addAll("bottom-container");

        this.setLeft(menu.getView());
        this.setCenter(centerPane);
        this.setBottom(controllerView);
    }
}
