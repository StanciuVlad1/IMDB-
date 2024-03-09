package org.example;

import java.util.*;

public class Functions {

    // productions
    public static void sortProductionsGenre(String genre) {
        List<Production> aux = IMDB.getInstance().getProductions();
        for (Production production : aux)
            for (int j = 0; j < production.getGenres().size(); j++)
                if (production.getGenres().get(j).toString().equalsIgnoreCase(genre))
                    System.out.println(production);


    }

    public static void sortProductionsRating() {
        List<Production> aux = IMDB.getInstance().getProductions();
        for (int i = 0; i < aux.size() - 1; i++)
            for (int j = i; j < aux.size(); j++)
                if (aux.get(i).getRatings().size() < aux.get(j).getRatings().size()) {
                    Production swap = aux.get(i);
                    aux.set(i, aux.get(j));
                    aux.set(j, swap);
                }
        System.out.println(aux);
    }

    public static void SelectShowProductions() {
        System.out.println("Alegeti in ce mod doriti vizualizarea :");
        System.out.println("1) Toate productiile din sistem");
        System.out.println("2) Sorteaza dupa genul dorit");
        System.out.println("3) Sorteaza dupa numarul de rating-uri");
        Scanner scanner1 = new Scanner(System.in);
        int choice = 0;
        while (choice < 1 || choice > 3) {
            int ok = 0;
            try {
                choice = scanner1.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un numar intre 1 si 3 : ");
                scanner1.nextLine();
                ok = 1;
            }
            if ((choice < 1 || choice > 3) && ok == 0)
                System.out.println("Alegeti un numar intre 1 si 3 :");
        }
        switch (choice) {
            case 1:
                System.out.println(IMDB.getInstance().getProductions());
                break;
            case 2:
                System.out.println("Introduceti gen-ul dorit : ");
                String genre = scanner1.next();
                sortProductionsGenre(genre);
                break;
            case 3:
                sortProductionsRating();
                break;
            default:
                System.out.println("NU ATI INTRODUS UN NUMAR INTRE 1 SI 3");

        }
    }

    // actors
    public static void sortActorsName() {
        List<Actor> aux = IMDB.getInstance().getActors();
        for (int i = 0; i < aux.size() - 1; i++)
            for (int j = i; j < aux.size(); j++)
                if (aux.get(i).getName().compareTo(aux.get(j).getName()) > 0) {
                    Actor swap = aux.get(i);
                    aux.set(i, aux.get(j));
                    aux.set(j, swap);

                }
        System.out.println(aux);
    }

    public static void showActors() {
        System.out.println("Alegeti in ce mod doriti vizualizarea :");
        System.out.println("1) Actorii din sistem in ordinea adaugarii");
        System.out.println("2) Actorii sortati  in functie de nume");
        Scanner scanner1 = new Scanner(System.in);
        int choice = 0;
        while (choice < 1 || choice > 2) {
            int ok = 0;
            try {
                choice = scanner1.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un numar intre 1 si 2 : ");
                scanner1.nextLine();
                ok = 1;
            }
            if ((choice < 1 || choice > 2) && ok == 0)
                System.out.println("Alegeti un numar intre 1 si 2 :");
        }
        switch (choice) {
            case 1:
                System.out.println(IMDB.getInstance().getActors());
                break;
            case 2:
                sortActorsName();
                break;
            default:
                System.out.println("NU ATI INTRODUS UN NUMAR INTRE 1 SI 2");

        }

    }

    public static Object find() {
        Scanner scanner1 = new Scanner(System.in);
        String name = scanner1.nextLine();
        List<Actor> actors = IMDB.getInstance().getActors();
        for (Actor actor : actors)
            if (name.equalsIgnoreCase(actor.getName()))
                return actor;
        List<Production> productions = IMDB.getInstance().getProductions();
        for (Production production : productions)
            if (name.equalsIgnoreCase(production.getTitle()))
                return production;
        return null;
    }

    public static Object find(String name) {

        List<Actor> actors = IMDB.getInstance().getActors();
        for (Actor actor : actors)
            if (name.equalsIgnoreCase(actor.getName()))
                return actor;
        List<Production> productions = IMDB.getInstance().getProductions();
        for (Production production : productions)
            if (name.equalsIgnoreCase(production.getTitle()))
                return production;
        List<User> users = IMDB.getInstance().getUsers();
        for (User user : users)
            if (name.equals(user.getUsername()))
                return user;
        return null;
    }

    public static Staff setTo(String name) {
        Object o = find(name);
        if (o == null)
            return null;
        List<User> users = IMDB.getInstance().getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getClass() == Contributor.class) {
                Contributor x = (Contributor) users.get(i);
                for (int j = 0; j < x.getContributions().size(); j++)
                    if (x.getContributions().contains(o))
                        return (Staff) users.get(i);
            }
            if (users.get(i).getClass() == Admin.class) {
                Admin x = (Admin) users.get(i);
                for (int j = 0; j < x.getContributions().size(); j++)
                    if (x.getContributions().contains(o))
                        return (Staff) users.get(i);
            }
        }
        return null;
    }

    public static Actor addActorToSystem() {
        Actor a = new Actor();
        System.out.println("Introduceti numele acestuia : ");
        Scanner scanner4 = new Scanner(System.in);
        a.setName(scanner4.nextLine());
        System.out.println("In continuare va trebui sa introduceti productiile in care actorul a jucat. Adaugarea de productii se va termina atunci cand" +
                "la numele productiei veti introduce end.");
        System.out.println("Introduceti titlul productiei : ");
        Scanner scanner5 = new Scanner(System.in);
        String name = scanner5.nextLine();
        while (!name.equalsIgnoreCase("end")) {
            System.out.println("Introduceti tipul productiei : ");
            String type = scanner5.nextLine();
            Map<String, String> x = new TreeMap<>();
            x.put("title", name);
            x.put("type", type);
            a.getPerformances().add(x);
            System.out.println("Introduceti titlul productiei :");
            name = scanner5.nextLine();
        }
        System.out.println("Introduceti biografia actorului : ");
        Scanner scan = new Scanner(System.in);
        String bio = scan.nextLine();
        a.setBiography(bio);
        return a;
    }

    public static Movie addMovieToSystem() {
        Movie a = new Movie();
        System.out.println("Introduceti titlul acestuia :");
        Scanner scanner4 = new Scanner(System.in);
        a.setTitle(scanner4.nextLine());
        a.setType("Movie");
        System.out.println("In continuare va trebui sa introduceti regizorii filmului. Adaugarea de regizori se va termina atunci cand" +
                "veti introduce end.");
        System.out.println("Introduceti numele regizorului : ");
        Scanner scanner5 = new Scanner(System.in);
        String name = scanner5.nextLine();
        while (!name.equalsIgnoreCase("end")) {
            a.getDirectors().add(name);
            System.out.println("Introduceti numele regizorului : ");
            name = scanner5.nextLine();
        }
        System.out.println("In continuare va trebui sa introduceti actorii filmului. Adaugarea de actori se va termina atunci cand" +
                "veti introduce end.");
        System.out.println("Introduceti numele actorului : ");
        Scanner scanner6 = new Scanner(System.in);
        String name1 = scanner6.nextLine();
        while (!name1.equalsIgnoreCase("end")) {
            a.getActors().add(name1);
            System.out.println("Introduceti numele actorului :");
            name1 = scanner6.nextLine();
        }
        System.out.println("Introduceti genurile filmului din urmatoarea lista : ");
        Genre[] genres = Genre.values();
        for (int i = 0; i < genres.length; i++) {
            System.out.println(i + 1 + ") " + genres[i]);
        }
        System.out.println(genres.length + 1 + ") End");
        System.out.println("Introduceti genul : ");
        Scanner scanner7 = new Scanner(System.in);
        int genre = 0;
        while (genre < 1 || genre > genres.length + 1) {
            int ok = 0;
            try {
                genre = scanner7.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Introduceti un numar valabil din lista : ");
                scanner7.nextLine();
                ok = 1;
                if ((genre < 1 || genre > genres.length + 1) && ok == 0)
                    System.out.println("Introduceti un numar valabil din lista : ");
            }

        }
        while (genre != genres.length + 1) {
            if (!a.getGenres().contains(genres[genre - 1]))
                a.getGenres().add(genres[genre - 1]);
            System.out.println("Introduceti genul : ");
            genre = 0;
            while (genre < 1 || genre > genres.length + 1) {
                int ok = 0;
                try {
                    genre = scanner7.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Introduceti un numar valabil din lista : ");
                    scanner7.nextLine();
                    ok = 1;
                }
                if ((genre < 1 || genre > genres.length + 1) && ok == 0)
                    System.out.println("Introduceti un numar valabil din lista : ");
            }

        }

        Scanner scanner8 = new Scanner(System.in);
        System.out.println("Introduceti plotul filmului : ");
        a.setPlot(scanner8.nextLine());
        Scanner scanner9 = new Scanner(System.in);
        System.out.println("Introduceti durata filmului :");
        a.setDuration(scanner9.nextLine());
        Scanner scanner10 = new Scanner(System.in);
        System.out.println("Introduceti anul de aparitie al filmului : ");
        int ok = 0;
        int an = 0;
        while (ok == 0) {
            ok = 1;
            try {

                an = scanner10.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Introduceti un an valid compus doar din cifre :");
                ok = 0;
                scanner10.nextLine();
            }
        }
        a.setReleaseYear(an);
        return a;
    }

    public static Series addSeriesToSystem() {
        Series a = new Series();
        System.out.println("Introduceti titlul acestuia :");
        Scanner scanner4 = new Scanner(System.in);
        a.setTitle(scanner4.nextLine());
        a.setType("Series");
        System.out.println("In continuare va trebui sa introduceti regizorii serialului. Adaugarea de regizori se va termina atunci cand" +
                "veti introduce end.");
        System.out.println("Introduceti numele regizorului : ");
        Scanner scanner5 = new Scanner(System.in);
        String name = scanner5.nextLine();
        while (!name.equalsIgnoreCase("end")) {
            a.getDirectors().add(name);
            System.out.println("Introduceti numele regizorului : ");
            name = scanner5.nextLine();
        }
        System.out.println("In continuare va trebui sa introduceti actorii serialului. Adaugarea de actori se va termina atunci cand" +
                "veti introduce end.");
        System.out.println("Introduceti numele actorului : ");
        Scanner scanner6 = new Scanner(System.in);
        String name1 = scanner6.nextLine();
        while (!name1.equalsIgnoreCase("end")) {
            a.getActors().add(name1);
            System.out.println("Introduceti numele actorului :");
            name1 = scanner6.nextLine();
        }
        System.out.println("Introduceti genurile serialului din urmatoarea lista : ");
        Genre[] genres = Genre.values();
        for (int i = 0; i < genres.length; i++) {
            System.out.println(i + 1 + ") " + genres[i]);
        }
        System.out.println(genres.length + 1 + ") End");
        System.out.println("Introduceti genul : ");
        Scanner scanner7 = new Scanner(System.in);
        int genre = 0;
        while (genre < 1 || genre > genres.length + 1) {
            int ok = 0;
            try {
                genre = scanner7.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Introduceti un numar valabil din lista : ");
                scanner7.nextLine();
                ok = 1;
                if ((genre < 1 || genre > genres.length + 1) && ok == 0)
                    System.out.println("Introduceti un numar valabil din lista : ");
            }

        }
        while (genre != genres.length + 1) {
            if (!a.getGenres().contains(genres[genre - 1]))
                a.getGenres().add(genres[genre - 1]);
            System.out.println("Introduceti genul : ");
            genre = 0;
            while (genre < 1 || genre > genres.length + 1) {
                int ok = 0;
                try {
                    genre = scanner7.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Introduceti un numar valabil din lista : ");
                    scanner7.nextLine();
                    ok = 1;
                }
                if ((genre < 1 || genre > genres.length + 1) && ok == 0)
                    System.out.println("Introduceti un numar valabil din lista : ");
            }

        }
        Scanner scanner8 = new Scanner(System.in);
        System.out.println("Introduceti plotul serialului : ");
        a.setPlot(scanner8.nextLine());
        Scanner scanner9 = new Scanner(System.in);
        System.out.println("Introduceti anul lansarii : ");
        int ok = 0;
        int an = 0;
        while (ok == 0) {
            ok = 1;
            try {

                an = scanner9.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Introduceti un an valid compus doar din cifre :");
                ok = 0;
                scanner9.nextLine();
            }
        }
        a.setReleaseYear(an);
        Scanner scanner10 = new Scanner(System.in);
        System.out.println("Introduceti numarul de sezoane : ");
        ok = 0;
        int nr = 0;
        while (ok == 0) {
            ok = 1;
            try {

                nr = scanner10.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Introduceti un numar valid compus doar din cifre :");
                ok = 0;
                scanner10.nextLine();
            }
        }
        a.setNumSeasons(nr);
        System.out.println("In continuare veti introduce pe rand episoadele fiecarui sezon.Atunci cand vreti sa treceti la urmatorul sezon, introduceti end la numele episodului curent.");
        for (int i = 0; i < a.getNumSeasons(); i++) {
            List<Episode> ep = new ArrayList<>();
            System.out.println("Introduceti numele episodului : ");
            Scanner scanner11 = new Scanner(System.in);
            String name2 = scanner11.nextLine();
            while (!name2.equalsIgnoreCase("end")) {
                Scanner scanner12 = new Scanner(System.in);
                System.out.println("Introduceti durata episodului : ");
                String duration = scanner12.nextLine();
                Episode x = new Episode();
                x.setDuration(duration);
                x.setEpisodeName(name2);
                System.out.println("Introduceti numele episodului : ");
                name2 = scanner12.nextLine();
                ep.add(x);
            }
            String nameOfSeason = "Season " + i;
            a.getSeasons().put(nameOfSeason, ep);
        }
        return a;

    }
}
