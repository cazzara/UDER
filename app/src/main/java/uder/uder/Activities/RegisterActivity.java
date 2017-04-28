package uder.uder.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import uder.uder.HelperClasses.RequestClass;
import uder.uder.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText et_fname = (EditText) findViewById(R.id.et_fname);
        final EditText et_lname = (EditText) findViewById(R.id.et_lname);
        final EditText et_username = (EditText) findViewById(R.id.et_username);
        final EditText et_password = (EditText) findViewById(R.id.et_password);

        final TextView tv_register_welcome = (TextView) findViewById(R.id.tv_register_welcome);
        final TextView tv_choice = (TextView) findViewById(R.id.tv_choice);

        final RadioGroup rg_group = (RadioGroup) findViewById(R.id.rg_group);
        final RadioButton rb_user = (RadioButton) findViewById(R.id.rb_user);
        final RadioButton rb_getter = (RadioButton) findViewById(R.id.rb_getter);

        final Button b_signup = (Button) findViewById(R.id.b_signup);

        b_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /* When creating the variables, the toString() call is necessary because
                getText() returns a CharSequence */
                final String username = et_username.getText().toString();
                final String password = et_password.getText().toString();
                final String first_name = et_fname.getText().toString();
                final String last_name = et_fname.getText().toString();

                // Awesome use of ternary conditional operator '?'
                // condition ? value_if_true : value_if_false
                final String user_type = rb_user.isSelected() ? "buyer" : "milker";


                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            System.out.println(response.toString());
                            if(response.getString("status").equals("OK")){
                                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                                login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(login);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                JSONObject params = new JSONObject();

                try {
                    params.put("username", username);
                    params.put("password", password);
                    params.put("first_name", first_name);
                    params.put("last_name", last_name);
                    params.put("user_type", user_type);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestClass POSTRegister = new RequestClass("http://34.208.156.179:4567/api/v1/auth/register", params, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error);
                        AlertDialog.Builder serverError = new AlertDialog.Builder(RegisterActivity.this);
                        serverError.setTitle("Something went wrong :(");
                        serverError.setMessage("Sorry your request could not be completed at this time. Try again later.");
                        serverError.setNegativeButton("Back to Login Page", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(loginIntent);
                            }
                        }).create().show();
                    }
                });

                RequestQueue queue =  Volley.newRequestQueue(RegisterActivity.this);
                queue.add(POSTRegister);

            }

        });



    }
}
