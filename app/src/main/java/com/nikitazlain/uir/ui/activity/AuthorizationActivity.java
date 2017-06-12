package com.nikitazlain.uir.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import com.nikitazlain.uir.R;
import com.nikitazlain.uir.persistance.SharedPreferencesWork;


public class AuthorizationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Class<?> intent = SharedPreferencesWork.getInstance().isAuth() ? NavigationActivity.class
                        : LoginActivity.class;
                Intent startMain = new Intent(AuthorizationActivity.this, intent);
                startActivity(startMain);
            }
        }, 1000);
    }
}
