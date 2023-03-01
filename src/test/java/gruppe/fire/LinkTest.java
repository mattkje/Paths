package gruppe.fire;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {

    @Test
    void getText() {
        Link link = new Link("text", "reference");
        System.out.println(link.getText());
    }

    @Test
    void getReference() {
        Link link = new Link("text", "reference");
        System.out.println(link.getReference());
    }

    @Test
    void addAction() {
    }

    @Test
    void getActions() {
    }

    @Test
    void testToString() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
        System.out.println(hashCode());
    }
}