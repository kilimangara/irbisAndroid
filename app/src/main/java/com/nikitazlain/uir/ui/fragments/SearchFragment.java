package com.nikitazlain.uir.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikitazlain.uir.R;
import com.nikitazlain.uir.models.DocumentsModel;
import com.nikitazlain.uir.presenters.DocumentsPresenter;
import com.nikitazlain.uir.view.DocumentsView;

public class SearchFragment extends Fragment {

    DocumentsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_search, container, false);
        DocumentsView view = new DocumentsView(rootView);
        DocumentsModel model = new DocumentsModel(view);
        presenter = new DocumentsPresenter(view, model);
        presenter.onCreate(getActivity(), savedInstanceState);
        return rootView;
    }

    public DocumentsPresenter getPresenter(){
        return presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
    }
}
