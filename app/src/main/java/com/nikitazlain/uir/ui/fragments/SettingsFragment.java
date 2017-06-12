package com.nikitazlain.uir.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.entity.Database;
import com.nikitazlain.uir.persistance.SharedPreferencesWork;
import com.nikitazlain.uir.providers.SettingsProvider;
import com.nikitazlain.uir.ui.activity.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingsFragment extends Fragment {

    @BindView(R.id.edtxt_database)
    TextView txtDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        setDatabase(SettingsProvider.getDatabase(SharedPreferencesWork.getInstance().getDB()));
        return view;
    }


    public void setDatabase(Database database){
        txtDatabase.setText(database.getName());
    }

    @OnClick(R.id.edtxt_database)
    public void chooseDatabase(){
        ((SettingsActivity)getActivity()).doByType(SettingsActivity.PROCEED_TO_DATABASE);
    }
}
