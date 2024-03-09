package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Production implements Comparable{
    //field-uri
    private String title;
    private String type;
    private List<String> directors = new ArrayList<>();
    private List<String> actors = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private List<Rating> ratings = new ArrayList<>();
    private String plot;
    private Double averageRating;

    // gettere si settere

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
    // constructor

    public Production() {
    }

    public Production(String title, String type, List<String> directors, List<String> actors, List<Genre> genres, List<Rating> ratings, String plot, Double averageRating) {
        this.title = title;
        this.type = type;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.plot = plot;
        this.averageRating = averageRating;
    }
    // toString

    @Override
    public String toString() {
        return "Production{\n" +
                "title='" + title + '\'' +
                ",\ntype='" + type + '\'' +
                ",\ndirectors=" + directors +
                ",\nactors=" + actors +
                ",\ngenres=" + genres +
                ",\nratings=" + ratings +
                ",\nplot='" + plot + '\'' +
                ",\naverageRating=" + averageRating +
                "}\n";
    }
    // metodele necesare
    public abstract void displayInfo();
    @Override
    public int compareTo(@NotNull Object o) {
        if(o.getClass()==Production.class) {
            Production m = (Movie) o;
            return this.getTitle().compareTo(m.getTitle());
        }
        if(o.getClass()== Series.class){
            Series s= (Series) o;
            return this.getTitle().compareTo(s.getTitle());
        }
        Actor a = (Actor) o;
        return this.getTitle().compareTo(a.getName());
    }
    // metoda de calculare average
    public Double updateRatingAverage(){
        double s = 0;
        for(Rating r : ratings){
            s+=r.getRating();
        }
        return (Double) (s/this.ratings.size());
    }
    public void sortRatings(){
        for (int i = 0 ; i < ratings.size()-1;i++)
            for (int j = i+1;j<ratings.size();j++)
            {
                String name1 = ratings.get(i).getUsername();
                String name2 = ratings.get(j).getUsername();
                User u1 = (User)Functions.find(name1);
                User u2 = (User)Functions.find(name2);
                if(u1.getExperience()<u2.getExperience()){
                    Rating r = ratings.get(i);
                    ratings.set(i,ratings.get(j));
                    ratings.set(j,r);
                }

            }
    }
    public void showRatings(){
        sortRatings();
        System.out.println(ratings);
    }
}
