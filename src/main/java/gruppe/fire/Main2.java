package gruppe.fire;

import gruppe.fire.fileHandling.StoryFileHandler;


import java.io.IOException;


public class Main2 {
    public static void main(String[] args) throws IOException {
        StoryFileHandler handler = new StoryFileHandler();
        String string = handler.fileToString();
    }
}

