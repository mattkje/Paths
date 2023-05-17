package gruppe.fire.ui;

import gruppe.fire.fileHandling.DataBase;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Represents the music in the game
 */
public class JukeBox {

    private MediaPlayer mediaPlayer;


    public MediaPlayer getMainMenuMusic(){
        DataBase dataBase = new DataBase();
        Media sound = new Media(Objects.requireNonNull(getClass().getResource("/gruppe/fire/Media/backgroundMusic.wav")).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume((double) dataBase.readSettingsFromFile().get("vlm"));
        return mediaPlayer;
    }

    public MediaPlayer getPlayerMenuMusic(){
        DataBase dataBase = new DataBase();
        Media sound = new Media(Objects.requireNonNull(getClass().getResource("/gruppe/fire/Media/backgroundMusic.mp3")).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume((double) dataBase.readSettingsFromFile().get("vlm"));
        return mediaPlayer;
    }

    public MediaPlayer getGameplayMusic(){
        DataBase dataBase = new DataBase();

        Media defaultSound = new Media(Objects.requireNonNull(getClass().getResource("/gruppe/fire/Media/backgroundMusic.mp3")).toString());
        Media murderSound = new Media(Objects.requireNonNull(getClass().getResource("/gruppe/fire/Media/MurderMystery.mp3")).toString());
        Media hauntedSound = new Media(Objects.requireNonNull(getClass().getResource("/gruppe/fire/Media/backgroundMusic.mp3")).toString());
        Media castleSound = new Media(Objects.requireNonNull(getClass().getResource("/gruppe/fire/Media/backgroundMusic.mp3")).toString());
        Media spaceSound = new Media(Objects.requireNonNull(getClass().getResource("/gruppe/fire/Media/SpaceShip.mp3")).toString());

        if (dataBase.getActiveStoryPath().contains("HauntedHouse")){
            this.mediaPlayer = new MediaPlayer(hauntedSound);
        }
        if (dataBase.getActiveStoryPath().contains("Murder")){
            this.mediaPlayer = new MediaPlayer(murderSound);
        }
        if (dataBase.getActiveStoryPath().contains("Castle")){
            this.mediaPlayer = new MediaPlayer(castleSound);
        }
        if (dataBase.getActiveStoryPath().contains("SpaceShip")){
            this.mediaPlayer = new MediaPlayer(spaceSound);
        }
        if (dataBase.getActiveStoryPath().contains("currentGpaths")){

            File mediaFile = new File("Data/currentGpaths/music.mp3");
            String absolutePath = mediaFile.toURI().toString();
            Media gpathsMusic = new Media(absolutePath);
            this.mediaPlayer = new MediaPlayer(gpathsMusic);
        }
        if (mediaPlayer == null){
            this.mediaPlayer = new MediaPlayer(defaultSound);
        }
        mediaPlayer.setVolume((double) dataBase.readSettingsFromFile().get("vlm"));
        return mediaPlayer;
    }
}