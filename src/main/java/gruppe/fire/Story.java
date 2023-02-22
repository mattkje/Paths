package gruppe.fire;

import java.util.Collection;
import java.util.Map;

public class Story {
    private String title;
    private Map<Link, Passage> passages;
    private Passage openingPassage;

    /**
     * Creates an instance of story.
     *
     * @param title          The title of the story
     * @param openingPassage The opening passage of the story.
     */
    public Story(String title, Passage openingPassage) {
        this.title = title;
        this.openingPassage = openingPassage;
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

    public void addPassage(Passage passage) {

    }


    public Passage getPassage(Link link) {
        return openingPassage;
    }

    public Collection<Passage> getPassages() {

        return null;
    }
}