package org.example;

import javax.swing.*;
import java.util.ArrayList;

public class Link {
    private String text;
    private String reference;
    private ArrayList<Action> actions;

    public void Link(String text, String reference) {

    }

    public String getText() {
        return text;
    }

    public String getReference() {
        return reference;
    }

    public void addAction(Action action) {

    }

    public void getActions(ArrayList<Action> action) {

    }

    public String toString() {
        return text + " " + reference;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}