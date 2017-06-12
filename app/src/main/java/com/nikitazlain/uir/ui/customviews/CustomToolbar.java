package com.nikitazlain.uir.ui.customviews;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;


public class CustomToolbar extends Toolbar {

    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMenu(@MenuRes int resId){
        clearMenu();
        inflateMenu(resId);
    }

    public void clearMenu(){
        getMenu().clear();
    }
}
