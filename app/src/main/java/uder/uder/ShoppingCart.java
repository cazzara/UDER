package uder.uder;

import java.util.HashMap;

/**
 * Created by cazza223 on 2/23/2017.
 */

public class ShoppingCart {
    private HashMap<String, String> cart;

    public ShoppingCart(){
        cart = new HashMap<>();

    }

    public void addItem(String id, String quantity){
        cart.put(id, quantity);
    }

    public void removeItem(String id){
        if(cart.containsKey(id))
            cart.remove(id);

    }

    public void incrementQuantity(String id){
        if(cart.containsKey(id)){
            int quantity = Integer.parseInt(cart.get(id));
        }
    }


}
