package info.diepnguyen.locationtracking;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class CurrentUserMap extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;

    private String email;
    DatabaseReference locations;

    Double lat, lng;

    //Sensor
    private SensorManager mSensorManager;
    private Sensor mLight;
    private Sensor mPressure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_user_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Sensor
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

        //Ref to Firebase first
        locations = FirebaseDatabase.getInstance().getReference("Locations");

        //Get intent
        if(getIntent() != null){
            email = getIntent().getStringExtra("email");
            lat  = getIntent().getDoubleExtra("lat",0);
            lng = getIntent().getDoubleExtra("lng",0);

        }
        if(!TextUtils.isEmpty(email)){
            loadLocationForThisUser(email);
        }
        Log.i("Current User Map","on Create starting");
    }

    private void loadLocationForThisUser(String email) {
        Query user_location = locations.orderByChild("email").equalTo(email);
        user_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Tracking tracking = postSnapshot.getValue(Tracking.class);

                    //Create location from user coordinate
                    Location currentUser = new Location("");
                    currentUser.setLatitude(lat);
                    currentUser.setLongitude(lng);

                }
                //Create marker for current user
                LatLng current = new LatLng(lat,lng);
                mMap.addMarker(new MarkerOptions().position(current)
                        .title(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),12.0f));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Current User Map","on Resume starting");

    }

    @Override
    protected void onPause() {

        super.onPause();
        Log.i("Current User Map","on Pause starting");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Current User Map","on Start starting");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Current User Map","on Stop starting");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Current User Map","on Restart starting");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Current User Map","on Destroy starting");
    }
}
