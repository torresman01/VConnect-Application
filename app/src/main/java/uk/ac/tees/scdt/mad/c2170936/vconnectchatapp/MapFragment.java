package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MapFragment extends Fragment{

    boolean isPermissionGranted;

    GoogleMap mGoogleMap;
    FloatingActionButton fabLocation;
    private FusedLocationProviderClient mLocationClient;
    Button button;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map, container, false);

        button  = view.findViewById(R.id.accessMapBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        //fabLocation = view.findViewById(R.id.fabIcon);


     // Initialize map fragment
//            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.map_fragment);
//            mapFragment.getMapAsync(new OnMapReadyCallback() {
//                @Override
//                public void onMapReady(GoogleMap googleMap) {
//                    mGoogleMap = googleMap;
//
//                    // Add your code here to customize the map
//                }
//            });


//        mLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
//
//        fabLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getCurrentLocation();
//
//            }
//        });






        return view;
    }

//    @SuppressLint("MissingPermission")
//    private void getCurrentLocation() {
//
//        mLocationClient.getLastLocation().addOnCompleteListener(task -> {
//
//            if (task.isSuccessful()){
//                Location location = task.getResult();
//                gotoLocation(location.getLatitude(), location.getLongitude());
//            }
//
//        });
//    }

//    private void gotoLocation(double latitude, double longitude) {
//
//        LatLng latLng = new LatLng(latitude, longitude);
//
//        //Set camera on location
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,15);
//        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Your Location");
//        mGoogleMap.addMarker(markerOptions);
//        mGoogleMap.moveCamera(cameraUpdate);
//        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//
//    }

//    private void checkMyPermission() {
//
//        Dexter.withContext(getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
//            @Override
//            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                Toast.makeText(getContext(),"Permission Granted", Toast.LENGTH_SHORT).show();
//                isPermissionGranted = true;
//            }
//
//            @Override
//            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS)
//                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//            }
//
//            @Override
//            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                permissionToken.cancelPermissionRequest();
//            }
//        }).check();
//    }

//    @SuppressLint("MissingPermission")
//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        mGoogleMap = googleMap;
//        //mGoogleMap.setMyLocationEnabled(true);
//    }


}
