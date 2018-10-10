package com.example.pcpv.animator.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Matt
 */
public class AnimatedPathView extends View {

    Paint mPaint;
    Path mPath;
    int mStrokeColor;
    float mStrokeWidth;

    float mProgress = 0f;
    float mPathLength = 0f;


    public AnimatedPathView(Context context) {
        this(context, null);
        init();
    }

    public AnimatedPathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public AnimatedPathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mStrokeColor = Color.RED;
        mStrokeWidth = 8.0f;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mStrokeColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setAntiAlias(true);

        setPath(new Path());
    }

    public void setPath(Path p) {
        mPath = p;
        PathMeasure measure = new PathMeasure(mPath, false);
        mPathLength = measure.getLength();
    }

    /**
     * Set the drawn path using an array of array of floats. First is x parameter, second is y.
     *
     * @param points The points to set on
     */
    public void setPath(float[]... points) {
        if (points.length == 0)
            throw new IllegalArgumentException("Cannot have zero points in the line");

        Path p = new Path();
        p.moveTo(points[0][0], points[0][1]);

        for (int i = 1; i < points.length; i++) {
            p.lineTo(points[i][0], points[i][1]);
        }

        setPath(p);
    }

    public void setPercentage(float percentage) {
        if (percentage < 0.0f || percentage > 1.0f)
            throw new IllegalArgumentException("setPercentage not between 0.0f and 1.0f");

        mProgress = percentage;
        invalidate();
    }

    public void scalePathBy(float x, float y) {
        Matrix m = new Matrix();
        m.postScale(x, y);
        mPath.transform(m);
        PathMeasure measure = new PathMeasure(mPath, false);
        mPathLength = measure.getLength();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*draw shape with animation*/
        // start drawing at phase 360->0 (mPathLength - mPathLength * 0 = mPathLength(360))
        // PathEffect pathEffect = new DashPathEffect(new float[]{mPathLength, mPathLength}, (mPathLength - mPathLength * mProgress));
        //mPaint.setPathEffect(pathEffect);

   /*
    intervals[]
    line:mPathLength / 10
    space:mPathLength * 4 / 10
    phase is a offset angle to begin draw path
    */

        // start drawing at phase 0->360 (mPathLength * mProgress = 0(0))
        PathEffect pathEffect = new DashPathEffect(new float[]{mPathLength / 10, mPathLength * 4 / 10,
                mPathLength / 10, mPathLength * 4 / 10}, mPathLength * mProgress);
        mPaint.setPathEffect(pathEffect);

        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);

        int measuredWidth, measuredHeight;

        if (widthMode == MeasureSpec.AT_MOST)
            throw new IllegalStateException("AnimatedPathView cannot have a WRAP_CONTENT property");
        else
            measuredWidth = widthSize;

        if (heightMode == MeasureSpec.AT_MOST)
            throw new IllegalStateException("AnimatedPathView cannot have a WRAP_CONTENT property");
        else
            measuredHeight = heightSize;

        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
