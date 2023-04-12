package gruppe.fire;

import gruppe.fire.logic.Link;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest {

    @Test
    void testLinkConstructor() {
        String text = "Go to the park";
        String reference = "park";
        Link link = new Link(text, reference);

        assertEquals(text, link.getText());
        assertEquals(reference, link.getReference());
    }

    @Test
    void testLinkConstructorWithNullText() {
        assertThrows(IllegalArgumentException.class, () -> new Link(null, "reference"));
    }

    @Test
    void testLinkConstructorWithNullReference() {
        assertThrows(IllegalArgumentException.class, () -> new Link("text", null));
    }

    @Test
    void testEquals() {
        String text = "text";
        String reference = "reference";
        Link link1 = new Link(text, reference);
        Link link2 = new Link(text, reference);

        if(link1.equals(link2)){
            System.out.println("Success");
        } else {
            System.out.println("Wrong");
        }
    }

    @Test
    void testHashCode() {
        String text = "Go to the park";
        String reference = "park";
        Link link = new Link(text, reference);

        int expectedHashCode = 31 * (31 * text.hashCode() + reference.hashCode());
        assertEquals(expectedHashCode, link.hashCode());
    }
}
