package gruppe.fire.filehandling;

import gruppe.fire.logic.Player;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;
import javafx.scene.image.Image;

/**
 * Represents reading saved players and converting them to player objects.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-19
 */
public class FileToPlayer {

  private final File playerFile;

  public FileToPlayer(File playerFile) {
    this.playerFile = playerFile;
  }

  /**
   * This method is responsible for reading and returning a
   * player object from file.
   *
   * @return player The player.
   */
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
      String exceptionString = "Something went wrong while reading active player file" + e;
      System.getLogger(exceptionString);
    }
    return player;
  }


}
