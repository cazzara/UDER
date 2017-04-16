package uder.uder.HelperClasses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cazza223 on 2/23/2017.
 */

public class ShoppingCart implements Serializable {
    // Shopping Cart --> Product , Quantity
    private HashMap<Product, String> cart;
    private double total;

    public ShoppingCart(){ cart = new HashMap<>(); }

    @Override
    public String toString() {
        String cartContents = "";
        for(Product product : cart.keySet())
           cartContents += product + " Quantity: " + cart.get(product) + "\n";
        return cartContents;
    }

    public void clearCart() { cart.clear(); }

    public void calcTotal(){
        total = 0;
        for(Product p: cart.keySet())
            total += Double.parseDouble(p.getProductPrice()) * Integer.parseInt(cart.get(p));

    }

    public String getTotal(){ return String.format("%.2f", total); }

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
        calcTotal();

    }

    public String getItemQuantity(Product p){
        return "" + cart.get(p);
    }

    public int numberOfItems(){ return cart.size(); }

    public void removeItem(Product product){
        if(cart.containsKey(product))
            cart.remove(product);
        calcTotal();
    }

    public ArrayList<Product> toArrayList(){
        ArrayList<Product> products = new ArrayList<>();
        for(Product p: cart.keySet())
            products.add(p);
        return products;
    }

    public JSONObject cartContentsToJSON(){
        JSONObject product_list = new JSONObject();
        ArrayList<Product> products = toArrayList();
        for(int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            try {
                JSONObject json_p = new JSONObject();
                json_p.put("id", p.getProductID());
                json_p.put("name", p.getProductName());
                json_p.put("price", p.getProductPrice());
                json_p.put("quantity", cart.get(p));

                product_list.put(""+i, json_p);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return product_list;
    }

    public void incrementQuantity(Product product){
        int quantity = Integer.parseInt(cart.get(product));
        quantity++;
        cart.put(product, "" + quantity);
        calcTotal();
    }

    public void decrementQuantity(Product product){
        int quantity = Integer.parseInt(cart.get(product));
        quantity--;
        cart.put(product, "" + quantity);
        calcTotal();
    }


}
