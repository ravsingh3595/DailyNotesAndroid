package com.androidproject.dailynotesandroid;
import android.Manifest;
        import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
        import android.location.Location;

        import android.location.LocationManager;
        import android.os.Build;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;
        import android.support.v4.content.ContextCompat;
        import android.util.Log;
        import android.widget.Toast;

        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;

        import com.google.android.gms.location.LocationListener;

public class ShowUserLocationActivity extends FragmentActivity implements
        OnMapReadyCallback
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        LocationListener

{

    private GoogleMap mMap;
    boolean mLocationPermissionGranted = false;


    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int CONNECTION_RESOLUTION_REQUEST = 2;
    private GoogleApiClient googleApiClient;
    private Location mLastKnownLocation;
    private LocationRequest locationRequest;
    private  Location lastLocation;
    private  Location userLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;

    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_location);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            checkUserLocationPermission();
//        }
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng objLatLng = getIntent().getExtras().getParcelable("Latlng");

        if (objLatLng !=null){
            LatLng temp = new LatLng(objLatLng.latitude, objLatLng.longitude);
            mMap.addMarker(new MarkerOptions().position(temp)
                    .title("Note saved location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, 15));

        }

    }

}

