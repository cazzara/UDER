package uder.uder.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import uder.uder.HelperClasses.Order;
import uder.uder.HelperClasses.Regular_User;
import uder.uder.HelperClasses.RequestClass;
import uder.uder.R;

public class CreateOrderActivity extends AppCompatActivity {

    private Regular_User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        currentUser = (Regular_User) getIntent().getSerializableExtra("user");

        final Button backToCheckout = (Button) findViewById(R.id.b_goBackCreateOrder);
        final Button submitOrder = (Button) findViewById(R.id.b_submitOrderFinal);

        final EditText addressLine1 = (EditText) findViewById(R.id.et_addressLine1);
        final EditText addressLine2 = (EditText) findViewById(R.id.et_addressLine2);
        final EditText city = (EditText) findViewById(R.id.et_city);
        final EditText zip = (EditText) findViewById(R.id.et_zip);

        final Spinner state = (Spinner) findViewById(R.id.sp_state);
        ArrayList<String> statesArrayList = statesList();

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, statesArrayList);

        state.setAdapter(stateAdapter);

        backToCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkout = new Intent(v.getContext(), CheckoutActivity.class);
                checkout.putExtra("user", currentUser);
                v.getContext().startActivity(checkout);
            }
        });

        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> address = new HashMap<>();
                address.put("street", addressLine1.getText().toString() + " " + addressLine2.getText().toString());
                address.put("city", city.getText().toString());
                address.put("state", state.getSelectedItem().toString());
                address.put("zip", zip.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String orderCreated = sdf.format(new Date());

                Order myOrder = new Order(null, address, currentUser.getUserID(), "Not Milked", null, orderCreated, currentUser.getShoppingCart());


                JSONObject order = myOrder.orderToJSON();
                System.out.println(order.toString());
                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Server response: " + response.toString());
                        try{
                            if(response.get("status").equals("OK")){
                                AlertDialog.Builder serverError = new AlertDialog.Builder(CreateOrderActivity.this);
                                serverError.setTitle("Order Received! :)");
                                serverError.setMessage("Your order has been received and will be picked up by a Milker shortly\n Thanks for using UDER!");
                                serverError.setNegativeButton("Back to Main Page", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent userActivity = new Intent(getApplicationContext(), UserActivity.class);
                                        userActivity.putExtra("user", currentUser);
                                        userActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        getApplicationContext().startActivity(userActivity);
                                    }
                                }).create().show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                };

                RequestClass sendOrderToServer = new RequestClass("http://34.208.156.179:4567/api/v1/order/create", order, listener, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                });

                RequestQueue queue = Volley.newRequestQueue(CreateOrderActivity.this);
                queue.add(sendOrderToServer);


            }
        });

    }

    public ArrayList<String> statesList(){
        String states = "Alabama,Alaska,Arizona,Arkansas,California,Colorado,Connecticut,Delaware,Florida,Georgia,Hawaii,Idaho,Illinois, Indiana,Iowa,Kansas,Kentucky,Louisiana,Maine,Maryland,Massachusetts,Michigan,Minnesota,Mississippi,Missouri,Montana,Nebraska,Nevada,New Hampshire,New Jersey,New Mexico,New York,North Carolina,North Dakota,Ohio,Oklahoma,Oregon,Pennsylvania,Rhode Island,South Carolina,South Dakota,Tennessee,Texas,Utah,Vermont,Virginia,Washington,West Virginia,Wisconsin,Wyoming";
        ArrayList<String> list = new ArrayList<>();
        Scanner state_scan = new Scanner(states);
        state_scan.useDelimiter(",");
        while(state_scan.hasNext()) {
            String state = state_scan.next();
            list.add(state);
        }
        return list;

    }
}
