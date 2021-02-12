package com.example.covofficial;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class OnTipClickActivity extends AppCompatActivity {

    ImageView imageView ;
    TextView title , discription ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_tip_click);

        imageView = findViewById(R.id.ontipclick_img);
        title = findViewById(R.id.ontipclick_txt);
        discription = findViewById(R.id.ontipclick_discription);

        findViewById(R.id.ontip_backbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OnTipClickActivity.this,TipsActivity.class));
            }
        });

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container),true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        String cardview = getIntent().getStringExtra("cardview");

        if (cardview.equals("corona")){
            imageView.setImageResource(R.drawable.virus);
            title.setText(R.string.what_corona);
            discription.setText(R.string.corona_description);
        }
        if (cardview.equals("symptoms")){
            imageView.setImageResource(R.drawable.cough);
            title.setText(R.string.what_symptom);
            discription.setText(R.string.symptoms_description);
        }
        if (cardview.equals("transmitted")){
            imageView.setImageResource(R.drawable.transmitted);
            title.setText(R.string.how_transmited);
            discription.setText(R.string.transmited_description);

        }
        if (cardview.equals("prevent")) {
            imageView.setImageResource(R.drawable.watertap);
            title.setText(R.string.how_prevent);
            discription.setText(R.string.prevent_description);

        }
        if (cardview.equals("mask")){
            imageView.setImageResource(R.drawable.facemask);
            title.setText(R.string.when_mask);
            discription.setText(R.string.mask_description);
        }
        if (cardview.equals("egypt")) {
            imageView.setImageResource(R.drawable.computer);
            title.setText(R.string.how_survive_in_egypt);
            discription.setText(R.string.egypt_survue_description);
        }
        if (cardview.equals("vaccine")){
            imageView.setImageResource(R.drawable.syringe);
            title.setText(R.string.is_there_vaci);
            discription.setText(R.string.vaccine_description);
        }
        if (cardview.equals("diagonsis")){
            imageView.setImageResource(R.drawable.diagnosis);
            title.setText(R.string.how_diagnose);
            discription.setText(R.string.diagnosis_description);
        }
        if (cardview.equals("plane")){
            imageView.setImageResource(R.drawable.plane);
            title.setText(R.string.info_travels);
            discription.setText(R.string.plane_description);
        }
    }
}
