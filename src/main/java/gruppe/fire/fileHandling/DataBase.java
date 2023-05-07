package gruppe.fire.fileHandling;

import java.io.*;

public class DataBase {

    public String getActivePlayerPath(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Data/currentPlayerFile.cfg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String activePlayer;
        try {
            activePlayer = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return activePlayer;
    }

    public String getActiveStoryPath(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Data/currentPathsFile.cfg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String activeStory;
        try {
            activeStory = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return activeStory;

    }
}
