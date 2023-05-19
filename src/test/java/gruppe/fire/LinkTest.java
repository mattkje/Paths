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
}
