package gruppe.fire.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PassageTest {

  @Test
  void addLinkTest(){
    Passage passage = new Passage("Title", "Content");
    assertTrue(passage.addLink(new Link("text", "reference")));
  }

  @Test
  void getLinkTest(){
    Passage passage = new Passage("Title", "Content");
    Link link = new Link("text", "reference");
    passage.addLink(link);
    assertTrue(passage.getLinks().contains(link));
  }

  @Test
  void hasLinksTest(){
    Passage passage = new Passage("Title", "Content");
    Link link = new Link("text", "reference");
    passage.addLink(link);
    assertTrue(passage.hasLinks());
  }

  @Test
  void hasLinksNegativeTest(){
    Passage passage = new Passage("Title", "Content");
    assertFalse(passage.hasLinks());
  }

  @Test
  void equalsTest(){
    Passage passage = new Passage("Title", "Content");
    Passage passage2 = new Passage("Title", "Content");
    Link link = new Link("text", "reference");
    passage.addLink(link);
    passage2.addLink(link);
    assertEquals(passage, passage2);
  }

  @Test
  void equalsNegativeTest1(){
    Passage passage = new Passage("Title", "Content");
    Passage passage2 = new Passage("Title2", "Content");
    Link link = new Link("text", "reference");
    passage.addLink(link);
    passage2.addLink(link);
    assertNotEquals(passage, passage2);
  }

  @Test
  void equalsNegativeTest(){
    Passage passage = new Passage("Title", "Content");
    Passage passage2 = new Passage("Title", "Content2");
    Link link = new Link("text", "reference");
    passage.addLink(link);
    passage2.addLink(link);
    assertNotEquals(passage, passage2);
  }

  @Test
  void equalsNegativeTest3(){
    Passage passage = new Passage("Title", "Content");
    Passage passage2 = new Passage("Title", "Content");
    Link link = new Link("text", "reference");
    passage.addLink(link);
    assertNotEquals(passage, passage2);
  }
}