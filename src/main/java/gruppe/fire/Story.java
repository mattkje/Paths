package gruppe.fire;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

/**
 * The Story class represents a story composed of Passage objects.
 */
public class Story {
    private String title;
    private HashMap<Link, Passage> passages;
    private Passage openingPassage;

    /**
     * Creates an instance of story.
     *
     * @param title The title of the story
     * @param openingPassage The opening passage of the story.
     * @param passages A Map containing the passage of the story.
     */
    public Story(String title, Passage openingPassage, HashMap<Link, Passage> passages) {
        if (title == null) {
            throw new IllegalArgumentException("The title can not be empty");
        }
        if (openingPassage == null) {
            throw new IllegalArgumentException("The openingPassage can not be empty");
        }
        if (passages == null) {
            throw new IllegalArgumentException("The passages can not be empty");
        }

        this.title = title;
        this.openingPassage = openingPassage;
        this.passages = new HashMap<Link, Passage>();
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
    public void addPassage(Passage passage){
        Link link = new Link(passage.getTitle(), passage.getTitle());
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
}