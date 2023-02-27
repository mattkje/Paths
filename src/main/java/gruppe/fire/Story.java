package gruppe.fire;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Story {
    private String title;
    private HashMap<Link, Passage> passages;
    private Passage openingPassage;

    /**
     * Creates an instance of story.
     *
     * @param title          The title of the story
     * @param openingPassage The opening passage of the story.
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    public Passage getOpeningPassage() {
        return openingPassage;
    }

    public void addPassage(Passage passage){
        Link link = new Link(passage.getTitle(), passage.getTitle());
        passages.put(link, passage);
    }


    public Passage getPassage(Link link) {
        return openingPassage;
    }

    public Collection<Passage> getPassages() {
        return Collections.list(Collections.enumeration(passages.values()));
    }
}