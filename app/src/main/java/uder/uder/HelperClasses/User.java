package uder.uder.HelperClasses;

import java.io.Serializable;

/**
 * Created by cazza223 on 4/3/2017.
 */

public class User implements Serializable {

    private String userID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String user_type;


    public User(String id, String fName, String lName, String uName, String pass, String type){
        userID = id;
        firstName = fName;
        lastName = lName;
        username = uName;
        password = pass;
        user_type = type;
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

    public String getUserType() {
        return user_type;
    }

    public void setUserType(String user_type) {
        this.user_type = user_type;
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
