package akedari.cs442.com.kedari_abhijeet_a7;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Button search;
    TextView latitude;
    TextView longitude;
    EditText cityname;
    String cityToSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        search = (Button) findViewById(R.id.navigate);
        latitude  = (TextView)findViewById(R.id.latitude);
        longitude = (TextView)findViewById(R.id.longitude);

        cityname = (EditText) findViewById(R.id.cityname);

        // To search City on Hit of Enter
        cityname.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    getLocationDetails(v);
                    return true;
                }
                return false;
            }
        });


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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    //To move the camera and add marker on entered city

    public  void moveLocation(long latitude, long longitude){
        LatLng mycity = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(mycity).title("This is My city"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(mycity));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mycity,6));
    }

    //To get the entered city address and validate if it is valid input or if it is not found.

    public void getLocationDetails(View view) {

        cityToSearch = String.valueOf(cityname.getText());
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Geocoder coder = new Geocoder(getApplicationContext());
        List<Address> address;

        try {
            address = coder.getFromLocationName(cityToSearch,5);
            if (address==null) {
                //return null;
                Toast.makeText(getApplicationContext(),
                        "Location not found", Toast.LENGTH_LONG).show();
            }
            Address location=address.get(0);
            latitude.setText(Double.toString(location.getLatitude()));
            longitude.setText(Double.toString(location.getLongitude()));

            moveLocation((long) location.getLatitude(), (long) location.getLongitude());
        }
        catch (Exception ex) {
            Toast.makeText(getApplicationContext(),
                    "Location is valid", Toast.LENGTH_LONG).show();
        }
    }
}
