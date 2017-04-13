package uder.uder.HelperClasses;

import java.io.Serializable;

/**
 * Created by cazza223 on 4/13/2017.
 */

public class Regular_User extends User implements Serializable{

    protected ShoppingCart shoppingCart;
    protected Filter filter;

    public Regular_User(String id, String fName, String lName, String uName, String pass, ShoppingCart sCart, Filter Filter) {
        super(id, fName, lName, uName, pass);
        shoppingCart = sCart;
        filter = Filter;
    }

    public ShoppingCart getShoppingCart(){
        return shoppingCart;
    }

    public Filter getFilter(){
        return filter;
    }

    public String toString(){
        return super.toString() + "Shopping Cart: " + shoppingCart + "\n" + "Filter: " + filter;
    }

}
