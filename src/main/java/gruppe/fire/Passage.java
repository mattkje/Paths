package gruppe.fire;

import java.util.ArrayList;
import java.util.Objects;

public class Passage {
    private String title;
    private String content;
    private ArrayList<Link> links;

    public Passage(String title, String content){
        this.title = title;
        this.content = content;
        this.links = new ArrayList<Link>();
    }

    /**
     * This method returns the title.
     * @return the title.
     */
    public String getTitle(){
        return title;
    }

    /**
     * This method returns the content.
     * @return returns the content.
     */
    public String getContent(){
        return content;
    }

    /**
     * This method checks if the link is available.
     * @param link
     * @return true or false dependent on the link availability.
     */
    public boolean addLink(Link link){

        return false; // correct latrrr
    }

    /**
     * This method returns all available links.
     * @return an ArrayList containing active links.
     */
    public ArrayList<Link> getLinks(){
        ArrayList<Link> activeLinks = new ArrayList<Link>();
        for (Link link : links) {
            if (link != null) {
                activeLinks.add(link);
            }
        }
        return activeLinks;
    }

    public boolean hasLinks(){
        return links != null;
    }
    public String toString(){
        return title + " " + content;
    }

    /**
     * Checks if the two objects are the same reference, in which case they are equal.
     * It then checks if the other object is an instance of the Passage class.
     * Finally, it compares the title, content, and links fields of the two
     * Passage objects using the Objects.equals() method.
     * @param obj
     * @return True if both fields are null, or if both fields are equal according to their equals() method
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Passage)) {
            return false;
        }
        Passage other = (Passage) obj;
        return Objects.equals(title, other.title)
                && Objects.equals(content, other.content)
                && Objects.equals(links, other.links);
    }
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + title.hashCode();
        hash = 31 * hash + content.hashCode();
        hash = 31 * hash + links.hashCode();
        return hash;
    }
}

