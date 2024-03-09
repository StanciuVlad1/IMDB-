package org.example;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Series extends Production{
    // Field-uri
    private int releaseYear;
    private int numSeasons;
    private Map<String, List<Episode>> seasons = new TreeMap<>();
    // gettere si settere

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getNumSeasons() {
        return numSeasons;
    }

    public void setNumSeasons(int numSeasons) {
        this.numSeasons = numSeasons;
    }

    public Map<String, List<Episode>> getSeasons() {
        return seasons;
    }

    public void setSeasons(Map<String, List<Episode>> seasons) {
        this.seasons = seasons;
    }
    //constructor

    public Series() {
    }

    public Series(String title, String type, List<String> directors, List<String> actors, List<Genre> genres, List<Rating> ratings, String plot, Double averageRating, int releaseYear, int numSeasons, Map<String, List<Episode>> seasons) {
        super(title, type, directors, actors, genres, ratings, plot, averageRating);
        this.releaseYear = releaseYear;
        this.numSeasons = numSeasons;
        this.seasons = seasons;
    }

    @Override
    public void displayInfo() {
        System.out.println("releaseYear " + this.releaseYear);
        System.out.println("numSeasons " + this.numSeasons);

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
        return "Series{" +
                "\nreleaseYear=" + releaseYear +
                ",\nnumSeasons=" + numSeasons +
                ",\nseasons=" + seasons +
                "\n}\n" + super.toString();
    }
}
