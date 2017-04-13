package uder.uder.HelperClasses;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by cazza223 on 2/23/2017.
 */

public class ShoppingCart implements Serializable {
    // Shopping Cart --> Product , Quantity
    private HashMap<Product, String> cart;

    public ShoppingCart(){ cart = new HashMap<>(); }

    @Override
    public String toString() {
        String cartContents = "";
        for(Product product : cart.keySet())
           cartContents += product + " Quantity: " + cart.get(product) + "\n";
        return cartContents;
    }

    public void clearCart() { cart.clear(); }

    public boolean isEmpty(){
        if(cart.size() == 0)
            return true;
        else
            return false;
    }

    public void addItem(Product product){
        if(cart.containsKey(product))
            incrementQuantity(product);
        else
            cart.put(product, "1");

    }

    public int numberOfItems(){ return cart.size(); }

    public void removeItem(Product product){
        if(cart.containsKey(product))
            cart.remove(product);

    }

    public void incrementQuantity(Product product){
        int quantity = Integer.parseInt(cart.get(product));
        quantity++;
        cart.put(product, "" + quantity);
    }


}
