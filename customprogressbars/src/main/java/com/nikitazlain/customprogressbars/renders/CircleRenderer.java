package com.nikitazlain.customprogressbars.renders;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.nikitazlain.customprogressbars.DensityUtils;
import com.nikitazlain.customprogressbars.LoadingRenderer;

/**
 * Created by nikitazlain on 15.05.17.
 */

public class CircleRenderer extends LoadingRenderer {

    private static final Interpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(2.5f);

    private static final long ANIMATION_DURATION = 2500;

    private static final float DEFAULT_CIRCLE_COUNT = 5f;

    private static final float DEFAULT_BALL_RADIUS = 7.5f;
    private static final float DEFAULT_WIDTH = 15.0f * 11;
    private static final float DEFAULT_HEIGHT = 15.0f * 5;
    private static final float DEFAULT_STROKE_WIDTH = 0.75f;

    private static final int DEFAULT_COLOR = Color.WHITE;

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mColor;

    private int mSwapIndex;
    private float mBallCount;

    private float mBallCenterY;
    private float mBallRadius;
    private float mBallInterval;
    private float mSwapBallOffsetX;
    private float mSwapBallOffsetY;
    private float mASwapThreshold;

    private float currentFillRadius;

    private float mStrokeWidth;

    private CircleRenderer(Context context) {
        super(context);
        init(context);
        adjustParams();
        setupPaint();
    }

    private void init(Context context) {
        mWidth = DensityUtils.dip2px(context, DEFAULT_WIDTH);
        mHeight = DensityUtils.dip2px(context, DEFAULT_HEIGHT);
        mBallRadius = DensityUtils.dip2px(context, DEFAULT_BALL_RADIUS);
        mStrokeWidth = DensityUtils.dip2px(context, DEFAULT_STROKE_WIDTH);
        currentFillRadius = 0f;
        mColor = DEFAULT_COLOR;
        mDuration = ANIMATION_DURATION;
        mBallCount = DEFAULT_CIRCLE_COUNT;

        mBallInterval = 2*mBallRadius;
    }

    private void adjustParams() {
        mBallCenterY = mHeight / 2.0f;
        //mBallSideOffsets = (mWidth - mBallRadius * 2 * mBallCount - mBallInterval * (mBallCount - 1)) / 2.0f;

        mASwapThreshold = 1.0f / mBallCount;
    }

    private void setupPaint() {
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    protected void draw(Canvas canvas) {
        int saveCount = canvas.save();

        for( int i=0; i< mBallCount ; i++){
            int adjustToIBall =i+1 - Math.round(mBallCount/ 2f);
            int cx = (int) ((mWidth / 2.0f) + adjustToIBall*(mBallInterval+mBallRadius));
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(cx, mBallCenterY, mBallRadius, mPaint);
            if( i == mSwapIndex) {
                mPaint.setStyle(Paint.Style.FILL);
                canvas.drawCircle(cx, mBallCenterY, mBallRadius*currentFillRadius, mPaint);
            }
        }


        canvas.restoreToCount(saveCount);
    }

    @Override
    protected void computeRender(float renderProgress) {
        mSwapIndex = (int) (renderProgress / mASwapThreshold);
        currentFillRadius = OVERSHOOT_INTERPOLATOR.getInterpolation((renderProgress - mSwapIndex*mASwapThreshold)/mASwapThreshold);
    }

    @Override
    protected void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    protected void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    protected void reset() {
    }

    private void apply(Builder builder) {
        this.mWidth = builder.mWidth > 0 ? builder.mWidth : this.mWidth;
        this.mHeight = builder.mHeight > 0 ? builder.mHeight : this.mHeight;
        this.mStrokeWidth = builder.mStrokeWidth > 0 ? builder.mStrokeWidth : this.mStrokeWidth;

        this.mBallRadius = builder.mBallRadius > 0 ? builder.mBallRadius : this.mBallRadius;
        this.mBallInterval = builder.mBallInterval > 0 ? builder.mBallInterval : this.mBallInterval;
        this.mBallCount = builder.mBallCount > 0 ? builder.mBallCount : this.mBallCount;

        this.mColor = builder.mColor != 0 ? builder.mColor : this.mColor;

        this.mDuration = builder.mDuration > 0 ? builder.mDuration : this.mDuration;

        adjustParams();
        setupPaint();
    }

    public static class Builder {
        private Context mContext;

        private int mWidth;
        private int mHeight;
        private int mStrokeWidth;

        private int mBallCount;
        private int mBallRadius;
        private int mBallInterval;

        private int mDuration;

        private int mColor;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setWidth(int width) {
            this.mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.mHeight = height;
            return this;
        }

        public Builder setStrokeWidth(int strokeWidth) {
            this.mStrokeWidth = strokeWidth;
            return this;
        }

        public Builder setBallRadius(int ballRadius) {
            this.mBallRadius = ballRadius;
            return this;
        }

        public Builder setBallInterval(int ballInterval) {
            this.mBallInterval = ballInterval;
            return this;
        }

        public Builder setBallCount(int ballCount) {
            this.mBallCount = ballCount;
            return this;
        }

        public Builder setColor(int color) {
            this.mColor = color;
            return this;
        }

        public Builder setDuration(int duration) {
            this.mDuration = duration;
            return this;
        }

        public CircleRenderer build() {
            CircleRenderer loadingRenderer = new CircleRenderer(mContext);
            loadingRenderer.apply(this);
            return loadingRenderer;
        }
    }

}
