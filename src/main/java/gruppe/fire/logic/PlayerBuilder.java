package gruppe.fire.logic;

import javafx.scene.image.Image;

public class PlayerBuilder {
    private String name;
    private Image image;
    private int health;
    private int score;
    private int gold;


    public void setName(String name) {
        this.name = name;
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

    public Player getPlayer() {
        return new Player(name, image, health, score, gold);
    }
}
