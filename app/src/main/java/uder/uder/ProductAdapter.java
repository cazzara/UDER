package uder.uder;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import uder.uder.HelperClasses.Product;

/**
 * Created by cazza223 on 4/6/2017.
 */

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products){
        super(context, -1, products);
        this.context = context;
        this.products = products;
    }

}
