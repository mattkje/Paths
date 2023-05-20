package gruppe.fire.filehandling;

import java.io.*;
import java.util.Scanner;
import gruppe.fire.logic.Passage;
import gruppe.fire.logic.Story;
import gruppe.fire.actions.*;
import gruppe.fire.logic.Link;

/**
 * Represents paths file imports and converting them to a story object.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-19
 */
public class FileToStory {

  private File storyFile;

  /**
   * Creates an instance of storyfile.
   * Required File object path from user
   *
   * @param storyFile
   */
  public FileToStory(File storyFile) {
    this.storyFile = storyFile;
  }

  public Story readFile() {
    Passage openingPassage = new Passage("Null", "Null");
    Story story = new Story("Null", openingPassage);
    boolean openingPassageState = false;

    try (Scanner scanner = new Scanner(this.storyFile)) {

      // Sets the first line as the title of the story
      story.setTitle(scanner.nextLine());
      String line = scanner.nextLine();
      while (scanner.hasNext()) {
        if (line.contains("::")) {
          String passageTitle = line.split("::")[1];
          String passageContent = scanner.nextLine();

          if (!openingPassageState) {
            openingPassage.setTitle(passageTitle);
            openingPassage.setContent(passageContent);
            story.setOpeningPassage(openingPassage);
            openingPassageState = true;
            line = scanner.nextLine();
            while (line.startsWith("[")) {

              openingPassage.addLink(createLink(line));
              if (scanner.hasNext()) {
                line = scanner.nextLine();
              } else {
                line = "";
              }
            }
          } else {
            Passage passage = new Passage(passageTitle, passageContent);
            if (scanner.hasNextLine()) {
              line = scanner.nextLine();
              if (line.startsWith("[")) {
                while (line.startsWith("[")) {
                  passage.addLink(createLink(line));
                  if (scanner.hasNext()) {
                    line = scanner.nextLine();
                  } else {
                    line = "";
                  }
                }
              }
              story.addPassage(passage);
            }
          }
        } else {
          throw new IllegalArgumentException("Something went wrong");
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return story;
  }

  public Link createLink(String linkString) {
    int i = 1;
    int tIA = linkString.indexOf("[") + 1;
    int tIB = linkString.indexOf("]");
    int rIA = linkString.indexOf("(") + 1;
    int rIB = linkString.indexOf(")");

    String title = linkString.substring(tIA, tIB);
    String reference = linkString.substring(rIA, rIB);
    Link link = new Link(title, reference);
    String[] splitString = linkString.split(";");

    while (i < splitString.length) {
      link.addAction(this.createAction(splitString[i], splitString[i + 1]));
      i = i + 2;
    }

    return link;
  }

  public Action createAction(String actionType, String actionAmount) {
    Action action = null;
    switch (actionType) {
      case "GoldAction" -> action = new GoldAction(Integer.parseInt(actionAmount));
      case "HealthAction" -> action = new HealthAction(Integer.parseInt(actionAmount));
      case "InventoryAction" -> action = new InventoryAction(actionAmount);
      case "ScoreAction" -> action = new ScoreAction(Integer.parseInt(actionAmount));
    }

    return action;
  }

}
