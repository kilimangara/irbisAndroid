package com.nikitazlain.uir.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nikitazlain.uir.R;

import butterknife.BindView;

public class DocumentViewHolder extends BaseViewHolder {

    @BindView(R.id.progress_search)
    public ProgressBar progressBar;

    @BindView(R.id.txt_placeholder)
    public TextView placeHolder;

    @BindView(R.id.rv_search_results)
    public RecyclerView recyclerView;

    public DocumentViewHolder(View rootView) {
        super(rootView);
    }

    public void showLoading(){
        Log.d("test","showLoading");
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        placeHolder.setVisibility(View.GONE);
    }

    public void showResults(){
        Log.d("test","showResults");
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        placeHolder.setVisibility(View.GONE);
    }

    public void clearResults(){
        Log.d("test","clearResults");
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        placeHolder.setVisibility(View.VISIBLE);
    }
}
