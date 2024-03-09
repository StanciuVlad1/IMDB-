package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Request {
    // date formatter
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:s");
    // field-uri
    private RequestTypes type;
    @JsonIgnore
    private LocalDateTime createdDate;
    private  String username;
    private String actorName;
    private String movieTitle;
    private String to;
    private String description;

    // gettere si settere

    public RequestTypes getType() {
        return type;
    }

    public void setType(RequestTypes type) {
        this.type = type;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // constructor

    public Request() {
    }

    public Request(RequestTypes type, LocalDateTime createdDate, String username, String actorName, String movieTitle, String to, String description) {
        this.type = type;
        this.createdDate = createdDate;
        this.username = username;
        this.actorName = actorName;
        this.movieTitle = movieTitle;
        this.to = to;
        this.description = description;
    }
    // toString

    @Override
    public String toString() {
        return "Request{" +
                "\ntype=" + type +
                ",\ncreatedDate=" + createdDate +
                ",\nusername='" + username + '\'' +
                ",\nactorName='" + actorName + '\'' +
                ",\nmovieTitle='" + movieTitle + '\'' +
                ",\nto='" + to + '\'' +
                ",\ndescription='" + description + '\'' +
                "\n}\n";
    }

}
