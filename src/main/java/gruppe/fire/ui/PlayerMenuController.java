package gruppe.fire.ui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Popup;

/**
 * Represents a controller for the player menu class.
 * This class should handle events triggered by the PlayerMenu.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-18
 */
public class PlayerMenuController {

  /**
   * This method should write the active player to a temporary file.
   *
   * @param filename Player file name.
   */
  public void setActivePlayer(String filename) {
    Path savedPlayers = Path.of("Data/PlayerData/Players/" + filename);
    try (FileWriter fileWriter = new FileWriter("Data/currentPlayerFile.cfg")) {
      fileWriter.write(String.valueOf(savedPlayers));

    } catch (IOException ex) {
      String exceptionString = "Could not write to file " + ex;
      System.getLogger(exceptionString);
    }
  }

  /**
   * This method is responsible for informing the user if the story
   * has no set goals.
   */
  public void noGoalsPopUp(GameDisplay gameDisplay, Font font, Font menuFontLarge, Scene scene,
                           MediaPlayer player) {
    Label warningInfo = new Label("No goals are set");
    warningInfo.setFont(font);
    Button cancelButton = new Button("Cancel");
    cancelButton.setFont(font);
    Button proceedButton = new Button("Proceed without goals");
    proceedButton.setFont(font);
    HBox buttonBox = new HBox(proceedButton, cancelButton);
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(30);
    Label warning = new Label("Warning");
    warning.setFont(menuFontLarge);
    warning.setAlignment(Pos.CENTER);
    VBox warningBox = new VBox(warning, warningInfo, buttonBox);
    warningBox.setSpacing(20);
    warningBox.setPrefWidth(600);
    warningBox.setPadding(new Insets(20));
    warningBox.setId("warningBox");
    warningBox.setAlignment(Pos.CENTER);
    Popup popup = new Popup();
    popup.getContent().add(warningBox);
    popup.show(scene.getWindow());
    cancelButton.setOnAction(e ->
        popup.hide());
    proceedButton.setOnAction(e -> {
      popup.hide();
      player.dispose();
      gameDisplay.start(scene, false);
    });
  }
}
