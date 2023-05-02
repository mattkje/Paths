package gruppe.fire.fileHandling;

import gruppe.fire.logic.Link;
import gruppe.fire.logic.Passage;
import gruppe.fire.logic.Story;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a file handler for Items. Responsible for reading and writing
 * items-collection to and from a file.
 */
public class StoryFileHandler {

    private String fileAsString2;


    /**
     * This method reads the users selected file and converts it to string
     * @return Selected paths file as String.
     * @throws IOException
     */


    public String fileToString() throws IOException {


        Path filePath = Path.of("src/main/java/gruppe/fire/fileHandling/currentPathsFile.txt");
        byte[] fileBytes = Files.readAllBytes(filePath);
        String fileAsString = new String(fileBytes);


        Path filePath2 = Path.of(fileAsString);
        byte[] fileBytes2 = Files.readAllBytes(filePath2);
        String fileAsString2 = new String(fileBytes2);

        return fileAsString2;

    }

    public void readGameFile(String fileAsString2) {
        //File gameFile = new File("Aokigahara.paths");

        Scanner scanner = new Scanner(fileAsString2);
        String storyName = scanner.nextLine();
        scanner.useDelimiter("::");
        System.out.println(storyName);
        scanner.next();
        String openingPassage = scanner.next();
        String[] openingParts = openingPassage.split("\r?\n");
        String openingPassageTitle = openingParts[0];
        String openingPassageContent = openingParts[1];
        String openingActions = openingParts[2];
        System.out.println(openingPassageTitle);
        System.out.println(openingPassageContent);
        System.out.println(openingActions);

        for (int i = 2; i <= 3; i++) {

            String openingPassageLinks = openingParts[i + 1];
            parseLink(openingPassageLinks);
            System.out.println(parseLink(openingPassageLinks));
        }

    }



    public Link parseLink(String openingPassageLinks) {
        String[] tokens = openingPassageLinks.split("[\\[\\]()]+");
        String text = tokens[1].strip();
        String reference = tokens[2].strip();
        Link link = new Link(text, reference);
        return link;
    }


}


