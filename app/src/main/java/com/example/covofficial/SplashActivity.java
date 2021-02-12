package com.example.covofficial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.covofficial.Utils.Preferences_Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static maes.tech.intentanim.CustomIntent.customType;

public class SplashActivity extends AppCompatActivity {
        private static int SPLASH_TIME_OUT=3000;
    Preferences_Utils preferences_utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
       // List<String>s = Arrays.asList(getResources().getStringArray(R.array.dess_array));

       // Log.i("testets",s.get(0));
        preferences_utils = new Preferences_Utils();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
                if(preferences_utils.IsUsed(getApplicationContext())!=null&&!preferences_utils.IsUsed(getApplicationContext()).equals("True"))
                {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    customType(SplashActivity.this,"fadein-to-fadeout");
                    finish();
                }
                else
                {

                    preferences_utils.Used("ISUserd",getApplicationContext());
                    startActivity(new Intent(SplashActivity.this,BordScreenActivity.class));
                    customType(SplashActivity.this,"fadein-to-fadeout");
                    finish();
                }
            }
        },SPLASH_TIME_OUT);
    }
}
