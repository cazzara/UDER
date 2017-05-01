package uder.uder.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import uder.uder.CheckoutAdapter;
import uder.uder.HelperClasses.Product;
import uder.uder.HelperClasses.Regular_User;
import uder.uder.R;

public class CheckoutActivity extends AppCompatActivity {


    private Regular_User currentUser;
    private ArrayList<Product> userCartContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        final ImageButton submit = (ImageButton) findViewById(R.id.b_submitOrder);
        final ImageButton goBack = (ImageButton) findViewById(R.id.b_goBack);
        final TextView displayTotal = (TextView) findViewById(R.id.tv_total);
        currentUser = (Regular_User) getIntent().getSerializableExtra("user");
        userCartContents = currentUser.getShoppingCart().toArrayList();
        updateTotal(displayTotal);
        testJSON();


        final ListView cartList = (ListView) findViewById(R.id.lv_cartList);
        final CheckoutAdapter adapter = new CheckoutAdapter(getApplicationContext(), userCartContents, currentUser.getShoppingCart());

        cartList.setAdapter(adapter);

        cartList.setOnItemClickListener( new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Product p = userCartContents.get(position);
                AlertDialog.Builder modifyCart = new AlertDialog.Builder(CheckoutActivity.this);
                modifyCart.setTitle("Add/Remove Product");
                modifyCart.setItems(R.array.product_choices, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which == 0){
                            // Add 1 selected
                            currentUser.getShoppingCart().incrementQuantity(p);
                            updateTotal(displayTotal);
                            cartList.invalidateViews();
                        }
                        else if(which == 1){
                            // Remove 1 selected
                            if(Integer.parseInt(currentUser.getShoppingCart().getItemQuantity(p)) == 1){
                                adapter.remove(p);
                                cartList.invalidateViews();
                            }else {
                                currentUser.getShoppingCart().decrementQuantity(p);
                                cartList.invalidateViews();
                            }
                            updateTotal(displayTotal);

                        }
                        else{
                            // Remove From Cart Selected
                            currentUser.getShoppingCart().removeItem(p);
                            updateTotal(displayTotal);
                            adapter.remove(p);
                            cartList.invalidateViews();

                        }
                    }
                });
                modifyCart.show();


            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent createOrder = new Intent(v.getContext(), CreateOrderActivity.class);
                createOrder.putExtra("user", currentUser);
                CheckoutActivity.this.startActivity(createOrder);
            }
        });


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIntent = new Intent(v.getContext(), UserActivity.class);
                userIntent.putExtra("user", currentUser);
                v.getContext().startActivity(userIntent);
            }
        });
    }
    public void updateTotal(TextView total){
        total.setText("Total: " + currentUser.getShoppingCart().getTotal());
    }

    public void testJSON(){
        System.out.println(currentUser.getShoppingCart().cartContentsToJSON());
    }
}
