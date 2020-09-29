package com.blavasgps.covidcheckup;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.blavasgps.covidcheckup.Design.Login.LoginActivity;
import com.blavasgps.covidcheckup.Design.MainActivity;
import com.blavasgps.covidcheckup.Design.Register.RegisterUserActivity;
import com.blavasgps.covidcheckup.SliderDesign.WelcomeActivity;
import com.blavasgps.covidcheckup.commons.session.SessionManager;
import com.blavasgps.covidcheckup.splash.SplashPresenter;
import com.blavasgps.covidcheckup.splash.SplashPresenterImpl;
import com.blavasgps.covidcheckup.splash.SplashView;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {
    private SplashPresenter splashPresenter;
    SessionManager sessionManager;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
      //  splashPresenter = new SplashPresenterImpl(this);
        sessionManager = new SessionManager(this);
        user = sessionManager.getUserDetails();
     //   splashPresenter.startCountDownTimer();

        new CountDownTimer(2000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                if(sessionManager.isLoggedIn())
                {
                    if (user.get(SessionManager.KEY_VERIFIED_USER).equalsIgnoreCase("2")) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(SplashActivity.this, RegisterUserActivity.class));
                        finish();
                    }
                }else
                {
                    startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                    finish();
                }

            }
        }.start();

    }

}
