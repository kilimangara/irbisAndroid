package com.nikitazlain.customprogressbars;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class LoadingDrawable extends Drawable implements Animatable {

    private LoadingRenderer mLoadingRender;

    private final Callback mCallback = new Callback() {
        @Override
        public void invalidateDrawable(Drawable d) {
            invalidateSelf();
        }

        @Override
        public void scheduleDrawable(Drawable d, Runnable what, long when) {
            scheduleSelf(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable d, Runnable what) {
            unscheduleSelf(what);
        }
    };


    public LoadingDrawable(LoadingRenderer loadingRender) {
        this.mLoadingRender = loadingRender;
        this.mLoadingRender.setCallback(mCallback);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.mLoadingRender.setBounds(bounds);
    }

    @Override
    public void start() {
        this.mLoadingRender.start();
    }

    @Override
    public void stop() {
        this.mLoadingRender.stop();
    }

    @Override
    public boolean isRunning() {
        return this.mLoadingRender.isRunning();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (!getBounds().isEmpty()) {
            this.mLoadingRender.draw(canvas);
        }
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int i) {
        this.mLoadingRender.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.mLoadingRender.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) this.mLoadingRender.mHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) this.mLoadingRender.mWidth;
    }
}
