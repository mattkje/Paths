package gruppe.fire.actions;

import gruppe.fire.logic.Player;

/**
 * This class is responsible for changing the player's score.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-21
 */
public class ScoreAction implements Action {
  private final int score;

  public ScoreAction(int score) {
    this.score = score;
  }

  public void execute(Player player) {
    player.addScore(score);
  }
}
