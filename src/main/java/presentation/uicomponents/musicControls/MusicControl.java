package presentation.uicomponents.musicControls;

import business.MP3;
import business.PlaylistManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import presentation.uicomponents.volume.VolumeSlider;
import presentation.uicomponents.volume.VolumeSliderController;

/*
 * MusicControl stellt alle Bedienelemente der Musik dar
 */

public class MusicControl extends HBox {
    Button playButton;
    Button pauseButton;
    Button skipButtonPrev;
    Button skipButtonNext;
    Button shuffleButton;
    Button repeatButton;
    Slider musicSlider;
    Label timeLabel;
    VolumeSliderController volume;
    int songLength = 100;
    VBox middleContainer;
    HBox songContainer;
    HBox volumeSlider;

    public MusicControl(MP3 player, PlaylistManager manager) {
        middleContainer = new VBox();
        playButton = new Button();
        pauseButton = new Button();
        skipButtonPrev = new Button();
        skipButtonNext = new Button();
        shuffleButton = new Button();
        repeatButton = new Button();
        musicSlider = new Slider(0, songLength, 0);
        timeLabel = new Label("0:00");
        volume = new VolumeSliderController(player, manager);

        Region fillerRegion1 = new Region();
        Region fillerRegion2 = new Region();

        playButton.setId("play");
        pauseButton.setId("pause");
        skipButtonPrev.setId("skip_prev");
        skipButtonNext.setId("skip_next");
        shuffleButton.setId("shuffle");
        repeatButton.setId("repeat");

        timeLabel.getStyleClass().add("controls-timelabel");
        playButton.getStyleClass().addAll("playButton", "icon-button");
        pauseButton.getStyleClass().addAll("pauseButton", "icon-button");
        playButton.setManaged(false);
        skipButtonPrev.getStyleClass().addAll("skipButtonPrev", "icon-button");
        skipButtonNext.getStyleClass().addAll("skipButtonNext", "icon-button");
        shuffleButton.getStyleClass().addAll("shuffleButton", "icon-button");
        repeatButton.getStyleClass().addAll("repeatButton", "icon-button");
        musicSlider.getStyleClass().addAll("musicBar, timeLabel");

        songContainer = new HBox();
        HBox timeBox = new HBox();

        timeBox.getChildren().addAll(musicSlider, timeLabel);
        timeBox.setSpacing(10);
        timeBox.setPadding(new Insets(10));
        timeBox.setId("timeBox");

        HBox.setHgrow(fillerRegion1, Priority.ALWAYS);
        HBox.setHgrow(musicSlider, Priority.ALWAYS);
        HBox.setHgrow(fillerRegion2, Priority.ALWAYS);


        HBox controls = new HBox();
        songContainer.getStyleClass().addAll("songbox-controls");
        controls.getChildren().addAll(shuffleButton, skipButtonPrev, playButton, pauseButton, skipButtonNext, repeatButton);
        controls.setId("controls");
        volumeSlider = volume.getView();
        volumeSlider.getStyleClass().add("volslider-controls");
        controls.setSpacing(10);
        middleContainer.setAlignment(Pos.CENTER);
        volumeSlider.setAlignment(Pos.CENTER_RIGHT);
        songContainer.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10));
        middleContainer.getChildren().addAll(controls, timeBox);

        this.getChildren().addAll(songContainer, fillerRegion1, middleContainer, fillerRegion2, volumeSlider);

    }
}
