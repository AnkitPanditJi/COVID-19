package com.blavasgps.covidcheckup.Design.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.blavasgps.covidcheckup.R;

public class MapViewActivity extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        setupToolbar();
        init();
    }

    public void init()
    {

    }

    public void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("MapView");
        setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
           getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);
        toolbar.setNavigationOnClickListener(v -> { onBackPressed(); });
    }
}
