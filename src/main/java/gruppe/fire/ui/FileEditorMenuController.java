package gruppe.fire.ui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Represents a controller for the file editor class.
 * This class should handle events triggered by the FileEditorMenu.
 * @author Matti Kjellstadli
 * @version 2023-05-18
 */
public class FileEditorMenuController {
  private String fileContent;

  /**
   * This method is responsible for handling the open file event.
   * @param fileChooser Filechooser used to open file.
   * @param scene The game scene.
   * @param noFile Label giving feedback to the User.
   * @return String containing the entire paths file.
   */
  public String openFileButton(FileChooser fileChooser, Scene scene, Label noFile) {
    File selectedFile = fileChooser.showOpenDialog(scene.getWindow());

    if (!String.valueOf(selectedFile).endsWith(".paths") && selectedFile != null) {
      noFile.setText("Incorrect file type!");

    } else if (selectedFile != null) {
      try {
        byte[] fileBytes = Files.readAllBytes(selectedFile.toPath());
        this.fileContent = new String(fileBytes);
        noFile.setText("Successfully uploaded file" + selectedFile.toPath());
      } catch (IOException e) {
        noFile.setText("Something went wrong (Wrong format?");
      }

    } else {
      noFile.setText("No file was selected");
    }
    return fileContent;
  }

  /**
   * This method is responsible for handling the save file event.
   * @param fileChooser Filechooser used to save file.
   * @param scene The game scene.
   * @param noFile Label giving feedback to the User.
   */
  public void saveFileButton(FileChooser fileChooser, Scene scene, String fileText, Label noFile) {
    File selectedFile = fileChooser.showSaveDialog(scene.getWindow());
    if (selectedFile != null) {
      BufferedWriter bufferedWriter;
      try (FileWriter fileWriter = new FileWriter(selectedFile)) {
        // Create a FileWriter and BufferedWriter

        bufferedWriter = new BufferedWriter(fileWriter);

        // Write the fileText to the file
        bufferedWriter.write(fileText);

        // Close the writers
        bufferedWriter.close();

        noFile.setText("File saved successfully: " + selectedFile.getAbsolutePath());
      } catch (IOException e) {
        noFile.setText("Could not save file");
      }
    }
  }
}
