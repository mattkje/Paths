package gruppe.fire.ui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Represents a controller for the player menu class.
 * This class should handle events triggered by the PlayerMenu.
 * @author Matti Kjellstadli
 * @version 2023-05-18
 */
public class PlayerMenuController {

  /**
   * This method should write the active player to a temporary file.
   * @param filename Player file name.
   */
  public void setActivePlayer(String filename) {
    Path savedPlayers = Path.of("Data/PlayerData/Players/" + filename);
    try (FileWriter fileWriter = new FileWriter("Data/currentPlayerFile.cfg")){
      fileWriter.write(String.valueOf(savedPlayers));

    } catch (IOException ex) {
      String exceptionString = "Could not write to file "+ex;
      System.getLogger(exceptionString);
    }
  }
}
