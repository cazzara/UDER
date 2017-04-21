package uder.uder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import uder.uder.HelperClasses.Milker_User;
import uder.uder.HelperClasses.Order;
import uder.uder.HelperClasses.Product;
import uder.uder.HelperClasses.ShoppingCart;
import uder.uder.OrderAdapter;
import uder.uder.R;

public class OrderListActivity extends AppCompatActivity {

    private Milker_User currentUser;
    private ArrayList<Order> orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        final Button accept = (Button) findViewById(R.id.b_acceptJob);
        final Button cancel = (Button) findViewById(R.id.b_cancelOrderSelection);
        currentUser = (Milker_User) getIntent().getSerializableExtra("user");
        final ListView orderList = (ListView) findViewById(R.id.lv_orderList);
        orders = dummyOrders();
        final OrderAdapter adapter = new OrderAdapter(getApplicationContext(), orders);
        orderList.setAdapter(adapter);

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentUser.setCurrentOrder(orders.get(position));
                Toast.makeText(getApplicationContext(),
                        "Order ID  " + orders.get(position).getOrder_id() + " Set As Current Order", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jobAccepted = new Intent(v.getContext(), GetterActivity.class);
                jobAccepted.putExtra("user", currentUser);
                v.getContext().startActivity(jobAccepted);
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

    public ArrayList<Order> dummyOrders(){
        ArrayList<Order> orders = new ArrayList<>();
        ShoppingCart plist1 = new ShoppingCart();
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("1", "Horizon Whole Milk", "5.99"));
        products.add(new Product("2", "Dairy Pure Soy Milk", "1.99"));
        products.add(new Product("3", "Amish Farms 1% Milk", "3.99"));
        products.add(new Product("4", "Horizon 2% Milk", "4.99"));
        products.add(new Product("5", "Tuscan Whole Milk", "6.99"));
        products.add(new Product("6", "Wellsley Farms 2% Milk", "2.50"));
        products.add(new Product("7", "Tuscan Half and Half", "2.19"));
        products.add(new Product("8", "Dairy Pure 1% Milk", "1.99"));

        plist1.addItem(products.get(0));
        plist1.addItem(products.get(0));
        plist1.addItem(products.get(4));
        plist1.addItem(products.get(4));
        plist1.addItem(products.get(3));

        ShoppingCart plist2 = new ShoppingCart();
        plist2.addItem(products.get(3));
        plist2.addItem(products.get(3));
        plist2.addItem(products.get(5));
        plist2.addItem(products.get(4));
        plist2.addItem(products.get(6));

        ShoppingCart plist3 = new ShoppingCart();
        plist3.addItem(products.get(4));
//
//        orders.add(new Order("1", "8000 Utopia Pkwy Jamaica NY 11439", "32", "Not Milked", null, "2017-10-9", plist1));
//        orders.add(new Order("2", "175-12 Mayfield Rd Jamaica NY 11432", "33", "Not Milked", null, "2017-10-9", plist2));
//        orders.add(new Order("3", "168-12 Union Tpke, Jamaica, NY 11432", "34", "Not Milked", null, "2017-10-9", plist3));

        return null;
    }
}
