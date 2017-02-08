package com.geakw.carousel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlq on 2017/2/8
 */

public class MarqueeCarousel extends AnimateCarousel {
    List<Animator> animators = new ArrayList<>();
    private AnimatorSet animatorSet = new AnimatorSet();

    public MarqueeCarousel(Context context) {
        super(context);
    }

    public MarqueeCarousel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    void start() {
        if (animatorSet.isPaused()) {
            animatorSet.resume();
        } else {
            removeCallbacks(startRunnable);
            animators.clear();
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                child.setTranslationX(DeviceUtils.getScreenWidth(getContext()));
                AnimateStrategy strategy = (AnimateStrategy) child.getTag();
                Animator animator = strategy.animatorIn(child);
                animator.setInterpolator(new LinearInterpolator());
                animator.setDuration(5000);
                animators.add(animator);
            }
            postDelayed(startRunnable, 1000);
        }
    }

    @Override
    void stop() {
        animatorSet.pause();
    }

    private Runnable startRunnable = new Runnable() {
        @Override
        public void run() {
            animatorSet.playSequentially(animators);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    animatorSet.start();
                }
            });
            animatorSet.start();
        }
    };
}
