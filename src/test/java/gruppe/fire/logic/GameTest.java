package gruppe.fire.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import gruppe.fire.goals.Goal;
import gruppe.fire.goals.GoldGoal;
import gruppe.fire.goals.HealthGoal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Test class for the Game Class.
 * The following positive tests:
 * - testGameConstructor
 * - setGoalsListTest
 * - goTest
 * <p>
 * No negative tests.
 */
class GameTest {


  @Test
  void testGameConstructor() {
    Story story = new Story("1", new Passage("1", "1"));
    Player player = new Player.PlayerBuilder()
        .build();
    Game game = new Game(player, story);
    assertTrue(game.getStory().equals(story) && game.getPlayer().equals(player));
  }

  @Test
  void setGoalsListTest() {
    Story story = new Story("1", new Passage("1", "1"));
    Player player = new Player.PlayerBuilder()
        .build();
    Game game = new Game(player, story);
    Goal goal1 = new GoldGoal(1);
    Goal goal2 = new HealthGoal(1);
    List<Goal> goalList = new ArrayList<>();
    goalList.add(goal1);
    goalList.add(goal2);
    game.setGoalsList(goalList);
    assertTrue(game.getGoals().contains(goal1) && game.getGoals().contains(goal2));
  }

  @Test
  void goTest() {
    Passage passage = new Passage("Reference", "Content");
    Link link = new Link("Text", "Reference");
    passage.addLink(link);
    Story story = new Story("1", passage);
    Player player = new Player.PlayerBuilder()
        .build();
    Game game = new Game(player, story);
    assertEquals(passage, game.go(link));
  }
}
