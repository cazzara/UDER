package uder.uder.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uder.uder.HelperClasses.Regular_User;
import uder.uder.R;

public class CreateOrderActivity extends AppCompatActivity {

    private Regular_User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        currentUser = (Regular_User) getIntent().getSerializableExtra("user");
    }
}
