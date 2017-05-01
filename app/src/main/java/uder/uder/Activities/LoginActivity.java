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
        final TextView tv_register = (TextView) findViewById(R.id.tv_register);
        final TextView tv_welcome  = (TextView) findViewById(R.id.tv_welcome);
        final ImageButton b_login = (ImageButton) findViewById(R.id.b_login);
        final ImageButton user_button = (ImageButton) findViewById(R.id.user_button);
        final ImageButton driver_button = (ImageButton) findViewById(R.id.driver_button);



        user_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Regular_User me = new Regular_User("5", "Chris", "Azzara", "cazzara", "password", new ShoppingCart(), new Filter());

                Intent user_areaIntent = new Intent(v.getContext(), UserActivity.class);
                user_areaIntent.putExtra("user", me);

                LoginActivity.this.startActivity(user_areaIntent);
            }
        });

        driver_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Milker_User me = new Milker_User("5", "Chris", "Azzara", "cazzara", "password", null);
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
                            String user_type = response.getString("user_type");
                            String user_id = response.getString("user_id");
                            String first_name = response.getString("first_name");
                            String last_name = response.getString("last_name");

                            if(status.equals("OK")){
                                if(user_type.equals("buyer")) {

                                    Regular_User user = new Regular_User(user_id, first_name, last_name, username, password, new ShoppingCart(), new Filter());
                                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);

                                    intent.putExtra("user", user);
                                    LoginActivity.this.startActivity(intent);
                                }
                                else{
                                    Milker_User user = new Milker_User(user_id, first_name, last_name, username, password, null);
                                    // // TODO: 4/27/2017 Return milker active order 
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


                    }catch (JSONException e){
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

        tv_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(v.getContext(), RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
}
