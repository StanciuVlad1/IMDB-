package org.example;


public class Credentials {
    // field-uri
    private String email;
    private String password;

    //gettere si settere
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // constructor

    public Credentials() {
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
    // toString


    @Override
    public String toString() {
        return "Credentials{" +
                "\nemail='" + email + '\'' +
                ",\npassword='" + password + '\'' +
                "\n}\n";
    }
}
