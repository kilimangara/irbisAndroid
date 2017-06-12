package com.nikitazlain.uir.presenters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.nikitazlain.uir.models.BaseModel;
import com.nikitazlain.uir.view.BaseView;
import com.nikitazlain.uir.viewholders.BaseViewHolder;

public abstract class BasePresenter<V extends BaseView<VH>, M extends BaseModel<V, VH>, VH extends BaseViewHolder> {

    final M model;

    final V view;

    public BasePresenter(V view, M model){
        this.model = model;
        this.view = view;
    }

    public abstract void onCreate(Activity activity, Bundle savedInstance);

    public abstract void onResume();

    public abstract void onDestroy();

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
