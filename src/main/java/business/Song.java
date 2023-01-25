package business;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.mpatric.mp3agic.*;

import javax.imageio.ImageReader;

/*
 *	Speichert und laed Daten von MP3 Datein
 */

public class Song {
	private final Mp3File mp3MagicFile;
	private final String filename;
	private String title;
 	private String artist;
	private final long duration;
	private String coverDir = "src/main/resources/application/music/album_artworks/";

	private RandomAccessFile file;

	private ImageReader image;
	
	public Song(String fileName) throws UnsupportedTagException, InvalidDataException, IOException {
		this.mp3MagicFile = new Mp3File(fileName);
		this.filename = fileName;
		this.duration = mp3MagicFile.getLengthInSeconds();

		if(mp3MagicFile.hasId3v2Tag()) {
			ID3v2 songTag = mp3MagicFile.getId3v2Tag();
			title = songTag.getTitle();
			artist = songTag.getArtist();
			byte[] imageData = songTag.getAlbumImage();

			if(imageData != null) {
				String mimeType = songTag.getAlbumImageMimeType();
				file = new RandomAccessFile(coverDir + title + "album_artworks.jpg", "rw");

				file.write(imageData);
				file.close();
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public String getFilename() {
		return filename;
	}

	public RandomAccessFile getFile() {
		return file;
	}
	public String getArtist() {
		return artist;
	}

	public int getDuration() {
		return (int) duration;
	}

	public Mp3File getMp3MagicFile() {
		return mp3MagicFile;
	}

	public ImageReader getImage() {
		return image;
	}
}
