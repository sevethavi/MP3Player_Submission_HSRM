package presentation.uicomponents.songBox;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import business.Song;
import javafx.scene.layout.VBox;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/*
 * Fasst alle UI Elemente für das Lied zusammen: Cover, Songtitel und Interpret
 */

public class SongBox extends HBox {

    Label songName;
    Label artist;
    String coverDir = "src/main/resources/application/music/album_artworks/";

    public SongBox(Song song){
        artist = new Label();
        songName = new Label();
        VBox informationContainer = new VBox();
        artist.setText(song.getArtist());
        songName.setText(song.getTitle());
        informationContainer.getChildren().addAll(songName, artist);

        artist.getStyleClass().addAll("songbox-artist", "songbox");
        songName.getStyleClass().addAll("songbox-songname", "songbox");
        ImageView coverView = new ImageView();

        /*
         * liest Bild aus der aktuellen Directory aus
         */

        try {
            coverView.setImage(new Image(new FileInputStream(coverDir + song.getTitle() + "album_artworks.jpg")));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        coverView.setFitWidth(70);
        coverView.setFitHeight(70);
        coverView.setSmooth(true);

        artist.getStyleClass().add("hbox-styling");
        songName.getStyleClass().add("hbox-styling");

        this.setSpacing(10);
        this.getChildren().addAll(coverView, informationContainer);
        this.setId(song.getTitle());
    }
}