package uder.uder.HelperClasses;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by cazza223 on 2/23/2017.
 */

public class ShoppingCart implements Serializable {
    // Shopping Cart --> ProductID , Quantity
    private HashMap<String, String> cart;

    public ShoppingCart(){
        cart = new HashMap<>();

    }

    @Override
    public String toString() {
        String cartContents = "";
        for(String product : cart.keySet()){
           cartContents += product + " : " + cart.get(product);
        }
        return cartContents;
    }

    public void addItem(String ProductID){
        if(cart.containsKey(ProductID))
            incrementQuantity(ProductID);
        else
            cart.put(ProductID, "1");

    }

    public void removeItem(String ProductID){
        if(cart.containsKey(ProductID))
            cart.remove(ProductID);

    }

    public void incrementQuantity(String ProductID){
        int quantity = Integer.parseInt(cart.get(ProductID));
        quantity++;
        cart.put(ProductID, ""+quantity);

    }


}
