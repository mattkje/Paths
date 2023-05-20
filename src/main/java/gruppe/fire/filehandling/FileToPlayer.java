package gruppe.fire.filehandling;

import gruppe.fire.logic.Player;
import javafx.scene.image.Image;
import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Represents reading saved players and converting them to player objects.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-19
 */
public class FileToPlayer {

  private File playerFile;

  public FileToPlayer(File playerFile) {
    this.playerFile = playerFile;
  }

  public Player readFile() {
    Player player = new Player.PlayerBuilder()
        .build();


    try (Scanner scanner = new Scanner(this.playerFile)) {


      // Sets the first line as the title of the story
      player.setName(scanner.nextLine());
      Path path = Path.of(scanner.nextLine());
      Image image = new Image(path.toUri().toString());
      player.setImage(image);
      player.setHealth(scanner.nextInt());
      player.setScore(scanner.nextInt());
      player.setGold(scanner.nextInt());

    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return player;
  }


}
