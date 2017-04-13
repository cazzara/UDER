package uder.uder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uder.uder.HelperClasses.Product;

/**
 * Created by cazza223 on 4/6/2017.
 */
// http://www.journaldev.com/10416/android-listview-with-custom-adapter-example-tutorial
public class ProductAdapter extends ArrayAdapter<Product> {
    Context context;
    private ArrayList<Product> products;

    private static class ViewHolder {
        TextView productName;
        TextView price;
    }

    public ProductAdapter(Context context, ArrayList<Product> products){
        super(context, R.layout.product_list_item, products);
        this.context = context;
        this.products = products;
    }

    private int lastPosition = -1;


    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Product product = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag



        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.product_list_item, parent, false);
        viewHolder.productName = (TextView) convertView.findViewById(R.id.productName);
        viewHolder.price = (TextView) convertView.findViewById(R.id.price);

        convertView.setTag(viewHolder);


        lastPosition = position;

        viewHolder.productName.setText(product.getProductName());
        viewHolder.price.setText(product.getProductPrice());

        // Return the completed view to render on screen
        return convertView;
    }
}
