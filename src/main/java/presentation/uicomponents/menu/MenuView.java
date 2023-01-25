package presentation.uicomponents.menu;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/*
 * Menu View, gibt Zugriff auf Playlist, Player und Settings
 */

public class MenuView extends VBox {

    Button buttonPlaylist;
    Button buttonPlayer;
    Button buttonSettings;

    public MenuView() {
        buttonPlaylist = new Button("Playlists");
        buttonPlaylist.getStyleClass().addAll("content-button");

        buttonPlayer = new Button("Player");
        buttonPlayer.getStyleClass().addAll("content-button");

        buttonSettings = new Button("Einstellungen");
        buttonSettings.getStyleClass().addAll("content-button");

        this.getChildren().addAll(buttonPlaylist, buttonPlayer, buttonSettings);

        this.getStyleClass().addAll("container", "center-top");
        this.setPadding(new Insets(25, 10, 10, 10));
        this.setSpacing(10);
    }
}
