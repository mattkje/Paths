package gruppe.fire.logic;

public class PlayerBuilder {
    private String name;
    private int health;
    private int score;
    private int gold;


    public void setName(String name) {
        this.name = name;
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

    public Player getPlayer() {
        return new Player(name, health, score, gold);
    }
}
