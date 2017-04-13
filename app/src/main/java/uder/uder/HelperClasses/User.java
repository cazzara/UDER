package uder.uder.HelperClasses;

import java.io.Serializable;

/**
 * Created by cazza223 on 4/3/2017.
 */

public class User implements Serializable {

    protected String userID;
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;

    public User(String id, String fName, String lName, String uName, String pass){
        userID = id;
        firstName = fName;
        lastName = lName;
        username = uName;
        password = pass;
    }

    public String getUserID() {
        return userID;
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
        return "User ID: "+ userID + " Name: " + firstName + " " + lastName + "\n" + " Username: " + username + "\n" + "Password: " + password;
    }

}
