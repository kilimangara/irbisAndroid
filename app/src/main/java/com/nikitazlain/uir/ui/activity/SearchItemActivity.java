package com.nikitazlain.uir.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nikitazlain.swipeactivity.SlidingActivity;
import com.nikitazlain.uir.R;
import com.nikitazlain.uir.ui.customviews.CustomToolbar;
import com.nikitazlain.uir.utils.HtmlCompat;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchItemActivity extends SlidingActivity {

    @BindView(R.id.placeholder_annotation)
    TextView annotation;

    @BindView(R.id.search_item_toolbar)
    CustomToolbar toolbar;

    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    View rootView;

    public static final String ANNOTATIONS = "annotations";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);
        ButterKnife.bind(this);
        rootView =findViewById(android.R.id.content);
        String annotations = getIntent().getExtras().getString(ANNOTATIONS);
        HtmlCompat.fromHtml(annotation, annotations);
        toolbar.setTitle("Результат поиска");
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @NotNull
    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public boolean canSlideDown() {
        Log.d("test","scroll y "+scrollView.getScrollY());
        return scrollView.getScrollY() == 0;
    }
}
