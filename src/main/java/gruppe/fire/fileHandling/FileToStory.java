package gruppe.fire.fileHandling;

import java.io.*;
import java.util.Scanner;

import gruppe.fire.logic.Passage;
import gruppe.fire.logic.Story;
import gruppe.fire.actions.*;
import gruppe.fire.logic.Link;

// TODO: Fix issue with whitespaces causing crashes
// TODO: Add actions availability to reading files - DONE
// TODO: add toLowerCase for actions and other features
// TODO: fix exception handling
// TODO: Fix crashes on typos etc

/**
 * Class responsible for handling all file imports and exports
 * An object represents one file and contains methods for handling it
 */
public class FileToStory {

  private File storyFile;

  /**
   * Creates an instance of storyfile.
   * Required File object path from user
   * @param storyFile
   */
  public FileToStory(File storyFile){
    this.storyFile = storyFile;
  }

  public File getStoryFile(){
    return this.storyFile;
  }

  public Story readFile(){
    Passage openingPassage = new Passage("Null","Null");
    Story story = new Story("Null",openingPassage);
    boolean openingPassageState = false;

    try (Scanner scanner = new Scanner(this.storyFile)){

      // Sets the first line as the title of the story
      story.setTitle(scanner.nextLine());
      String line = scanner.nextLine();
      while (scanner.hasNext()){
        if (line.contains("::")){
          String passageTitle = line.split("::")[1];
          String passageContent = scanner.nextLine();

          if(!openingPassageState){
            openingPassage.setTitle(passageTitle);
            openingPassage.setContent(passageContent);
            story.setOpeningPassage(openingPassage);
            openingPassageState = true;
            line = scanner.nextLine();
            while (line.startsWith("[")){

              openingPassage.addLink(createLink(line));
              if(scanner.hasNext()){
                line = scanner.nextLine();
              } else {
                line="";
              }
            }
          } else {
            Passage passage = new Passage(passageTitle,passageContent);
            line = scanner.nextLine();
            while (line.startsWith("[")){
              passage.addLink(createLink(line));
              if(scanner.hasNext()){
                line = scanner.nextLine();
              } else {
                line="";
              }
            }
            story.addPassage(passage);
          }
        } else {
          throw new IllegalArgumentException("Something went wrong");
        }
      }
    } catch (FileNotFoundException e){
      e.printStackTrace();
    }

    return story;
  }

  public Link createLink(String linkString){
    int i = 1;
    int tIA = linkString.indexOf("[") + 1;
    int tIB = linkString.indexOf("]");
    int rIA = linkString.indexOf("(") + 1;
    int rIB = linkString.indexOf(")");

    String title = linkString.substring(tIA,tIB);
    String reference = linkString.substring(rIA,rIB);
    Link link = new Link(title,reference);
    String[] splitString = linkString.split(";");

    while (i < splitString.length){
      link.addAction(this.createAction(splitString[i],splitString[i + 1]));

      i = i + 2;
    }
    return link;
  }

  private Action createAction(String actionType, String actionAmount) {
    Action action = null;
    switch (actionType) {
      case "GoldAction" -> action = new GoldAction(Integer.parseInt(actionAmount));
      case "HealthAction" -> action = new HealthAction(Integer.parseInt(actionAmount));
      case "InventoryAction" -> action = new InventoryAction(actionAmount);
      case "ScoreAction" -> action = new ScoreAction(Integer.parseInt(actionAmount));
    }

    return action;
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
}
