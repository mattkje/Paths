package gruppe.fire.ui;



import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class MainUiController {

    private File selectedFile;
    private static final String PATH = "Data/SavedPaths/";


    public void setActiveFile(String filename){
        Path savedPaths = Path.of(PATH + filename);
        try {
            FileWriter writer;
            writer = new FileWriter("Data/currentPathsFile.cfg");
            writer.write(String.valueOf(savedPaths));
            writer.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
