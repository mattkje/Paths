package gruppe.fire.goals;

import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the HealthGoal Class.
 * The following positive tests:
 * - testIsFulfilled
 * - testRemainingHealth
 * <p>
 * The following negative tests:
 * - testIsNotFulfilled
 */
class HealthGoalTest {

  @Test
  void testIsFulfilled() {
    HealthGoal goal = new HealthGoal(5);
    Player player = new Player.PlayerBuilder()
        .health(10)
        .build();
    assertTrue(goal.isFulfilled(player));
  }

  @Test
  void testIsNotFulfilled() {
    HealthGoal goal = new HealthGoal(5);
    Player player = new Player.PlayerBuilder()
        .health(2)
        .build();
    assertFalse(goal.isFulfilled(player));
  }

  @Test
  void testRemainingHealth() {
    HealthGoal goal = new HealthGoal(100);
    Player player = new Player.PlayerBuilder()
        .health(50)
        .build();
    assertEquals(50, goal.remainingHealth(player));
  }
}
