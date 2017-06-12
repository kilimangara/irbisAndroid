package com.nikitazlain.uir.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.nikitazlain.uir.R;

/**
 * Created by nikitazlain on 24.05.17.
 */

public abstract class SwipeBehavior extends ItemTouchHelper.SimpleCallback {

    private Drawable background;
    private Drawable deleteIcon;
    private Drawable acceptIcon;

    private int xMarkMargin;

    private boolean initiated;
    private Context context;

    private int rightColorSwipe;
    private String rightSwipeLabel;

    private int leftcolorCode;
    private String leftSwipeLable;

    public SwipeBehavior(Context context,int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        this.context = context;
    }

    private void init() {
        background = new ColorDrawable();
        xMarkMargin = (int) context.getResources().getDimension(R.dimen.activity_horizontal_margin);
        deleteIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete);
        deleteIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        acceptIcon =ContextCompat.getDrawable(context, android.R.drawable.ic_menu_add);
        acceptIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        initiated = true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public abstract void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        //Log.d("test","swiping "+dX+" "+dY+" actionState "+actionState);
        View itemView = viewHolder.itemView;
        if (!initiated) {
            init();
        }
        int itemHeight = itemView.getBottom() - itemView.getTop();
        if(dX<0) {
            //Setting Swipe Background
            ((ColorDrawable) background).setColor(getLeftcolorCode());
            background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            background.draw(c);

            int intrinsicWidth = deleteIcon.getIntrinsicWidth();
            int intrinsicHeight = deleteIcon.getIntrinsicWidth();

            int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
            int xMarkRight = itemView.getRight() - xMarkMargin;
            int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            int xMarkBottom = xMarkTop + intrinsicHeight;


            //Setting Swipe Icon
            deleteIcon.setBounds(xMarkLeft, xMarkTop + 16, xMarkRight, xMarkBottom);
            deleteIcon.draw(c);

            //Setting Swipe Text
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(48);
            paint.setTextAlign(Paint.Align.CENTER);
            c.drawText(getLeftSwipeLable(), xMarkLeft + 40, xMarkTop + 10, paint);
        }
        if(dX > 0){
            ((ColorDrawable) background).setColor(rightColorSwipe);
            background.setBounds(itemView.getLeft(),itemView.getTop(), (int)dX, itemView.getBottom() );
            background.draw(c);
            int intrinsicWidth = acceptIcon.getIntrinsicWidth();
            int intrinsicHeight = acceptIcon.getIntrinsicWidth();

            int xMarkLeft = itemView.getLeft() + xMarkMargin ;
            int xMarkRight = itemView.getLeft() + xMarkMargin +intrinsicWidth;
            int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
            int xMarkBottom = xMarkTop + intrinsicHeight;

            //Setting Swipe Icon
            acceptIcon.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);
            acceptIcon.draw(c);
            //sett swipe text
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(48);
            paint.setTextAlign(Paint.Align.CENTER);
            c.drawText(rightSwipeLabel, xMarkLeft + 120, xMarkTop , paint);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public void setRightSwipeLabel(String label){
        rightSwipeLabel = label;
    }

    public void setRightColorSwipe(@ColorInt int colorSwipe){
        rightColorSwipe = colorSwipe;
    }

    public String getLeftSwipeLable() {
        return leftSwipeLable;
    }

    public void setLeftSwipeLable(String leftSwipeLable) {
        this.leftSwipeLable = leftSwipeLable;
    }

    public int getLeftcolorCode() {
        return leftcolorCode;
    }

    public void setLeftcolorCode(int leftcolorCode) {
        this.leftcolorCode = leftcolorCode;
    }
}
