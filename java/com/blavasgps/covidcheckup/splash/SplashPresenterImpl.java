package com.blavasgps.covidcheckup.splash;

import android.os.CountDownTimer;

import com.blavasgps.covidcheckup.commons.utils.AppConstants;

/**
 * Created By Ravi Kant on 26/11/2019
 */
public class SplashPresenterImpl implements SplashPresenter{

    SplashView splashView;
    public SplashPresenterImpl(SplashView splashView)
    {
        this.splashView = splashView;
    }

    @Override
    public void startCountDownTimer() {
        new CountDownTimer(AppConstants.SPLASH_SHOW_TIME *1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
              splashView.onCountDownTimerFinish();
            }
        }.start();
    }
}
