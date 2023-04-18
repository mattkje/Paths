package gruppe.fire.fileParsing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Represents a file handler for Items. Responsible for reading and writing
 * items-collection to and from a file.
 */
public class StoryFileHandler {

    private String text;

    private String fileName;

    public StoryFileHandler(){
        this.text = text;
    }

    /**
     * This method reads the users selected file and converts it to string
     * @return Selected paths file as String.
     * @throws IOException
     */
    public static String fileToString() throws IOException {


        Path filePath = Path.of("src/main/java/gruppe/fire/fileParsing/currentPathsFile.txt");
        byte[] fileBytes = Files.readAllBytes(filePath);
        String fileAsString = new String(fileBytes);


        Path filePath2 = Path.of(fileAsString);
        byte[] fileBytes2 = Files.readAllBytes(filePath2);
        String fileAsString2 = new String(fileBytes2);

        System.out.println(fileAsString2);
        return fileAsString2;
    }

}
