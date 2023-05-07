package gruppe.fire.fileHandling;

import gruppe.fire.logic.GameBuilder;
import gruppe.fire.logic.Player;
import javafx.scene.image.Image;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class DataBase {
    private Path targetPath;

    public String getActivePlayerPath(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Data/currentPlayerFile.cfg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String activePlayer;
        try {
            activePlayer = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return activePlayer;
    }

    public String getActiveStoryPath(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Data/currentPathsFile.cfg"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String activeStory;
        try {
            activeStory = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return activeStory;

    }

    public void writeFile(Player player){
        DataBase dataBase = new DataBase();
        GameBuilder gameBuilder = new GameBuilder(new File(dataBase.getActivePlayerPath()), new File(dataBase.getActiveStoryPath()));
        String[] players = gameBuilder.createGame().readPlayers();
        int i = players.length + 1;

        // Get the player's image
        if(player.getImage() != null){
            Image playerImage = player.getImage();
            String imagePath = playerImage.getUrl();
            String filePathStr = imagePath.replace("file:/", "");
            Path sourcePath = Paths.get(filePathStr);
            this.targetPath = Paths.get("Data/PlayerData/Images/pp"+i+".png");
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            this.targetPath = Paths.get("Data/PlayerData/Images/noSelect.png");
        }



        try (FileWriter fileWriter = new FileWriter("Data/PlayerData/Players/player"+i+".txt")) {
            fileWriter.write(player.getName()+"\n");
            fileWriter.write(targetPath +"\n");
            fileWriter.write(player.getHealth()+"\n");
            fileWriter.write(player.getScore()+"\n");
            fileWriter.write(player.getGold()+"\n");
        } catch (IOException e) {
            System.out.println("HAah stoopid");
        }
    }
}
