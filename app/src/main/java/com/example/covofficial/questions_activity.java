package com.example.covofficial;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.example.covofficial.databinding.ActivityQuestionsActivityBinding;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.example.covofficial.databinding.ActivityQuestionsActivityBinding;


public class questions_activity extends AppCompatActivity {
    ActivityQuestionsActivityBinding binding;
    public int total=0;
    int A1Q1,A2Q1,A3Q1Btn,A4Q1;
    int A1Q2,A2Q2,A3Q2,A4Q2;
    int A1Q3,A2Q3,A3Q3,A4Q3;
    int A1Q4,A2Q4;
    int A1Q5,A2Q5,A3Q5,A4Q5,A5Q5,A6Q5;
    int A1Q6,A2Q6,A3Q6,A4Q6,A5Q6,A6Q6;
    String message="إ";
    //Location
    private static  final int REQUEST_LOCATION=1;
    LocationListener locationListener;
    LocationManager locationManager;
    Location myLocation;
    int last_valQ2=0;
    int last_valQ3=0;
    int last_valQ4=0;
    int total_Q5=0;
    int total_Q6=0;
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
    public void onBackPressed() {
        startActivity(new Intent(questions_activity.this,MainPageActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuestionsActivityBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        A1Q1=A2Q1=A3Q1Btn=A4Q1=0;
        A1Q2=A2Q2=A3Q2=A4Q2=0;
        A1Q3=A2Q3=A3Q3=A4Q3=0;
        A1Q4=A2Q4=0;
        A1Q5=A2Q5=A3Q5=A4Q5=A5Q5=A6Q5=0;
        A1Q6=A2Q6=A3Q6=A4Q6=A5Q6=A6Q6=0;
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
    }
    public void A1Q1_Click(View view) {
        if(A1Q1==0){
            binding.A1Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A1Q1=1;total+=10;
        }else{
            binding.A1Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A1Q1=0;total-=10;
        }
    }
    public void A2Q1_Click(View view) {
        if(A2Q1==0){
            binding.A2Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A2Q1=1;total+=10;
        }else{
            binding.A2Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A2Q1=0;total-=10;
        }
    }
    public void A3Q1Btn_Click(View view){
        if(A3Q1Btn==0){
            binding.A3Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A3Q1Btn=1;total+=15;
        }else{
            binding.A3Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A3Q1Btn=0;total-=15;
        }
    }
    public void AaQ1_click(View view) {
        if(A4Q1==0){
            binding.A4Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A4Q1=1;total+=10;
        }else{
            binding.A4Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A4Q1=0;total-=10;
        }
    }
    public void A1Q2_Click(View view) {
        if(A1Q2==0){
            binding.A1Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A1Q2=1;total+=1;
            A2Q2=0;
            A3Q2=0;
            A4Q2=0;
            binding.A2Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A3Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A4Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            total-=last_valQ2;
            last_valQ2=1;
        }

    }
    public void A2Q2_Click(View view) {
        if(A2Q2==0){
            binding.A2Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A2Q2=1;total+=3;
            A1Q2=0;
            A3Q2=0;
            A4Q2=0;
            binding.A1Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A3Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A4Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            total-=last_valQ2;
            last_valQ2=3;
        }

    }
    public void A3Q2_Click(View view) {
        if(A3Q2==0){
            binding.A3Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A3Q2=1;total+=4;
            A1Q2=0;
            A2Q2=0;
            A4Q2=0;
            binding.A1Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A2Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A4Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            total-=last_valQ2;
            last_valQ2=4;
        }
    }
    public void A4Q2_Click(View view) {
        if(A4Q2==0){
            binding.A4Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A4Q2=1;total+=7;
            A1Q2=0;
            A3Q2=0;
            A2Q2=0;
            binding.A1Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A3Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A2Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            total-=last_valQ2;
            last_valQ2=7;
        }
    }
    public void A1Q3_Click(View view) {
        if(A1Q3==0){
            binding.A1Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A1Q3=1;total+=1;
            A4Q3=0;
            A3Q3=0;
            A2Q3=0;
            binding.A4Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A3Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A2Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            total-=last_valQ3;
            last_valQ3=1;
        }

    }
    public void A2Q3_Click(View view) {
        if(A2Q3==0){
            binding.A2Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A2Q3=1;total+=2;
            A4Q3=0;
            A3Q3=0;
            A1Q3=0;
            binding.A4Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A3Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A1Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            total-=last_valQ3;
            last_valQ3=2;
        }
    }
    public void A3Q3_Click(View view) {
        if(A3Q3==0){
            binding.A3Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A3Q3=1;total+=4;
            A4Q3=0;
            A2Q3=0;
            A1Q3=0;
            binding.A4Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A2Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A1Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            total-=last_valQ3;
            last_valQ3=4;
        }
    }
    public void A4Q3_Click(View view) {
        if(A4Q3==0){
            binding.A4Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A4Q3=1;total+=8;
            A2Q3=0;
            A3Q3=0;
            A1Q3=0;
            binding.A2Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A3Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A1Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            total-=last_valQ3;
            last_valQ3=8;
        }
    }
    public void A1Q4_Click(View view) {
        if(A1Q4==0){
            binding.A1Q4Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A1Q4=1;total+=1;
            binding.A2Q4Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A2Q4=0;
            total-=last_valQ4;
            last_valQ4=1;
        }

    }
    public void A2Q4_Click(View view) {
        if(A2Q4==0){
            binding.A2Q4Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A2Q4=1;total+=2;
            binding.A1Q4Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A1Q4=0;
            total-=last_valQ4;
            last_valQ4=2;
        }
    }
    public void A1Q5_Click(View view) {
        if(A1Q5==0){
            binding.A1Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A1Q5=1;total+=3;total_Q5+=3;
            binding.A6Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A6Q5=0;
        }else{
            binding.A1Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A1Q5=0;total-=3;total_Q5-=3;
        }
    }
    public void A2Q5_Click(View view) {
        if(A2Q5==0){
            binding.A2Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A2Q5=1;total+=3;total_Q5+=3;
            binding.A6Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A6Q5=0;
        }else{
            binding.A2Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A2Q5=0;total-=3;total_Q5-=3;
        }
    }
    public void A3Q5_Click(View view) {
        if(A3Q5==0){
            binding.A3Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A3Q5=1;total+=3;total_Q5+=3;
            binding.A6Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A6Q5=0;
        }else{
            binding.A3Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A3Q5=0;total-=3;total_Q5-=3;
        }
    }
    public void A4Q5_Click(View view) {
        if(A4Q5==0){
            binding.A4Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A4Q5=1;total+=3;total_Q5+=3;
            binding.A6Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A6Q5=0;
        }else{
            binding.A4Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A4Q5=0;total-=3;total_Q5-=3;
        }
    }
    public void A5Q5_Click(View view) {
        if(A5Q5==0){
            binding.A5Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A5Q5=1;total+=3;total_Q5+=3;
            binding.A6Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A6Q5=0;
        }else{
            binding.A5Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A5Q5=0;total-=3;total_Q5-=3;
        }
    }
    public void A6Q5_Click(View view) {
        if(A6Q5==0){
            binding.A6Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A6Q5=1;total-=total_Q5;
            binding.A2Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A3Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A4Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A5Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A1Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            A1Q5=0;
            A2Q5=0;
            A3Q5=0;
            A4Q5=0;
            A5Q5=0;
            total_Q5=0;
        }else{
            binding.A6Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A6Q5=0;total-=0;
        }
    }
    public void A1Q6_Click(View view) {
        if(A1Q6==0){
            binding.A1Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A1Q6=1;total+=3;total_Q6+=3;
            binding.A4Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A4Q6=0;
        }else{
            binding.A1Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A1Q6=0;total-=3;total_Q6-=3;
        }
    }
    public void A2Q6_Click(View view) {
        if(A2Q6==0){
            binding.A2Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A2Q6=1;total+=2;total_Q6+=2;
            binding.A4Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A4Q6=0;
        }else{
            binding.A2Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A2Q6=0;total-=2;total_Q6-=2;
        }
    }
    public void A3Q6_Click(View view) {
        if(A3Q6==0){
            binding.A3Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A3Q6=1;total+=1;total_Q6+=1;
            binding.A4Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A4Q6=0;
        }else{
            binding.A3Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A3Q6=0;total-=1;total_Q6-=1;
        }
    }
    public void A4Q6_Click(View view) {
        if(A4Q6==0){
            binding.A4Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A4Q6=1;total-=total_Q6;
            binding.A1Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A2Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A3Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A5Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            binding.A6Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
            A1Q6=0;
            A2Q6=0;
            A3Q6=0;
            A5Q6=0;
            A6Q6=0;total_Q6=0;
        }else{
            binding.A4Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A4Q6=0;

        }
    }
    public void A5Q6_Click(View view) {
        if(A5Q6==0){
            binding.A5Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A5Q6=1;total+=1;total_Q6+=1;
            binding.A4Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A4Q6=0;
        }else{
            binding.A5Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A5Q6=0;total-=1;total_Q6-=1;

        }
    }
    public void A6Q6_Click(View view) {
        if(A6Q6==0){
            binding.A6Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswertClcked)); A6Q6=1;total+=1;total_Q6+=1;
            binding.A4Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A4Q6=0;
        }else{
            binding.A6Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));A6Q6=0;total-=1;total_Q6-=1;
        }
    }
    public void confirm_click(View view) {
        if(myLocation !=null)
        confirmFunction();
        else if(myLocation==null)
            Toast.makeText(this, R.string.locationt_oast, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, R.string.question, Toast.LENGTH_SHORT).show();
    }
    public void confirmFunction() {
        getLocation();
        //saving the total
        SharedPreferences sharedPreferences=this.getSharedPreferences("com.example.covofficial",Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("Total",total).apply();
        System.out.println(total);

        //Toast.makeText(getApplicationContext(),myLocation.getLatitude()+"--"+myLocation.getLongitude(),Toast.LENGTH_LONG).show();
        //*******************
        //Toast.makeText(getApplicationContext(),""+myLocation.getLongitude(),Toast.LENGTH_LONG).show();
        if(total>=10&&total<20){message=getString(R.string.HomeInsulation_message);}
        else if(total>=20&&total<40){message=getString(R.string.YouNeedTocCall105_message);}
        else if(total>=40&&total<55){message=getString(R.string.YouNeedToVisitTheHospital_message);

            removeLocation();
              String current_user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("PatientLocation");

                GeoFire geoFire=new GeoFire(databaseReference);
                geoFire.setLocation(current_user_id, new GeoLocation(myLocation.getLatitude(), myLocation.getLongitude()), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error !=null)
                            System.out.println("errrror");
                    }
                });





            /*Mina Save MyLocation here*/}
        else if(total>=55){message=getString(R.string.YouNeedToVisitTheHospital_message);
                removeLocation();
                String current_user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("PatientLocation");
                GeoFire geoFire=new GeoFire(databaseReference);
                geoFire.setLocation(current_user_id, new GeoLocation(myLocation.getLatitude(), myLocation.getLongitude()), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error !=null)
                            System.out.println("errrror");
                    }
                });



        /*Mina Save MyLocation here*/}
        else if(total<10){message=getString(R.string.YourHealthIsNatural_message);}
        if(myLocation!=null)
        {
            new TTFancyGifDialog.Builder(questions_activity.this)
                    .setTitle((getString(R.string.ttf_score)+total))
                    .setMessage(message)
                    .setPositiveBtnText(getString(R.string.ttfOk))
                    .setPositiveBtnBackground("#22b573")
                    .setNegativeBtnText(getString(R.string.ttfRetry))
                    .setNegativeBtnBackground("#c1272d")
                    .setGifResource(R.drawable.heart2)    //pass your gif, png or jpg
                    .isCancellable(true)
                    .OnPositiveClicked(new TTFancyGifDialogListener() {
                        @Override
                        public void OnClick() {

                          Intent intent=new Intent(questions_activity.this, MainPageActivity.class);
                          startActivity(intent);
                          finish();
                        }
                    })
                    .OnNegativeClicked(new TTFancyGifDialogListener() {
                        @Override
                        public void OnClick() {
                            Toast.makeText(getApplicationContext(), R.string.Retry_Toast,Toast.LENGTH_SHORT).show();
                            Retry();
                        }
                    })
                    .build();
        }
    }

    private void removeLocation() {
        String current_user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("PatientLocation");
        databaseReference.child(current_user_id).removeValue();
    }

    public void Retry() {
        total=0;
        binding.A1Q1Btn.setEnabled(true);
        binding.A2Q1Btn.setEnabled(true);
        binding.A3Q1Btn.setEnabled(true);
        binding.A4Q1Btn.setEnabled(true);
        binding.A1Q2Btn.setEnabled(true);
        binding.A2Q2Btn.setEnabled(true);
        binding.A3Q2Btn.setEnabled(true);
        binding.A4Q2Btn.setEnabled(true);
        binding.A1Q3Btn.setEnabled(true);
        binding.A2Q3Btn.setEnabled(true);
        binding.A3Q3Btn.setEnabled(true);
        binding.A4Q3Btn.setEnabled(true);
        binding.A1Q4Btn.setEnabled(true);
        binding.A2Q4Btn.setEnabled(true);
        binding.A1Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A2Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A3Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A4Q1Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A1Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A2Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A3Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A4Q2Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A1Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A2Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A3Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A4Q3Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A1Q4Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A2Q4Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A1Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A2Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A3Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A4Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A5Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A6Q5Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A1Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A2Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A3Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A4Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A5Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A6Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        binding.A6Q6Btn.setBackgroundColor(getResources().getColor(R.color.AnswerNotClck));
        A1Q1=A2Q1=A3Q1Btn=A4Q1=0;
        A1Q2=A2Q2=A3Q2=A4Q2=0;
        A1Q3=A2Q3=A3Q3=A4Q3=0;
        A1Q4=A2Q4=0;
        A1Q5=A2Q5=A3Q5=A4Q5=A5Q5=A6Q5=0;
        A1Q6=A2Q6=A3Q6=A4Q6=A5Q6=A6Q6=0;
    }
    //Location
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
            if (ActivityCompat.checkSelfPermission(questions_activity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(questions_activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
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
                    Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_LONG).show();
                }

                //Thats All Run Your App
            }
        }


    }
    //pop alert to open GPS
    private void OnGPS() {
        new TTFancyGifDialog.Builder(questions_activity.this)
                .setTitle(getString(R.string.ttf_location))
                .setMessage(getString(R.string.ttf_loc_req))
                .setPositiveBtnText(getString(R.string.ttfBtn_ok))
                .setPositiveBtnBackground("#22b573")
                .setNegativeBtnText(getString(R.string.ttfBtn_Cancel))
                .setNegativeBtnBackground("#c1272d")
                .setGifResource(R.drawable.warning3)    //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        //return to main page
                        startActivity(new Intent(questions_activity.this, MainPageActivity.class));
                        finish();
                        return;
                    }
                })
                .build();
    }


}


