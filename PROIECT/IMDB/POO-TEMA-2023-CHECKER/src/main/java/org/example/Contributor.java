package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contributor<T extends Comparable<T>> extends Staff implements RequestsManager{
    // constructor

    public Contributor() {
    }

    public Contributor(String username, int experience, Information information, AccountType userType, SortedSet favorites, List notifications, List list, SortedSet contributions) {
        super(username, experience, information, userType, favorites, notifications, list, contributions);
    }
    // request-uri

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
                if(Functions.setTo(movieTitle).getUsername().equals(this.getUsername())){
                    System.out.println("Nu poti face o cerere la o productie pe care tu ai adaugat-o in sistem");
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
                if(Functions.setTo(actorName).getUsername().equals(this.getUsername())){
                    System.out.println("Nu poti face o cerere la o productie pe care tu ai adaugat-o in sistem");
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

    @Override
    public void addProductionSystem(Production p) {
        IMDB.getInstance().getProductions().add(p);
        this.addContributions(p);
    }

    @Override
    public void addActorSystem(Actor a) {
        IMDB.getInstance().getActors().add(a);
        this.addContributions(a);
    }

    @Override
    public void removeProductionSystem(String name) {
        Production p = (Production) Functions.find(name);
        IMDB.getInstance().getProductions().remove(p);
        this.dellContributions(p);

    }

    @Override
    public void removeActorSystem(String name) {
        Actor a = (Actor)Functions.find(name);
        IMDB.getInstance().getActors().remove(a);
        this.dellContributions(a);

    }

}
