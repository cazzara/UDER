package uder.uder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import uder.uder.HelperClasses.Product;
import uder.uder.HelperClasses.ShoppingCart;

/**
 * Created by cazza223 on 4/13/2017.
 */

public class CheckoutAdapter extends ArrayAdapter<Product> {
    Context context;
    private ArrayList<Product> products;
    private ShoppingCart cart;

    private static class ViewHolder{
        TextView productName;
        TextView priceNquantity;
    }

    public CheckoutAdapter(Context context, ArrayList<Product> products, ShoppingCart shoppingCart){
        super(context, R.layout.product_list_item, products);
        this.context = context;
        this.products = products;
        this.cart = shoppingCart;
    }

    private int lastPosition = -1;

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Product product = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.checkout_list_item, parent, false);
        viewHolder.productName = (TextView) convertView.findViewById(R.id.productName);
        viewHolder.priceNquantity = (TextView) convertView.findViewById(R.id.priceNquantity);


        convertView.setTag(viewHolder);


        lastPosition = position;

        viewHolder.productName.setText(product.getProductName());
        viewHolder.priceNquantity.setText(product.getProductPrice() + " Quantity: " + cart.getItemQuantity(product));

        // Return the completed view to render on screen
        return convertView;
    }


}
