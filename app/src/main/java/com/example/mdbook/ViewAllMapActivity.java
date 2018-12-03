package com.example.mdbook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class ViewAllMapActivity extends AppCompatActivity implements OnMapReadyCallback {


    private static final String TAG = "ViewMapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;


    /* Vars */
    private ArrayList<MarkerOptions> markers;
    private Boolean mLocationPermissionGranted = false;
    private Integer problemPos;
    private GoogleMap mMap;
    private UserManager userManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);
        UserManager.initManager();
        userManager = UserManager.getManager();

        problemPos = getIntent().getExtras().getInt("problemPos");


        getProblemLocation();
        getLocationPermission();





    }

    private void init(){
        Log.d(TAG,"init: initializing");
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        Patient patient = (Patient) UserController.getController().getUser();
        ArrayList<Record> allRecords = patient.getProblems().get(problemPos).getRecords();
        if (problemPos != null){
            if (allRecords.size() >0) {
                if (allRecords.get(0).getLocation() != null) {
                    Double Latitude = allRecords.get(0).getLocation().getLat();
                    Double Longitude = allRecords.get(0).getLocation().getLong();
                    String Title = allRecords.get(0).getLocation().getTitle();
                    moveCamera(new LatLng(Latitude, Longitude), 11);
                }
            }
        }
    }


    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG,"moveCamera: moving the camera to: lat:" +latLng.latitude +", lng: " +latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));


    }


    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(ViewAllMapActivity.this);
    }


    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission
                .ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,permission,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else{
            ActivityCompat.requestPermissions(this,permission,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionResult: called");
        mLocationPermissionGranted = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if ( grantResults.length > 0){
                    for (int i = 0 ; i < grantResults.length; i++){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionResult: permission granted");
                    mLocationPermissionGranted = true;
                    //Initialize map
                    initMap();
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        for (int i = 0; i < markers.size();i++){
            mMap.addMarker(markers.get(i));
        }

        if (mLocationPermissionGranted) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                    .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission
                            .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            init();
        }
    }



    @Override
    public void onBackPressed() {
        this.finish();

    }

    public void getProblemLocation(){
        if (problemPos !=null){
            UserManager.initManager();
            UserManager userManager = UserManager.getManager();
            Patient patient = (Patient) UserController.getController().getUser();
            markers = new ArrayList<>();
            ArrayList<Record> allRecords = patient.getProblems().get(problemPos).getRecords();
            for (int i = 0; i < allRecords.size(); i++){
                if (allRecords.get(i).getLocation()!= null) {
                    LatLng LLPos = new LatLng(allRecords.get(i).getLocation().getLat(),
                            allRecords.get(i).getLocation().getLong());
                    String title = allRecords.get(i).getLocation().getTitle();
                    markers.add(new MarkerOptions().position(LLPos).title(title));

                }
            }
        }

    }
}
