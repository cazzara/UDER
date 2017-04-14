package uder.uder.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uder.uder.HelperClasses.Filter;
import uder.uder.HelperClasses.Product;
import uder.uder.HelperClasses.Regular_User;
import uder.uder.HelperClasses.ShoppingCart;
import uder.uder.HelperClasses.User;
import uder.uder.ProductAdapter;
import uder.uder.R;

public class ProductListActivity extends AppCompatActivity {

    private ArrayList<Product> products = new ArrayList<>();
    private Regular_User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = (Regular_User) getIntent().getSerializableExtra("user");
        setContentView(R.layout.activity_product_list);
        ArrayList<Product> serverProducts = getProducts(currentUser.getFilter());

        final Button checkout = (Button) findViewById(R.id.b_checkout);
        final Button goBack = (Button) findViewById(R.id.b_goBack);
        products.add(new Product("1", "Horizon Whole Milk", "5.99"));
        products.add(new Product("2", "Dairy Pure Soy Milk", "1.99"));
        products.add(new Product("3", "Amish Farms 1% Milk", "3.99"));
        products.add(new Product("4", "Horizon 2% Milk", "4.99"));
        products.add(new Product("5", "Tuscan Whole Milk", "6.99"));
        products.add(new Product("6", "Wellsley Farms 2% Milk", "2.50"));
        products.add(new Product("7", "Tuscan Half and Half", "2.19"));
        products.add(new Product("8", "Dairy Pure 1% Milk", "1.99"));





        ListView productList = (ListView) findViewById(R.id.lv_productList);
        ProductAdapter adapter = new ProductAdapter(getApplicationContext(),products);
        productList.setAdapter(adapter);

        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                currentUser.getShoppingCart().addItem(products.get(position));
                Toast.makeText(getApplicationContext(),
                        "Added  " + products.get(position).getProductName() + " to shopping cart", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser.getShoppingCart().isEmpty()){
                    AlertDialog.Builder emptyCartAlert = new AlertDialog.Builder(ProductListActivity.this);
                    emptyCartAlert.setMessage("No Items in Cart")
                            .setNegativeButton("Exit", null)
                            .create()
                            .show();
                } else{
                    Intent checkoutIntent = new Intent(v.getContext(), CheckoutActivity.class);
                    checkoutIntent.putExtra("user", currentUser);
                    v.getContext().startActivity(checkoutIntent);
                }

            }
        });


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIntent = new Intent(v.getContext(), UserActivity.class);
                userIntent.putExtra("user", currentUser);
                v.getContext().startActivity(userIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

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
