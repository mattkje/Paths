package gruppe.fire;

import gruppe.fire.fileHandling.FileToPlayer;
import gruppe.fire.ui.MainUI;

import java.io.File;

public class Main2 {

    public static void main(String[] args) {
        FileToPlayer handler = new FileToPlayer(new File("Data/PlayerData/Players/player1.txt"));
        handler.readFile();
    }
}
