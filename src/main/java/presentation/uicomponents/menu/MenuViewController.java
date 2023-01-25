package presentation.uicomponents.menu;

import application.MP3PlayerApp;
import business.MP3;
import exceptions.PlaylistNotFoundException;

import java.io.IOException;

/*
 * Controller fuer das Menu, ist verantwortlich fuer Szenenaenderung
 */

public class MenuViewController {
    private final MP3PlayerApp application;
    private final MP3 player;
    private final MenuView view = new MenuView();



    public MenuViewController (MP3PlayerApp application, MP3 player){
        this.player = player;
        this.application = application;
        initialize();
    }

    /*
     * Eventhandler fuer unsere verschiedenen Buttons, um in der MP3PlayerApp application Szene zu aendern
     */

    public void initialize(){

        view.buttonPlaylist.setOnAction(mouseEvent -> {
            try {
                application.switchScene("PlaylistOverView");
            } catch (PlaylistNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        view.buttonPlayer.setOnAction(mouseEvent -> {
            try {
                application.switchScene("PlayerView");
            } catch (PlaylistNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        view.buttonSettings.setOnAction(mouseEvent -> {
            try {
                application.switchScene("SettingsView");
            } catch (PlaylistNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public MenuView getView(){
        return this.view;
    }
}
