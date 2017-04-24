package uder.uder.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import uder.uder.HelperClasses.Filter;
import uder.uder.HelperClasses.Product;
import uder.uder.HelperClasses.Regular_User;
import uder.uder.HelperClasses.RequestClass;
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
        getProducts(currentUser.getFilter());


        final Button checkout = (Button) findViewById(R.id.b_checkout);
        final Button goBack = (Button) findViewById(R.id.b_goBack);

        ListView productList = (ListView) findViewById(R.id.lv_productList);
        ProductAdapter adapter = new ProductAdapter(getApplicationContext(),products);
        productList.setAdapter(adapter);
        productList.invalidateViews();


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

    public void getProducts(Filter f){

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Server response: " + response.toString());
                try {
                    if(response.get("status").equals("OK")){
                        JSONArray productArray = response.getJSONArray("products");
                        for(int i = 0; i < productArray.length(); i++){
                            JSONObject product = productArray.getJSONObject(i);
                            String id = product.getString("id");
                            String price = product.getString("price");
                            String name = product.getString("name");
                            products.add(new Product(id, name, price));
                        }
                        System.out.println(products);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        JSONObject productFilters = new JSONObject();

        try {
            productFilters.put("type", f.getMilk_type());
            String price = f.getMilk_price_range();
            switch (price){
                case "More than $7.00":
                    productFilters.put("price_range", "7 1000");
                    break;
                case "$5.00 - $7.00":
                    productFilters.put("price_range", "5 7");
                    break;
                case "$3.00 - $4.99":
                    productFilters.put("price_range", "3 5");
                    break;
                case "Less than $3.00":
                    productFilters.put("price_range", "0 3");
                    break;
                default:
                    productFilters.put("price_range", "Any");

            }
            productFilters.put("flavor", f.getMilk_flavor());
            productFilters.put("brand", f.getMilk_brand());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestClass getProductsFromServer = new RequestClass("http://34.208.156.179:4567/api/v1/products/filter", productFilters, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlertDialog.Builder serverError = new AlertDialog.Builder(ProductListActivity.this);
                serverError.setTitle("Something went wrong :(");
                serverError.setMessage("Sorry your request could not be completed at this time. Try again later.");
                serverError.setNegativeButton("Back to Main Page", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent userActivity = new Intent(getApplicationContext(), UserActivity.class);
                        userActivity.putExtra("user", currentUser);
                        userActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(userActivity);
                    }
                }).create().show();

                System.out.println(error);

            }
        });
        RequestQueue queue = Volley.newRequestQueue(ProductListActivity.this);

        System.out.println("Sent to Server: " + productFilters.toString());
        queue.add(getProductsFromServer);


    }
}
