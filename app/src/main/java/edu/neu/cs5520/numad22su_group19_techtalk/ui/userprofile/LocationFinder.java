package edu.neu.cs5520.numad22su_group19_techtalk.ui.userprofile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import edu.neu.cs5520.numad22su_group19_techtalk.R;

public class LocationFinder extends AppCompatActivity {
    private static final String TAG = "LocationActivity";
    private static final int PERMISSION_GPS_REQUEST_CODE = 10;
    private static final int PRECISION_UPDATE_INTERVAL = 1000;
    private static final int PRECISION_HIGH_UPDATE_INTERVAL = 500;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallBack;
    private Location startLocation;
    private double latitude;
    private double longitude;
    public String city;

    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate()");
//        setContentView(R.layout.fragment_user_profile);
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate()");
        setContentView(R.layout.fragment_user_profile);
        // start Location
        startLocation = null;
        // Update interval
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(PRECISION_UPDATE_INTERVAL);
        locationRequest.setFastestInterval(PRECISION_HIGH_UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Called on specified precision interval
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                } else {
                    refreshUI(locationResult.getLastLocation());
                }
            }
        };
//            btnResetDistance = findViewById(R.id.btnResetDist);
//            btnResetDistance.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startLocation = null;
//                    distanceTraveled = 0;
//                    updateGPSCoordinates();
//                    updateDistance();
//                }
//            });
        // Update Coordinates
        Log.d(TAG, "Calling update gps coordinates");

        updateGPSCoordinates();
        // start Tracking
//            updateDistance();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_GPS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateGPSCoordinates();
            } else {
                Toast.makeText(LocationFinder.this, "This Activity requires location permissions!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateGPSCoordinates() {
        // Set permission
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LocationFinder.this);
        if (ActivityCompat.checkSelfPermission(LocationFinder.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(LocationFinder.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location loc) {
                    if (startLocation == null) {
                        startLocation = loc;
                    }
                    Log.d(TAG, "Calling refreshUI");

                    refreshUI(loc);
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GPS_REQUEST_CODE);
            }
        }
    }

    private void refreshUI(Location location) {

        // Detect if user is in the same location
//            if (location.distanceTo(startLocation) > 0) {
//                distanceTraveled += location.distanceTo(startLocation);
//                startLocation = location;
//            }
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Geocoder geocoder = new Geocoder(LocationFinder.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            city = addresses.get(0).getAddressLine(0);
            Log.d(TAG, city);

        }
//            txtLatitude.setText("Latitude: " + String.valueOf(location.getLatitude()));
//            txtLongitude.setText("Longitude: " + String.valueOf(location.getLongitude()));
//            txtDistanceTraveled.setText("Distance Traveled in meter: " + String.valueOf(distanceTraveled));
//            if(swPrecision.isChecked()) {
//                locationRequest.setInterval(PRECISION_UPDATE_INTERVAL);
//                locationRequest.setFastestInterval(PRECISION_HIGH_UPDATE_INTERVAL);
//                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            } else {
//                locationRequest.setInterval(PRECISION_UPDATE_INTERVAL);
//                locationRequest.setFastestInterval(PRECISION_HIGH_UPDATE_INTERVAL);
//                locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//            }
    }

    @SuppressLint("MissingPermission")
    private void updateDistance() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
    }
}

