package org.example;

import java.util.Collection;
import java.util.Map;

public class Story {
    private String title;
    private Map<Link, Passage> passages;
    private Passage openingPassage;

    public void Story(String title, Passage openingPassage){

    }

    public String getTitle(){
        return title;
    }

    public Passage getOpeningPassage() {
        return openingPassage;
    }

    public void addPassage(Passage passage){

    }

    public Passage getPassage(Link link){
        Passage passage = new Passage();
        return passage;
    }

    public Collection<Passage> getPassages(){
        Story passage = new Story();
        return passage.getPassages();
    }
}
