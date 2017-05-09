package uder.uder.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;

import uder.uder.HelperClasses.Filter;
import uder.uder.HelperClasses.Milker_User;
import uder.uder.HelperClasses.Order;
import uder.uder.HelperClasses.Product;
import uder.uder.HelperClasses.Regular_User;
import uder.uder.HelperClasses.ShoppingCart;
import uder.uder.HelperClasses.User;
import uder.uder.R;
import uder.uder.HelperClasses.RequestClass;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText et_username = (EditText) findViewById(R.id.et_username);
        final EditText et_password = (EditText) findViewById(R.id.et_password);
        final ImageButton b_login = (ImageButton) findViewById(R.id.b_login);
        final ImageButton b_register = (ImageButton) findViewById(R.id.b_register);
        final ImageButton user_button = (ImageButton) findViewById(R.id.user_button);
        final ImageButton driver_button = (ImageButton) findViewById(R.id.driver_button);



        user_button.setVisibility(View.INVISIBLE);
        driver_button.setVisibility(View.INVISIBLE);

        user_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Regular_User me = new Regular_User("5", "Chris", "Azzara", "cazzara", "password", "buyer", new ShoppingCart(), new Filter());

                Intent user_areaIntent = new Intent(v.getContext(), UserActivity.class);
                user_areaIntent.putExtra("user", me);

                LoginActivity.this.startActivity(user_areaIntent);
            }
        });

        driver_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Milker_User me = new Milker_User("5", "Chris", "Azzara", "cazzara", "password", "milker", null);
                Intent getter_areaIntent = new Intent(v.getContext(), GetterActivity.class);

                getter_areaIntent.putExtra("user", me);
                LoginActivity.this.startActivity(getter_areaIntent);
            }
        });



        b_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                final String username = et_username.getText().toString();
                final String password = et_password.getText().toString();

                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try{

                            String status = response.getString("status");

                            if(status.equals("OK")){

                                String user_type = response.getString("user_type");
                                String user_id = response.getString("user_id");
                                String first_name = response.getString("first_name");
                                String last_name = response.getString("last_name");

                                if(user_type.equals("buyer")) {

                                    Regular_User user = new Regular_User(user_id, first_name, last_name, username, password, "buyer", new ShoppingCart(), new Filter());
                                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                    intent.putExtra("user", user);
                                    LoginActivity.this.startActivity(intent);
                                }
                                else{
                                    Milker_User user = new Milker_User(user_id, first_name, last_name, username, password, "milker", null);
                                    boolean has_order = response.getBoolean("has_order");

                                    if(!has_order){
                                        Intent intent = new Intent(LoginActivity.this, GetterActivity.class);
                                        intent.putExtra("user", user);
                                        LoginActivity.this.startActivity(intent);
                                    }
                                    else {
                                        JSONObject order = response.getJSONObject("order");
                                        JSONArray products = order.getJSONArray("products");
                                        ShoppingCart productQuantities = new ShoppingCart();

                                        String street = order.getString("street");
                                        String city = order.getString("city");
                                        String state = order.getString("state");
                                        String zip = order.getString("zip");
                                        String created = order.getString("date");
                                        String order_status = order.getString("status");
                                        String order_id = order.getString("order_id");
                                        String milker_id = order.getString("milker_id");
                                        String buyer_id = order.getString("buyer_id");
                                        HashMap<String, String> address = new HashMap<>();
                                        address.put("street", street);
                                        address.put("city", city);
                                        address.put("state", state);
                                        address.put("zip", zip);
                                        for (int j = 0; j < products.length(); j++) {
                                            JSONObject aProduct = products.getJSONObject(j);
                                            String product_id = aProduct.getString("product_id");
                                            String name = aProduct.getString("name");
                                            String price = aProduct.getString("price");
                                            Product p = new Product(product_id, name, price);
                                            String quantity = aProduct.getString("quantity");
                                            productQuantities.put(p, quantity);
                                        }
                                        Order o = new Order(order_id, address, buyer_id, order_status, milker_id, created, productQuantities);
                                        user.setCurrentOrder(o);
                                        Intent intent = new Intent(LoginActivity.this, GetterActivity.class);
                                        intent.putExtra("user", user);
                                        LoginActivity.this.startActivity(intent);
                                    }
                                }
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }


                    }catch (JSONException e){
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Login Failed")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                }
                };

                JSONObject params = new JSONObject();

                try {
                    params.put("username", username);
                    params.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(params.toString());
                RequestClass POSTLogin = new RequestClass("http://34.208.156.179:4567/api/v1/auth/login", params, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error);
                        AlertDialog.Builder serverError = new AlertDialog.Builder(LoginActivity.this);
                        serverError.setTitle("Something went wrong :(");
                        serverError.setMessage("Sorry your request could not be completed at this time. Try again later.");
                        serverError.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                    }
                });

                RequestQueue queue =  Volley.newRequestQueue(LoginActivity.this);
                queue.add(POSTLogin);

            }
        });

        b_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(v.getContext(), RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
}
