package gruppe.fire;


import gruppe.fire.fileHandling.FileToStory;
import gruppe.fire.logic.Story;
import org.junit.Test;

import java.io.File;

public class Main2 {

    @Test
    public void main(){
        FileToStory fileToStory = new FileToStory(new File("Data/SavedPaths/paths2.paths"));
        Story story = fileToStory.readFile();
        System.out.println(story);
    }

}

