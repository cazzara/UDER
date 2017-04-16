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

import uder.uder.HelperClasses.Milker_User;
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
            // Account Management Fragment
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
