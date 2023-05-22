package gruppe.fire.goals;

import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the GoldGoal Class.
 * The following positive tests:
 * - testIsFulfilled
 * <p>
 * The following negative tests:
 * - testIsNotFulfilled
 */
class GoldGoalTest {

  @Test
  void testIsFulfilled() {
    GoldGoal goal = new GoldGoal(1000);
    Player player = new Player.PlayerBuilder()
        .gold(3000)
        .build();
    assertTrue(goal.isFulfilled(player));
  }

  @Test
  void testIsNotFulfilled() {
    GoldGoal goal = new GoldGoal(1000);
    Player player = new Player.PlayerBuilder()
        .gold(300)
        .build();
    assertFalse(goal.isFulfilled(player));
  }


}


