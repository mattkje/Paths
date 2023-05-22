package gruppe.fire.logic;

import gruppe.fire.actions.GoldAction;
import gruppe.fire.logic.Link;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Link Class.
 * The following positive tests:
 * - testLinkConstructor
 * - addActionTest
 * - equalsTest
 * <p>
 * The following negative tests:
 * - testLinkConstructorWithNullReference
 * - testLinkConstructorWithNullText
 * - addActionNegativeTest
 * - equalsNegativeTest1
 * - equalsNegativeTest2
 */
class LinkTest {

  @Test
  void testLinkConstructor() {
    String text = "Go to the park";
    String reference = "park";
    Link link = new Link(text, reference);

    assertEquals(text, link.getText());
    assertEquals(reference, link.getReference());
  }

  @Test
  void testLinkConstructorWithNullText() {
    Link link = new Link(null, "reference");
    assertNull(link.getText());
  }

  @Test
  void testLinkConstructorWithNullReference() {
    assertThrows(IllegalArgumentException.class, () -> new Link("title", null));
  }

  @Test
  void addActionTest() {
    GoldAction goldAction = new GoldAction(100);
    Link link = new Link("text", "reference");
    link.addAction(goldAction);
    assertTrue(link.getActions().contains(goldAction));
  }

  @Test
  void addActionNegativeTest() {
    GoldAction goldAction = new GoldAction(100);
    Link link = new Link("text", "reference");
    assertFalse(link.getActions().contains(goldAction));
  }

  @Test
  void equalsTest() {
    Link link = new Link("text", "reference");
    Link link2 = new Link("text", "reference");
    assertEquals(link, link2);
  }

  @Test
  void equalsNegativeTest1() {
    Link link = new Link("text", "reference");
    Link link2 = new Link("text2", "reference");
    assertNotEquals(link, link2);
  }

  @Test
  void equalsNegativeTest2() {
    Link link = new Link("text", "reference");
    Link link2 = new Link("text", "reference2");
    assertNotEquals(link, link2);
  }
}
