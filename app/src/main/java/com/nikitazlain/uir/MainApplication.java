package com.nikitazlain.uir;

import android.app.Application;

import com.nikitazlain.uir.persistance.SharedPreferencesWork;


public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesWork.getInstance().init(this);
    }
}
