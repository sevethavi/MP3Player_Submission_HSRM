package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import business.Einstellungen;
import business.PlaylistManager;
import exceptions.PlaylistNotFoundException;
import javafx.application.Application;
import javafx.stage.Stage;
import presentation.scenes.playerView.PlayerController;
import presentation.scenes.playlistOverView.PlaylistOverViewController;
import presentation.scenes.playlistView.PlaylistViewController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import business.MP3;
import presentation.scenes.settingsView.SettingsController;
import presentation.uicomponents.menu.MenuViewController;

/*
*   @author Tim Reinicke, Sophia Mueller
*   Main Klasse des MP3-Players, instanziiert die Businesslogik, ruft die Szenen auf und fügt sie in die HashMap ein
*   Kann zwischen Szenen wechseln, stellt die Main-Stage dar
 */

public class MP3PlayerApp extends Application {
    private Stage primaryStage;
    private Map<String, Pane> scenes;
    private Scene mainScene;
    MP3 player;
    PlaylistManager manager;
    Einstellungen settings;
    PlaylistOverViewController playlistOverView;

    public void start(Stage primaryStage) {

       try {
            this.primaryStage = primaryStage;
            settings = new Einstellungen();
            scenes = new HashMap<String, Pane>();
            manager = new PlaylistManager(settings);
            player = new MP3(this);


            MenuViewController menu = new MenuViewController(this, player);
            PlayerController playerView = new PlayerController(this, player, manager);
            playlistOverView = new PlaylistOverViewController(this, player, manager);
            PlaylistViewController playlistViewController = new PlaylistViewController(this, player, manager, "Rock.m3u");
            SettingsController settingsController = new SettingsController(this, player, manager, settings);

            scenes.put("PlayerView", playerView.getView());
            scenes.put("PlaylistOverView", playlistOverView.getView());
            scenes.put("MenuView",  menu.getView());
            scenes.put("PlaylistView",  playlistViewController.getRoot());
            scenes.put("SettingsView",  settingsController.getView());

            Pane root = scenes.get("PlaylistOverView");

            Scene scene = new Scene(root,800,800);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            mainScene = scene;
            primaryStage.setScene(mainScene);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void switchScene(String viewName, String... optPane) throws PlaylistNotFoundException, IOException {
        if(mainScene != null) {
            Pane nextView = scenes.get(viewName);
            if(nextView != null) {
                if(optPane.length == 0) {
                    mainScene.setRoot(nextView);
                } else {
                    PlaylistViewController playlistViewController = new PlaylistViewController(this, player, manager, optPane[0]);
                    mainScene.setRoot(playlistViewController.getRoot());
                }

            }
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public PlaylistOverViewController getPlaylistOverView() {
        return playlistOverView;
    }

    public PlaylistManager getManager() {
        return manager;
    }

    public static void main(String[] args) {
        launch();
    }
}