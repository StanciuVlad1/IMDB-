package org.example;

public class Episode {
    // field-uri
    private String episodeName;
    private String duration;
    //gettere si settere

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    // constructor

    public Episode() {
    }

    public Episode(String episodeName, String duration) {
        this.episodeName = episodeName;
        this.duration = duration;
    }
    // toString

    @Override
    public String toString() {
        return "Episode{" +
                "\nepisodeName='" + episodeName + '\'' +
                ",\nduration='" + duration + '\'' +
                "\n}\n";
    }
}
