package uder.uder.HelperClasses;

import java.io.Serializable;

/**
 * Created by cazza223 on 4/3/2017.
 */

public class User {

    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;

    public User(String fName, String lName, String uName, String pass){
        firstName = fName;
        lastName = lName;
        username = uName;
        password = pass;
    }

    public String getfName(){
        return firstName;
    }

    public String getlName(){
        return lastName;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String toString(){
        return "Name: " + firstName + " " + lastName + "\n" + " Username: " + username + "\n" + "Password: " + password;
    }

}
