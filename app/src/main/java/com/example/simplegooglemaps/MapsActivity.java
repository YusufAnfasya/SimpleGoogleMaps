package com.example.simplegooglemaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.simplegooglemaps.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final int PERMISSION_GET_LAST_LOCATION = 10;
    public static final int PERMISSION_REQUEST_LAST_LOCATION = 11;

    private GoogleMap mMap;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    protected final static String REQUEST_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "requesting-location-updates-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

    protected GoogleApi mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mCurrentLocation;


    //UI Widgets
    protected Button mStartUpdatesButton;
    protected Button mStopUpdatesButton;
    protected TextView mLastUpdateTimeTextView;
    protected TextView mLatitudeTextView;
    protected TextView mLongitudeTextView;

    //Labels
    protected String mLatitudeLabel = "Lat: ";
    protected String mLongitudeLabel = "Long: ";
    protected String mLastUpdateTimeLabel = "Last Up;date: ";

    protected Boolean mRequestingLocationUpdates;
    protected String mLastUpdateTime = "";


    private ActivityMapsBinding binding;

    private LocationManager lm;
    private LocationListener ll;


    private class lokasiListener implements LocationListener {

        private TextView txtLat, txtLong, txtUpdate;

        /**
         * @param location the updated location
         */
        @Override
        public void onLocationChanged(@NonNull Location location) {

            txtLat = (TextView) findViewById(R.id.txtLat);
            txtLong = (TextView) findViewById(R.id.txtLong);
            txtUpdate = (TextView) findViewById(R.id.txtUpdate);


            txtLat.setText(String.valueOf(location.getLatitude()));
            txtLong.setText(String.valueOf(location.getLongitude()));
            txtUpdate.setText(DateFormat.getTimeInstance().format(new Date()));

            if(R.id.)



            Toast.makeText(getBaseContext(),
                    "GPS Capture", Toast.LENGTH_LONG).show();

            LatLng Lokasibaru = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().
                    position(Lokasibaru).title("Rute"));
            mMap.moveCamera(CameraUpdateFactory.
                    newLatLngZoom(Lokasibaru, 15));

        }


        /**
         * @param provider
         * @param status
         * @param extras
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            LocationListener.super.onStatusChanged(provider, status, extras);
        }

        /**
         * @param provider the name of the location provider
         */
        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        /**
         * @param provider the name of the location provider
         */
        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //GPS Locator

        lm = (LocationManager) getSystemService
                (Context.LOCATION_SERVICE);
        ll = new lokasiListener();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 200, ll);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button go = (Button) findViewById(R.id.idGo);
        go.setOnClickListener(op);

        Button cari = (Button) findViewById(R.id.idCari);
        cari.setOnClickListener(op);

//        Button update = (Button) findViewById(R.id.mStartUpdatesButton);
//        update.setOnClickListener(op);
//
//        Button stop = (Button) findViewById(R.id.mStopUpdatesButton);
//        stop.setOnClickListener(op);


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
        LatLng its = new LatLng(-7.28, 112.79);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(its, 8));
    }

    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.idGo) {
                gotoLokasi();
            } else if (view.getId() == R.id.idCari) {
                goCari();
            }

            sembunyikanKeyBoard(view);
        }
    };

    private void gotoLokasi() {
        EditText lat = (EditText) findViewById(R.id.idLokasiLat);
        EditText lng = (EditText) findViewById(R.id.idLokasiLng);
        EditText zoom = (EditText) findViewById(R.id.idZoom);

        Double dbllat = Double.parseDouble(lat.getText().toString());
        Double dbllng = Double.parseDouble(lng.getText().toString());
        Float dblzoom = Float.parseFloat(zoom.getText().toString());

        Toast.makeText(this, "Move to Lat:" + dbllat + " Long:" + dbllng, Toast.LENGTH_LONG).show();
        gotoPeta(dbllat, dbllng, dblzoom);
    }


    private void sembunyikanKeyBoard(View v) {
        InputMethodManager a = (InputMethodManager)
                getSystemService(INPUT_METHOD_SERVICE);
        a.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void gotoPeta(Double lat, Double lng, float z) {
        LatLng Lokasibaru = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().
                position(Lokasibaru).
                title("Marker in  " + lat + ":" + lng));
        mMap.moveCamera(CameraUpdateFactory.
                newLatLngZoom(Lokasibaru, z));

    }

    private void goCari(){
        EditText tempat = (EditText) findViewById(R.id.idDaerah);
        Geocoder g = new Geocoder(getBaseContext());
        try {
            List<android.location.Address> daftar =
                    g.getFromLocationName(tempat.getText().toString(),1);
            Address alamat = daftar.get(0);

            String nemuAlamat =  alamat.getAddressLine(0);
            Double lintang = alamat.getLatitude();
            Double bujur = alamat.getLongitude();
            Integer zm = 10;
            Toast.makeText(getBaseContext(),"Ketemu " + nemuAlamat,Toast.LENGTH_LONG).show();


            float dblzoom = 10;
            Toast.makeText(this,"Move to "+ nemuAlamat +" Lat:" +
                    lintang + " Long:" +bujur,Toast.LENGTH_LONG).show();
            gotoPeta(lintang,bujur,dblzoom);

            EditText lat = (EditText) findViewById(R.id.idLokasiLat);
            EditText lng = (EditText) findViewById(R.id.idLokasiLng);
            EditText zom = (EditText) findViewById(R.id.idZoom);


            lat.setText(lintang.toString());
            lng.setText(bujur.toString());
//            zom.setAlpha(dblzoom);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}