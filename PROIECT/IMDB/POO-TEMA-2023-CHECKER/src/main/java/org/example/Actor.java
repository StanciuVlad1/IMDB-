package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Actor implements Comparable{
    //fields
    private String name;
    private List<Map<String,String>> performances = new ArrayList<>();
    private String biography;

    //gettere si settere
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, String>> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Map<String, String>> performances) {
        this.performances = performances;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }


    //constructor

    public Actor() {
    }

    public Actor(String name, List<Map<String, String>> performances, String biography) {
        this.name = name;
        this.performances = performances;
        this.biography = biography;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "\nname='" + name + '\'' +
                ",\n performances=" + performances +
                ",\n biography='" + biography + '\'' +
                "\n}\n";
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if(o.getClass()==Actor.class) {
            Actor that = (Actor) o;
            return this.name.compareTo(that.name);
        }
        if(o.getClass()== Movie.class){
            Movie that = (Movie) o;
            return this.name.compareTo(that.getTitle());
        }

            Series that = (Series) o;
            return this.name.compareTo(that.getTitle());

    }
}
