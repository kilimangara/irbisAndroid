package com.nikitazlain.uir.view;

import android.view.View;
import android.widget.Toast;

import com.nikitazlain.uir.viewholders.DocumentViewHolder;

public class DocumentsView extends BaseView<DocumentViewHolder> {

    public DocumentsView(View rootView) {
        super(rootView, DocumentViewHolder.class);
    }

    public void startSearching(){
        viewHolder.showLoading();
    }

    public void endSerching(){
       viewHolder.showResults();
    }

    public void reset(){
        viewHolder.clearResults();
    }

    public void showToast(String desc){
        Toast.makeText(getContext(), desc, Toast.LENGTH_LONG).show();
    }
}
