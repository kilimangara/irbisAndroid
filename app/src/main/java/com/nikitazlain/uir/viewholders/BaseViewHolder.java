package com.nikitazlain.uir.viewholders;

import android.view.View;

import butterknife.ButterKnife;



public abstract class BaseViewHolder {

    public BaseViewHolder(View rootView){
        ButterKnife.bind(this, rootView);
    }
}
