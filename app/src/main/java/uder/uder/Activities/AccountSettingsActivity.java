
package uder.uder.Activities;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONException;
import org.json.JSONObject;

import uder.uder.HelperClasses.RequestClass;
import uder.uder.HelperClasses.User;
import uder.uder.R;


public class AccountSettingsActivity extends AppCompatActivity {
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        currentUser = (User)getIntent().getSerializableExtra("user");

        final EditText et_password = (EditText) findViewById(R.id.editText2);
        final EditText new_password = (EditText) findViewById(R.id.editText3);
        final EditText check_password = (EditText) findViewById(R.id.editText4);

        final ImageButton updatePassword = (ImageButton) findViewById(R.id.button2);
        final ImageButton goBack = (ImageButton) findViewById(R.id.b_goBackAcctSettings);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentUser.getUserType().equals("milker")){
                    Intent milkerIntent = new Intent(getApplicationContext(), GetterActivity.class);
                    milkerIntent.putExtra("user", currentUser);
                    milkerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(milkerIntent);
                }
                else{
                    Intent buyerIntent = new Intent(getApplicationContext(), UserActivity.class);
                    buyerIntent.putExtra("user", currentUser);
                    buyerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(buyerIntent);
                }
            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* When creating the variables, the toString() call is necessary because
                getText() returns a CharSequence */
                final String password = et_password.getText().toString();
                final String newPassword = new_password.getText().toString();
                final String check_pass = check_password.getText().toString();

                JSONObject params = new JSONObject();


                try {
                    if (newPassword.equals(check_pass) && password.equals(currentUser.getPassword())) {
                        params.put("password", newPassword);
                    }
                    else if(!password.equals(currentUser.getPassword())){
                        AlertDialog.Builder passError = new AlertDialog.Builder(AccountSettingsActivity.this);
                        passError.setTitle("Something went wrong :(");
                        passError.setMessage("Sorry the password you entered is invalid. Please try again.");
                        passError.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                        return;
                    }
                    else{
                        AlertDialog.Builder matchError = new AlertDialog.Builder(AccountSettingsActivity.this);
                        matchError.setTitle("Something went wrong :(");
                        matchError.setMessage("Sorry the new passwords don't match. Please try again.");
                        matchError.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create().show();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        try{
                            if(response.getString("status").equals("OK")){
                                AlertDialog.Builder passOK = new AlertDialog.Builder(AccountSettingsActivity.this);
                                passOK.setTitle("Password change successful! :)");
                                passOK.setMessage("You can now login using your newly created password.");
                                passOK.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(currentUser.getUserType().equals("milker")){
                                            Intent milkerIntent = new Intent(getApplicationContext(), GetterActivity.class);
                                            milkerIntent.putExtra("user", currentUser);
                                            milkerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            getApplicationContext().startActivity(milkerIntent);
                                        }
                                        else{
                                            Intent buyerIntent = new Intent(getApplicationContext(), UserActivity.class);
                                            buyerIntent.putExtra("user", currentUser);
                                            buyerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            getApplicationContext().startActivity(buyerIntent);
                                        }
                                    }
                                }).create().show();
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                RequestClass POSTRegister = new RequestClass(String.format("http://34.208.156.179:4567/api/v1/auth/%s/change_password", currentUser.getUserID()), params, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error);
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(AccountSettingsActivity.this);
                queue.add(POSTRegister);

            }


        });
    }
}