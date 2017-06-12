package com.nikitazlain.uir.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.entity.Database;
import com.nikitazlain.uir.persistance.SharedPreferencesWork;
import com.nikitazlain.uir.providers.SettingsProvider;
import com.nikitazlain.uir.ui.activity.SettingsActivity;
import com.nikitazlain.uir.ui.adapter.DatabasesAdapter;
import com.nikitazlain.uir.ui.adapter.RecyclerViewClick;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseDatabaseFragment extends Fragment implements RecyclerViewClick {

    @BindView(R.id.rv_databases)
    RecyclerView rvDatabases;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_databases, container, false);
        ButterKnife.bind(this, view);
        DatabasesAdapter adapter = new DatabasesAdapter(SettingsProvider.provideDatabases());
        adapter.setListener(this);
        rvDatabases.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDatabases.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL ));
        rvDatabases.setAdapter(adapter);
        return view;
    }


    @Override
    public void click(int pos) {
        SharedPreferencesWork.getInstance().saveDB(pos);
        ((SettingsActivity)getActivity()).doByType(SettingsActivity.APPLY_DATABASE);
    }
}
