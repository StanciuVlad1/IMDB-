package org.example;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import kotlin.time.DurationJvmKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public abstract class User<T extends Comparable<T>> {
    // field-uri
    private String username;
    private int experience;
    private Information information;
    private AccountType userType;
    private SortedSet<T> favorites = new TreeSet<>();
    private List<String> notifications;
    @JsonIgnore
    private ExperienceStrategy strategy;
    // gettere si settere

    public ExperienceStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(ExperienceStrategy strategy) {
        this.strategy = strategy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public AccountType getUserType() {
        return userType;
    }

    public void setUserType(AccountType userType) {
        this.userType = userType;
    }

    public SortedSet<T> getFavorites() {
        return favorites;
    }

    public void setFavorites(SortedSet<T> favorites) {
        this.favorites = favorites;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }



    // constructor

    public User() {
    }

    public User(String username, int experience, Information information, AccountType userType, SortedSet<T> favorites, List<String> notifications) {
        this.username = username;
        this.experience = experience;
        this.information = information;
        this.userType = userType;
        this.favorites = favorites;
        this.notifications = notifications;
    }

    // tostring


    @Override
    public String toString() {
        return "User{" +
                "\nusername='" + username + '\'' +
                ",\nexperience=" + experience +
                ",\ninformation=" + information +
                ",\nuserType='" + userType + '\'' +
                ",\nfavorites=" + favorites +
                ",\nnotifications=" + notifications +
                "\n}\n";
    }

    // Information
    public static class Information{
        // field-uri

        private Credentials credentials;
        private String name;
        private String country;
        private int age;
        private String gender;

        private String birthDate;
        // gettere

        public Credentials getCredentials() {
            return credentials;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public int getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public String getBirthDate() {
            return birthDate;
        }
        // Constructor
        @JsonCreator
        private Information(InformationBuilder informationBuilder) {
            this.credentials = informationBuilder.credentials;
            this.name = informationBuilder.name;
            this.country = informationBuilder.country;
            this.age = informationBuilder.age;
            this.gender = informationBuilder.gender;
            this.birthDate = informationBuilder.birthDate;
        }
        // toString

        @Override
        public String toString() {
            return "Information{" +
                    "credentials=" + credentials +
                    ", \nname='" + name + '\'' +
                    ", \ncountry='" + country + '\'' +
                    ", \nage=" + age +
                    ", \ngender='" + gender + '\'' +
                    ", \nbirthDate=" + birthDate +
                    '}';
        }

        // InformationBuilder
        public static class InformationBuilder{
            //field-uri
            private Credentials credentials;
            private String name;
            private String country;
            private int age;
            private String gender;

            private String birthDate;

            // settere
            @JsonSetter
            public InformationBuilder credentials(Credentials credentials){
                this.credentials = credentials;
                return this;
            }
            @JsonSetter
            public InformationBuilder name(String name){
                this.name = name;
                return this;
            }
            @JsonSetter
            public InformationBuilder country(String country){
                this.country = country;
                return this;
            }
            @JsonSetter
            public InformationBuilder age(int age){
                this.age =age;
                return this;
            }
            @JsonSetter
            public InformationBuilder gender(String gender){
                this.gender = gender;
                return this;
            }
            @JsonSetter
            public InformationBuilder birthDate(String birthDate){
                this.birthDate=birthDate;
                return this;
            }

            public Information build(){
                return new Information(this);
            }

        }
    }
    //metode specifice
    public void addFavorites(Object e){
        favorites.add((T) e);
    }
    public void deleteFavorites(Object o){
        if(favorites.isEmpty()==false)
            favorites.remove((T)o);
    }
    public boolean logout(){return false;}
    public void updateExperience(ExperienceStrategy strategy){
        this.setExperience(this.getExperience()+strategy.calculateExperience());
    }

}
