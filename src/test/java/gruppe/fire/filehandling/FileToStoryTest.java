package gruppe.fire.filehandling;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gruppe.fire.actions.Action;
import gruppe.fire.logic.Link;
import gruppe.fire.logic.Player;
import gruppe.fire.logic.Story;
import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for the FileToStory Class.
 * The following positive tests:
 * - readFileNoLinksTest
 * - readFileMultipleActionsTest
 * - createLinkTest
 * - createActionText
 * <p>
 * The following negative tests:
 * - readFileEmptyTest
 */
class FileToStoryTest {

  @Test
  void readFileEmptyTest() {
    String path = "src/test/resources/testPaths/emptyFile.paths";
    FileToStory fileToStory = new FileToStory(new File(path));
    assertThrows(NoSuchElementException.class, fileToStory::readFile);
  }

  @Test
  void readFileNoLinksTest() {
    String path = "src/test/resources/testPaths/noLinks.paths";
    FileToStory fileToStory = new FileToStory(new File(path));
    Story story = fileToStory.readFile();
    story.getOpeningPassage().getLinks();
    Assertions.assertEquals(new ArrayList<>(), story.getOpeningPassage().getLinks());
  }

  @Test
  void readFileMultipleActionsTest() {
    String path = "src/test/resources/testPaths/multipleActions.paths";
    FileToStory fileToStory = new FileToStory(new File(path));
    Story story = fileToStory.readFile();
    Assertions.assertTrue(1 < story.getOpeningPassage().getLinks().get(0).getActions().size());
  }

  @Test
  void createLinkTest() {
    String path = "src/test/resources/testPaths/workingFile.paths";
    FileToStory fileToStory = new FileToStory(new File(path));
    String linkString = "[link1](Room2)";
    Link link = fileToStory.createLink(linkString);
    Assertions.assertTrue(link.getText().equals("link1") && link.getReference().equals("Room2"));
  }

  @Test
  void createActionText() {
    String path = "src/test/resources/testPaths/workingFile.paths";
    FileToStory fileToStory = new FileToStory(new File(path));
    Action action = fileToStory.createAction("GoldAction", "100");
    Player player = new Player.PlayerBuilder()
        .name("Mathias")
        .gold(100)
        .build();
    action.execute(player);
    assertEquals(200, player.getGold());
  }
}
