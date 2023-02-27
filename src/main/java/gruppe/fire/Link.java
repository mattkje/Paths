package gruppe.fire;

import javax.swing.*;
import java.util.ArrayList;

public class Link {
    private String text;
    private String reference;
    private ArrayList<Action> actions;

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

    public void addAction(Action actions) {
        this.actions = new ArrayList<Action>();
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public String toString() {
        return text + " " + reference;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + text.hashCode();
        hash = 31 * hash + reference.hashCode();
        hash = 31 * hash + actions.hashCode();
        return hash;
    }
}