package org.example;

public class Rating {
    // field-uri
    private String username;
    private Double rating;
    private String comment;
    // gettere si settere

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    //constructor


    public Rating() {
    }

    public Rating(String username, Double rating, String comment) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    // toString

    @Override
    public String toString() {
        return "Rating{" +
                "\nusername='" + username + '\'' +
                ",\nrating=" + rating +
                ",\ncomment='" + comment + '\'' +
                "\n}\n";
    }
}
