package presentation.scenes.playerView;

import application.MP3PlayerApp;
import business.MP3;
import business.PlaylistManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import presentation.uicomponents.ImageViewPane;
import presentation.uicomponents.menu.MenuView;
import presentation.uicomponents.menu.MenuViewController;
import presentation.uicomponents.musicControls.MusicControl;
import presentation.uicomponents.musicControls.MusicControlController;
import presentation.uicomponents.volume.VolumeSliderController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

/*
 * View für eine große Anzeige des aktuellen Songs inklusive Bedienelemente für den Song
 */

public class PlayerView extends BorderPane {
    Text title;
    String coverDir = "src/main/resources/application/music/album_artworks/";
    ImageViewPane coverViewPane;
    Label songName;
    Label songArtist;
    ImageView coverView;
    VolumeSliderController volume;
    HBox volumeView;

    public PlayerView(MP3PlayerApp application, MP3 player, PlaylistManager manager){

        MenuViewController menu = new MenuViewController(application, player);
        VBox centerPane = new VBox();
        MusicControlController controller = new MusicControlController(player, manager);
        volume = new VolumeSliderController(player, manager);
        volumeView = volume.getView();
        songName = new Label();
        songArtist = new Label();

        coverView = new ImageView();
        try {
            coverView.setImage(new Image(new FileInputStream("src/main/resources/application/music/album_artworks/default.jpg")));
            coverViewPane = new ImageViewPane(coverView);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        coverViewPane.setMaxWidth(400);
        coverViewPane.setMaxHeight(400);
        coverViewPane.getStyleClass().add("player-cover");
        songName.getStyleClass().add("player-songname");
        songArtist.getStyleClass().add("player-songartist");
        volumeView.setAlignment(Pos.CENTER);
        centerPane.setPadding(new Insets(25, 10 ,100,10));
        centerPane.setSpacing(25);

        centerPane.getStyleClass().addAll("center-container");
        centerPane.setAlignment(Pos.BOTTOM_CENTER);
        centerPane.getChildren().addAll(coverViewPane, songName, songArtist, controller.getRoot(), volumeView);

        this.getStyleClass().add("player");
        this.setLeft(menu.getView());
        this.setCenter(centerPane);
    }
}
