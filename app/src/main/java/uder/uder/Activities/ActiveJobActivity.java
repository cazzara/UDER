package uder.uder.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import uder.uder.HelperClasses.Milker_User;
import uder.uder.R;

public class ActiveJobActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private boolean LOCATION_PERMISSION_GRANTED;
    private Milker_User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_job);
        currentUser = (Milker_User) getIntent().getSerializableExtra("user");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            LOCATION_PERMISSION_GRANTED = true;
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        // Add a marker in Sydney and move the camera
        mMap.setMyLocationEnabled(LOCATION_PERMISSION_GRANTED);
        Address jobLocation = null;
        try {
            jobLocation = getJobAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LatLng job = new LatLng(jobLocation.getLatitude(), jobLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(job).title("Order ID: " + currentUser.getCurrentOrder().getOrder_id())).showInfoWindow();


        mMap.moveCamera(CameraUpdateFactory.newLatLng(job));

    }

    public Address getJobAddress() throws IOException{
        Geocoder gc = new Geocoder(this);

        List<Address> list = gc.getFromLocationName(currentUser.getCurrentOrder().getAddress(), 1);
        Address address = list.get(0);
        return address;
    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LOCATION_PERMISSION_GRANTED = false;
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LOCATION_PERMISSION_GRANTED = true;
                }
                break;
            }

        }

    }
}


