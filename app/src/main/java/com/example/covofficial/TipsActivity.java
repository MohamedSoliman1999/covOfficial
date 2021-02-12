package com.example.covofficial;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class TipsActivity extends AppCompatActivity {

    public void onBackPressed() {
        startActivity(new Intent(TipsActivity.this,MainPageActivity.class));
        finish();
    }

    CardView corona ,
            symptoms ,
            transmitted ,
            prevent ,
            mask
            , vaccine
            , diagonsis
            , egypt
            , plane ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        corona = findViewById(R.id.corona);
        symptoms = findViewById(R.id.symptoms);
        transmitted = findViewById(R.id.transmitted);
        prevent = findViewById(R.id.prevent);
        mask = findViewById(R.id.mask);
        vaccine = findViewById(R.id.vaccine);
        diagonsis = findViewById(R.id.diagonsis);
        //egypt = findViewById(R.id.egypt);
        plane = findViewById(R.id.plane);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container),true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        corona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsActivity.this,OnTipClickActivity.class);
                intent.putExtra("cardview","corona");
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(findViewById(R.id.coronaimg),"imagetransition");
                pairs[1] = new Pair<View,String>(findViewById(R.id.coronatxt),"texttransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TipsActivity.this,pairs);

                startActivity(intent,options.toBundle());

            }
        });
        symptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsActivity.this,OnTipClickActivity.class);
                intent.putExtra("cardview","symptoms");
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(findViewById(R.id.syptomsimg),"imagetransition");
                pairs[1] = new Pair<View,String>(findViewById(R.id.syptomstxt),"texttransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TipsActivity.this,pairs);

                startActivity(intent,options.toBundle());

            }
        });
        transmitted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsActivity.this,OnTipClickActivity.class);
                intent.putExtra("cardview","transmitted");
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(findViewById(R.id.transmittedimg),"imagetransition");
                pairs[1] = new Pair<View,String>(findViewById(R.id.transmittedtxt),"texttransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TipsActivity.this,pairs);

                startActivity(intent,options.toBundle());

            }
        });
        prevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsActivity.this,OnTipClickActivity.class);
                intent.putExtra("cardview","prevent");
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(findViewById(R.id.preventimg),"imagetransition");
                pairs[1] = new Pair<View,String>(findViewById(R.id.preventtxt),"texttransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TipsActivity.this,pairs);

                startActivity(intent,options.toBundle());

            }
        });
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsActivity.this,OnTipClickActivity.class);
                intent.putExtra("cardview","mask");
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(findViewById(R.id.maskimg),"imagetransition");
                pairs[1] = new Pair<View,String>(findViewById(R.id.masktxt),"texttransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TipsActivity.this,pairs);

                startActivity(intent,options.toBundle());

            }
        });
        vaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsActivity.this,OnTipClickActivity.class);
                intent.putExtra("cardview","vaccine");
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(findViewById(R.id.vaccineimg),"imagetransition");
                pairs[1] = new Pair<View,String>(findViewById(R.id.vaccinetxt),"texttransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TipsActivity.this,pairs);

                startActivity(intent,options.toBundle());

            }
        });
        diagonsis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsActivity.this,OnTipClickActivity.class);
                intent.putExtra("cardview","diagonsis");
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(findViewById(R.id.diagonsisimg),"imagetransition");
                pairs[1] = new Pair<View,String>(findViewById(R.id.diagonsistxt),"texttransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TipsActivity.this,pairs);

                startActivity(intent,options.toBundle());

            }
        });

        plane.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TipsActivity.this,OnTipClickActivity.class);
                intent.putExtra("cardview","plane");
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(findViewById(R.id.planeimg),"imagetransition");
                pairs[1] = new Pair<View,String>(findViewById(R.id.planetxt),"texttransition");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(TipsActivity.this,pairs);

                startActivity(intent,options.toBundle());

            }
        });

    }
}
