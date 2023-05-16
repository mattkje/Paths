package gruppe.fire.fileHandling;

import gruppe.fire.goals.Goal;
import gruppe.fire.logic.*;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class FileToGame {


    public Game readFile() {
        Player player = new Player("Null", null ,0, 0, 0);
        //Passage passage = new Passage(null, null);

        try (Scanner scanner = new Scanner(new File("Data/GameStates/state1.txt"))) {


            // Sets the first line as the title of the story
            Path path = Path.of(scanner.nextLine());
            FileToStory fileToStory = new FileToStory(path.toFile());
            Story story = fileToStory.readFile();

            String passageTitle = scanner.nextLine();
            //passage.setContent(scanner.nextLine());
            player.setName(scanner.nextLine());
            player.setGold(scanner.nextInt());
            player.setHealth(scanner.nextInt());
            player.setScore(scanner.nextInt());
            String goals = scanner.nextLine();
            String inventory = scanner.nextLine();
            inventory.strip();
            //String gold = scanner.nextLine();
            //String health = scanner.nextLine();
            //String score = scanner.nextLine();
            //ArrayList goals = new ArrayList<>();
            //if (gold != null){
            //    goals.add(gold);
            //}
            //if (health != null){
            //    goals.add(health);
            //}
            //if (score != null){
            //    goals.add(score);
            //}

            Link link = new Link(passageTitle, passageTitle);

            Passage passage = story.getPassage(link);
            story.setOpeningPassage(passage);
            player.addToInventory(inventory);
            Game game = new Game(player, story);
            //game.setGoalsList(goals);
            return game;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}