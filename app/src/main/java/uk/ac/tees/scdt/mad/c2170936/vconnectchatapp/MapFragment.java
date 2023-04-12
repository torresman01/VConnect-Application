package uk.ac.tees.scdt.mad.c2170936.vconnectchatapp;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener  {

    boolean isPermissionGranted;
    MapView mapView;
    GoogleMap mGoogleMap;
    FloatingActionButton fabLocation;
    private FusedLocationProviderClient mLocClient;
    private int GPS_REQUEST_CODE = 0001;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map, container, false);

        mapView = view.findViewById(R.id.mapView);
        fabLocation = view.findViewById(R.id.fabLocation);


        checkMyPermission();

        if (isPermissionGranted)
        {
            if (isGPSenabled()){
                mapView.getMapAsync(this);
                mapView.onCreate(savedInstanceState);
            }
        }



        mLocClient = LocationServices.getFusedLocationProviderClient(getContext());

        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getCurrentLocationOfUser();

            }
        });




        return view;
    }

    private boolean isGPSenabled()
    {
       LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

       boolean providerEnable = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);

       if (providerEnable){
           return true;
       }else{

           AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle("GPS Permission")
                   .setMessage("GPS service is required for this section to work. Please enable GPS")
                   .setPositiveButton("yes", ((dialogInterface, i) -> {
                       Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                       startActivityForResult(intent, GPS_REQUEST_CODE);
                   }))
                   .setCancelable(false)
                   .show();
       }

        return false;
    }



    @SuppressLint("MissingPermission")
    private void getCurrentLocationOfUser() {

        mLocClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                    Location location = task.getResult();
                    gotoLocation(location.getLatitude(), location.getLongitude());


            }
        });


    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);

        //Set camera on location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,13);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    private void checkMyPermission() {

        Dexter.withContext(getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                Toast.makeText(getContext(),"Permission Granted", Toast.LENGTH_SHORT).show();

                isPermissionGranted = true;

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                permissionToken.cancelPermissionRequest();

            }
        }).check();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_REQUEST_CODE){
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

            boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (providerEnabled){
                Toast.makeText(getContext(), "GPS is enabled", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "GPS is not enabled", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
