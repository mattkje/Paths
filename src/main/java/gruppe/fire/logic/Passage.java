package gruppe.fire.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a smaller part of a story. The class makes it possible to leave
 * one passage to another through a link.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-21
 */
public class Passage {
  private String title;
  private String content;
  private final ArrayList<Link> links;

  /**
   * Constructs a new Passage object with the specified title, content, and links.
   *
   * @param title   an overall description that also serves as an identifier.
   * @param content A textual content that typically represents a paragraph or part of one
   *                dialogue.
   */
  public Passage(String title, String content) {

    this.title = title;
    this.content = content;
    this.links = new ArrayList<>();
  }

  /**
   * Returns the title.
   *
   * @return the title.
   */
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Returns the content.
   *
   * @return returns the content.
   */
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  /**
   * This method checks if the link is available and adds it to the link list if it is.
   *
   * @param link The Link object to be added.
   * @return true if the link is added successfully, false otherwise.
   */
  public boolean addLink(Link link) {
    if (link == null || links.contains(link)) {
      return false;
    } else {
      links.add(link);
      return true;
    }
  }

  /**
   * Returns all available links in a new list: activeLink.
   *
   * @return an ArrayList containing active links.
   */
  public List<Link> getLinks() {
    List<Link> activeLinks = new ArrayList<>();
    for (Link link : links) {
      if (link != null) {
        activeLinks.add(link);
      }
    }
    return activeLinks;
  }

  /**
   * Determines whether the Passage object has any links.
   *
   * @return true if there are any links associated with the passage, false otherwise.
   */
  public boolean hasLinks() {
    return !this.links.isEmpty();
  }

  /**
   * Check if a passage has any links with empty text.
   *
   * @return True if passage has empty link text.
   */
  public boolean emptyLinks() {
    boolean brokenLinks = false;
    for (int i = 0; i < this.links.size(); i++) {
      brokenLinks = this.links.get(i).getText().isEmpty();
    }
    return brokenLinks;
  }

  /**
   * Returns a String representation of the Passage object, which consists of the title
   * and content fields concatenated together, separated by a space.
   *
   * @return a String representation of the Passage object.
   */
  public String toString() {
    return title + " " + content;
  }

  /**
   * Checks if the two objects are the same reference, in which case they are equal.
   * It then checks if the other object is an instance of the Passage class.
   * Finally, it compares the title, content, and links fields of the two
   * Passage objects using the Objects.equals() method.
   *
   * @param obj The object to compare to.
   * @return True if both fields are null or equal, false otherwise.
   */
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Passage other)) {
      return false;
    }
    return Objects.equals(title, other.title)
        && Objects.equals(content, other.content)
        && Objects.equals(links, other.links);
  }

  /**
   * Computes the hash code for this object based on the values of its fields.
   *
   * @return the hash code value for this object.
   */
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + title.hashCode();
    hash = 31 * hash + content.hashCode();
    hash = 31 * hash + links.hashCode();
    return hash;
  }
}

