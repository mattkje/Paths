package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class Passage {
    private String title;
    private String content;
    private ArrayList<Link> links;

    public void Passage(String title, String content){

    }

    public String getTitle(){
        return title;
    }
    public String getContent(){
        return content;
    }
    public boolean addLink(Link link){
        return links.add(link);
    }
    public void getLinks(ArrayList<Link> links){

    }
    public boolean hasLinks(){
        return links.size() > 0;
    }
    public String toString(){
        return title + " " + content;
    }
    public boolean equals(Object object){
        return super.equals(object);
    }
    public int hashCode(){
        return Objects.hash(title, content);
    }
}

