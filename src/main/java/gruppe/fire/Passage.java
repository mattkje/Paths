package gruppe.fire;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class represents a smaller part of a story. The class makes it possible to leave
 * one passage to another through a link.
 */
public class Passage {
    private String title;
    private String content;
    private ArrayList<Link> links;

    /**
     * Constructs a new Passage object with the specified title, content, and links.
     * @param title an overall description that also serves as an identifier.
     * @param title an overall description that also serves as an identifier.
     * @param content A textual content that typically represents a paragraph or part of one
     * dialogue.
     * @param links Links that connect this passage to other passages.
     */
    public Passage(String title, String content, ArrayList<Link> links){
        if (title == null) {
            throw new IllegalArgumentException("The title can not be empty");
        }
        if (content == null) {
            throw new IllegalArgumentException("The content can not be empty");
        }
        if (links == null) {
            throw new IllegalArgumentException("The links can not be empty");
        }

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

    /**
     * Determines whether the Passage object has any links.
     * @return true if there are any links associated with the passage, false otherwise.
     */
    public boolean hasLinks(Link links){
        return links != null;
    }

    /**
     * Returns a String representation of the Passage object, which consists of the title
     * and content fields concatenated together, separated by a space.
     * @return a String representation of the Passage object.
     */
    public String toString(){
        return title + " " + content;
    }

    /**
     * Checks if the two objects are the same reference, in which case they are equal.
     * It then checks if the other object is an instance of the Passage class.
     * Finally, it compares the title, content, and links fields of the two
     * Passage objects using the Objects.equals() method.
     * @param obj The object to compare to.
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
    /**
     * Computes the hash code for this object based on the values of its fields.
     * @return the hash code value for this object.
     */
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + title.hashCode();
        hash = 31 * hash + content.hashCode();
        hash = 31 * hash + links.hashCode();
        return hash;
    }
}

