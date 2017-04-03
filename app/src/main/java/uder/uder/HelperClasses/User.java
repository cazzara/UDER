package uder.uder.HelperClasses;

import java.io.Serializable;

/**
 * Created by cazza223 on 4/3/2017.
 */

public class User implements Serializable {
    private String username;
    private String password;

    public User(String uname, String pword){
        username = uname;
        password = pword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void logout(){
        // TODO implement logout function
    }


}
