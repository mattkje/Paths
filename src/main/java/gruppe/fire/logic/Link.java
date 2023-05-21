package gruppe.fire.logic;

import gruppe.fire.actions.Action;
import java.util.ArrayList;
import java.util.List;

/**
 * This class makes it possible to go from one passage to another. Links bind together
 * different parts of a story.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-21
 */
public class Link {
  private final String text;
  private final String reference;
  private final ArrayList<Action> actions;

  /**
   * Creates an instance of Link.
   *
   * @param text      a descriptive text that indicates a choice or action in a story.
   * @param reference a string that uniquely identifies a passage.
   */
  public Link(String text, String reference) {
    if (reference == null) {
      throw new IllegalArgumentException("Reference can not be null");
    }
    this.text = text;
    this.reference = reference;
    this.actions = new ArrayList<>();
  }

  /**
   * Returns the text.
   *
   * @return The text.
   */
  public String getText() {
    return text;
  }

  /**
   * Returns the reference.
   *
   * @return reference a string that uniquely identifies a passage.
   */
  public String getReference() {
    return reference;
  }

  /**
   * Adds the specified action to this object's list of actions.
   *
   * @param action the action to be added to the list.
   */
  public void addAction(Action action) {
    actions.add(action);
  }

  /**
   * Returns a list of actions.
   *
   * @return List The list of actions.
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * Returns a sensible textual representation of the link for use in debug.
   *
   * @return A link in a textual form.
   */
  public String toString() {
    return text + " " + reference;
  }

  /**
   * Compares this object with the specified object for equality.
   *
   * @param object The object to compare to.
   * @return True if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Link otherLink)) {
      return false;
    }
    return this.text.equals(otherLink.text)
        && this.reference.equals(otherLink.reference)
        && this.actions.equals(otherLink.actions);
  }

  /**
   * Computes the hash code for this object based on the values of its fields.
   *
   * @return the hash code value for this object.
   */
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + text.hashCode();
    hash = 31 * hash + reference.hashCode();
    hash = 31 * hash + actions.hashCode();
    return hash;
  }
}