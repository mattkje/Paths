package gruppe.fire.logic;


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * The Story class represents a story composed of Passage objects.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-21
 */
public class Story {
  private String title;
  private final HashMap<Link, Passage> passages;
  private Passage openingPassage;

  /**
   * Creates an instance of story.
   */
  public Story(String title, Passage openingPassage) {

    this.title = title;
    this.passages = new HashMap<>();
    this.openingPassage = openingPassage;
    this.addPassage(openingPassage);
  }


  /**
   * Returns the title of the story.
   *
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Returns the openingPassage of the story.
   *
   * @return the openingPassage
   */
  public Passage getOpeningPassage() {
    return openingPassage;
  }

  public void setOpeningPassage(Passage openingPassage) {
    this.openingPassage = openingPassage;
  }

  /**
   * Adds a new passage to the story.
   *
   * @param passage The passage to add to the story.
   */
  public void addPassage(Passage passage) {
    if (passage == null) {
      throw new IllegalArgumentException("Passage can not be null");
    }
    Link link = new Link(passage.getTitle(), passage.getTitle());
    this.passages.put(link, passage);
  }

  /**
   * Returns the openingPassage of the story.
   *
   * @param link The link of the passage to retrieve.
   * @return The passage with the specified link, or null if it does not exist.
   */
  public Passage getPassageByLink(Link link) {
    link = new Link(link.getReference(), link.getReference());
    return passages.get(link);
  }


  /**
   * Returns a collection of all the passages in the story.
   *
   * @return A collection of all the passages in the story.
   */
  public Collection<Passage> getPassages() {
    return Collections.list(Collections.enumeration(passages.values()));
  }


  /**
   * Finds and returns a list of dead links. A link is dead if it refers to a passage
   * not found in passages.
   *
   * @return A list of dead links.
   */
  public List<Link> getBrokenLinks() {
    return passages.values()
        .stream()
        .flatMap(passage -> passage.getLinks()
            .stream())
        .filter(link -> this.getPassageByLink(link) == null)
        .toList();
  }

  /**
   * Finds and returns a list of dead passages. A passage is dead if it has no links.
   *
   * @return A list of dead passages.
   */
  public List<Passage> getBrokenPassage() {
    return passages.values()
        .stream()
        .filter(passage -> !passage.hasLinks())
        .toList();
  }

}