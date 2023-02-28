package gruppe.fire;

import javax.swing.*;
import java.util.ArrayList;

public class Link {
    private String text;
    private String reference;
    private ArrayList<Action> actions;

    /**
     *
     * @param text a descriptive text that indicates a choice or action in a story.
     * @param reference a string that uniquely identifies a passage.
     */
    public Link(String text, String reference) {
        if (text == null) {
            throw new IllegalArgumentException("The text can not be empty");
        }
        if (reference == null) {
            throw new IllegalArgumentException("The reference can not be empty");
        }
        this.text = text;
        this.reference = reference;
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
        if (object == null){
            return true;
        }else {
            return false;
        }
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