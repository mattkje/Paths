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
    public int addScore() {
        return score;
    }

    /**
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @return
     */
    public int addGold() {
        return gold;
    }

    /**
     *
     * @return
     */
    public String addToInventory() {
        return null;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getInventory() {
        return inventory;
    }
}
