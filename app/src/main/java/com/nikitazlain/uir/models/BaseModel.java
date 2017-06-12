package com.nikitazlain.uir.models;

import android.content.Context;

import com.nikitazlain.uir.view.BaseView;
import com.nikitazlain.uir.viewholders.BaseViewHolder;


public class BaseModel<T extends BaseView<R>, R extends BaseViewHolder> {

    public T view;

    public BaseModel(T view){
        this.view = view;
    }

    public Context getContext(){
        return view.getContext();
    }
}
