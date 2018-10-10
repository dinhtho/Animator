package com.example.pcpv.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.location.Address;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.pcpv.animator.lib.AnimatedPathView;

public class MainActivity extends AppCompatActivity {
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivLogo = findViewById(R.id.iv_logo);

        initAnimatedPathView();

        findViewById(R.id.bt_scale_XY).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleLogo(ivLogo);
            }
        });
        findViewById(R.id.bt_translationXY).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translationLogo(ivLogo);
            }
        });
        findViewById(R.id.bt_rotationXY).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotationLogo(ivLogo);
            }
        });
        findViewById(R.id.bt_XY).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xY(ivLogo);
            }
        });
    }

    private void scaleLogo(View view) {
        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.45f, 1.05f);
        animScaleX.setDuration(1000);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.45f, 1.05f);
        animScaleY.setDuration(1000);
        ObjectAnimator animAlpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        animAlpha.setDuration(1000);
        ObjectAnimator animSlightBounceX = ObjectAnimator.ofFloat(view, "scaleX", 1.05f, 1);
        animSlightBounceX.setStartDelay(1000);
        animSlightBounceX.setDuration(100);
        ObjectAnimator animSlightBounceY = ObjectAnimator.ofFloat(view, "scaleY", 1.05f, 1);
        animSlightBounceY.setStartDelay(1000);
        animSlightBounceY.setDuration(100);
        final AnimatorSet mAnimationSet = new AnimatorSet();
        mAnimationSet.playTogether(animScaleX, animScaleY, animAlpha, animSlightBounceX, animSlightBounceY);
        mAnimationSet.start();
        mAnimationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimationSet.removeListener(this);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mAnimationSet.removeListener(this);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    //move to x and y relative position base on previous view's position
    private void translationLogo(View view) {
        ObjectAnimator animScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.786f);
        ObjectAnimator animScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.786f);
        ObjectAnimator animTranslationX = ObjectAnimator.ofFloat(view, "translationX",
                0);
        ObjectAnimator animTranslationY = ObjectAnimator.ofFloat(view, "translationY", -500);
        final AnimatorSet mAnimationSet = new AnimatorSet();
        mAnimationSet.playTogether(animScaleX, animScaleY, animTranslationX, animTranslationY);
        mAnimationSet.setDuration(1000);
        mAnimationSet.start();
        mAnimationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimationSet.removeListener(this);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mAnimationSet.removeListener(this);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void rotationLogo(View view) {
        // set pivot
        pivotLogo(view);

        ObjectAnimator animRotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 90f);
        animRotation.setDuration(2000);
        animRotation.start();

        ObjectAnimator animRotationX = ObjectAnimator.ofFloat(view, "rotationX", 0f, 180f);
        animRotationX.setStartDelay(1000);
        animRotationX.setDuration(2000);
        animRotationX.start();

        ObjectAnimator animRotationY = ObjectAnimator.ofFloat(view, "rotationY", 0f, 180f);
        animRotationY.setStartDelay(1000);
        animRotationY.setDuration(2000);
        animRotationY.start();
    }

    private void pivotLogo(View view) {
        ObjectAnimator pivotY = ObjectAnimator.ofFloat(view, "pivotY", view.getMeasuredHeight());
        pivotY.setDuration(2000);
        pivotY.start();
        ObjectAnimator pivotX = ObjectAnimator.ofFloat(view, "pivotX", view.getMeasuredWidth());
        pivotX.setDuration(2000);
        pivotX.start();
    }

    // move to x and y absolute position
    private void xY(View view) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "x", 0);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "y", 0);
        final AnimatorSet mAnimationSet = new AnimatorSet();
        mAnimationSet.playTogether(animatorX, animatorY);
        mAnimationSet.setDuration(2000);
        mAnimationSet.start();
        mAnimationSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mAnimationSet.removeListener(this);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mAnimationSet.removeListener(this);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void initAnimatedPathView() {
        final AnimatedPathView animatedPathView = findViewById(R.id.animated_path);

        ViewTreeObserver observer = animatedPathView.getViewTreeObserver();
        if (observer != null) {
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    animatedPathView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    float[][] points = new float[][]{
                            {0, 0},
                            {animatedPathView.getWidth(), 0},
                            {animatedPathView.getWidth(), animatedPathView.getHeight()},
                            {0, animatedPathView.getHeight()},
                            {0, 0},
                    };
                    animatedPathView.setPath(points);
                }
            });
        }
        ObjectAnimator anim = ObjectAnimator.ofFloat(animatedPathView, "percentage", 0.0f, 1.0f);
        anim.setDuration(2000);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setInterpolator(new LinearInterpolator());
        anim.start();
    }
}


