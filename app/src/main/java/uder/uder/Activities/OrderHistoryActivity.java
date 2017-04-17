package uder.uder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import uder.uder.HelperClasses.Regular_User;
import uder.uder.HelperClasses.RequestClass;
import uder.uder.R;

public class OrderHistoryActivity extends AppCompatActivity {
    private Regular_User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        final TextView orderTextView = (TextView) findViewById(R.id.tvOrder1);
        final TextView orderHistoryMessage = (TextView) findViewById(R.id.tvOrderHistoryMessage);
        final Button goBack = (Button) findViewById(R.id.b_goBackOrderHistory);
        currentUser = (Regular_User) getIntent().getSerializableExtra("user");
        orderHistoryMessage.setText("Order History Details for " + currentUser.getfName() + " " + currentUser.getlName());

        JSONObject orders = getPastOrders();
        updateOrderTextView(orderTextView, orders);


        System.out.println(orders);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToUserPage = new Intent(v.getContext(), UserActivity.class);
                backToUserPage.putExtra("user", currentUser);
                v.getContext().startActivity(backToUserPage);
            }
        });



    }

    protected void updateOrderTextView(TextView textView, JSONObject orders){
        if(orders.length() == 0){
            textView.setText("Sorry " + currentUser.getfName() + " you have no past orders, get milkin'!");
        }
        else{
            // TODO Display past orders for user
        }

    }

    protected JSONObject getPastOrders(){
        final JSONObject pastOrders = new JSONObject();

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    pastOrders.put("orders", response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        JSONObject params = new JSONObject();
        try {
            params.put("user_id", currentUser.getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestClass getOrders = new RequestClass("http://34.208.156.179:4567/api/orderhistory", params, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

//        RequestQueue queue = Volley.newRequestQueue(OrderHistoryActivity.this);
//        queue.add(getOrders);

        return pastOrders;

    }


}
