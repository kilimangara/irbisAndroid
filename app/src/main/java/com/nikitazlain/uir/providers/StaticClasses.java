package com.nikitazlain.uir.providers;

import android.view.View;



public class StaticClasses {

    public final static View.OnClickListener EMPTY_LISTENER =  new View.OnClickListener() {
        @Override
        public void onClick(View view) {}
    };
}
