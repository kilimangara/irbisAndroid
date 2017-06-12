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
import com.nikitazlain.uir.providers.SearchProvider;
import com.nikitazlain.uir.ui.adapter.SearchProtocolsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchProtocolsFragment extends Fragment {


    @BindView(R.id.rv_for_protocol)
    RecyclerView recyclerView;

    SearchProtocolsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_protocols, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        adapter = new SearchProtocolsAdapter(SearchProvider.getSearchProtocols());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
