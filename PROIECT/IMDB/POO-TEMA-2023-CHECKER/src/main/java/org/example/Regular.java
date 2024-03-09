package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Regular<T extends Comparable<T>> extends User implements RequestsManager {
    // constructor

    public Regular() {
    }

    public Regular(String username, int experience, Information information, AccountType userType, SortedSet favorites, List notifications) {
        super(username, experience, information, userType, favorites, notifications);
    }

    @Override
    public void createRequest() {
        Request r = new Request();
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduce descrierea cererii : ");
        String description = scan.nextLine();
        System.out.println("Alegeti tipul de cerere : ");
        System.out.println("1) DELETE_ACCOUNT");
        System.out.println("2) MOVIE_ISSUE");
        System.out.println("3) ACTOR_ISSUE");
        System.out.println("4) OTHERS");
        LocalDateTime l = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String dataOraFormate = l.format(formatter);
        LocalDateTime date = LocalDateTime.parse(l.toString(), formatter);
        r.setCreatedDate(date);
        r.setUsername(this.getUsername());
        r.setDescription(description);
        int choice = 0;
        while (choice < 1 || choice > 4) {
            int ok = 0;
            try {
                choice = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un numar intre 1 si 4 : ");
                scan.nextLine();
                ok = 1;
            }
            if ((choice < 1 || choice > 4) && ok == 0)
                System.out.println("Alegeti un numar intre 1 si 4 : ");
        }
        switch (choice) {
            case 1:
                r.setType(RequestTypes.DELETE_ACCOUNT);
                r.setTo("ADMIN");
                Admin.RequestsHolder.add(r);
                IMDB.getInstance().getRequests().add(r);
                break;
            case 2:
                Scanner scan2 = new Scanner(System.in);
                r.setType(RequestTypes.MOVIE_ISSUE);
                System.out.println("Introduceti numele productiei : ");
                String movieTitle = scan2.nextLine();
                r.setMovieTitle(movieTitle);
                if (Functions.setTo(movieTitle) == null) {
                    System.out.println("Productia nu a fost gasita !");
                    break;
                }
                r.setTo(Functions.setTo(movieTitle).getUsername());
                IMDB.getInstance().getRequests().add(r);
                Staff o = (Staff) Functions.find(Functions.setTo(movieTitle).getUsername());
                o.getRequestList().add(r);
                break;
            case 3:
                Scanner scan3 = new Scanner(System.in);
                r.setType(RequestTypes.ACTOR_ISSUE);
                System.out.println("Introduceti numele actorului : ");
                String actorName = scan3.nextLine();
                r.setActorName(actorName);
                if (Functions.setTo(actorName) == null) {
                    System.out.println("Actorul nu a fost gasit !");
                    break;
                }
                r.setTo(Functions.setTo(actorName).getUsername());
                IMDB.getInstance().getRequests().add(r);
                Staff o2 = (Staff) Functions.find(Functions.setTo(actorName).getUsername());
                o2.getRequestList().add(r);
                break;
            case 4:
                r.setType(RequestTypes.OTHERS);
                r.setTo("ADMIN");
                Admin.RequestsHolder.add(r);
                IMDB.getInstance().getRequests().add(r);
                break;
        }
    }

    @Override
    public void removeRequest() {
        int poz = -1;
        int fin = -1;
        System.out.println("Cererile dumneavoastra sunt : ");
        for (int i = 0; i < IMDB.getInstance().getRequests().size(); i++)
            if (IMDB.getInstance().getRequests().get(i).getUsername().equalsIgnoreCase(this.getUsername())) {
                System.out.println(i + 1 + ") " + IMDB.getInstance().getRequests().get(i));
                if (poz == -1)
                    poz = i + 1;
                fin = i + 1;

            }
        System.out.println("Introduce-ti indexul cererii pe care vreti sa o retrageti : ");
        Scanner scan = new Scanner(System.in);
        int rem = 0;
        while (rem < poz || rem > fin) {
            int ok = 0;
            try {
                rem = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un index intre " + poz + " si " + fin + " : ");
                scan.nextLine();
                ok = 1;
            }
            if ((rem < poz || rem > fin) && ok == 0)
                System.out.println("Alegeti un index intre " + poz + " si " + fin + " : ");
        }
        if ((rem >= 1) && (rem <= IMDB.getInstance().getRequests().size()) && (IMDB.getInstance().getRequests().isEmpty() == false)) {
            if (IMDB.getInstance().getRequests().get(rem - 1).getType().equals(RequestTypes.DELETE_ACCOUNT) || IMDB.getInstance().getRequests().get(rem - 1).getType().equals(RequestTypes.OTHERS))
                Admin.RequestsHolder.requests.remove(IMDB.getInstance().getRequests().get(rem - 1));
            IMDB.getInstance().getRequests().remove(rem - 1);

        }
        System.out.println(Admin.RequestsHolder.requests);
    }

    public void addRating(Production p) {
        for (int i = 0; i < p.getRatings().size(); i++)
            if (p.getRatings().get(i).getUsername().equalsIgnoreCase(this.getUsername())) {
                System.out.println("Aveti deja o evaluare facuta.Pentru a putea crea una noua, stergeti-o pe cea anterioara!");
                return;
            }
        Rating r = new Rating();
        r.setUsername(this.getUsername());
        System.out.println("Introduceti nota pe care doriti sa o acrodati : ");
        Scanner scan = new Scanner(System.in);
        double grade = 0;
        while (grade < 1 || grade > 10) {
            int ok = 0;
            try {
                grade = scan.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Introduceti un numar intre 1 si 10 : ");
                scan.nextLine();
                ok = 1;
            }
            if ((grade < 1 || grade > 10) && ok == 0)
                System.out.println("Introduceti un numar intre 1 si 10 : ");
        }
        r.setRating(grade);
        System.out.println("Introduceti un comentariu : ");
        Scanner scan2 = new Scanner(System.in);
        r.setComment(scan2.nextLine());
        p.sortRatings();
        p.getRatings().add(r);
        p.sortRatings();
        p.setAverageRating(p.updateRatingAverage());
        AddRatingExperienceStrategy exp = new AddRatingExperienceStrategy();
        this.updateExperience(exp);

    }

    public void deleteRating(Production p) {
        Rating del = null;
        for (Rating r : p.getRatings())
            if (r.getUsername().equalsIgnoreCase(this.getUsername()))
                del = r;
        p.getRatings().remove(del);
        if (del != null) {
            DeleteRatingStrategy exp = new DeleteRatingStrategy();
            this.updateExperience(exp);
        }
    }
}