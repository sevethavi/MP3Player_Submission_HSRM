package presentation.uicomponents.songBox;

import business.Song;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

/*
 * Verwaltung der Songbox
 */

public class SongBoxController {
    Song song;
    Label artist;
    Label songName;
    SongBox view;

    /*
     *@param aktueller Song um Songbox zu erzeugen
     */

    public SongBoxController(Song song){
        this.song = song;
        view = new SongBox(song);
        artist = view.artist;
        songName = view.songName;
    }

    public SongBox getRoot() {
        return view;
    }

}
