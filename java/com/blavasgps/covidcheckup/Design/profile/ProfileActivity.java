package com.blavasgps.covidcheckup.Design.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.blavasgps.covidcheckup.R;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setupToolbar();
        init();
    }

    public void init()
    {

    }

    public void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
           getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);
        toolbar.setNavigationOnClickListener(v -> { onBackPressed(); });
    }
}
