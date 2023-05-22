package gruppe.fire.goals;

import gruppe.fire.logic.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for the ScoreGoal Class.
 * The following positive tests:
 * - testIsFulfilled
 * <p>
 * The following negative tests:
 * - testIsNotFulFilled
 * - testRemainingPoints
 */
class ScoreGoalTest {
  @Test
  void testIsFulfilled() {
    Player player = new Player.PlayerBuilder()
        .score(5)
        .build();
    ScoreGoal goal = new ScoreGoal(0);
    Assertions.assertTrue(goal.isFulfilled(player));
  }

  @Test
  void testIsNotFulFilled() {
    Player player = new Player.PlayerBuilder()
        .score(100)
        .build();
    ScoreGoal goal = new ScoreGoal(0);
    Assertions.assertTrue(goal.isFulfilled(player));
  }

  @Test
  void testRemainingPoints() {
    Player player = new Player.PlayerBuilder()
        .score(50)
        .build();
    ScoreGoal goal = new ScoreGoal(100);
    Assertions.assertEquals(50, goal.remainingPoints(player));
  }
}
