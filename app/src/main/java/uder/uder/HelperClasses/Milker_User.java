package uder.uder.HelperClasses;

/**
 * Created by cazza223 on 4/13/2017.
 */

public class Milker_User extends User{

    protected Order currentOrder;

    public Milker_User(String id, String fName, String lName, String uName, String pass, String type, Order cOrder) {
        super(id, fName, lName, uName, pass, type);
        currentOrder = cOrder;
    }

    public Order getCurrentOrder(){
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public String toString(){
        return super.toString() + "Current Order: " + currentOrder ;
    }

}