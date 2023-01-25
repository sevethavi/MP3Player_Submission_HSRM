package presentation.uicomponents.musicControls;
import business.MP3;
import business.PlaylistManager;
import exceptions.SongNotFoundException;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import presentation.uicomponents.songBox.SongBoxController;
import java.io.IOException;

/*
 * Macht die View funktional
 */

public class MusicControlController {
    private final MusicControl view;
    MP3 player;
    int tempTime;
    Label timeLabel = new Label();
    int songLength;
    Slider musicSlider = new Slider();
    String tempAktSong;
    String coverDir = "src/main/resources/application/music/album_artworks/";

    public MusicControlController(MP3 player, PlaylistManager manager) {
        this.player = player;
        view = new MusicControl(player, manager);
        timeLabel = view.timeLabel;
        this.songLength = view.songLength;
        this.musicSlider = view.musicSlider;

        initialize();
    }

    /*
     * Hier sind alle Eventhandler fuer die verschiedenen Buttons gebuendelt
     * Entweder rufen sie die entsprechenden Methoden auf und oder aktivieren sich (aendert bspw. Bild von Play auf Pause)
     */

    public void initialize() {
        view.playButton.setOnAction(e -> {
            try {
                player.play();
                view.playButton.setManaged(false);
                view.playButton.getStyleClass().add("inactive");
                view.pauseButton.setManaged(true);
                view.pauseButton.getStyleClass().remove("inactive");
            } catch (IOException | SongNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });


        view.pauseButton.setOnAction(e -> {
            try {
                player.pause();
                System.out.println("test");
                view.playButton.setManaged(true);
                if(view.playButton.getStyleClass().contains("inactive")) {
                    view.playButton.getStyleClass().remove("inactive");
                }
                System.out.println("test");
                view.pauseButton.setManaged(false);
                view.pauseButton.getStyleClass().add("inactive");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });


        view.shuffleButton.setOnAction(e -> {
            player.shuffle();

            if(view.shuffleButton.getStyleClass().contains("active")) {
                view.shuffleButton.getStyleClass().remove("active");
            } else {
                view.shuffleButton.getStyleClass().add("active");
            }

        });


        view.skipButtonNext.setOnAction(e -> {
            try {
                player.skip();
            } catch (SongNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        view.skipButtonPrev.setOnAction(e -> {
            try {
                player.skipBack();
            } catch (SongNotFoundException | IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        view.repeatButton.setOnAction(e -> {
            player.repeat();
            if(view.repeatButton.getStyleClass().contains("active")) {
                view.repeatButton.getStyleClass().remove("active");
            } else {
                view.repeatButton.getStyleClass().add("active");
            }
        });

        /*
         * Hier wird die Zeit ausgelesen und in Sekunden umgewandelt
         */

        player.currTimeProperty().addListener((observableValue, oldValue, newValue) -> Platform.runLater(() ->{

            tempTime = (int) newValue /1000;
            timeLabel.setText(String.valueOf(tempTime));
            musicSlider.valueProperty().set(tempTime);
            }));

        /*
         * Hier wird der Slider fuer die Musik funktional gemacht, sodass man im Lied skippen kann
         */

        musicSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> Platform.runLater(() ->{
            if(tempAktSong == null) {
            tempAktSong = player.getAktSong().getFilename();
            songLength = player.getAktSong().getDuration();
            }

            if (!tempAktSong.equals(player.getAktSong().getFilename())) {
            tempAktSong = player.getAktSong().getFilename();
            songLength = player.getAktSong().getDuration();
            }

            timeLabel.setText(newValue.toString());
            musicSlider.valueProperty().set(newValue.intValue());
            })
        );

        /*
         * Hier wird die Songbox aktuell gehalten
         */

        player.songNameProperty().addListener((observableValue, oldValue, newValue) -> Platform.runLater(() ->{
            view.songContainer.getChildren().clear();
            SongBoxController songAnzeige = new SongBoxController(player.getAktSong());
            view.songContainer.getChildren().add(songAnzeige.getRoot());
        }));
    }

        public HBox getRoot() {
            return view;
        }
}