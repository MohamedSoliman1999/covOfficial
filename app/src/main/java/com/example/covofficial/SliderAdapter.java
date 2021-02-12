package com.example.covofficial;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.Locale;

public class SliderAdapter extends PagerAdapter {
    Context Mycontext;
    LayoutInflater inflate;
    public SliderAdapter(Context context)
    {
        Mycontext = context;
    }


    public int []Images= {

            R.drawable.chart1_icon,
            R.drawable.notification_2,
            R.drawable.map3_icon
    };

    public String []Heading={
            "Latest Statistics", "Notifications", "Location Services"

    };
    public String []Heading_Ara={
            "أحدث الإحصائيات", "إشعارات", "خدمات الموقع"

    };

    public String []Des={
            "    We will care to inform you of \n    The Latest Statistics about Covid-19  \n    that are about all cases, new \n    cases, deathes and recovered ",
            "    if you are near to a person \n    who is infected with coronavairous ,\n    you will get a notfication with high " +
                    "\n    mobile vibration to inform you are \n    in a danger area ",
            "    Depending on your location,it showes \n    you if an infected person is near to you \n    By a blue circle."

    };
    public String []Des_ara={
            "     سنحرص على إبلاغك بأحدث الإحصائيات حول \n         فيروس كورونا التي تتعلق بجميع الحالات. ",
            "   إذا كنت قريبًا من شخص مصاب بالفيروس   \n   التاجي  فستتلقى إشعارًا  باهتزاز عال للتليفون \n    لإبلاغك  أنك في منطقة خطرة.   ",
            "       اعتمادًا على موقعك يوضح لك ما إذا كان  \n       الشخص المصاب قريبًا منك. بواسطة  \n        دائرة زرقاء .      "

    };
    @Override
    public int getCount() {
        return Heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }


    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflate = (LayoutInflater) Mycontext.getSystemService(Mycontext.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.sliderlayout, (ViewGroup) container,false);
        String s = Locale.getDefault().getDisplayLanguage();
        ImageView Image = view.findViewById(R.id.ImageView);
        TextView textHeding  =view.findViewById(R.id.txt_Header);
        TextView textDis=view.findViewById(R.id.txt_Dis);
        Image.setImageResource(Images[position]);
        if(s.equals("English"))
        {
            textHeding.setText(Heading[position]);
            textDis.setText(Des[position]);
        }
        else
        {
            textHeding.setText(Heading_Ara[position]);
            textDis.setText(Des_ara[position]);
        }



        container.addView(view);;
        return  view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
