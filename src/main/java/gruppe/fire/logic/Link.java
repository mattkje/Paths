package gruppe.fire.logic;

import gruppe.fire.actions.Action;

import java.util.ArrayList;

/**
 * this class makes it possible to go from one passage to another. Links bind together
 * different parts of a story.
 */
public class Link {
    private String text;
    private String reference;
    private ArrayList<Action> actions;

    /**
     * Creates an instance of Link.
     * @param text a descriptive text that indicates a choice or action in a story.
     * @param reference a string that uniquely identifies a passage.
     */
    public Link(String text, String reference, ArrayList<Action> actions) {

        this.text = text;
        this.reference = reference;
        this.actions = actions;
    }

    /**
     * This method returns the text.
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * This method returns the reference.
     * @return the reference.
     */
    public String getReference() {
        return reference;
    }

    /**
     * Adds the specified action to this object's list of actions.
     * @param actions the action to be added to the list.
     */
    public void addAction(Action actions) {
        this.actions = new ArrayList<Action>();
    }

    /**
     * This method returns a list of actions.
     * @return The list of actions.
     */
    public ArrayList<Action> getActions() {
        return actions;
    }

    /**
     * Returns a sensible textual representation of the link for use in debug.
     * @return A link in a textual form.
     */
    public String toString() {
        return text + " " + reference;
    }

    /**
     * Compares this object with the specified object for equality.
     * @param object The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Link)) {
            return false;
        }
        Link otherLink = (Link) object;
        return this.text.equals(otherLink.text) &&
                this.reference.equals(otherLink.reference) &&
                this.actions.equals(otherLink.actions);
    }

    /**
     * Computes the hash code for this object based on the values of its fields.
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