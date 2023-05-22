package gruppe.fire.logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test class for the Story Class.
 * The following positive tests:
 * - testStoryConstructor
 * - setOpeningPassageTest
 * - addPassageTest
 * - getPassageByLinkTest
 * - getPassagesTest
 * <p>
 * The following negative tests:
 * - getBrokenLinksTest
 * - getBrokenPassageTest
 */
class StoryTest {

  @Test
  void testStoryConstructor() {
    String text1 = "Title";
    Passage passage = new Passage("Passage Title", "Passage Reference");

    Story story = new Story("Title", passage);

    assertEquals(text1, story.getTitle());
    assertTrue(story.getPassages().contains(passage));
  }

  @Test
  void setOpeningPassageTest() {
    Passage passage = new Passage("1", "1");
    Story story = new Story("Title", passage);

    Passage openingPassage = new Passage("2", "2");

    story.setOpeningPassage(openingPassage);
    assertEquals(story.getOpeningPassage(), openingPassage);
  }

  @Test
  void addPassageTest() {
    Passage passage = new Passage("1", "1");
    Story story = new Story("Title", passage);

    Passage passage2 = new Passage("2", "2");

    story.addPassage(passage2);
    assertTrue(story.getPassages().contains(passage2));
  }

  @Test
  void getPassageByLinkTest() {
    Passage passage = new Passage("1", "1");
    Link link = new Link("1", "1");
    passage.addLink(link);

    Story story = new Story("Title", passage);

    assertEquals(passage, story.getPassageByLink(link));
  }

  @Test
  void getPassagesTest() {
    Passage passage1 = new Passage("1", "1");
    Passage passage2 = new Passage("2", "2");
    Passage passage3 = new Passage("3", "3");
    Story story = new Story("Title", passage1);
    story.addPassage(passage2);
    story.addPassage(passage3);

    assertTrue(story.getPassages().contains(passage1)
        && story.getPassages().contains(passage2)
        && story.getPassages().contains(passage3));
  }

  @Test
  void getBrokenLinksTest() {
    Passage passage1 = new Passage("1", "1");
    Link link1 = new Link("1", "");
    passage1.addLink(link1);

    Passage passage2 = new Passage("1", "1");
    Link link2 = new Link("", "");
    passage2.addLink(link2);


    Story story = new Story("Title", passage1);
    story.addPassage(passage2);

    assertFalse(story.getBrokenLinks().contains(link1) && story.getBrokenLinks().contains(link2));
  }

  @Test
  void getBrokenPassageTest() {
    Passage passage1 = new Passage("", "1");
    Link link1 = new Link("1", "");
    passage1.addLink(link1);

    Passage passage2 = new Passage("1", "");
    Link link2 = new Link("", "");
    passage2.addLink(link2);


    Story story = new Story("Title", passage1);
    story.addPassage(passage2);

    assertFalse(story.getBrokenPassage().contains(passage1)
        && story.getBrokenPassage().contains(passage1));
  }
}
