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

public class OrderStatusActivity extends AppCompatActivity {

    private Regular_User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        currentUser = (Regular_User) getIntent().getSerializableExtra("user");
        final TextView orderStatusMessage = (TextView) findViewById(R.id.tvOrderStatusMessage);
        orderStatusMessage.setText("Order Status for " + currentUser.getfName() + " " + currentUser.getlName());

        final TextView orderStatus = (TextView) findViewById(R.id.tvOrderStatus);
        final Button goBack = (Button) findViewById(R.id.b_goBackOrderStatus);

        JSONObject order = getOrderStatus();
        updateOrderStatusMessage(orderStatus, order);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToUserPage = new Intent(v.getContext(), UserActivity.class);
                backToUserPage.putExtra("user", currentUser);
                v.getContext().startActivity(backToUserPage);
            }
        });

    }

    public void updateOrderStatusMessage(TextView message, JSONObject orderStatus){
        String status = null;
        try {
            status = orderStatus.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(status.equals("null")){
            message.setText("No Order in Progress");
        }
        else{
            try {
                message.setText("Order#: " + orderStatus.getString("id") + " Status: " + orderStatus.getString("status"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public JSONObject getOrderStatus(){
        final JSONObject orderStatus = new JSONObject();
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").equals("OK")) {
                        orderStatus.put("id", response.getString("id"));
                        orderStatus.put("order_status", response.getString("status"));
                    }
                    // More?
                    else{
                        orderStatus.put("id", "null");
                    }
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
        RequestClass getOrderStatusFromServer = new RequestClass("http://34.208.156.179:4567/api/v1/orderhistory", params, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(OrderStatusActivity.this);
        queue.add(getOrderStatusFromServer);
        return orderStatus;
    }
}
