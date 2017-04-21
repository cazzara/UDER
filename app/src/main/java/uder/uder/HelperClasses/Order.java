package uder.uder.HelperClasses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cazza223 on 3/20/2017.
 */

public class Order implements Serializable{

    private String order_id;
    private HashMap<String, String> address;
    private String buyer_id;
    private String status;
    private String milker_id;
    private String timestamp;
    private ShoppingCart products;

    public Order(String order_id, HashMap<String,String> address, String buyer_id, String status, String milker_id, String timestamp, ShoppingCart products) {
        this.order_id = order_id;
        this.address = address;
        this.buyer_id = buyer_id;
        this.status = status;
        this.milker_id = milker_id;
        this.timestamp = timestamp;
        this.products = products;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getAddress() {
        String addressString = "";
        addressString += address.get("street") + address.get("city") + address.get("city") + address.get("zip");
        return addressString;
    }

    public JSONObject orderToJSON(){
        JSONObject order = new JSONObject();
        try {
            order.put("street", address.get("street"));
            order.put("state", address.get("state"));
            order.put("city", address.get("city"));
            order.put("zip", address.get("zip"));
            order.put("buyer_id", buyer_id);
            order.put("created", timestamp);
            order.put("products", products.cartContentsToJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return order;
    }

    public void setStreet(String street){ address.put("street", street); }
    public void setState(String state){ address.put("state", state); }
    public void setCity(String city){ address.put("city", city); }
    public void setZip(String zip){ address.put("zip", zip); }


    public String getBuyer_id() {
        return buyer_id;
    }

    public String toString(){
        return products.toString() + " Total: " + products.getTotal();
    }

    public ShoppingCart getProducts() {
        return products;
    }

    public void setProducts(ShoppingCart products) {
        this.products = products;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMilker_id() {
        return milker_id;
    }

    public void setMilker_id(String milker_id) {
        this.milker_id = milker_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
