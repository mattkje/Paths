package gruppe.fire.fileHandling;


import gruppe.fire.actions.*;
import gruppe.fire.logic.Link;
import gruppe.fire.logic.Passage;
import gruppe.fire.logic.Story;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileManagement {

    private File gameFile;

    public FileManagement(File gameFile){
        this.gameFile = gameFile;
    }

    public Story readFile() {
        String fileContent;
        try {

            FileInputStream inputStream = new FileInputStream(gameFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
            fileContent = stringBuilder.toString();


            HashMap<Link, Passage> passages = new HashMap<>();
            String[] tokens = fileContent.split("\\n\\s*\\n");
            String storyTitle = tokens[0];
            String openingPassageString = tokens[1];
            String[] openingPassageParts = openingPassageString.split("\n");
            Passage openingPassage = parsePassage(openingPassageParts[0], openingPassageParts[2], openingPassageParts[3], openingPassageParts[1]);
            for(int i=2; i < tokens.length; i++){
                String passageString = tokens[i];
                String[] passageParts = passageString.split("\n");
                Passage passage = parsePassage(passageParts[0], passageParts[2], passageParts[3], passageParts[1]);
                for(int n = 0; n < parseLink(passageParts[3], passageParts[1]).size(); n++){
                    passages.put(parseLink(passageParts[3], passageParts[1]).get(n), passage);
                }

            }
            return new Story(storyTitle, openingPassage, passages);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Passage parsePassage(String passageTitle, String passageContent, String link, String action){
        String[] titleParts = passageTitle.split("::");
        String title = titleParts[1];
        return new Passage(title, passageContent, parseLink(link, action));
    }

    private ArrayList<Link> parseLink(String link, String action){
        String text;
        String reference;
        ArrayList<Link> links = new ArrayList<>();
        String[] parts = link.split("[\\[\\]()]+");
        for(int i = 2; i < parts.length; i+=2){
            text = parts[i-1];
            reference = parts[i];
            ArrayList<Action> actions = parseAction(action);
            Link linkage = new Link(text, reference, actions);
            links.add(linkage);
        }
        return links;
    }

    private ArrayList<Action> parseAction(String action){
        ArrayList<Action> listOfActions = new ArrayList<>();
        String[] tokens = action.split(";;");
        for(int i = 2; i < tokens.length; i+=2) {
            String actionType = tokens[i-1].strip();
            String actionAmount = tokens[i].strip(); // CHECK THIS CODE LATER!!!
            listOfActions.add(checkForAction(actionType, actionAmount));
        }
        return listOfActions;
    }

    private Action checkForAction(String actionType, String actionAmount) {
        Action action = null;
        switch (actionType) {
            case "GoldAction" -> action = new GoldAction(Integer.parseInt(actionAmount));
            case "HealthAction" -> action = new HealthAction(Integer.parseInt(actionAmount));
            case "InventoryAction" -> action = new InventoryAction(actionAmount);
            case "ScoreAction" -> action = new ScoreAction(Integer.parseInt(actionAmount));
        }
        return action;
    }

    /**
     * Reads first lines in saved files in saved folder and returns all story titles as an array of Strings.
     */
    public String[] readSavedStories() throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader("Data/SavedPaths/paths1.paths"));
        BufferedReader reader2 = new BufferedReader(new FileReader("Data/SavedPaths/paths2.paths"));
        BufferedReader reader3 = new BufferedReader(new FileReader("Data/SavedPaths/paths3.paths"));
        BufferedReader reader4 = new BufferedReader(new FileReader("Data/SavedPaths/paths4.paths"));
        String story1 = reader1.readLine();
        if (story1 == null || story1.isEmpty()) {
            story1 = "No File";
        }

        String story2 = reader2.readLine();
        if (story2 == null || story2.isEmpty()) {
            story2 = "No File";
        }

        String story3 = reader3.readLine();
        if (story3 == null || story3.isEmpty()) {
            story3 = "No File";
        }

        String story4 = reader4.readLine();
        if (story4 == null || story4.isEmpty()) {
            story4 = "No File";
        }

        String[] storyArray = { story1, story2, story3, story4 };
        return storyArray;

    }
}
