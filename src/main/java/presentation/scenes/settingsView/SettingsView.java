package presentation.scenes.settingsView;

import application.MP3PlayerApp;
import business.MP3;
import business.PlaylistManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import presentation.uicomponents.menu.MenuViewController;
import presentation.uicomponents.musicControls.MusicControlController;
import presentation.uicomponents.volume.VolumeSliderController;

/*
 * Stellt Einstellungen dar um Ordnerpfad zu aendern
 */

public class SettingsView extends BorderPane {

    TextField dirField;
    Label dirLabel;
    Button submitButton;
    Text title;

    public SettingsView(MP3PlayerApp application, MP3 player, PlaylistManager manager) {

        title = new Text("Einstellungen");
        title.getStyleClass().addAll("title", "center");

        VBox centerPane = new VBox();


        HBox inputContainer = new HBox();
        dirLabel = new Label("Setze den Playlistpfad");
        dirField = new TextField();
        submitButton = new Button("Setzen");

        dirLabel.getStyleClass().addAll("settings-label", "settings");
        dirField.getStyleClass().addAll("settings-field", "settings");
        submitButton.getStyleClass().addAll("settings-button", "settings");


        inputContainer.getChildren().addAll(dirLabel, dirField, submitButton);
        inputContainer.setSpacing(10);
        centerPane.getChildren().addAll(title, inputContainer);
        centerPane.setPadding(new Insets(25, 10, 10, 25));
        centerPane.setSpacing(10);
        centerPane.getStyleClass().addAll("center-container");

        MenuViewController menu = new MenuViewController(application, player);

        HBox bottomPane = new HBox();

        bottomPane.getStyleClass().addAll( "bottom-container");
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
