package gruppe.fire.logic;

import gruppe.fire.goals.Goal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The Game class is responsible for connecting a player to a story, and has
 * handy methods for starting and maneuvering in the game.
 */
public class Game {

    private Player player;
    private Story story;
    private ArrayList<Goal> goals;

    private Passage currentPassage;

    /**
     * Creates an instance of Game.
     * @param player The player of the game.
     * @param story The story of the game.
     */
    public Game(Player player, Story story){
        this.player = player;
        this.story = story;
        this.goals = new ArrayList<>();
    }

    /**
     * This method returns the player.
     * @return The player.
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * This method returns the story.
     * @return The story.
     */
    public Story getStory(){
        return story;
    }

    /**
     * This method returns the goals.
     * @return The goals.
     */
    public ArrayList<Goal> getGoals(){
        return goals;
    }

    /**
     * This method should simply return the first passage in the story for
     * this game.
     * @param openingPassage The first passage in the story.
     * @return The openingPassage
     */
    public Passage begin(Passage openingPassage){
        return openingPassage;
    }

    /**
     * This method should return the passage that matches the provided the link.
     * @param link
     * @return The passage which matches the provided Link.
     */
    public Passage g2o(Link link){
        Passage currentPassage = null;
        for (Passage passage : story.getPassages()) {
            if (passage.hasLinks(link)) {
                currentPassage = passage;
                break;
            }
        }
        return currentPassage;
    }

    public Passage go(Link link){
        this.currentPassage = story.getPassage(link);
        return currentPassage;
    }

    public String[] readPlayers() {
        File folder = new File("Data/PlayerData/Players");
        File[] listOfFiles = folder.listFiles();
        List<String> paths = new ArrayList<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                paths.add(file.getPath());
            }
        }

        String[] result = paths.toArray(new String[0]);
        return result;
    }

}
