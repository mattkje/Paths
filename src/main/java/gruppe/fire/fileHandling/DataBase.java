package gruppe.fire.fileHandling;

import gruppe.fire.logic.GameBuilder;
import gruppe.fire.logic.Player;
import javafx.scene.image.Image;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;


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
            System.out.println("Something went wrong");
        }
    }

    public void writeStateToFile(String gameState){
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("Data/GameStates/state1.txt");
            fileWriter.write(gameState);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeSettingsToFile(Boolean fs, Boolean bg, double vlm, double vlm2){
        try (FileWriter fileWriter = new FileWriter("Data/settings.cfg")) {
            fileWriter.write("fullscreen="+ fs +"\n");
            fileWriter.write("background="+ bg +"\n");
            fileWriter.write("music="+ vlm +"\n");
            fileWriter.write("fx="+ vlm2 +"\n");
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }

    public HashMap readSettingsFromFile() {
        HashMap map = new HashMap<>();
        File settingsFile = new File("Data/settings.cfg");
        try (Scanner scanner = new Scanner(settingsFile)) {
            String fs = scanner.nextLine();
            boolean fullscreen = Boolean.parseBoolean(fs.replace("fullscreen=", ""));
            String bg = scanner.nextLine();
            boolean background = Boolean.parseBoolean(bg.replace("background=", ""));
            String vlm = scanner.nextLine();
            double volume= Double.parseDouble(vlm.replace("music=", ""));
            String vlm2 = scanner.nextLine();
            double volume2 = Double.parseDouble(vlm2.replace("fx=", ""));


            map.put("fs", fullscreen);
            map.put("bg", background);
            map.put("vlm", volume);
            map.put("vlm2", volume2);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
