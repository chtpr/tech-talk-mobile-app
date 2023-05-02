package edu.neu.cs5520.numad22su_group19_techtalk;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import edu.neu.cs5520.numad22su_group19_techtalk.databinding.ActivityMainBinding;
import edu.neu.cs5520.numad22su_group19_techtalk.models.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private BottomNavigationView navView;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private User user;

    private static final int PERMISSION_GPS_REQUEST_CODE = 10;
    private static final int PRECISION_UPDATE_INTERVAL = 1000;
    private static final int PRECISION_HIGH_UPDATE_INTERVAL = 500;
    private Location startLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallBack;
    private double latitude;
    private double longitude;
    public String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = null;
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);


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
                    Log.d(TAG, "location is null");

                    return;
                } else {
                    userLocation(locationResult.getLastLocation());
                }
            }
        };

        Log.d(TAG, "GOT USER LOCATION " + city);


        // Update Coordinates
        updateGPSCoordinates();

        // Start Tracking
        updateUserLocation();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_collections, R.id.navigation_learning_paths, R.id.navigation_user_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                int visibility = (navDestination.getId() == R.id.navigation_login) ? View.GONE : View.VISIBLE;
                navView.setVisibility(visibility);
            }
        });
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public void onBackPressed() {
        navView.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        if (navController != null) {
            return navController.navigateUp();
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);

        // Update Action Bar Icon color
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.btnSettings) {
            Log.v(TAG, "onOptionsItemSelected Settings");

            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
            NavController navController = navHostFragment.getNavController();
            navController.navigate(R.id.navigation_settings);
        }
        return super.onOptionsItemSelected(item);
    }

    public Boolean isUserAuthenticated() {
        if (user != null) return true;
        return false;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getCity() {
        Log.d("MainActivity", "getting country in main " + city);
        return city;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_GPS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updateGPSCoordinates();
            } else {
                Toast.makeText(MainActivity.this, "This Activity requires location permissions!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateGPSCoordinates() {
        // Set permission
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location loc) {
                    if (startLocation == null) {
                        startLocation = loc;
                    }
                    userLocation(loc);
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_GPS_REQUEST_CODE);
            }
        }
    }

    private void userLocation(Location location) {
        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
        Log.d(TAG, "geocoder initialized");

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Log.d(TAG, "got addresses " + location.getLatitude() + " "  + location.getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null && addresses.size() > 0) {
            city = addresses.get(0).getLocality();
            Log.d(TAG, city);
        }
    }

    @SuppressLint("MissingPermission")
    private void updateUserLocation() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
    }

}