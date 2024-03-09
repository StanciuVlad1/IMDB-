package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Movie extends Production{
    // field-uri
    private String duration;
    private int releaseYear;
    // gettere si settere

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
    // constructor

    public Movie() {
    }

    public Movie(String title, String type, List<String> directors, List<String> actors, List<Genre> genres, List<Rating> ratings, String plot, Double averageRating, String duration, int releaseYear) {
        super(title, type, directors, actors, genres, ratings, plot, averageRating);
        this.duration = duration;
        this.releaseYear = releaseYear;
    }
    @Override
    public void displayInfo() {
        System.out.println("duration " + this.duration);
        System.out.println("releaseYear" + this.releaseYear);
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if(o.getClass()==Movie.class) {
            Movie m = (Movie) o;
            return this.getTitle().compareTo(m.getTitle());
        }
        if(o.getClass()== Series.class){
            Series s= (Series) o;
            return this.getTitle().compareTo(s.getTitle());
        }
        Actor a = (Actor) o;
        return this.getTitle().compareTo(a.getName());
    }

    @Override
    public String toString() {
        return "Movie{" +
                "\nduration='" + duration + '\'' +
                ",\nreleaseYear=" + releaseYear +
                "\n}\n" + super.toString();
    }
}
