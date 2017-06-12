package com.nikitazlain.uir.utils;

import android.os.Build;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by nikitazlain on 12.06.17.
 */

public class HtmlCompat {

    public static void fromHtml(TextView textView, String data){
        if(data == null){
            return;
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            textView.setText(Html.fromHtml(data, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(data));
        }
    }
}
