package gruppe.fire.logic;

import java.util.ArrayList;

/**
 * The Player class represents a player with various properties that can be influenced in a
 * story.
 */
public class Player {
    private String name;
    private int health;
    private int score;
    private int gold;
    private ArrayList<String> inventory;
    /**
     * Creates an instance of Player.
     * @param name The player name.
     * @param health The player health.
     * @param score The player score.
     * @param gold Amount of gold the player has.
     */
    public Player(String name, int health, int score, int gold){
        this.name = name;
        this.health = health;
        this.score = score;
        this.gold = gold;
        this.inventory = new ArrayList<>();
    }

    /**
     * Returns the player's name.
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds the specified amount to the player's health and returns the new health value.
     * @param amount The amount to add to the player's health.
     * @return The player's new health value.
     * @throws IllegalArgumentException If the resulting health value is less than 0.
     */
    public int addHealth(int amount) {
        health += amount;
        if(health >= 0) {
            return health;
        }else {
            throw new IllegalArgumentException("Health can not be less than 0");
        }
    }

    /**
     * Returns the player's current health value.
     * @return The player's health value.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Adds the specified amount to the player's score and returns the new score value.
     * @param amount The amount to add to the player's score.
     * @return The player's new score value.
     * @throws IllegalArgumentException If the resulting score value is less than 0.
     */
    public int addScore(int amount) {
        score += amount;
        if(score >= 0) {
            return score;
        }else {
            throw new IllegalArgumentException("Score can not be less than 0");
        }
    }

    /**
     * Returns the player's current score value.
     * @return The player's score value.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the player's current gold value.
     * @return The player's gold value.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Adds the specified amount to the player's gold and returns the new gold value.
     * @param amount The amount to add to the player's gold.
     * @return The player's new gold value.
     * @throws IllegalArgumentException If the resulting gold value is less than 0.
     */
    public int addGold(int amount) {
        gold += amount;
        if(gold >= 0) {
            return gold;
        }else {
            throw new IllegalArgumentException("Gold can not be less than 0");
        }
    }

    /**
     * Adds an item to the player's inventory.
     * @param item the item to add to the inventory.
     */
    public void addToInventory(String item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (inventory.contains(item)) {
            throw new IllegalArgumentException("Item already exists in inventory");
        }
        inventory.add(item);
    }

    /**
     * Returns a list of items in the player's inventory.
     * @return a list of items in the player's inventory
     */
    public ArrayList<String> getInventory() {
        return inventory;
    }
}
