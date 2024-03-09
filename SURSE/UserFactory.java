package org.example;

public class UserFactory {
    public static User factory(AccountType type){
        if(type.equals(AccountType.Regular))
            return new Regular();
        if(type.equals(AccountType.Contributor))
            return new Contributor();
        if (type.equals(AccountType.Admin))
            return new Admin();
        return null;
    }
}
