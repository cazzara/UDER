package uder.uder.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import uder.uder.HelperClasses.RequestClass;
import uder.uder.R;

public class OrderHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        TextView orderTextView = (TextView) findViewById(R.id.tvOrder1);

        updateOrderTextView(orderTextView);
        JSONObject orders = getPastOrders();



    }

    protected void updateOrderTextView(TextView textView){
        textView.setText("Order 1 details go here");
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

        RequestClass getOrders = new RequestClass("http://34.208.156.179:4567/api/orderhistory", null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        return pastOrders;

    }
}
