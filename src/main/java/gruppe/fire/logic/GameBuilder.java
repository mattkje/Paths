package gruppe.fire.logic;

import gruppe.fire.fileHandling.FileToPlayer;
import gruppe.fire.fileHandling.FileToStory;
import java.io.File;

public class GameBuilder {

  private File playerFile;
  private File storyFile;

  public GameBuilder(File playerFile, File storyFile) {
    this.playerFile = playerFile;
    this.storyFile = storyFile;
  }

  public Game createGame() {
    FileToPlayer fileToPlayer = new FileToPlayer(playerFile);
    Player player = fileToPlayer.readFile();

    FileToStory fileToStory = new FileToStory(storyFile);
    Story story = fileToStory.readFile();


    return new Game(player, story);
  }


}
