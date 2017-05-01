
package uder.uder.Activities;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        updatePassword.setOnClickListener(new View.OnClickListener() {
            final JSONObject serverResponse = new JSONObject();

            @Override
            public void onClick(View v) {
                /* When creating the variables, the toString() call is necessary because
                getText() returns a CharSequence */
                final String password = et_password.getText().toString();
                final String newPassword = new_password.getText().toString();

                JSONObject params = new JSONObject();

                try {
                    if (new_password.equals(check_password)) {
                        params.put("password", new_password);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

                RequestClass POSTRegister = new RequestClass("http://34.208.156.179:4567/api/v1/register", params, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.err.println(error);
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(AccountSettingsActivity.this);
                queue.add(POSTRegister);

                // TODO do something with server response
                // serverResponse.get()


            }


        });
    }
}