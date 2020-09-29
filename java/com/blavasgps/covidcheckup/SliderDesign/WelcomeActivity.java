package com.blavasgps.covidcheckup.SliderDesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.blavasgps.covidcheckup.Design.Login.LoginActivity;
import com.blavasgps.covidcheckup.Design.MainActivity;
import com.blavasgps.covidcheckup.R;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private SliderAdapter myadapter;
    Button LoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        myadapter = new SliderAdapter(this);
        viewPager.setAdapter(myadapter);

        LoginView = (Button)findViewById(R.id.login);
        LoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                finish();
             //   startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
             //   finish();
            }
        });
    }


}
