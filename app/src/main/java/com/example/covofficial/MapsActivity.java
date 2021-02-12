package com.example.covofficial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.example.covofficial.repository.DataLoadListener;
import com.example.covofficial.viewmodel.LocationViewModel;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DataLoadListener, GeoQueryEventListener {

    LocationViewModel viewmodel;

    private GoogleMap mMap = null;
    FirebaseAuth firebaseAuth;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private GeoFire geoFire;
    private DatabaseReference databaseReference;
    public void onBackPressed() {
        startActivity(new Intent(MapsActivity.this,MainPageActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        progrssdialogFun();
        if (isConnectedToInternet()){
            viewmodel= new ViewModelProvider(this).get(LocationViewModel.class);
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            Dexter.withActivity(this).withPermission(ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    buildLocationRequest();
                    buildLocationCallBack();
                    Context context;
                    fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(MapsActivity.this);
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(MapsActivity.this);
                    settingGeoFire();
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    Toast.makeText(MapsActivity.this, R.string.LocPerr_toast,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                }
            }).check();



        }
        else

                dialogFun();

    }

    private void dialogFun() {

        new TTFancyGifDialog.Builder(MapsActivity.this)
                .setPositiveBtnText(getString(R.string.ttfPbtn))
                .setMessage(getString(R.string.ttf_message))
                .setGifResource(R.drawable.e1)
                .setPositiveBtnText(getString(R.string.TTFRetry))
                .setPositiveBtnBackground("#22b573")
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        startActivity(new Intent(MapsActivity.this,MapsActivity.class));
                        finish();
                    }
                }).build();

    }




    private void progrssdialogFun() {
        final ProgressDialog progressDialog=new ProgressDialog(MapsActivity.this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }
        }).start();
    }
    public boolean isConnectedToInternet() {

        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                    .getSystemService
                            (Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;

        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;

    }

        private void settingGeoFire() {
        databaseReference=FirebaseDatabase.getInstance().getReference("MyLocation");
//        if(databaseReference!=null){
//            databaseReference.removeValue();
//            databaseReference=FirebaseDatabase.getInstance().getReference("MyLocation");
//        }
        geoFire=new GeoFire(databaseReference);

    }

    private void buildLocationCallBack() {
        locationCallback=new LocationCallback(){
            @Override
            public void onLocationResult(final LocationResult locationResult) {
                if(mMap!=null){
                    Log.i("my_id", String.valueOf(locationResult.getLastLocation().getLatitude()));
                    Log.i("my_id", String.valueOf(locationResult.getLastLocation().getLongitude()));

                    String user= FirebaseAuth.getInstance().getCurrentUser().getUid();

                    geoFire.setLocation(user,new GeoLocation(locationResult.getLastLocation().getLatitude(),
                            locationResult.getLastLocation().getLongitude()), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            mMap.setMyLocationEnabled(true);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationResult.getLastLocation().getLatitude(),
                                    locationResult.getLastLocation().getLongitude()),18.0f));
                        }
                    });
                }
            }
        };
    }

    private void buildLocationRequest() {
        locationRequest=new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if(fusedLocationProviderClient!=null){
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }


        onLocationLoad();


    }




    @Override
    public void onLocationLoad() {
        mMap.clear();
        viewmodel.getLocations(this).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> locations) {
                if(!locations.isEmpty()){
                    for (String loc: locations) {
                        Log.i("test1", "onChanged: 1");
                        loc= loc.replace(",","");
                        loc=loc.replace("]","");
                        loc=loc.replace("[","");
                        String [] latlng=loc.split(" ");
                        double latitude = Double.parseDouble(latlng[0]);
                        double longitude = Double.parseDouble(latlng[1]);
                        LatLng location = new LatLng(latitude, longitude);
                        mMap.addCircle(new CircleOptions().center(location).radius(50).strokeColor(Color.BLUE).fillColor(0x220000FF).strokeWidth(5.0f));
                        GeoQuery geoQuery=geoFire.queryAtLocation(new GeoLocation(location.latitude,location.longitude),0.5f);
                        geoQuery.addGeoQueryEventListener(MapsActivity.this);
                        //    dangerArea.add(location);
//
                    }
                }
            }
        });
    }



   /* @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }*/
    @Override
    public void onKeyEntered(String key, GeoLocation location) {
       String current_user_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
       Log.i("userId",current_user_id);
        Log.i("userId",key);

        if (current_user_id.equals(key)) {
            sendNotification(getString(R.string.EnteredDangerArea_notifi));
        }
    }
    int counter=0;
    private void sendNotification(String key) {
        Log.i("ssss",String.valueOf(counter));
        counter++;
        String NOTIFCATION_CHANNEL_ID="DANGER AREA";
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name;
            NotificationChannel notificationChannel= new NotificationChannel(NOTIFCATION_CHANNEL_ID, "My Notification",NotificationManager.IMPORTANCE_DEFAULT );
            notificationChannel.setDescription("Channel Description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Context context;
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,NOTIFCATION_CHANNEL_ID );
        builder.setContentTitle("AntiCovid").setContentText(key).setAutoCancel(false).setSmallIcon(R.mipmap.app_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.app_icon));
        Notification notification=builder.build();
        notificationManager.notify(new Random().nextInt(),notification);

    }

    @Override
    public void onKeyExited(String key) {
        String current_user_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (current_user_id==key)
            sendNotification( getString(R.string.ExitedDangerArea));

    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {

    }

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {

    }
}
