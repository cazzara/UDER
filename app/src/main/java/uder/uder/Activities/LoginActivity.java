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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uder.uder.R;
import uder.uder.RequestClass;

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


        user_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent user_areaIntent = new Intent(v.getContext(), UserActivity.class);
                LoginActivity.this.startActivity(user_areaIntent);
            }
        });

        driver_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent getter_areaIntent = new Intent(v.getContext(), GetterActivity.class);
                LoginActivity.this.startActivity(getter_areaIntent);
            }
        });

        b_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                final String username = et_username.getText().toString();
                final String password = et_password.getText().toString();

                Response.Listener<String> listener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String user_type = jsonObject.getString("user_type");
                            if(success){
                                if(user_type.equals("reg_user")) {
                                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                    intent.putExtra("username", username);
                                    LoginActivity.this.startActivity(intent);
                                }
                                else{
                                    Intent intent = new Intent(LoginActivity.this, GetterActivity.class);
                                    intent.putExtra("username", username);
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
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                RequestClass POSTLogin = new RequestClass("URLHERE", params, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error);
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
