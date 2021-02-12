package com.example.covofficial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SrevicesActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SrevicesActivity.this,MainPageActivity.class));
        finish();
    }

    CardView fawryCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srevices);

        fawryCard=findViewById(R.id.fawry_card);
        fawryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SrevicesActivity.this,FawryServiceActivity.class));
                finish();
            }
        });

    }
}
