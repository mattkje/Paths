package gruppe.fire.filehandling;

import gruppe.fire.actions.Action;
import gruppe.fire.actions.GoldAction;
import gruppe.fire.actions.HealthAction;
import gruppe.fire.actions.InventoryAction;
import gruppe.fire.actions.ScoreAction;
import gruppe.fire.logic.Link;
import gruppe.fire.logic.Passage;
import gruppe.fire.logic.Story;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents paths file imports and converting them to a story object.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-19
 */
public class FileToStory {

  private final File storyFile;

  /**
   * Creates an instance of storyfile.
   * Required File object path from user
   *
   * @param storyFile The story file.
   */
  public FileToStory(File storyFile) {
    this.storyFile = storyFile;
  }

  /**
   * This method is responsible for reading a paths file, and
   * returning a story object.
   *
   * @return The story
   */
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


  /**
   * This method is responsible for reading a String and returning it
   * as a Link object.
   *
   * @param linkString The String to be read.
   * @return The link object.
   */
  public Link createLink(String linkString) {
    int i = 1;
    int tiA = linkString.indexOf("[") + 1;
    int tiB = linkString.indexOf("]");
    int riA = linkString.indexOf("(") + 1;
    int riB = linkString.indexOf(")");

    String title = linkString.substring(tiA, tiB);
    String reference = linkString.substring(riA, riB);
    Link link = new Link(title, reference);
    String[] splitString = linkString.split(";");

    while (i < splitString.length) {
      link.addAction(this.createAction(splitString[i], splitString[i + 1]));
      i = i + 2;
    }

    return link;
  }

  /**
   * This method is responsible for creating an Action object.
   *
   * @param actionType   The action type.
   * @param actionAmount The action amount.
   * @return The Action object
   */
  public Action createAction(String actionType, String actionAmount) {
    Action action;
    switch (actionType) {
      case "GoldAction" -> action = new GoldAction(Integer.parseInt(actionAmount));
      case "HealthAction" -> action = new HealthAction(Integer.parseInt(actionAmount));
      case "InventoryAction" -> action = new InventoryAction(actionAmount);
      case "ScoreAction" -> action = new ScoreAction(Integer.parseInt(actionAmount));
      default -> action = null;
    }

    return action;
  }

}
