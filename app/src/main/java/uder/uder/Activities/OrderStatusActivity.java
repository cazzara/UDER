package uder.uder.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import uder.uder.HelperClasses.Regular_User;
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
        JSONObject order = getOrderStatus();
        updateOrderStatusMessage(orderStatus, order);
        final Button goBack = (Button) findViewById(R.id.b_goBackOrderStatus);

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
        if(orderStatus.length() == 0){
            message.setText("No Order in Progress");
        }
        else{
            // TODO Display order status
        }

    }

    public JSONObject getOrderStatus(){
        JSONObject orderStatus = new JSONObject();





        return orderStatus;
    }
}
