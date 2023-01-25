package presentation.scenes.playlistOverView;

import application.MP3PlayerApp;
import business.MP3;
import business.PlaylistManager;
import exceptions.PlaylistEmptyException;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.tritonus.share.sampled.TVolumeUtils;
import presentation.uicomponents.menu.MenuViewController;
import presentation.uicomponents.musicControls.MusicControlController;

import presentation.uicomponents.volume.VolumeSliderController;

import java.io.IOException;

/*
 * Zeigt alle existierenden Playlistem im Directory an
 */

public class PlaylistOverView extends BorderPane {

    Text title;
    MP3PlayerApp mp3App;
    MP3 player;
    PlaylistManager manager;
    VBox centerPane;

    public PlaylistOverView(MP3PlayerApp application, MP3 player, PlaylistManager manager) throws IOException, PlaylistEmptyException {
        this.mp3App = application;
        this.player = player;
        this.manager = manager;



        title = new Text("Playlist Overview");
        title.getStyleClass().addAll("title", "center");

        centerPane = new VBox();
        centerPane.getChildren().add(title);
        centerPane.setSpacing(10);

        /*
         * laed Standartpfad als erstes, wird jedoch neu geladen, falls es deupdated wird
         */

        for (String key : manager.getPlaylistManager().keySet()){

            Button button = new Button(key.split("\\.")[0]);
            button.setPadding(new Insets(10));
            button.getStyleClass().add("playlist-button");
            centerPane.getChildren().add(button);
            button.setId(key);
        }

        centerPane.setFillWidth(true);
        centerPane.setPadding(new Insets(25, 10, 10, 25));
        centerPane.getStyleClass().addAll("center-container");
        centerPane.setAlignment(Pos.TOP_CENTER);

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




        //bottomPane.getChildren().addAll(controller.getRoot());

        this.setLeft(menu.getView());
        this.setCenter(centerPane);
        this.setBottom(controllerView);
    }
}
