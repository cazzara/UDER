package uder.uder.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uder.uder.HelperClasses.Filter;
import uder.uder.HelperClasses.Product;
import uder.uder.HelperClasses.ShoppingCart;
import uder.uder.R;

public class ProductListActivity extends AppCompatActivity {

    private String[] products = {"Product 1", "Product 2", "Product 3", "Product 4", "Product 5"};
    private ShoppingCart userShoppingCart;
    private Filter userFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_list);


        userShoppingCart = (ShoppingCart) getIntent().getSerializableExtra("userShoppingCart");
        userFilter = (Filter) getIntent().getSerializableExtra("userFilter");

        ListView productList = (ListView) findViewById(R.id.lv_productList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, products);

        productList.setAdapter(adapter);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                userShoppingCart.addItem(products[position]);
                Toast.makeText(getApplicationContext(),
                        "Added  " + products[position] + " to shopping cart", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//
//    }

    public ArrayList<Product> getProducts(Filter f){

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        };

        JSONObject productFilters = new JSONObject();

        try {
            productFilters.put("type", f.getMilk_type());
            productFilters.put("price_range", f.getMilk_price_range());
            productFilters.put("flavor", f.getMilk_flavor());
            productFilters.put("brand", f.getMilk_brand());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}
