package com.example.covofficial;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainPageActivity extends AppCompatActivity {
    CardView mapCard,
            newsCard,
            servicesCard,
            chekHealthCard,adviecesCard;
    int total=0;
    DatabaseReference databaseReference;
    GeoFire geoFire;

    private static  final int REQUEST_LOCATION=1;
    LocationListener locationListener;
    LocationManager locationManager;
    Location myLocation;
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                if(LocationGps!=null){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }else if(LocationNetwork!=null){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                }else if(LocationPassive!=null){
                    locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, locationListener);
                }


            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);






        mapCard=findViewById(R.id.map_cardView);
        newsCard=findViewById(R.id.news_Card);
        chekHealthCard=findViewById(R.id.checkhealth_card);
         servicesCard=findViewById(R.id.services_card);
        adviecesCard=findViewById(R.id.advieces_card);

        newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainPageActivity.this,NewsActivity.class));
            }
        });
      /*  servicesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainPageActivity.this,SrevicesActivity.class));
                finish();
            }
        });*/
        chekHealthCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPageActivity.this,questions_activity.class));
                finish();
            }
        });
        adviecesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPageActivity.this,TipsActivity.class));
                finish();
            }
        });

        //map
        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Write Function To enable gps
            OnGPS();
        }
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(location!=null)
                {
                    myLocation=location;
                    //binding.showLocationTxt.setText("The Location is:\n"+String.valueOf(location.getLatitude())+"\n"+String.valueOf(location.getLatitude()));
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //binding.showLocationTxt.setText("Status The Location is:\n"+String.valueOf(myLocation.getLatitude())+"\n"+String.valueOf(myLocation.getLatitude()));
            }

            @Override
            public void onProviderEnabled(String provider) {
                //binding.showLocationTxt.setText("EnableThe Location is:\n"+String.valueOf(myLocation.getLatitude())+"\n"+String.valueOf(myLocation.getLatitude()));
            }

            @Override
            public void onProviderDisabled(String provider) {
                //OnGPS();
                // binding.showLocationTxt.setText("OnProviderDisabled The Location is:\n"+String.valueOf(myLocation.getLatitude())+"\n"+String.valueOf(myLocation.getLatitude()));
            }
        };
        //permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
            Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (LocationGps != null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
            else if (LocationNetwork != null) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
            else if (LocationPassive != null) {
                locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, locationListener);
            }
        }
        getLocation();
        //*********************************************************************************
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.covofficial",Context.MODE_PRIVATE);
        final int total=sharedPreferences.getInt("Total",0);
        System.out.println(total);

        removelocation();
        int var=total;
        if (var>=40&&var<55 )
        {
            //       removeLocationAfter14Days();
            //removelocation();
            String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("PatientLocation");
            GeoFire geoFire=new GeoFire(databaseReference);
            geoFire.setLocation(userid, new GeoLocation(myLocation.getLatitude(), myLocation.getLongitude()), new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    if (error !=null)
                        System.out.println("errrror");
                }
            });
        }

        /*Mina Save MyLocation here*/
        else if(var>=55){
            // removeLocationAfter14Days();
            //removelocation();
            String current_user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("PatientLocation");
            GeoFire geoFire=new GeoFire(databaseReference);
            geoFire.setLocation(current_user_id, new GeoLocation(myLocation.getLatitude(), myLocation.getLongitude()), new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    if (error !=null)
                        System.out.println("ereoreroeroeoreor");
                }
            });


            /*Mina Save MyLocation here*/}




        if (total>40&&total<55)
        {
            removeLocationAfter14Days();
        }
        else if (total >55)
        {
            removeLocationAfter14Days();
        }

        mapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        // Toast.makeText(getApplicationContext(),""+myLocation.getLongitude(),Toast.LENGTH_LONG).show();

                startActivity(new Intent(MainPageActivity.this,MapsActivity.class));
                finish();


            }


        });


    }

    private void removelocation() {


            String current_user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("PatientLocation");
            databaseReference.child(current_user_id).removeValue();




    }
 private void removeLocationAfter14Days(){

    final ScheduledExecutorService scheduledExecutorService=  Executors.newScheduledThreadPool(1);

     scheduledExecutorService.scheduleAtFixedRate
             (new Runnable() {
                 public void run() {
                     // call service
                     removelocation();
                     //beeperHandle.cancel(true);
                scheduledExecutorService.shutdown();

                 }
             }, 14, 14   , TimeUnit.DAYS);


      //   scheduledExecutorService.shutdown();
 }

    private void getLocation() {
        //Check Permissions again
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write Function To enable gps
            OnGPS();
        }
        else
        {
            //GPS is already On then
            if (ActivityCompat.checkSelfPermission(MainPageActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainPageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }
            else
            {
                Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                if (LocationGps !=null)
                {
                    myLocation=LocationGps;
                    //binding.showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                }
                else if (LocationNetwork !=null)
                {
                    myLocation=LocationNetwork;

                    //binding.showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                }
                else if (LocationPassive !=null)
                {
                    myLocation=LocationPassive;
                    //binding.showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                }
                else
                {
                    Toast.makeText(this, R.string.canNotgetloaction_toast, Toast.LENGTH_LONG).show();
                }

                //Thats All Run Your App
            }
        }


    }
    //pop alert to open GPS
    int OkisClicked=0;
    private void OnGPS() {
        new TTFancyGifDialog.Builder(MainPageActivity.this)
                .setTitle(getString(R.string.ttf_title_Location))
                .setMessage(getString(R.string.ttf_location_req_mess))
                .setPositiveBtnText(getString(R.string.ttf_ok))
                .setPositiveBtnBackground("#22b573")
                .setNegativeBtnText(getString(R.string.ttf_Cancel))
                .setNegativeBtnBackground("#c1272d")
                .setGifResource(R.drawable.warning3)    //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        if(OkisClicked==0)
                        {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            getLocation();
                            Intent intent=new Intent(MainPageActivity.this,MainPageActivity.class);OkisClicked=1;
                        }
                    }
                })
                .OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        //return to main page
                        if(OkisClicked==0)
                        {getLocation();}
                        /*startActivity(new Intent(MainPageActivity.this, MainPageActivity.class));
                        finish();
                        return;*/
                    }
                })
                .build();
    }

/*
    @Override
    protected void onStop() {
        super.onStop();




    }*/
}