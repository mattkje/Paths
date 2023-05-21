package gruppe.fire.logic;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 * The Player class represents a player with various properties that can be influenced in a story.
 *
 * @author Matti Kjellstadli
 * @version 2023-05-21
 */
public class Player {
  private String name;
  private Image image;
  private int health;
  private int score;
  private int gold;
  private final ArrayList<String> inventory;

  /**
   * Creates an instance of Player.
   *
   * @param name   The player name.
   * @param health The player health.
   * @param score  The player score.
   * @param gold   Amount of gold the player has.
   */
  private Player(String name, Image image, int health, int score, int gold) {
    this.name = name;
    this.image = image;
    this.health = health;
    this.score = score;
    this.gold = gold;
    this.inventory = new ArrayList<>();
  }

  /**
   * Returns the player's name.
   *
   * @return The player's name.
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setGold(int gold) {
    this.gold = gold;
  }

  /**
   * Adds the specified amount to the player's health and returns the new health value.
   *
   * @param amount The amount to add to the player's health.
   * @return The player's new health value.
   * @throws IllegalArgumentException If the resulting health value is less than 0.
   */
  public int addHealth(int amount) {
    health += amount;
    return health;
  }

  public boolean checkIfAlive() {
    return health >= 0;
  }

  /**
   * Returns the player's current health value.
   *
   * @return The player's health value.
   */
  public int getHealth() {
    return health;
  }

  /**
   * Adds the specified amount to the player's score and returns the new score value.
   *
   * @param amount The amount to add to the player's score.
   */
  public void addScore(int amount) {
    score += amount;
  }

  public boolean checkScore() {
    return score >= 0;
  }

  /**
   * Returns the player's current score value.
   *
   * @return The player's score value.
   */
  public int getScore() {
    return score;
  }

  /**
   * Returns the player's current gold value.
   *
   * @return The player's gold value.
   */
  public int getGold() {
    return gold;
  }

  /**
   * Adds the specified amount to the player's gold and returns the new gold value.
   *
   * @param amount The amount to add to the player's gold.
   * @return The player's new gold value.
   * @throws IllegalArgumentException If the resulting gold value is less than 0.
   */
  public int addGold(int amount) {
    gold += amount;
    return gold;
  }

  public boolean checkIfAfford() {
    return gold >= 0;
  }

  /**
   * Adds an item to the player's inventory.
   *
   * @param item the item to add to the inventory.
   */
  public void addToInventory(String item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    }
    if (inventory.contains(item)) {
      return;
    }
    inventory.add(item);
  }

  /**
   * Returns a list of items in the player's inventory.
   *
   * @return a list of items in the player's inventory
   */
  public List<String> getInventory() {
    return inventory;
  }

  /**
   * Uses the Builder design pattern on the Player class, so that it is possible to create different
   * representations of players with a dedicated builder.
   */
  public static class PlayerBuilder {
    private String name;
    private Image image;
    private int health;
    private int score;
    private int gold;

    /**
     * Creates a new instance of the PlayerBuilder class.
     */
    public PlayerBuilder() {
      this.name = "";
      this.image = null;
      this.health = 0;
      this.score = 0;
      this.gold = 0;
    }

    /**
     * Sets the name of the player.
     *
     * @param name Player name.
     * @return The player name.
     */
    public PlayerBuilder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the image of the player.
     *
     * @param image Player image.
     * @return The player image.
     */
    public PlayerBuilder image(Image image) {
      this.image = image;
      return this;
    }

    /**
     * Sets the health of the player.
     *
     * @param health Player health.
     * @return The player health.
     */
    public PlayerBuilder health(int health) {
      if (health > 0) {
        this.health = health;
      }
      return this;
    }

    /**
     * Sets the score of the player.
     *
     * @param score Player score.
     * @return The player score.
     */
    public PlayerBuilder score(int score) {
      if (score > 0) {
        this.score = score;
      }
      return this;
    }

    /**
     * Sets the gold of the player.
     *
     * @param gold Player gold.
     * @return The player gold.
     */
    public PlayerBuilder gold(int gold) {
      if (gold > 0) {
        this.gold = gold;
      }
      return this;
    }

    /**
     * Builds the player.
     *
     * @return The player.
     */
    public Player build() {
      return new Player(name, image, health, score, gold);
    }

  }
}
