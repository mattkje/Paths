package gruppe.fire.logic;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * The Player class represents a player with various properties that can be influenced in a
 * story.
 */
public class Player {
    private String name;
    private Image image;
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
    public Player(String name, Image image, int health, int score, int gold){
        this.name = name;
        this.image = image;
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

    public void setName(String name){
        this.name = name;
    }

    public Image getImage(){
        return image;
    }

    public void setImage(Image image){
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
     * @param amount The amount to add to the player's health.
     * @return The player's new health value.
     * @throws IllegalArgumentException If the resulting health value is less than 0.
     */
    public int addHealth(int amount) {
        health += amount;
        return health;
    }

    public boolean checkIfAlive() {
        if(health >= 0) {
            return true;
        }else {
            return false;
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
        return score;
    }

    public boolean checkScore() {
        if(score >= 0) {
            return true;
        }else {
            return false;
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
        return gold;
    }

    public boolean checkIfAfford() {
        if(gold >= 0) {
            return true;
        }else {
            return false;
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
