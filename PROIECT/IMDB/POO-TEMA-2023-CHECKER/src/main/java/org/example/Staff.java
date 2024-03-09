package org.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public abstract class Staff<T extends Comparable<T>> extends User implements StaffInterface {
    // field-uri
    @JsonIgnore
    private List<Request> requestList = new ArrayList<>();
    private SortedSet<T> contributions;
    // gettere si settere


    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }

    public SortedSet<T> getContributions() {
        return contributions;
    }

    public void setContributions(SortedSet<T> contributions) {
        this.contributions = contributions;
    }
    // constructor

    public Staff() {
    }

    public Staff(String username, int experience, Information information, AccountType userType, SortedSet favorites, List notifications, List<Request> requestList, SortedSet<T> contributions) {
        super(username, experience, information, userType, favorites, notifications);
        this.requestList = requestList;
        this.contributions = contributions;
    }

    // toString

    @Override
    public String toString() {
        return "Staff{" +
                "\nrequestList=" + requestList +
                ",\ncontributions=" + contributions +
                "\n}\n" + super.toString();
    }

    //metode auxiliare
    public void addContributions(Object e) {
        if (contributions == null)
            contributions = new TreeSet<>();
        contributions.add((T) e);
    }

    public void dellContributions(Object e) {
        contributions.remove((T) e);
    }

    // metoda de show requests
    public void showMyRequests() {
        for (int i = 0; i < this.getRequestList().size(); i++)
            System.out.println(i + 1 + " )" + this.getRequestList().get(i));
    }

    public void resolveMyRequests() {
        System.out.println("Alegeti din urmatoarea lista cererea pe care doriti sa o rezolvati : ");
        showMyRequests();
        Scanner scan = new Scanner(System.in);
        int ch = 0;
        while (ch < 1 || ch > this.getRequestList().size()) {
            int ok = 0;
            try {
                ch = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un numar intre 1 si " + this.getRequestList().size() + " : ");
                scan.nextLine();
                ok = 1;
            }
            if ((ch < 1 || ch > this.getRequestList().size()) && ok == 0)
                System.out.println("Alegeti un numar intre 1 si " + this.getRequestList().size() + " : ");
        }
        Request r = this.getRequestList().get(ch - 1);
        User u = (User) Functions.find(r.getUsername());
        System.out.println("Cerea a fost : ");
        System.out.println("1) Rezolvata");
        System.out.println("2) Respinsa");
        Scanner scan2 = new Scanner(System.in);
        int ch2 = 0;
        while (ch2 < 1 || ch2 > 2) {
            int ok = 0;
            try {
                ch2 = scan2.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un numar intre 1 si 2 : ");
                scan2.nextLine();
                ok = 1;
            }
            if ((ch2 < 1 || ch2 > 2) && ok == 0)
                System.out.println("Alegeti un numar intre 1 si 2 : ");
        }
        if (ch2 == 1) {
            AddRequestExperienceStrategy exp = new AddRequestExperienceStrategy();
            u.updateExperience(exp);
            System.out.println(u);
        }
        this.getRequestList().remove(r);
        IMDB.getInstance().getRequests().remove(r);

    }

    // metode de sistem
    public void updateProduction(Production p) {
        int index = 0;
        for (int i = 0; i < IMDB.getInstance().getProductions().size(); i++)
            if (IMDB.getInstance().getProductions().get(i).equals(p))
                index = i;
        if (p.getClass() == Movie.class) {
            Movie a = (Movie) p;
            System.out.println("Introduceti ce vreti sa modificati : ");
            System.out.println("1) Titlul");
            System.out.println("2) Tipul");
            System.out.println("3) Regizorii");
            System.out.println("4) Actorii");
            System.out.println("5) Genurile");
            System.out.println("6) Plot-ul");
            System.out.println("7) Durata");
            System.out.println("8) Anul lansarii");
            Scanner scan = new Scanner(System.in);
            Scanner text = new Scanner(System.in);
            int ch = 0;
            while (ch < 1 || ch > 8) {
                int ok = 0;
                try {
                    ch = scan.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Alege un numar intre 1 si 8 : ");
                    scan.nextLine();
                    ok = 1;
                }
                if ((ch < 1 || ch > 8) && ok == 0)
                    System.out.println("Alege un numar intre 1 si 8 : ");
            }
            switch (ch) {
                case 1:
                    System.out.println("Introduceti noul titlu");
                    a.setTitle(text.nextLine());
                    break;
                case 2:
                    System.out.println("Introduceti noul tip");
                    a.setType(text.nextLine());
                    break;
                case 3:
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Adaugarea unui nou regizor");
                    System.out.println("2) Stergerea unui regizor");
                    Scanner text2 = new Scanner(System.in);
                    Scanner scan2 = new Scanner(System.in);
                    int ch2 = 0;
                    while (ch2 < 1 || ch2 > 2) {
                        int ok = 0;
                        try {
                            ch2 = scan2.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si 2 ");
                            scan2.nextLine();
                            ok = 1;
                        }
                        if ((ch2 < 1 || ch2 > 2) && ok == 0)
                            System.out.println("Alegeti un numar intre 1 si 2 ");

                    }
                    switch (ch2) {
                        case 1:
                            System.out.println("Introduceti numele noului regizor : ");
                            a.getDirectors().add(text2.nextLine());
                            break;
                        case 2:
                            System.out.println("Lista actuala a regizorilor : ");
                            for (int i = 0; i < a.getDirectors().size(); i++)
                                System.out.println(i + 1 + ") " + a.getDirectors().get(i));
                            System.out.println("Alegeti regizorul pe care doriti sa il eliminati : ");
                            int reg = 0;
                            while (reg < 1 || reg > a.getDirectors().size()) {
                                int ok = 0;
                                try {
                                    reg = text2.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Alegeti un numar din lista : ");
                                    text2.nextLine();
                                    ok = 1;
                                }
                                if ((reg < 1 || reg > a.getDirectors().size()) && ok == 0)
                                    System.out.println("Alegeti un numar din lista : ");
                            }
                            a.getDirectors().remove(reg - 1);
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Adaugarea unui nou actor");
                    System.out.println("2) Stergerea unui actor");
                    Scanner text3 = new Scanner(System.in);
                    Scanner scan3 = new Scanner(System.in);
                    int ch3 = 0;
                    while (ch3 < 1 || ch3 > 2) {
                        int ok = 0;
                        try {
                            ch3 = scan3.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si 2 :");
                            scan3.nextLine();
                            ok = 1;
                        }
                        if ((ch3 < 1 || ch3 > 2) && ok == 0)
                            System.out.println("Alegeti un numar intre 1 si 2 :");
                    }
                    switch (ch3) {
                        case 1:
                            System.out.println("Introduceti numele noului actor : ");
                            a.getActors().add(text3.nextLine());
                            break;
                        case 2:
                            System.out.println("Lista actuala a actorilor : ");
                            for (int i = 0; i < a.getActors().size(); i++)
                                System.out.println(i + 1 + ") " + a.getActors().get(i));
                            System.out.println("Alegeti actorul pe care doriti sa il eliminati : ");
                            int act = 0;
                            while (act < 1 || act > a.getActors().size()) {
                                int ok = 0;
                                try {
                                    act = text3.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Alegeti un numar valid din lista : ");
                                    text3.nextLine();
                                    ok = 1;
                                }
                                if ((act < 1 || act > a.getActors().size()) && ok == 0)
                                    System.out.println("Alegeti un numar valid din lista : ");
                            }
                            a.getActors().remove(act - 1);
                            break;
                    }
                    break;
                case 5:
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Adaugarea unui nou gen");
                    System.out.println("2) Stergerea unui gen");
                    Scanner text4 = new Scanner(System.in);
                    Scanner scan4 = new Scanner(System.in);
                    int ch4 = 0;
                    while (ch4 < 1 || ch4 > 2) {
                        int ok = 0;
                        try {
                            ch4 = scan4.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si 2 : ");
                            scan4.nextLine();
                            ok = 1;
                        }
                        if ((ch4 < 1 || ch4 > 2) && ok == 0)
                            System.out.println("Alegeti un numar intre 1 si 2 : ");
                    }
                    switch (ch4) {
                        case 1:
                            System.out.println("Introduceti genul filmului din urmatoarea lista : ");
                            Genre[] genres = Genre.values();
                            for (int i = 0; i < genres.length; i++) {
                                System.out.println(i + 1 + ") " + genres[i]);
                            }
                            ;
                            System.out.println("Introduceti genul : ");
                            Scanner scanner7 = new Scanner(System.in);
                            int genre = 0;
                            while (genre < 1 || genre > genres.length ) {
                                int ok = 0;
                                try {
                                    genre = scanner7.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Introduceti un numar valabil din lista : ");
                                    scanner7.nextLine();
                                    ok = 1;
                                }
                                if ((genre < 1 || genre > genres.length ) && ok == 0)
                                    System.out.println("Introduceti un numar valabil din lista : ");
                            }
                            a.getGenres().add(genres[genre - 1]);
                            break;
                        case 2:
                            System.out.println("Lista actuala a genurilor : ");
                            for (int i = 0; i < a.getGenres().size(); i++)
                                System.out.println(i + 1 + ") " + a.getGenres().get(i));
                            System.out.println("Alegeti genul pe care doriti sa il eliminati : ");
                            int gen = 0;
                            while (gen < 1 || gen > a.getGenres().size()) {
                                int ok = 0;
                                try {
                                    gen = text4.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Alegeti un numar valid din lista : ");
                                    text4.nextLine();
                                    ok = 1;
                                }
                                if ((gen < 1 || gen > a.getGenres().size()) && ok == 0)
                                    System.out.println("Alegeti un numar valid din lista : ");
                            }
                            a.getGenres().remove(gen - 1);
                            break;
                    }
                    break;
                case 6:
                    System.out.println("Introduceti noul plot : ");
                    Scanner plot = new Scanner(System.in);
                    a.setPlot(plot.nextLine());
                    break;
                case 7:
                    System.out.println("Introduceti noua durata : ");
                    Scanner durata = new Scanner(System.in);
                    a.setDuration(durata.nextLine());
                    break;
                case 8:
                    System.out.println("Introduceti noul an de lansare : ");
                    Scanner newYear = new Scanner(System.in);
                    int ok = 0;
                    int an = 0;
                    while (ok == 0) {
                        ok = 1;
                        try {

                            an = newYear.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un an valid compus doar din cifre :");
                            ok = 0;
                            newYear.nextLine();
                        }
                    }
                    a.setReleaseYear(an);
                    break;
            }
            if (index != 0)
                IMDB.getInstance().getProductions().set(index, a);
        }
        if (p.getClass() == Series.class) {
            Series a = (Series) p;
            System.out.println("Introduceti ce vreti sa modificati : ");
            System.out.println("1) Titlul");
            System.out.println("2) Tipul");
            System.out.println("3) Regizorii");
            System.out.println("4) Actorii");
            System.out.println("5) Genurile");
            System.out.println("6) Plot-ul");
            System.out.println("7) Anul lansarii");
            System.out.println("8) Numarul de sezoane");
            System.out.println("9) Sezoanele");
            Scanner scan = new Scanner(System.in);
            Scanner text = new Scanner(System.in);
            int ch = 0;
            while (ch < 1 || ch > 9) {
                int ok = 0;
                try {
                    ch = scan.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Alege un numar intre 1 si 9 : ");
                    scan.nextLine();
                    ok = 1;
                }
                if ((ch < 1 || ch > 9) && ok == 0)
                    System.out.println("Alege un numar intre 1 si 9 : ");
            }
            switch (ch) {
                case 1:
                    System.out.println("Introduceti noul titlu");
                    a.setTitle(text.nextLine());
                    break;
                case 2:
                    System.out.println("Introduceti noul tip");
                    a.setType(text.nextLine());
                    break;
                case 3:
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Adaugarea unui nou regizor");
                    System.out.println("2) Stergerea unui regizor");
                    Scanner text2 = new Scanner(System.in);
                    Scanner scan2 = new Scanner(System.in);
                    int ch2 = 0;
                    while (ch2 < 1 || ch2 > 2) {
                        int ok = 0;
                        try {
                            ch2 = scan2.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si 2 ");
                            scan2.nextLine();
                            ok = 1;
                        }
                        if ((ch2 < 1 || ch2 > 2) && ok == 0)
                            System.out.println("Alegeti un numar intre 1 si 2 ");

                    }
                    switch (ch2) {
                        case 1:
                            System.out.println("Introduceti numele noului regizor : ");
                            a.getDirectors().add(text2.nextLine());
                            break;
                        case 2:
                            System.out.println("Lista actuala a regizorilor : ");
                            for (int i = 0; i < a.getDirectors().size(); i++)
                                System.out.println(i + 1 + ") " + a.getDirectors().get(i));
                            System.out.println("Alegeti regizorul pe care doriti sa il eliminati : ");
                            int reg = 0;
                            while (reg < 1 || reg > a.getDirectors().size()) {
                                int ok = 0;
                                try {
                                    reg = text2.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Alegeti un numar din lista : ");
                                    text2.nextLine();
                                    ok = 1;
                                }
                                if ((reg < 1 || reg > a.getDirectors().size()) && ok == 0)
                                    System.out.println("Alegeti un numar din lista : ");
                            }
                            a.getDirectors().remove(reg - 1);
                            break;
                    }
                    break;
                case 4:
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Adaugarea unui nou actor");
                    System.out.println("2) Stergerea unui actor");
                    Scanner text3 = new Scanner(System.in);
                    Scanner scan3 = new Scanner(System.in);
                    int ch3 = 0;
                    while (ch3 < 1 || ch3 > 2) {
                        int ok = 0;
                        try {
                            ch3 = scan3.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si 2 :");
                            scan3.nextLine();
                            ok = 1;
                        }
                        if ((ch3 < 1 || ch3 > 2) && ok == 0)
                            System.out.println("Alegeti un numar intre 1 si 2 :");
                    }
                    switch (ch3) {
                        case 1:
                            System.out.println("Introduceti numele noului actor : ");
                            a.getActors().add(text3.nextLine());
                            break;
                        case 2:
                            System.out.println("Lista actuala a actorilor : ");
                            for (int i = 0; i < a.getActors().size(); i++)
                                System.out.println(i + 1 + ") " + a.getActors().get(i));
                            System.out.println("Alegeti actorul pe care doriti sa il eliminati : ");
                            int act = 0;
                            while (act < 1 || act > a.getActors().size()) {
                                int ok = 0;
                                try {
                                    act = text3.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Alegeti un numar valid din lista : ");
                                    text3.nextLine();
                                    ok = 1;
                                }
                                if ((act < 1 || act > a.getActors().size()) && ok == 0)
                                    System.out.println("Alegeti un numar valid din lista : ");
                            }
                            a.getActors().remove(act - 1);
                            break;
                    }
                    break;
                case 5:
                    System.out.println("Alegeti optiunea dorita : ");
                    System.out.println("1) Adaugarea unui nou gen");
                    System.out.println("2) Stergerea unui gen");
                    Scanner text4 = new Scanner(System.in);
                    Scanner scan4 = new Scanner(System.in);
                    int ch4 = 0;
                    while (ch4 < 1 || ch4 > 2) {
                        int ok = 0;
                        try {
                            ch4 = scan4.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si 2 : ");
                            scan4.nextLine();
                            ok = 1;
                        }
                        if ((ch4 < 1 || ch4 > 2) && ok == 0)
                            System.out.println("Alegeti un numar intre 1 si 2 : ");
                    }
                    switch (ch4) {
                        case 1:
                            System.out.println("Introduceti genul filmului din urmatoarea lista : ");
                            Genre[] genres = Genre.values();
                            for (int i = 0; i < genres.length; i++) {
                                System.out.println(i + 1 + ") " + genres[i]);
                            }
                            ;
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
                                }
                                if ((genre < 1 || genre > genres.length + 1) && ok == 0)
                                    System.out.println("Introduceti un numar valabil din lista : ");
                            }
                            a.getGenres().add(genres[genre - 1]);
                            break;
                        case 2:
                            System.out.println("Lista actuala a genurilor : ");
                            for (int i = 0; i < a.getGenres().size(); i++)
                                System.out.println(i + 1 + ") " + a.getGenres().get(i));
                            System.out.println("Alegeti genul pe care doriti sa il eliminati : ");
                            int gen = 0;
                            while (gen < 1 || gen > a.getGenres().size()) {
                                int ok = 0;
                                try {
                                    gen = text4.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Alegeti un numar valid din lista : ");
                                    text4.nextLine();
                                    ok = 1;
                                }
                                if ((gen < 1 || gen > a.getGenres().size()) && ok == 0)
                                    System.out.println("Alegeti un numar valid din lista : ");
                            }
                            a.getGenres().remove(gen - 1);
                            break;
                    }
                    break;
                case 6:
                    System.out.println("Introduceti noul plot : ");
                    Scanner plot = new Scanner(System.in);
                    a.setPlot(plot.nextLine());
                    break;
                case 7:
                    System.out.println("Introduceti noul an de lansare : ");
                    Scanner newYear = new Scanner(System.in);
                    int ok = 0;
                    int an = 0;
                    while (ok == 0) {
                        ok = 1;
                        try {

                            an = newYear.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduceti un an valid compus doar din cifre :");
                            ok = 0;
                            newYear.nextLine();
                        }
                    }
                    a.setReleaseYear(an);
                    break;
                case 8:
                    System.out.println("Introduceti noul numar de sezoane : ");
                    Scanner newNum = new Scanner(System.in);
                    int nrn = 0;
                    while (nrn < 1 || nrn > a.getNumSeasons()) {
                        int ok2 = 0;
                        try {
                            nrn = newNum.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si " + a.getNumSeasons() + " : ");
                            newNum.nextLine();
                            ok2 = 1;
                        }
                        if ((nrn < 1 || nrn > a.getNumSeasons()) && ok2 == 0)
                            System.out.println("Alegeti un numar intre 1 si " + a.getNumSeasons() + " : ");
                    }
                    a.setNumSeasons(nrn);
                    for (int i = a.getNumSeasons(); i < a.getSeasons().size(); i++)
                        a.getSeasons().remove("Season " + i);
                    break;
                case 9:
                    System.out.println("Serialul are " + a.getSeasons().size() + " sezoane");
                    System.out.println("Alegeti sezonul pe care doriti sa il modificati :");
                    Scanner sez = new Scanner(System.in);
                    int edit = 0;
                    while (edit < 1 || edit > a.getNumSeasons()) {
                        int ok2 = 0;
                        try {
                            edit = sez.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si " + a.getNumSeasons() + " : ");
                            sez.nextLine();
                            ok2 = 1;
                        }
                        if ((edit < 1 || edit > a.getNumSeasons()) && ok2 == 0)
                            System.out.println("Alegeti un numar intre 1 si " + a.getNumSeasons() + " : ");
                    }
                    System.out.println("Alegeti din lista urmatoare ce episod doriti sa modficiati : ");
                    for (int i = 0; i < a.getSeasons().get("Season " + (edit)).size(); i++)
                        System.out.println(i + 1 + ") " + a.getSeasons().get("Season " + (edit)).get(i));
                    Scanner ep = new Scanner(System.in);
                    int edit2 = 0;
                    while (edit2 < 1 || edit2 > a.getSeasons().get("Season " + (edit)).size()) {
                        int ok2 = 0;
                        try {
                            edit2 = ep.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Alegeti un numar intre 1 si " + a.getSeasons().get("Season " + (edit)).size() + " : ");
                            ep.nextLine();
                            ok2 = 1;
                        }
                        if ((edit2 < 1 || edit2 > a.getSeasons().get("Season " + (edit)).size()) && ok2 == 0)
                            System.out.println("Alegeti un numar intre 1 si " + a.getSeasons().get("Season " + (edit)).size() + " : ");
                    }
                    Scanner title = new Scanner(System.in);
                    Scanner duration = new Scanner(System.in);
                    System.out.println("Introduceti noul titlu : ");
                    String t = title.nextLine();
                    System.out.println("Introduceti noua durata : ");
                    String d = duration.nextLine();
                    a.getSeasons().get("Season " + (edit)).get(edit2 - 1).setEpisodeName(t);
                    a.getSeasons().get("Season " + (edit)).get(edit2 - 1).setDuration(d);
                    break;
            }
            if (index != 0)
                IMDB.getInstance().getProductions().set(index, a);
        }
    }

    @Override
    public void updateActor(Actor a) {
        int index = 0;
        for (int i = 0; i < IMDB.getInstance().getActors().size(); i++)
            if (IMDB.getInstance().getActors().get(i).equals(a))
                index = i;
        System.out.println("Introducei ce doriti sa modificati : ");
        System.out.println("1) Numele actorului");
        System.out.println("2) Aparitiile acestuia");
        System.out.println("3) Biografia acestuia");
        Scanner ch = new Scanner(System.in);
        int chh = 0;
        while (chh < 1 || chh > 3) {
            int ok = 0;
            try {
                chh = ch.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un numar intre 1 si 3 : ");
                ch.nextLine();
                ok = 1;
            }
            if ((chh < 1 || chh > 3) && ok == 0)
                System.out.println("Alegeti un numar intre 1 si 3 : ");
        }
        switch (chh) {
            case 1:
                System.out.println("Introduceti noul nume : ");
                Scanner nume = new Scanner(System.in);
                String n = nume.nextLine();
                a.setName(n);
                break;
            case 2:
                System.out.println("Alegeti aparitia pe care vreti sa o modificati : ");
                for (int i = 0; i < a.getPerformances().size(); i++)
                    System.out.println(i + 1 + ") " + a.getPerformances().get(i));
                System.out.println(a.getPerformances().size() + 1 + ") End");
                Scanner ap = new Scanner(System.in);
                int app = 0;
                while (app < 1 || app > a.getPerformances().size() + 1) {
                    int ok = 0;
                    try {
                        app = ap.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Introduce un numar din lista : ");
                        ap.nextLine();
                        ok = 1;
                    }
                    if ((app < 1 || app > a.getPerformances().size() + 1) && ok == 0)
                        System.out.println("Introduce un numar din lista : ");

                }
                while (app != a.getPerformances().size() + 1) {
                    System.out.println("Introduceti noul titlu : ");
                    Scanner scan = new Scanner(System.in);
                    String title = scan.nextLine();
                    System.out.println("Introduceti noul tip : ");
                    Scanner scan2 = new Scanner(System.in);
                    String type = scan2.nextLine();
                    a.getPerformances().get(app - 1).put("title", title);
                    a.getPerformances().get(app - 1).put("type", type);
                    System.out.println("Introduceti aparitia pe care vreti sa o modificati : ");
                    app = 0;
                    while (app < 1 || app > a.getPerformances().size() + 1) {
                        int ok = 0;
                        try {
                            app = ap.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Introduce un numar din lista : ");
                            ap.nextLine();
                            ok = 1;
                        }
                        if ((app < 1 || app > a.getPerformances().size() + 1) && ok == 0)
                            System.out.println("Introduce un numar din lista : ");

                    }
                }
                break;
            case 3:
                System.out.println("Introduceti noua biografie : ");
                Scanner bio = new Scanner(System.in);
                a.setBiography(bio.nextLine());
                break;
        }
        if (index != 0)
            IMDB.getInstance().getActors().set(index, a);
    }
}
