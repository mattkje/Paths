package gruppe.fire.logic;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * The Story class represents a story composed of Passage objects.
 */
public class Story {
    private String title;
    private static HashMap<Link, Passage> passages;
    private Passage openingPassage;

    /**
     * Creates an instance of story.
     */
    public Story(String title, Passage openingPassage, HashMap<Link, Passage> passages) {

        this.title = title;
        this.openingPassage = openingPassage;
        this.passages = passages;
    }


    /**
     * Returns the title of the story.
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the openingPassage of the story.
     * @return the openingPassage
     */
    public Passage getOpeningPassage() {
        return openingPassage;
    }

    /**
     * Adds a new passage to the story.
     * @param passage The passage to add to the story.
     */
    public static void addPassage(Passage passage){
        Link link = new Link(passage.getTitle(), passage.getTitle(), new ArrayList<>());
        passages.put(link, passage);
    }

    /**
     * Returns the openingPassage of the story.
     * @param link The link of the passage to retrieve.
     * @return The passage with the specified link, or null if it does not exist.
     */
    public Passage getPassage(Link link) {
        return passages.get(link);
    }

    /**
     * Returns a collection of all the passages in the story.
     * @return A collection of all the passages in the story.
     */
    public Collection<Passage> getPassages() {
        return Collections.list(Collections.enumeration(passages.values()));
    }
    /**

     Removes a given passage from passages. It shouldn't be possible to remove the passage
     if there are other passages linking to it.
     @param link The link of the passage to remove.
     */
    public void removePassage(Link link){
        boolean isUsed = false;
        for (Passage passage : passages.values()) {
            if (passage.hasLinks(link)) {
                isUsed = true;
                break;
            }
        }
        if (!isUsed) {
            passages.remove(link);
        }
    }

    /**

     Finds and returns a list of dead links. A link is dead if it refers to a passage
     not found in passages.
     @return A list of dead links.
     */
    public ArrayList<Link> getBrokenLinks(){
        ArrayList<Link> brokenLinks = new ArrayList<Link>();
        for (Passage passage : passages.values()) {
            for (Link link : passage.getLinks()) {
                if (!passages.containsKey(link)) {
                    brokenLinks.add(link);
                }
            }
        }
        return brokenLinks;
    }

}