package uder.uder.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import uder.uder.HelperClasses.Milker_User;
import uder.uder.HelperClasses.RequestClass;
import uder.uder.R;

public class GetterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CharSequence appBarTitle;
    private Milker_User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getter);
        currentUser = (Milker_User) getIntent().getSerializableExtra("user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final ImageButton orderComplete = (ImageButton) findViewById(R.id.b_completeOrder);
        if(currentUser.getCurrentOrder() == null) { orderComplete.setVisibility(View.INVISIBLE); }
        orderComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("status").equals("OK")) {
                                    currentUser.setCurrentOrder(null);
                                    orderComplete.setVisibility(View.INVISIBLE);
                                    AlertDialog.Builder jobMapAlert = new AlertDialog.Builder(GetterActivity.this);
                                    jobMapAlert.setMessage("Job Complete!")
                                            .setNegativeButton("Exit", null)
                                            .create()
                                            .show();

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
                    } catch (JSONException error){
                        System.out.println(error);
                    }

                    RequestClass orderComplete = new RequestClass("http://34.208.156.179:4567/api/v1/orders/complete", params, listener, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    RequestQueue queue = Volley.newRequestQueue(GetterActivity.this);
                    queue.add(orderComplete);


            }
        });

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        appBarTitle = "Welcome " + currentUser.getfName();
        setTitle(appBarTitle);
    }
    @Override
    public void setTitle(CharSequence title) {
        appBarTitle = title;
        getSupportActionBar().setTitle(appBarTitle);

    }

    @Override
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
        getMenuInflater().inflate(R.menu.getter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_jobMap:
                if(currentUser.getCurrentOrder() == null){
                    AlertDialog.Builder jobMapAlert = new AlertDialog.Builder(GetterActivity.this);
                    jobMapAlert.setMessage("No Active Job!")
                            .setNegativeButton("Exit", null)
                            .create()
                            .show();
                }
                else{
                    Intent activeJobIntent = new Intent(this, ActiveJobActivity.class);
                    activeJobIntent.putExtra("user", currentUser);
                    this.startActivity(activeJobIntent);

                }
                return true;
        }
        return true;

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_acct_mgmt) {
            Intent acctMgmtIntent = new Intent(this, AccountSettingsActivity.class);
            acctMgmtIntent.putExtra("user", currentUser);
            this.startActivity(acctMgmtIntent);
        }
        else if (id == R.id.action_order_details){
            Intent jobDetails = new Intent(this, ActiveJobDetails.class);
            jobDetails.putExtra("user", currentUser);
            this.startActivity(jobDetails);

        }
        else if (id == R.id.action_logout){
            // Logout Sequence
            Intent logout = new Intent(this, LoginActivity.class);
            currentUser = null;
            this.startActivity(logout);
        }
        else if(id == R.id.action_list_orders){
            Intent orderListIntent = new Intent(this, OrderListActivity.class);
            orderListIntent.putExtra("user", currentUser);
            this.startActivity(orderListIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
