package presentation.scenes.settingsView;

import application.MP3PlayerApp;
import business.Einstellungen;
import business.MP3;
import business.PlaylistManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
 * Controller fuer SettingsView
 */

public class SettingsController {
    TextField dirField;
    Label dirLabel;
    Button submitButton;
    SettingsView view;
    PlaylistManager manager;
    MP3PlayerApp application;
    Einstellungen settings;

    public SettingsController(MP3PlayerApp application, MP3 player, PlaylistManager manager, Einstellungen settings){
        view = new SettingsView(application, player, manager);
        this.dirField = view.dirField;
        this.dirLabel = view.dirLabel;
        this.submitButton = view.submitButton;
        this.manager = manager;
        this.application = application;
        this.settings = settings;
        initialize();
    }

    /*
     * Eventhandler fuer submitButton, wenn der Pfad falsch oder nicht angegeben worden ist, wird eine Fehlermeldung ausgegeben
     */

    public void initialize(){
        submitButton.setOnAction(mouseEvent -> {
            if((dirField.getText() != null && !dirField.getText().isEmpty() && validPath(dirField.getText()))){
                dirLabel.setText("Pfad erfolgreich geändert!");
                try {
                    settings.setPlaylistsDir(dirField.getText());
                    manager.getPlaylists();
                    application.getPlaylistOverView().reloadPlaylists();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else {
                dirLabel.setText("Feld leer oder Pfad ungültig");
            }
        });
    }

    /*
     * ueberprueft, ob der Pfad exsistiert und valide ist
     */

    public boolean validPath(String toCheck) {
        Path path = Path.of(toCheck);
        return Files.exists(path);
    }

    public SettingsView getView() {
        return view;
    }
}
