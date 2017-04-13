package uder.uder.HelperClasses;

import java.io.Serializable;

/**
 * Created by cazza223 on 3/20/2017.
 */

public class Product implements Serializable{
    private String productID;
    private String productName;
    private String productPrice;

    public Product(String id, String name, String price){
        this.productID = id;
        this.productName = name;
        this.productPrice = price;
    }

    @Override
    public String toString() {
        return this.productName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
