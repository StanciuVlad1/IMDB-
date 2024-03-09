package org.example;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IMDB {
    String root = "/home/vlad/Desktop/citireJson/usersOrNothing/POO-TEMA-2023-CHECKER/src/main/resources/input/";
    //field-uri
    private List<User> users;
    private List<Actor> actors;
    private List<Request> requests;
    private List<Production> productions;
    //gettere si settere


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public void setProductions(List<Production> productions) {
        this.productions = productions;
    }
    //singleTone

    private static IMDB instance = null;

    private IMDB() {
        this.actors = new ArrayList<>();
        this.productions = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.users = new ArrayList<>();

    }

    public static IMDB getInstance() {
        if (instance == null)
            instance = new IMDB();
        return instance;
    }

    //metodeAuxiliare
    public Actor findActors(String name) {
        for (int i = 0; i < IMDB.getInstance().actors.size(); i++) {
            Actor aux = (Actor) IMDB.getInstance().actors.get(i);
            if (name.equals(aux.getName()))
                return aux;
        }
        return null;
    }

    public Object findProduction(String name) {
        for (int i = 0; i < IMDB.getInstance().productions.size(); i++) {
            if (IMDB.getInstance().productions.get(i).getClass() == Movie.class) {
                Movie aux = (Movie) IMDB.getInstance().productions.get(i);
                if (name.equals(aux.getTitle()))
                    return aux;
            } else {
                Series aux = (Series) IMDB.getInstance().productions.get(i);
                if (name.equals(aux.getTitle()))
                    return aux;
            }
        }
        return null;
    }

    //metodeCitire
    public void citireActors() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String path = root+"actors.json";
        File citire = new File(path);
        IMDB.getInstance().actors = mapper.readValue(citire, new TypeReference<ArrayList<Actor>>() {
        });
    }

    public void citireProductions() throws IOException {
        String path = root+"production.json";
        ObjectMapper objectMapper = new ObjectMapper();
        File citire = new File(path);
        ArrayList<Object> x = new ArrayList<>();
        try {
            x = objectMapper.readValue(citire, new TypeReference<ArrayList<Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < x.size(); i++) {
            String y = objectMapper.writeValueAsString(x.get(i));
            if (y.contains("Movie")) {
                Movie a = objectMapper.readValue(y, Movie.class);
                IMDB.getInstance().productions.add(a);
            } else {
                Series a = objectMapper.readValue(y, Series.class);
                IMDB.getInstance().productions.add(a);
            }
        }

    }

    public void citireRequests() throws IOException {
        String path = root+"requests.json";
        ObjectMapper objectMapper = new ObjectMapper();
        File citire = new File(path);
        ArrayList<Request> x2 = new ArrayList<>();
        ArrayList<Object> x = objectMapper.readValue(citire, new TypeReference<ArrayList<Object>>() {
        });
        for (int i = 0; i < x.size(); i++) {
            String y = objectMapper.writeValueAsString(x.get(i));
            JsonNode obj = objectMapper.readTree(y);
            Request r = objectMapper.readValue(y, Request.class);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime date = LocalDateTime.parse(obj.get("createdDate").asText(), formatter);
            r.setCreatedDate(date);
            if (r.getTo().equals("ADMIN")) {
                Admin.RequestsHolder.requests.add(r);
            } else {
                Staff s = (Staff) Functions.find(r.getTo());
                s.getRequestList().add(r);
            }

            x2.add(r);
        }
        IMDB.getInstance().requests.addAll(x2);
    }

    public void citireUsres() throws IOException {
        String path1 = root+"accounts.json";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File citire = new File(path1);
        ArrayList<Object> x = new ArrayList<>();
        x = objectMapper.readValue(citire, new TypeReference<ArrayList<Object>>() {
        });
        for (int i = 0; i < x.size(); i++) {
            String y = objectMapper.writeValueAsString(x.get(i));
            if (y.contains("Regular")) {
                Regular r = objectMapper.readValue(y, Regular.class);
                JsonNode obj = objectMapper.readTree(y);
                if (obj.get("favoriteActors") != null) {
                    for (int j = 0; j < obj.get("favoriteActors").size(); j++) {
                        String aux = obj.get("favoriteActors").get(j).asText();
                        Actor auxiliar = findActors(aux);
                        r.addFavorites(auxiliar);
                    }
                }
                if (obj.get("favoriteProductions") != null) {
                    for (int j = 0; j < obj.get("favoriteProductions").size(); j++) {
                        String aux = obj.get("favoriteProductions").get(j).asText();
                        Object auxiliar = findProduction(aux);
                        if (auxiliar.getClass() == Movie.class)
                            r.addFavorites((Movie) auxiliar);
                        else {
                            r.addFavorites((Series) auxiliar);
                        }
                    }
                }
                IMDB.getInstance().users.add(r);
            }
            if (y.contains("Contributor")) {
                Contributor r = objectMapper.readValue(y, Contributor.class);
                JsonNode obj = objectMapper.readTree(y);
                if (obj.get("favoriteActors") != null) {
                    for (int j = 0; j < obj.get("favoriteActors").size(); j++) {
                        String aux = obj.get("favoriteActors").get(j).asText();
                        Actor auxiliar = findActors(aux);
                        r.addFavorites(auxiliar);
                    }
                }
                if (obj.get("favoriteProductions") != null) {
                    for (int j = 0; j < obj.get("favoriteProductions").size(); j++) {
                        String aux = obj.get("favoriteProductions").get(j).asText();
                        Object auxiliar = findProduction(aux);
                        if (auxiliar.getClass() == Movie.class)
                            r.addFavorites((Movie) auxiliar);
                        else
                            r.addFavorites((Series) auxiliar);
                    }
                }
                if (obj.get("actorsContribution") != null) {
                    for (int j = 0; j < obj.get("actorsContribution").size(); j++) {
                        String aux = obj.get("actorsContribution").get(j).asText();
                        Actor auxiliar = findActors(aux);
                        r.addContributions(auxiliar);
                    }
                }
                if (obj.get("productionsContribution") != null) {
                    for (int j = 0; j < obj.get("productionsContribution").size(); j++) {
                        String aux = obj.get("productionsContribution").get(j).asText();
                        Object auxiliar = findProduction(aux);
                        if (auxiliar.getClass() == Movie.class)
                            r.addContributions((Movie) auxiliar);
                        else
                            r.addContributions((Series) auxiliar);
                    }
                }
                IMDB.getInstance().users.add(r);
            }
            if (y.contains("Admin")) {
                Admin r = objectMapper.readValue(y, Admin.class);
                JsonNode obj = objectMapper.readTree(y);
                if (obj.get("favoriteActors") != null) {
                    for (int j = 0; j < obj.get("favoriteActors").size(); j++) {
                        String aux = obj.get("favoriteActors").get(j).asText();
                        Actor auxiliar = findActors(aux);
                        r.addFavorites(auxiliar);
                    }
                }
                if (obj.get("favoriteProductions") != null) {
                    for (int j = 0; j < obj.get("favoriteProductions").size(); j++) {
                        String aux = obj.get("favoriteProductions").get(j).asText();
                        Object auxiliar = findProduction(aux);
                        if (auxiliar.getClass() == Movie.class)
                            r.addFavorites((Movie) auxiliar);
                        else
                            r.addFavorites((Series) auxiliar);
                    }
                }
                if (obj.get("actorsContribution") != null) {
                    for (int j = 0; j < obj.get("actorsContribution").size(); j++) {
                        String aux = obj.get("actorsContribution").get(j).asText();
                        Actor auxiliar = findActors(aux);
                        r.addContributions(auxiliar);
                    }
                }
                if (obj.get("productionsContribution") != null) {
                    for (int j = 0; j < obj.get("productionsContribution").size(); j++) {
                        String aux = obj.get("productionsContribution").get(j).asText();
                        Object auxiliar = findProduction(aux);
                        if (auxiliar.getClass() == Movie.class)
                            r.addContributions((Movie) auxiliar);
                        else
                            r.addContributions((Series) auxiliar);
                    }
                }
                IMDB.getInstance().users.add(r);
            }
        }

    }

    //metode de flow
    public void loadData() throws IOException {
        IMDB.getInstance().citireActors();
        IMDB.getInstance().citireProductions();
        IMDB.getInstance().citireUsres();
        IMDB.getInstance().citireRequests();

    }

    public User login() {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Bine ati revenit!");
        System.out.println("Introduceti email : ");
        String email = scanner1.nextLine();
        System.out.println("Introduceti parola : ");
        String password = scanner1.nextLine();
        List<User> aux = IMDB.getInstance().users;
        boolean found = false;
        for (int i = 0; i < aux.size(); i++)
            if (aux.get(i).getInformation().getCredentials().getEmail().equals(email) && aux.get(i).getInformation().getCredentials().getPassword().equals(password)){
                System.out.println("Bine ati revenit utilizator " + aux.get(i).getUsername() + "!");
                System.out.println("Username : " + aux.get(i).getUsername());
                if(aux.get(i).getUserType()!=AccountType.Admin) {
                    System.out.println("User experience : " + aux.get(i).getExperience());
                }else {
                    System.out.println("User experience : -");
                }
                return aux.get(i);
            }

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Email-ul sau parola nu corespund! Alegeti optinuea :");
        System.out.println("1) Incearca din nou");
        System.out.println("2) Iesire");
        int choice = 0;
        while (choice != 1 && choice != 2) {
            int ok = 0;
            try {
                choice = scanner2.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Introduceti un numar intre 1 si 2 : ");
                scanner2.nextLine();
                ok = 1;
            }
            if (choice != 1 && choice != 2 && ok == 0)
                System.out.println("Introduceti un numar intre 1 si 2 : ");
        }
        if (choice == 1) {
            return login();
        }
        return null;
    }

    public void flowRegular(Regular r) throws IOException {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        if (r == null)
            return;
        while (running) {
            System.out.println("Alegeti operatia dorita : ");
            System.out.println("1) Afisati productiile din sistem");
            System.out.println("2) Afisati actorii din sistem");
            System.out.println("3) Afisati notificarile primite");
            System.out.println("4) Cauta un film/serial/actor");
            System.out.println("5) Adăugarea/Stergerea unei producii/actor din lista de favorite");
            System.out.println("6) Crearea/Retragerea unei cereri");
            System.out.println("7) Adaugarea/Stergerea unei recenzii pentru o productie");
            System.out.println("8) Logout!");
            int choice = 0;
            while (choice < 1 || choice > 8) {
                int ok = 0;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Alegeti un numar intre in 1 si 8 : ");
                    ok = 1;
                    scanner.nextLine();
                }
                if ((choice < 1 || choice > 8) && ok == 0)
                    System.out.println("Alegeti un numar intre in 1 si 8 : ");
            }
            switch (choice) {
                case 1:
                    Functions.SelectShowProductions();
                    break;
                case 2:
                    Functions.showActors();
                    break;
                case 3:
                    System.out.println(r.getNotifications());
                    break;
                case 4:
                    System.out.println("Introduce numele filmului/serialului/actorului pe care doresti sa il cauti : ");
                    Object o = Functions.find();
                    if (o != null)
                        System.out.println(o);
                    else
                        System.out.println("Filmul/serialul/actorul cautat nu a fost gasit!");
                    break;
                case 5:
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Introduceti numele filmului/serialului/actorului pe care vreti sa il adaugati sau sa il stergeti din lista de favorite");
                    String name = scanner1.nextLine();
                    System.out.println("Alegeti optiunea dortia : ");
                    System.out.println("1) Adaugare");
                    System.out.println("2) Stergere");
                    int choice1 = 0;
                    while (choice1 < 1 || choice1 > 2) {
                        int ok = 0;
                        try {
                            choice1 = scanner1.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                            scanner1.nextLine();
                            ok = 1;
                        }
                        if ((choice1 < 1) || (choice1 > 2) && ok == 0)
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                    }
                    switch (choice1) {
                        case 1:
                            Object o1 = Functions.find(name);
                            r.addFavorites(o1);
                            System.out.println(r.getFavorites());
                            break;
                        case 2:
                            Object o2 = Functions.find(name);
                            if (o2 != null)
                                r.deleteFavorites(o2);
                            System.out.println(r.getFavorites());
                            break;
                    }

                    break;
                case 6:
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Creare");
                    System.out.println("2) Retragere");
                    int choice2 = 0;
                    while (choice2 != 1 && choice2 != 2) {
                        int ok = 0;
                        try {
                            choice2 = scanner2.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                            scanner2.nextLine();
                            ok = 1;
                        }
                        if (choice2 != 1 && choice2 != 2 && ok == 0)
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                    }
                    switch (choice2) {
                        case 1:
                            r.createRequest();
                            break;
                        case 2:
                            r.removeRequest();
                            break;
                    }
                    break;
                case 7:
                    Scanner prod = new Scanner(System.in);
                    System.out.println("Introduceti numele productiei : ");
                    String prodName = prod.nextLine();
                    Production p = (Production) Functions.find(prodName);
                    if (p == null) {
                        System.out.println("Productia nu a fost gasita!");
                        break;
                    }
                    Scanner ch = new Scanner(System.in);
                    System.out.println("Alegeti operatia dorita : ");
                    System.out.println("1) Adaugare");
                    System.out.println("2) Stergere");
                    int ch2 = 0;
                    while (ch2 != 1 && ch2 != 2) {
                        int ok = 0;
                        try {
                            ch2 = ch.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si 2 : ");
                            ch.nextLine();
                            ok = 1;
                        }
                        if (ch2 != 1 && ch2 != 2 && ok == 0)
                            System.out.println("Alegeti un numar intre 1 si 2 :");
                    }
                    switch (ch2) {
                        case 1:
                            r.addRating(p);
                            break;
                        case 2:
                            r.deleteRating(p);
                            break;
                    }
                    break;
                case 8:
                    running = r.logout();
                    break;
            }
        }
        System.out.println("Alegeti operatia dorita : ");
        System.out.println("1) Autentificare cu un alt cont");
        System.out.println("2) Inchide aplicatia");
        int choice2 = 0;
        while (choice2 != 1 && choice2 != 2) {
            int ok = 0;
            try {
                choice2 = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un numar intre 1 si 2 : ");
                scanner.nextLine();
                ok = 1;
            }
            if (choice2 != 1 && choice2 != 2 && ok == 0)
                System.out.println("Alegeti un numar intre 1 si 2 : ");
        }
        if (choice2 == 1)
            IMDB.getInstance().run();
    }

    public void flowContributor(Contributor r) throws IOException {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        if (r == null)
            return;
        while (running) {
            System.out.println("Alegeti operatia dorita : ");
            System.out.println("1) Afisati productiile din sistem");
            System.out.println("2) Afisati actorii din sistem");
            System.out.println("3) Afisati notificarile primite");
            System.out.println("4) Cauta un film/serial/actor");
            System.out.println("5) Adăugarea/Stergerea unei producii/actor din lista de favorite");
            System.out.println("6) Crearea/Retragerea unei cereri");
            System.out.println("7) Adaugarea/Stergerea unei productii/actor din sistem");
            System.out.println("8) Vizualizarea si rezolvarea cererilor primite");
            System.out.println("9) Actualizarea informatiilor despre productii/actori");
            System.out.println("10) Logout!");
            int choice = 0;
            while (choice < 1 || choice > 10) {
                int ok = 0;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Alegeti un numar intre in 1 si 10 : ");
                    ok = 1;
                    scanner.nextLine();
                }
                if ((choice < 1 || choice > 10) && ok == 0)
                    System.out.println("Alegeti un numar intre in 1 si 10 : ");
            }
            switch (choice) {
                case 1:
                    Functions.SelectShowProductions();
                    break;
                case 2:
                    Functions.showActors();
                    break;
                case 3:
                    System.out.println(r.getNotifications());
                    break;
                case 4:
                    System.out.println("Introduce numele filmului/serialului/actorului pe care doresti sa il cauti : ");
                    Object o = Functions.find();
                    if (o != null)
                        System.out.println(o);
                    else
                        System.out.println("Filmul/serialul/actorul cautat nu a fost gasit!");
                    break;
                case 5:
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Introduceti numele filmului/serialului/actorului pe care vreti sa il adaugati sau sa il stergeti din lista de favorite");
                    String name = scanner1.nextLine();
                    System.out.println("Alegeti optiunea dortia : ");
                    System.out.println("1) Adaugare");
                    System.out.println("2) Stergere");
                    int choice1 = 0;
                    while (choice1 < 1 || choice1 > 2) {
                        int ok = 0;
                        try {
                            choice1 = scanner1.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                            scanner1.nextLine();
                            ok = 1;
                        }
                        if ((choice1 < 1) || (choice1 > 2) && ok == 0)
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                    }
                    switch (choice1) {
                        case 1:
                            Object o1 = Functions.find(name);
                            r.addFavorites(o1);
                            System.out.println(r.getFavorites());
                            break;
                        case 2:
                            Object o2 = Functions.find(name);
                            if (o2 != null)
                                r.deleteFavorites(o2);
                            System.out.println(r.getFavorites());
                            break;
                    }

                    break;
                case 6:
                    Scanner scanner2 = new Scanner(System.in);
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Creare");
                    System.out.println("2) Retragere");
                    int choice2 = 0;
                    while (choice2 != 1 && choice2 != 2) {
                        int ok = 0;
                        try {
                            choice2 = scanner2.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                            scanner2.nextLine();
                            ok = 1;
                        }
                        if (choice2 != 1 && choice2 != 2 && ok == 0)
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                    }
                    switch (choice2) {
                        case 1:
                            r.createRequest();
                            break;
                        case 2:
                            r.removeRequest();
                            break;
                    }
                    break;
                case 7:
                    Scanner scanner3 = new Scanner(System.in);
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Adaugare");
                    System.out.println("2) Stergere");
                    int choice3 = 0;
                    while (choice3 != 1 && choice3 != 2) {
                        int ok = 0;
                        try {
                            choice3 = scanner3.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si 2 : ");
                            scanner3.nextLine();
                            ok = 1;
                        }
                        if (choice3 != 1 && choice3 != 2 && ok == 0)
                            System.out.println("Alegeti un numar intre 1 si 2 : ");
                    }
                    switch (choice3) {
                        case 1:
                            System.out.println("Introduceti tipul dorit :");
                            System.out.println("1) Actor");
                            System.out.println("2) Movie");
                            System.out.println("3) Series");
                            int choice4 = 0;
                            while (choice4 < 1 || choice4 > 3) {
                                int ok = 0;
                                try {
                                    choice4 = scanner3.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Alegeti un numar intre 1 si 3 : ");
                                    scanner3.nextLine();
                                    ok = 1;
                                }
                                if ((choice4 < 1 || choice4 > 3) && ok == 0)
                                    System.out.println("Alegeti un numar intre 1 si 3 : ");
                            }
                            switch (choice4) {
                                case 1:
                                    Actor a = Functions.addActorToSystem();
                                    r.addActorSystem(a);
                                    break;
                                case 2:
                                    Movie m = Functions.addMovieToSystem();
                                    r.addProductionSystem(m);
                                    break;
                                case 3:
                                    Series s = Functions.addSeriesToSystem();
                                    r.addProductionSystem(s);
                            }
                            AddProductionStrategy exp = new AddProductionStrategy();
                            r.updateExperience(exp);
                            break;
                        case 2:
                            System.out.println("Alegeti actorul/serialul/filmul pe care doriti sa il stergeti din sistem : ");
                            Object[] x = r.getContributions().toArray();
                            for (int i = 0; i < x.length; i++)
                                System.out.println(i + 1 + ") " + x[i]);
                            Scanner scanner4 = new Scanner(System.in);
                            int rem = 0;
                            while (rem < 1 || rem > x.length) {
                                int ok = 0;
                                try {
                                    rem = scanner4.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Introduceti un numar intre 1 si " + x.length + " : ");
                                    scanner4.nextLine();
                                    ok = 1;
                                }
                                if ((rem < 1 || rem > x.length) && ok == 0)
                                    System.out.println("Introduceti un numar intre 1 si " + x.length + " : ");
                            }
                            if (x[rem - 1].getClass() == Actor.class)
                                r.removeActorSystem(((Actor) x[rem - 1]).getName());
                            else
                                r.removeProductionSystem(((Production) x[rem - 1]).getTitle());
                            break;
                    }
                    break;
                case 8:
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Vizualizare");
                    System.out.println("2) Rezolvare");
                    Scanner scann = new Scanner(System.in);
                    int ch = 0;
                    while (ch < 1 || ch > 2) {
                        int ok = 0;
                        try {
                            ch = scann.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                            scann.nextLine();
                            ok = 1;
                        }
                        if ((ch < 1 || ch > 2) && ok == 0)
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                    }
                    switch (ch) {
                        case 1:
                            r.showMyRequests();
                            break;
                        case 2:
                            r.resolveMyRequests();
                            break;
                    }
                    break;
                case 9:
                    System.out.println("Alegeti actorul/filmul/serialul pe care doriti sa il modificati : ");
                    Object[] x = r.getContributions().toArray();
                    for (int i = 0; i < x.length; i++)
                        System.out.println(i + 1 + ") " + x[i]);
                    Scanner scanner4 = new Scanner(System.in);
                    int rem = 0;
                    while (rem < 1 || rem > x.length) {
                        int ok = 0;
                        try {
                            rem = scanner4.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un numar intre 1 si " + x.length + " : ");
                            scanner4.nextLine();
                            ok = 1;
                        }
                        if ((rem < 1 || rem > x.length) && ok == 0)
                            System.out.println("Introduceti un numar intre 1 si " + x.length + " : ");
                    }
                    Object o2 = x[rem - 1];
                    if (o2.getClass() == Actor.class)
                        r.updateActor((Actor) o2);
                    if (o2.getClass() == Movie.class)
                        r.updateProduction((Movie) o2);
                    if (o2.getClass() == Series.class)
                        r.updateProduction((Series) o2);
                    System.out.println(o2);
                    break;
                case 10:
                    running = r.logout();
                    break;
            }
        }
        System.out.println("Alegeti operatia dorita : ");
        System.out.println("1) Autentificare cu un alt cont");
        System.out.println("2) Inchide aplicatia");
        int choice2 = 0;
        while (choice2 != 1 && choice2 != 2) {
            int ok = 0;
            try {
                choice2 = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un numar intre 1 si 2 : ");
                scanner.nextLine();
                ok = 1;
            }
            if (choice2 != 1 && choice2 != 2 && ok == 0)
                System.out.println("Alegeti un numar intre 1 si 2 : ");
        }
        if (choice2 == 1)
            IMDB.getInstance().run();
    }

    public void flowAdmin(Admin r) throws IOException {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        if (r == null)
            return;
        while (running) {
            System.out.println("Alegeti operatia dorita : ");
            System.out.println("1) Afisati productiile din sistem");
            System.out.println("2) Afisati actorii din sistem");
            System.out.println("3) Afisati notificarile primite");
            System.out.println("4) Cauta un film/serial/actor");
            System.out.println("5) Adăugarea/Stergerea unei producii/actor din lista de favorite");
            System.out.println("6) Adaugarea/Stergerea unei productii/actor din sistem");
            System.out.println("7) Vizualizarea si rezolvarea cererilor primite");
            System.out.println("8) Actualizarea informatiilor despre productii/actori");
            System.out.println("9) Adaugare/Stergere utilizator");
            System.out.println("10) Logout!");
            int choice = 0;
            while (choice < 1 || choice > 10) {
                int ok = 0;
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Alegeti un numar intre in 1 si 10 : ");
                    ok = 1;
                    scanner.nextLine();
                }
                if ((choice < 1 || choice > 10) && ok == 0)
                    System.out.println("Alegeti un numar intre in 1 si 10: ");
            }
            switch (choice) {
                case 1:
                    Functions.SelectShowProductions();
                    break;
                case 2:
                    Functions.showActors();
                    break;
                case 3:
                    System.out.println(r.getNotifications());
                    break;
                case 4:
                    System.out.println("Introduce numele filmului/serialului/actorului pe care doresti sa il cauti : ");
                    Object o = Functions.find();
                    if (o != null)
                        System.out.println(o);
                    else
                        System.out.println("Filmul/serialul/actorul cautat nu a fost gasit!");
                    break;
                case 5:
                    Scanner scanner1 = new Scanner(System.in);
                    System.out.println("Introduceti numele filmului/serialului/actorului pe care vreti sa il adaugati sau sa il stergeti din lista de favorite");
                    String name = scanner1.nextLine();
                    System.out.println("Alegeti optiunea dortia : ");
                    System.out.println("1) Adaugare");
                    System.out.println("2) Stergere");
                    int choice1 = 0;
                    while (choice1 < 1 || choice1 > 2) {
                        int ok = 0;
                        try {
                            choice1 = scanner1.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                            scanner1.nextLine();
                            ok = 1;
                        }
                        if ((choice1 < 1) || (choice1 > 2) && ok == 0)
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                    }
                    switch (choice1) {
                        case 1:
                            Object o1 = Functions.find(name);
                            r.addFavorites(o1);
                            System.out.println(r.getFavorites());
                            break;
                        case 2:
                            Object o2 = Functions.find(name);
                            if (o2 != null)
                                r.deleteFavorites(o2);
                            System.out.println(r.getFavorites());
                            break;
                    }
                    break;
                case 6:
                    Scanner scanner3 = new Scanner(System.in);
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Adaugare");
                    System.out.println("2) Stergere");
                    int choice3 = 0;
                    while (choice3 != 1 && choice3 != 2) {
                        int ok = 0;
                        try {
                            choice3 = scanner3.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si 2 : ");
                            scanner3.nextLine();
                            ok = 1;
                        }
                        if (choice3 != 1 && choice3 != 2 && ok == 0)
                            System.out.println("Alegeti un numar intre 1 si 2 : ");
                    }
                    switch (choice3) {
                        case 1:
                            System.out.println("Introduceti tipul dorit :");
                            System.out.println("1) Actor");
                            System.out.println("2) Movie");
                            System.out.println("3) Series");
                            int choice4 = 0;
                            while (choice4 < 1 || choice4 > 3) {
                                int ok = 0;
                                try {
                                    choice4 = scanner3.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Alegeti un numar intre 1 si 3 : ");
                                    scanner3.nextLine();
                                    ok = 1;
                                }
                                if ((choice4 < 1 || choice4 > 3) && ok == 0)
                                    System.out.println("Alegeti un numar intre 1 si 3 : ");
                            }
                            switch (choice4) {
                                case 1:
                                    Actor a = Functions.addActorToSystem();
                                    r.addActorSystem(a);
                                    break;
                                case 2:
                                    Movie m = Functions.addMovieToSystem();
                                    r.addProductionSystem(m);
                                    break;
                                case 3:
                                    Series s = Functions.addSeriesToSystem();
                                    r.addProductionSystem(s);
                            }
                            break;
                        case 2:
                            System.out.println("Alegeti actorul/serialul/filmul pe care doriti sa il stergeti din sistem : ");
                            Object[] x = r.getContributions().toArray();
                            for (int i = 0; i < x.length; i++)
                                System.out.println(i + 1 + ") " + x[i]);
                            Scanner scanner4 = new Scanner(System.in);
                            int rem = 0;
                            while (rem < 1 || rem > x.length) {
                                int ok = 0;
                                try {
                                    rem = scanner4.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Introduceti un numar intre 1 si " + x.length + " : ");
                                    scanner4.nextLine();
                                    ok = 1;
                                }
                                if ((rem < 1 || rem > x.length) && ok == 0)
                                    System.out.println("Introduceti un numar intre 1 si " + x.length + " : ");
                            }
                            if (x[rem - 1].getClass() == Actor.class)
                                r.removeActorSystem(((Actor) x[rem - 1]).getName());
                            else
                                r.removeProductionSystem(((Production) x[rem - 1]).getTitle());
                            break;
                    }
                    break;
                case 7:
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Vizualizare");
                    System.out.println("2) Rezolvare");
                    Scanner scann = new Scanner(System.in);
                    int ch = 0;
                    while (ch < 1 || ch > 2) {
                        int ok = 0;
                        try {
                            ch = scann.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                            scann.nextLine();
                            ok = 1;
                        }
                        if ((ch < 1 || ch > 2) && ok == 0)
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                    }
                    switch (ch) {
                        case 1:
                            System.out.println("Alegeti optiunea dorita : ");
                            System.out.println("1) Lista mea de cereri");
                            System.out.println("2) Lista tuturor adminilor de cereri");
                            Scanner scann2 = new Scanner(System.in);
                            int ch2 = 0;
                            while (ch2 < 1 || ch2 > 2) {
                                int ok = 0;
                                try {
                                    ch2 = scann2.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Alegeti un numar intre 1 si 2 : ");
                                    scann2.nextLine();
                                    ok = 1;
                                }
                                if ((ch2 < 1 || ch2 > 2) && ok == 0)
                                    System.out.println("Alegeti un numar intre 1 si 2 : ");
                            }
                            switch (ch2) {
                                case 1:
                                    r.showMyRequests();
                                    break;
                                case 2:
                                    r.showAdminRequests();
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("Alegeti optiunea dorita : ");
                            System.out.println("1) Lista mea de cereri");
                            System.out.println("2) Lista tuturor adminilor de cereri");
                            Scanner scann3 = new Scanner(System.in);
                            int ch3 = 0;
                            while (ch3 < 1 || ch3 > 2) {
                                int ok = 0;
                                try {
                                    ch2 = scann3.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Alegeti un numar intre 1 si 2 : ");
                                    scann3.nextLine();
                                    ok = 1;
                                }
                                if ((ch3 < 1 || ch3 > 2) && ok == 0)
                                    System.out.println("Alegeti un numar intre 1 si 2 : ");
                            }
                            switch (ch3) {
                                case 1:
                                    r.resolveMyRequests();
                                    break;
                                case 2:
                                    r.resolveAdminRequests();
                                    break;
                            }
                            break;
                    }
                    break;
                case 8:
                    System.out.println("Alegeti actorul/filmul/serialul pe care doriti sa il modificati : ");
                    Object[] x = r.getContributions().toArray();
                    for (int i = 0; i < x.length; i++)
                        System.out.println(i + 1 + ") " + x[i]);
                    Scanner scanner4 = new Scanner(System.in);
                    int rem = 0;
                    while (rem < 1 || rem > x.length) {
                        int ok = 0;
                        try {
                            rem = scanner4.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un numar intre 1 si " + x.length + " : ");
                            scanner4.nextLine();
                            ok = 1;
                        }
                        if ((rem < 1 || rem > x.length) && ok == 0)
                            System.out.println("Introduceti un numar intre 1 si " + x.length + " : ");
                    }
                    Object o2 = x[rem - 1];
                    if (o2.getClass() == Actor.class)
                        r.updateActor((Actor) o2);
                    if (o2.getClass() == Movie.class)
                        r.updateProduction((Movie) o2);
                    if (o2.getClass() == Series.class)
                        r.updateProduction((Series) o2);
                    System.out.println(o2);
                    break;
                case 9:
                    Scanner scanner5 = new Scanner(System.in);
                    System.out.println("Alegeti optiunea dortia : ");
                    System.out.println("1) Adaugare");
                    System.out.println("2) Stergere");
                    int choice2 = 0;
                    while (choice2 != 1 && choice2 != 2) {
                        int ok = 0;
                        try {
                            choice2 = scanner5.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                            scanner5.nextLine();
                            ok = 1;
                        }
                        if (choice2 != 1 && choice2 != 2 && ok == 0)
                            System.out.println("Introduceti un numar intre 1 si 2 : ");
                    }
                    switch (choice2) {
                        case 1:
                            r.addUser();
                            break;
                        case 2:
                            System.out.println(IMDB.getInstance().getUsers());
                            break;
                    }
                    break;
                case 10:
                    running = r.logout();
                    break;
            }
        }
        System.out.println("Alegeti operatia dorita : ");
        System.out.println("1) Autentificare cu un alt cont");
        System.out.println("2) Inchide aplicatia");
        int choice2 = 0;
        while (choice2 != 1 && choice2 != 2) {
            int ok = 0;
            try {
                choice2 = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un numar intre 1 si 2 : ");
                scanner.nextLine();
                ok = 1;
            }
            if (choice2 != 1 && choice2 != 2 && ok == 0)
                System.out.println("Alegeti un numar intre 1 si 2 : ");
        }
        if (choice2 == 1)
            IMDB.getInstance().run();
    }

    // run
    public void run() throws IOException {
        loadData();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Alegeti modul de utilizare al aplicatiei :");
        System.out.println("1) CLI");
        System.out.println("2) GUI");
        int choice = 0;
        while (choice != 1 && choice != 2) {
            int ok = 0;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Introduce un numar intre 1 si 2 :");
                scanner.nextLine();
                ok = 1;
            }
            if (choice != 1 && choice != 2 && ok == 0)
                System.out.println("Introduce un numar intre 1 si 2 :");
        }
        if (choice == 1) {
            User o = login();
            if (o == null) {
                return;
            }
            if (o.getClass() == Regular.class) {
                Regular r = (Regular) o;
                flowRegular(r);
            }
            if (o.getClass() == Contributor.class) {
                Contributor c = (Contributor) o;
                flowContributor(c);

            }
            if (o.getClass() == Admin.class) {
                Admin a = (Admin) o;
                flowAdmin(a);
            }
        }

    }

    public static void main(String[] args) throws IOException {
        IMDB.getInstance().run();
        System.out.println("La revedere !");
    }
}
