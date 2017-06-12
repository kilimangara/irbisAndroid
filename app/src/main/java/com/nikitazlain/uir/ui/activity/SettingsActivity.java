package com.nikitazlain.uir.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.entity.Database;
import com.nikitazlain.uir.persistance.SharedPreferencesWork;
import com.nikitazlain.uir.providers.SettingsProvider;
import com.nikitazlain.uir.ui.fragments.ChooseDatabaseFragment;
import com.nikitazlain.uir.ui.fragments.SettingsFragment;
import com.nikitazlain.uir.ui.customviews.CustomToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity {

    public static final int PROCEED_TO_DATABASE = 0;

    public static final int APPLY_DATABASE = 1;

    FragmentManager fragmentManager;

    SettingsFragment settingsFragment;

    ChooseDatabaseFragment databaseFragment;

    @BindView(R.id.toolbar_settings)
    CustomToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        settingsFragment = new SettingsFragment();
        databaseFragment = new ChooseDatabaseFragment();
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        fragmentManager.beginTransaction().replace(R.id.settings_frame, settingsFragment).commit();
    }

    public void doByType(int type){
        switch (type){
            case PROCEED_TO_DATABASE:
                toolbar.setMenu(R.menu.menu_settings);
                fragmentManager.beginTransaction().replace(R.id.settings_frame, databaseFragment).commit();
                break;
            case APPLY_DATABASE:
                fragmentManager.beginTransaction().replace(R.id.settings_frame, settingsFragment).commit();
                List<Database> database = SettingsProvider.provideDatabases();
                Log.d("test","size "+database.size() +" choose db "+ SharedPreferencesWork.getInstance().getDB());
                settingsFragment.setDatabase(database.get(SharedPreferencesWork.getInstance().getDB()));
                toolbar.clearMenu();
        }
    }
}
