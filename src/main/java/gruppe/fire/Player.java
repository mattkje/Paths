package gruppe.fire;

import java.util.ArrayList;

public class Player {
    private String name;
    private int health;
    private int score;
    private int gold;
    private ArrayList<String> inventory;

    public void Player(String name, int health, int gold){
        if (health <= 0) {
            throw new IllegalArgumentException("YOU DIED");
            }
            this.health = health;
        }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public int addHealth() {

    }

    /**
     *
     * @return
     */
    public int getHealth() {
        return health;
    }

    /**
     *
     * @return
     */
    public int addScore(int score) {
        return this.score;
    }

    /**
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    public int getGold() {
        return gold;
    }

    /**
     *
     * @return
     */
    public int addGold(int gold) {
        return this.gold;
    }

    /**
     *
     * @return
     */
    public void addToInventory(String item) {
        inventory.add(item);
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getInventory() {
        return inventory;
    }
}
