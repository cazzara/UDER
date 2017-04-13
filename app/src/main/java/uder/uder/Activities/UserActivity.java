package uder.uder.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uder.uder.HelperClasses.User;
import uder.uder.R;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CharSequence appBarTitle;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        System.out.println("User Activity created");

        currentUser = (User)getIntent().getSerializableExtra("user");

        System.out.println("Filter Object: "+currentUser.getMyFilter() +" Cart Object: "+ currentUser.getMyCart());


        final TextView cartContents = (TextView)findViewById(R.id.tv_cartContents);
        final TextView filterContents = (TextView)findViewById(R.id.tv_filterContents);

        Button clearCart = (Button)findViewById(R.id.b_clearCart);
        Button clearFilter = (Button)findViewById(R.id.b_clearFilter);

        clearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.getMyCart().clearCart();
                displayCartContents(cartContents, currentUser);
            }
        });

        clearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.getMyFilter().clearFilter();
                displayFilterContents(filterContents, currentUser);
            }
        });

        displayCartContents(cartContents,  currentUser);
        displayFilterContents(filterContents, currentUser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // https://developer.android.com/training/implementing-navigation/nav-drawer.html
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        appBarTitle = "Welcome " + currentUser.getFirstName();
        setTitle(appBarTitle);
    }

    public void displayCartContents(TextView cartContents, User user) {
        if(user.getMyCart().isEmpty())
            cartContents.setText("Shopping Cart is Empty");
        else
            cartContents.setText(user.getMyCart().toString());
    }

    public void displayFilterContents(TextView filterContents, User user){
        filterContents.setText(user.getMyFilter().toString());
    }

    @Override
    public void setTitle(CharSequence title) {
        appBarTitle = title;
        getSupportActionBar().setTitle(appBarTitle);

    }

    @Override
    // Close Navigation Drawer if it's open before exiting the activity
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_checkout:
                Intent checkoutIntent = new Intent(this, CheckoutActivity.class);
                checkoutIntent.putExtra("user", currentUser);
                this.startActivity(checkoutIntent);
                break;
            case R.id.action_filters:
                Intent filterIntent = new Intent(this, FilterActivity.class);
                filterIntent.putExtra("user", currentUser);
                this.startActivity(filterIntent);
                break;
        }
        return true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("User Activity Resumed");

        currentUser = (User)getIntent().getSerializableExtra("user");

        System.out.println("Filter Object: "+currentUser.getMyFilter() +" Cart Object: "+ currentUser.getMyCart());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.order_history) {
           //Start order history activity
            Intent orderHistoryIntent = new Intent(this, OrderHistoryActivity.class);
            this.startActivity(orderHistoryIntent);

        } else if (id == R.id.order_status) {
            //Start order status activity
            Intent orderStatusIntent = new Intent(this, OrderStatusActivity.class);
            this.startActivity(orderStatusIntent);

        }
        else if (id == R.id.action_acct_mgmt){
            // Start Account management activity
            Intent acctMgmtIntent = new Intent(this, AccountSettingsActivity.class);
            this.startActivity(acctMgmtIntent);
        }
        else if (id == R.id.action_logout){
            // Logout Sequence

        }
        else if (id == R.id.action_list_items){
            // Start Product List activity

            Intent productListIntent = new Intent(this, ProductListActivity.class);
            productListIntent.putExtra("user", currentUser);
            this.startActivity(productListIntent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
