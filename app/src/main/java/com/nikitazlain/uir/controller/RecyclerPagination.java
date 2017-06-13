package com.nikitazlain.uir.controller;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by nikitazlain on 25.05.17.
 */

public abstract class RecyclerPagination extends RecyclerView.OnScrollListener {

    LinearLayoutManager manager;

    public RecyclerPagination(LinearLayoutManager linearLayoutManager){
        manager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = manager.getChildCount();
        int totalItemCount = manager.getItemCount();
        int firstVisiblePos = manager.findFirstVisibleItemPosition();
        Log.d("test","visible items "+visibleItemCount+" totalItemCount "+totalItemCount+" firstVisiblePos "+firstVisiblePos);
        if(!isLastPage()){
            if(visibleItemCount + firstVisiblePos >= totalItemCount && totalItemCount >= 0){
                loadNextPage();
                Log.d("test","loadNextPage");
            }
        }
    }

    protected abstract void loadNextPage();

    public abstract int getCurrentPage();

    public abstract boolean isLastPage();
}
