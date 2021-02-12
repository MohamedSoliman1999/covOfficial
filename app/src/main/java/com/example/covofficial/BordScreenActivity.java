package com.example.covofficial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static maes.tech.intentanim.CustomIntent.customType;

public class BordScreenActivity extends AppCompatActivity {


    ViewPager viewPager;
    SliderAdapter sliderAdapter;
    TextView MYDots[];
    LinearLayout linearLayoutDots;
    Button Next;
    Button Skip;
    private int CPage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bord_screen);


        linearLayoutDots  = findViewById(R.id.linearHor);
        viewPager = findViewById(R.id.myViewPager);
        Next = findViewById(R.id.Next);
        Skip =  findViewById(R.id.Skip);
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        MakeDots(0);
        viewPager.addOnPageChangeListener(pageChangeListener);

    }



    private void MakeDots(int p)
    {
        MYDots = new TextView[3];
        linearLayoutDots.removeAllViews();
        for(int i=0;i<MYDots.length;i++)
        {
            MYDots[i] = new TextView(this);
            MYDots[i].setText(Html.fromHtml("&#8226;"));
            MYDots[i].setTextSize(35);
            MYDots[i].setTextColor(getResources().getColor(R.color.Gray));
            linearLayoutDots.addView(MYDots[i]);
        }
        if(MYDots.length>0)
        {
            MYDots[p].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    ViewPager.OnPageChangeListener pageChangeListener  =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            MakeDots(position);
            CPage = position;
            if(position==0)
            {
                Next.setText(R.string.next_button);
                Next.setTextColor(getResources().getColor(R.color.colorPrimaryDark));




            }
            else if(position==MYDots.length-1)
            {

                Next.setText(R.string.start);
                Next.setTextColor(getResources().getColor(R.color.colorPrimary));
                Skip.setVisibility(View.INVISIBLE);
            }
            else
            {

                Next.setEnabled(true);
                Skip.setEnabled(true);
                Next.setVisibility(View.VISIBLE);
                Skip.setVisibility(View.VISIBLE);
                Next.setText(R.string.next_button);
                Next.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                Skip.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void btn_Skip(View view) {
        startActivity(new Intent(BordScreenActivity.this,MainActivity.class));
        customType(BordScreenActivity.this,"fadein-to-fadeout");
        finish();

    }

    public void btn_Next(View view) {
        String Name = Next.getText().toString();
        if(Name.equals("Next"))
        {
            viewPager.setCurrentItem(CPage+1);
        }
        else
        {
            startActivity(new Intent(BordScreenActivity.this,MainActivity.class));
            customType(BordScreenActivity.this,"fadein-to-fadeout");
            finish();
        }

    }
}

