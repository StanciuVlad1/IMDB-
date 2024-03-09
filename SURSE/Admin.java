package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Admin<T extends Comparable<T>> extends Staff {
    // constructor

    public Admin() {
    }

    public Admin(String username, int experience, Information information, AccountType userType, SortedSet favorites, List notifications, List list, SortedSet contributions) {
        super(username, experience, information, userType, favorites, notifications, list, contributions);
    }

    // staffInterface
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
        Actor a = (Actor) Functions.find(name);
        IMDB.getInstance().getActors().remove(a);
        this.dellContributions(a);

    }


    // clasa interna
    public static class RequestsHolder {
        public static List<Request> requests = new ArrayList<>();

        public static void add(Request r) {
            requests.add(r);
        }

        public static void remove(Request r) {
            requests.remove(r);
        }
    }

    // metoda de solve request

    public void showAdminRequests() {
        for (int i = 0; i < RequestsHolder.requests.size(); i++)
            System.out.println(i + 1 + " )" + RequestsHolder.requests.get(i));
    }

    public void resolveAdminRequests() {
        System.out.println("Alegeti din urmatoarea lista cererea pe care doriti sa o rezolvati : ");
        showAdminRequests();
        Scanner scan = new Scanner(System.in);
        int ch = 0;
        while (ch < 1 || ch > RequestsHolder.requests.size()) {
            int ok = 0;
            try {
                ch = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Alegeti un numar intre 1 si " + RequestsHolder.requests.size() + " : ");
                scan.nextLine();
                ok = 1;
            }
            if ((ch < 1 || ch > RequestsHolder.requests.size()) && ok == 0)
                System.out.println("Alegeti un numar intre 1 si " + RequestsHolder.requests.size() + " : ");
        }
        Request r = RequestsHolder.requests.get(ch - 1);
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
        RequestsHolder.requests.remove(r);
        IMDB.getInstance().getRequests().remove(r);
    }

    //metode de add/delete user
    public void addUser() {
        System.out.println("Introduceti email-ul utilizatorului : ");
        Scanner em = new Scanner(System.in);
        String email = em.nextLine();
        System.out.println("Introduceti numele utilizatorului : ");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        String password = "";
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            if(i%2==0)
            password = password+(char)(random.nextInt(26) + 'a');
            else
                password = password+(char)(random.nextInt(26) + 'A');

        }
        for (int i = 7; i < 12; i++) {
            password = password + random.nextInt(9);
        }
        for (int i = 12; i < 15; i++) {
            password = password + (char)(random.nextInt(13) + '!');
        }
        Credentials credentials = new Credentials(email, password);
        System.out.println("Introduceti tara utilizatorului : ");
        String country = scan.nextLine();
        System.out.println("Introduceti varsta utilizatorului (numar pozitiv) : ");
        Scanner ag = new Scanner(System.in);
        int ok = 0;
        int age = -1;
        while (ok == 0) {
            try {
                age = ag.nextInt();
                ok = 1;
            } catch (InputMismatchException e) {
                System.out.println("Introduceti o varsta valabila (numar pozitiv) :");
                ag.nextLine();
            }
            if (ok == 1 && age<0){
                System.out.println("Introduceti o varsta valabila (numar pozitiv) :");
                ok = 0;
            }
        }
        System.out.println("Introduceti genul utilizatorului (Male or Female) : ");
        Scanner gen = new Scanner(System.in);
        String gender = gen.nextLine();
        while (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
            System.out.println("Introduceti un gen corect (Male or Female) : ");
            gender = gen.nextLine();
        }
        System.out.println("Introduceti data de nastere a utilizatorului in formatul yyyy-mm-dd: ");
        int sem = 0;
        Scanner birth = new Scanner(System.in);
        String birthDate = "";
        while(sem == 0) {
            sem = 1;
            birthDate = birth.nextLine();
            if(birthDate.length()!=10||birthDate.charAt(0)=='0'||(birthDate.charAt(5)=='0'&&birthDate.charAt(6)=='0')||(birthDate.charAt(8)=='0'&&birthDate.charAt(9)=='0')){
                System.out.println("Data nasterii este introdusa incorect, incearca din nou : ");
                continue;
            }
            for(int i = 0; i < 4; i++ )
                if(birthDate.charAt(i)<'0'||birthDate.charAt(i)>'9')
                    sem = 0;
            if(birthDate.charAt(4)!='-')
                sem = 0;
            for(int i = 5; i < 7; i++)
                if(birthDate.charAt(i)<'0'||birthDate.charAt(i)>'9')
                    sem = 0;
            if(birthDate.charAt(7)!='-')
                sem=0;
            for(int i = 8; i < 10; i++)
                if(birthDate.charAt(i)<'0'||birthDate.charAt(i)>'9')
                    sem = 0;
            if(sem==0)
                System.out.println("Data nasterii este introdusa incorect, incearca din nou : ");
        }
        String username = "";
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ')
                username = username + "_";
            else {
                username = username + name.charAt(i);
            }
        }
        username = username.toLowerCase();
        int unique = 0;
        String copy = "";
        while (unique == 0) {
            unique = 1;
            copy = username + random.nextInt(9999);
            for (User u : IMDB.getInstance().getUsers())
                if (u.getUsername().equals(copy))
                    unique = 0;
        }
        username = copy;
        int experience = 0;
        System.out.println("Introduceti tipul utilizatorului (Regular, Contributor, Admin) : ");
        int verif = 0;
        Scanner typ = new Scanner(System.in);
        User u = null;
        while (verif == 0) {
            String type = typ.nextLine();
            if (type.equalsIgnoreCase("Regular")) {
                u = UserFactory.factory(AccountType.Regular);
                u.setUserType(AccountType.Regular);
                verif = 1;
            }
            if (type.equalsIgnoreCase("Contributor")) {
                u = UserFactory.factory(AccountType.Contributor);
                u.setUserType(AccountType.Contributor);
                verif = 1;
            }
            if (type.equalsIgnoreCase("Admin")) {
                u = UserFactory.factory(AccountType.Admin);
                u.setUserType(AccountType.Admin);
                verif = 1;
            }
            if (verif == 0)
                System.out.println("Intrudoceti un tip valabil (Regular, Contributor, Admin) : ");
        }
        u.setUsername(username);
        u.setExperience(experience);
        Information information = new Information.InformationBuilder().credentials(credentials).name(name).country(country).age(age).gender(gender).birthDate(birthDate).build();
        u.setInformation(information);
        IMDB.getInstance().getUsers().add(u);
    }
}


