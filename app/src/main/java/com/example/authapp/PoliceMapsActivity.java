package com.example.authapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class PoliceMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mfusedloactionprovider;
    private static final String TAG = "PoliceMapsActivity";
    private Boolean mLocationPermissionGranted = false;
    private static final float DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_maps);
        getLocationPermission();


    }

    private void initMap() {
        Log.d(TAG, "initMap: Initialiaze map");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: Getting Device Current Location");
        mfusedloactionprovider = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted) {
                Task location = mfusedloactionprovider.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Found Location!");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                        } else {
                            Log.d(TAG, "onComplete: Current Location Is null");
                            Toast.makeText(PoliceMapsActivity.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: Security Exception" + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving camera: lat" + latLng.latitude + "long:" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: Getting Permission...");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1234);

            }

        } else {
            ActivityCompat.requestPermissions(this, permissions, 1234);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: Called.");
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case 1234: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults[0]; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            break;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: Permission Granted");
                    mLocationPermissionGranted = true;
                    //initialize map
                    initMap();

                }
            }
        }
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
        Log.d(TAG, "onMapReady: Map is Ready");
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }
}