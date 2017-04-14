package uder.uder.Activities;

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

import uder.uder.HelperClasses.Filter;
import uder.uder.HelperClasses.Milker_User;
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
        final TextView tv_register = (TextView) findViewById(R.id.tv_register);
        final TextView tv_welcome  = (TextView) findViewById(R.id.tv_welcome);
        final Button b_login = (Button) findViewById(R.id.b_login);
        final ImageButton user_button = (ImageButton) findViewById(R.id.user_button);
        final ImageButton driver_button = (ImageButton) findViewById(R.id.driver_button);

        JSONproducts();


        user_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Regular_User me = new Regular_User("1", "Chris", "Azzara", "cazzara", "password", new ShoppingCart(), new Filter());

                Intent user_areaIntent = new Intent(v.getContext(), UserActivity.class);
                user_areaIntent.putExtra("user", me);

                LoginActivity.this.startActivity(user_areaIntent);
            }
        });

        driver_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Milker_User me = new Milker_User("1", "Chris", "Azzara", "cazzara", "password", null);
                Intent getter_areaIntent = new Intent(v.getContext(), GetterActivity.class);

                getter_areaIntent.putExtra("user", me);
                LoginActivity.this.startActivity(getter_areaIntent);
            }
        });



        b_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                final String username = et_username.getText().toString();
                final String password = et_password.getText().toString();

                final JSONObject serverResponse = new JSONObject();
                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){


                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            serverResponse.put("status", response);

                        } catch (JSONException e){
                            e.printStackTrace();
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
                RequestClass POSTLogin = new RequestClass("http://34.208.156.179:4567", params, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error);
                    }
                });

                RequestQueue queue =  Volley.newRequestQueue(LoginActivity.this);
                queue.add(POSTLogin);


                String status = null;
                String user_type = null;
                String user_id = null;
                String first_name = null;
                String last_name = null;

                try {
                    status = serverResponse.getString("status");
                    user_type = serverResponse.getString("user_type");
                    user_id = serverResponse.getString("id");
                    first_name = serverResponse.getString("first_name");
                    last_name = serverResponse.getString("last_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(status.equals("OK")){
                    if(user_type.equals("buyer")) {

                        Regular_User user = new Regular_User(user_id, first_name, last_name, username, password, new ShoppingCart(), new Filter());
                        Intent intent = new Intent(LoginActivity.this, UserActivity.class);

                        intent.putExtra("user", user);
                        LoginActivity.this.startActivity(intent);
                    }
                    else{
                        Milker_User user = new Milker_User(user_id, first_name, last_name, username, password, null);
                        Intent intent = new Intent(LoginActivity.this, GetterActivity.class);
                        intent.putExtra("user", user);
                        LoginActivity.this.startActivity(intent);
                    }
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Login Failed")
                            .setNegativeButton("Retry", null)
                            .create()
                            .show();
                }
            }
        });

        tv_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(v.getContext(), RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }

    public void JSONproducts(){
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("1", "Horizon Whole Milk", "5.99"));
        products.add(new Product("2", "Dairy Pure Soy Milk", "1.99"));
        products.add(new Product("3", "Amish Farms 1% Milk", "3.99"));
        products.add(new Product("4", "Horizon 2% Milk", "4.99"));
        products.add(new Product("5", "Tuscan Whole Milk", "6.99"));
        products.add(new Product("6", "Wellsley Farms 2% Milk", "2.50"));
        products.add(new Product("7", "Tuscan Half and Half", "2.19"));
        products.add(new Product("8", "Dairy Pure 1% Milk", "1.99"));

        JSONObject product_list = new JSONObject();
        for(int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            try {
                JSONObject json_p = new JSONObject();
                json_p.put("id", p.getProductID());
                json_p.put("name", p.getProductName());
                json_p.put("price", p.getProductPrice());

                product_list.put(""+i, json_p);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println(product_list.toString());
        System.out.println(product_list.length());

    }
}
