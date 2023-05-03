package gruppe.fire.fileHandling;

import gruppe.fire.logic.Link;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Represents a file handler for Items. Responsible for reading and writing
 * items-collection to and from a file.
 */
public class StoryFileHandler {


    /**
     * This method reads the users selected file and converts it to string
     * @return Selected paths file as String.
     * @throws IOException
     */


    public String fileToString() throws IOException {


        Path filePath = Path.of("Data/currentPathsFile.cfg");
        byte[] fileBytes = Files.readAllBytes(filePath);
        String fileAsString = new String(fileBytes);


        Path filePath2 = Path.of(fileAsString);
        byte[] fileBytes2 = Files.readAllBytes(filePath2);
        String fileAsString2 = new String(fileBytes2);

        return fileAsString2;

    }

    /**
     * Reads first lines in saved files in saved folder and returns all story titles as an array of Strings.
     */
    public String[] readSavedStories() throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader("Data/SavedPaths/paths1.paths"));
        BufferedReader reader2 = new BufferedReader(new FileReader("Data/SavedPaths/paths2.paths"));
        BufferedReader reader3 = new BufferedReader(new FileReader("Data/SavedPaths/paths3.paths"));
        BufferedReader reader4 = new BufferedReader(new FileReader("Data/SavedPaths/paths4.paths"));
        String story1 = reader1.readLine();
        if (story1 == null || story1.isEmpty()) {
            story1 = "No File";
        }

        String story2 = reader2.readLine();
        if (story2 == null || story2.isEmpty()) {
            story2 = "No File";
        }

        String story3 = reader3.readLine();
        if (story3 == null || story3.isEmpty()) {
            story3 = "No File";
        }

        String story4 = reader4.readLine();
        if (story4 == null || story4.isEmpty()) {
            story4 = "No File";
        }

        String[] storyArray = { story1, story2, story3, story4 };
        return storyArray;

    }

    public String getStoryTitle() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Data/currentPathsfile.cfg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String storyPath;
        try {
            storyPath = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader2;
        try {
            reader2 = new BufferedReader(new FileReader(storyPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String storyTitle;
        try {
            storyTitle = reader2.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return storyTitle;
    }


}


