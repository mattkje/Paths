package gruppe.fire.goals;
import gruppe.fire.goals.HealthGoal;
import gruppe.fire.logic.Player;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HealthGoalTest {

    @Test
    void testIsFulfilled() {
        HealthGoal goal = new HealthGoal(50);
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(100)
            .gold(100)
            .score(100)
            .build();
        assertTrue(goal.isFulfilled(player));
    }

    @Test
    void testIsFulfilled2() {
        HealthGoal goal = new HealthGoal(25);
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(50)
            .gold(50)
            .score(50)
            .build();
        assertTrue(goal.isFulfilled(player));

    }

    @Test
    void testRemainingHealth() {
        HealthGoal goal = new HealthGoal(100);
        Player player = new Player.PlayerBuilder()
            .name("Mathias")
            .health(50)
            .gold(100)
            .score(100)
            .build();
        assertEquals(50, goal.remainingHealth(player));
    }
}
