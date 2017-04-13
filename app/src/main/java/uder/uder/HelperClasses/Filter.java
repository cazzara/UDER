package uder.uder.HelperClasses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by cazza223 on 3/20/2017.
 */

public class Filter implements Serializable{
    private String milk_type;
    private String milk_price_range;
    private String milk_flavor;
    private String milk_brand;

    public Filter(String type, String price, String flavor, String brand){
        milk_type = type;
        milk_price_range = price;
        milk_flavor = flavor;
        milk_brand = brand;
    }



    public Filter(){
        milk_brand = "Any";
        milk_flavor = "Any";
        milk_price_range = "Any";
        milk_type = "Any";
    }

    public String getMilk_type() {
        return milk_type;
    }

    public void setMilk_type(String milk_type) {
        this.milk_type = milk_type;
    }

    public String getMilk_price_range() {
        return milk_price_range;
    }

    public void setMilk_price_range(String milk_price_range) {
        this.milk_price_range = milk_price_range;
    }

    public void clearFilter(){
        this.setMilk_price_range("Any");
        this.setMilk_type("Any");
        this.setMilk_flavor("Any");
        this.setMilk_brand("Any");
    }

    public String getMilk_flavor() {
        return milk_flavor;
    }

    public void setMilk_flavor(String milk_flavor) {
        this.milk_flavor = milk_flavor;
    }

    public String getMilk_brand() {
        return milk_brand;
    }

    public void setMilk_brand(String milk_brand) {
        this.milk_brand = milk_brand;
    }

    public JSONObject toJSON(){

        JSONObject filterJSON = new JSONObject();
        try {
            filterJSON.put("type", getMilk_type());
            filterJSON.put("price_range", getMilk_price_range());
            filterJSON.put("flavor", getMilk_flavor());
            filterJSON.put("brand", getMilk_brand());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return filterJSON;
    }

    @Override
    public String toString() {
        return "Type: " + getMilk_type() + "\n" +
                "Price: "+ getMilk_price_range() + "\n" +
                "Brand: "+ getMilk_brand() + "\n" +
                "Flavor: " + getMilk_flavor();
    }
}
