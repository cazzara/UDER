package uder.uder.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import uder.uder.HelperClasses.Milker_User;
import uder.uder.HelperClasses.Order;
import uder.uder.HelperClasses.Product;
import uder.uder.HelperClasses.RequestClass;
import uder.uder.HelperClasses.ShoppingCart;
import uder.uder.OrderAdapter;
import uder.uder.R;

public class OrderListActivity extends AppCompatActivity {

    private Milker_User currentUser;
    private ArrayList<Order> orders;
    private OrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);


        getOrders();

        final Button cancel = (Button) findViewById(R.id.b_cancelOrderSelection);

        currentUser = (Milker_User) getIntent().getSerializableExtra("user");

        ListView orderList = (ListView) findViewById(R.id.lv_orderList);


        System.out.println(orders.toString());
        adapter = new OrderAdapter(getApplicationContext(), orders);
        orderList.setAdapter(adapter);

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder acceptJobDialog = new AlertDialog.Builder(OrderListActivity.this);
                acceptJobDialog.setTitle("Accept Order "+ orders.get(position).getOrder_id() + " as current order?");
                acceptJobDialog.setMessage("Order Details: " + orders.get(position).toString() + "\nAddress: " + orders.get(position).getAddress());
                acceptJobDialog.setNegativeButton("No Thanks", null);
                acceptJobDialog.setPositiveButton("Accept Order", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentUser.setCurrentOrder(orders.get(position));
                        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getString("status").equals("OK")) {
                                        Intent milkerIntent = new Intent(getApplicationContext(), GetterActivity.class);
                                        milkerIntent.putExtra("user", currentUser);
                                        milkerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        getApplicationContext().startActivity(milkerIntent);
                                    }
                                }
                                catch (JSONException error){
                                    System.out.println(error);
                                }

                            }
                        };
                        JSONObject params = new JSONObject();
                        try{
                            params.put("order_id", currentUser.getCurrentOrder().getOrder_id());
                            params.put("milker_id", currentUser.getUserID());
                        } catch (JSONException error){
                            System.out.println(error);
                        }

                        RequestClass orderAccept = new RequestClass("http://34.208.156.179:4567/api/v1/orders/accept", params, listener, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });

                        RequestQueue queue = Volley.newRequestQueue(OrderListActivity.this);
                        queue.add(orderAccept);
                    }
                }).create().show();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.setCurrentOrder(null);
                Intent jobNotAccepted = new Intent(v.getContext(), GetterActivity.class);
                jobNotAccepted.putExtra("user", currentUser);
                v.getContext().startActivity(jobNotAccepted);
            }
        });


    }

    public void getOrders() {

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("Server response: " + response.toString());
                try {
                    if (response.get("status").equals("OK")) {
                        JSONArray orderArray = response.getJSONArray("orders");
                        for (int i = 0; i < orderArray.length(); i++) {
                            JSONObject order = orderArray.getJSONObject(i);
                            JSONArray products = order.getJSONArray("products");
                            ShoppingCart productQuantities = new ShoppingCart();

                            String street = order.getString("street");
                            String city = order.getString("city");
                            String state = order.getString("state");
                            String zip = order.getString("zip");
                            String created = order.getString("date");
                            String order_id = order.getString("order_id");
                            String buyer_id = order.getString("buyer_id");
                            HashMap<String, String> address = new HashMap<>();
                            address.put("street", street);
                            address.put("city", city);
                            address.put("state", state);
                            address.put("zip", zip);
                            for (int j = 0; j < products.length(); j++) {
                                JSONObject aProduct = products.getJSONObject(j);
                                String product_id = aProduct.getString("product_id");
                                String name = aProduct.getString("name");
                                String price = aProduct.getString("price");
                                Product p = new Product(product_id, name, price);
                                String quantity = aProduct.getString("quantity");
                                productQuantities.put(p, quantity);
                            }
                            Order o = new Order(order_id, address, buyer_id, "Not Milked", null, created, productQuantities);
                            orders.add(o);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }
        };
        RequestClass getOrdersFromServer = new RequestClass("http://34.208.156.179:4567/api/v1/orders/unmilked", null, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                AlertDialog.Builder serverError = new AlertDialog.Builder(OrderListActivity.this);
                serverError.setTitle("Something went wrong :(");
                serverError.setMessage("Sorry your request could not be completed at this time. Try again later.");
                serverError.setNegativeButton("Back to Main Page", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent userActivity = new Intent(getApplicationContext(), GetterActivity.class);
                        userActivity.putExtra("user", currentUser);
                        userActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(userActivity);
                    }
                }).create().show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(OrderListActivity.this);
        orders = new ArrayList<>();
        queue.add(getOrdersFromServer);

        try {

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
