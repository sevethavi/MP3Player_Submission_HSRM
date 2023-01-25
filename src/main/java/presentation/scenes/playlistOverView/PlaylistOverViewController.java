package presentation.scenes.playlistOverView;


import application.MP3PlayerApp;
import business.MP3;
import business.PlaylistManager;
import exceptions.PlaylistEmptyException;
import exceptions.PlaylistNotFoundException;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import presentation.uicomponents.AlertBox;

import java.io.IOException;

/*
 * Controller fuer PlaylistOverView
 */

public class PlaylistOverViewController  {

    PlaylistOverView view;
    MP3PlayerApp application;
    MP3 player;
    PlaylistManager manager;


    public PlaylistOverViewController(MP3PlayerApp application, MP3 player, PlaylistManager manager) throws IOException, PlaylistEmptyException {
           view = new PlaylistOverView(application, player, manager);
           this.application = application;
           this.player = player;
           this.manager = manager;
           initialize();
    }

    public void initialize (){
        for(Node buttons: view.centerPane.getChildren()) {

            buttons.setOnMouseClicked(e -> {

                /*
                 * Eventhandler fuer Buttonclicks, falls die Playliste hier leer ist, wird ein PopUp-Window erzeugt und die Playliste
                 * wird nicht angezeigt
                 */

                try {
                    if(manager.playlistManager.get(buttons.getId()).getPlaylist().isEmpty()){
                        AlertBox.display("PLAYLIST EMPTY", "Playlist ist leer und wird nicht angezeigt.");
                    }else {
                        application.switchScene("PlaylistView", buttons.getId());
                    }
                } catch (PlaylistNotFoundException ex) {
                    try {
                        application.switchScene("PlaylistOverView");
                    } catch (PlaylistNotFoundException | IOException exc) {
                        throw new RuntimeException("Unable to reload Page");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException("Unable to reload Page");
                }
            });
        }
    }

    /*
     * falls neues directory gewaehlt wurde, wird die Playliste hiermit neu geladen
     */

    public void reloadPlaylists() {
        view.centerPane.getChildren().clear();
        for (String key : manager.getPlaylistManager().keySet()){
            Button button = new Button(key);
            button.setPadding(new Insets(10));
            button.getStyleClass().add("playlist-button");
            view.centerPane.getChildren().add(button);
            button.setId(key);
        }
        initialize();
    }

    public PlaylistOverView getView() {
        return view;
    }
}
