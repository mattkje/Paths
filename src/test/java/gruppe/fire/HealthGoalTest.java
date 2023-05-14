package gruppe.fire;
import gruppe.fire.goals.HealthGoal;
import gruppe.fire.logic.Player;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HealthGoalTest {

    @Test
    void testIsFulfilled() {
        HealthGoal goal = new HealthGoal(50);
        Player player = new Player("Mathias", null,100, 100, 100);
        assertTrue(goal.isFulfilled(player));
    }

    @Test
    void testIsFulfilled2() {
        HealthGoal goal = new HealthGoal(25);
        Player player = new Player("Mathias", null,50, 50, 50);
        assertTrue(goal.isFulfilled(player));

    }

    @Test
    void testRemainingHealth() {
        HealthGoal goal = new HealthGoal(100);
        Player player = new Player("Mathias", null,50,100,100);
        assertEquals(50, goal.remainingHealth(player));
    }
}
