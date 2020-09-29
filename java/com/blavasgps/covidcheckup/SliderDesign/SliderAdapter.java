package com.blavasgps.covidcheckup.SliderDesign;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.blavasgps.covidcheckup.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    // list of images
    public int[] lst_images = {
            R.drawable.stay_home,
            R.drawable.beat_crona,
            R.drawable.safe_location,
            R.drawable.safe_home1
    };
    // list of titles
    public String[] lst_title = {
            "#STAY HOME SAFE LIFE!",
            "Lets beat crona together !",
            "COVID-19  Track Location",
            "COVID-19 CHECKUP TEST"
    }   ;
    // list of descriptions
    public String[] lst_description = {
            "The world is suffering due to COVID-19 coronavirus. The very infectious and untreatable disease COVID-19 is gripping us day by day.",
            "Would you like to be kept informed if you have find someone who has tested COVID-19 positive ?",
            "Simply, Install the app, Switch on Location shared Always Active. Invite your friends and family to install the app too",
            "If you fill Correct Tested and submitted report then the app alerts are accompanied by instructions on how to isolate yourself."
    };
    // list of background colors
    public int[]  lst_backgroundcolor = {
            Color.rgb(110,49,89),
            Color.rgb(110,49,89),
            Color.rgb(110,49,89),
            Color.rgb(110,49,89)
    };


    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_slider,container,false);
        LinearLayout layoutslide = (LinearLayout) view.findViewById(R.id.slidelinearlayout);
        ImageView imgslide = (ImageView)  view.findViewById(R.id.slideimg);
        TextView txttitle= (TextView) view.findViewById(R.id.txttitle);
        TextView description = (TextView) view.findViewById(R.id.txtdescription);
        layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        imgslide.setImageResource(lst_images[position]);
        txttitle.setText(lst_title[position]);
        description.setText(lst_description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}