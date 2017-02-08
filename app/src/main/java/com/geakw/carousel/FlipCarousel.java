package com.geakw.carousel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wlq on 2017/2/8
 */

public class FlipCarousel extends AnimateCarousel {
    private static final int DEFAULT_INTERVAL = 3000;
    private int mWhichChild;
    private int mFlipInterval;
    public FlipCarousel(Context context) {
        super(context);
        init();
    }

    public FlipCarousel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    void start() {
        removeCallbacks(mFlipRunnable);
        postDelayed(mFlipRunnable, mFlipInterval);
    }

    @Override
    void stop() {
        removeCallbacks(mFlipRunnable);
    }

    private void init() {
        mFlipInterval = DEFAULT_INTERVAL;
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (getChildCount() == 1) {
            child.setVisibility(View.VISIBLE);
        } else {
            AnimateStrategy strategy = (AnimateStrategy) child.getTag();
            if (strategy == AnimateStrategy.LEFT) {
                child.setTranslationX(strategy.translation(child));
            } else if (strategy == AnimateStrategy.UP) {
                child.setTranslationY(strategy.translation(child));
            }
            child.setVisibility(View.GONE);
        }
        if (index >= 0 && mWhichChild >= index) {
            // Added item above current one, increment the index of the displayed child
            displayChild(mWhichChild + 1);
        }
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        mWhichChild = 0;
    }

    @Override
    public void removeView(View view) {
        final int index = indexOfChild(view);
        if (index >= 0) {
            removeViewAt(index);
        }
    }

    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
        final int childCount = getChildCount();
        if (childCount == 0) {
            mWhichChild = 0;
        } else if (mWhichChild >= childCount) {
            // Displayed is above child count, so float down to top of stack
            displayChild(childCount - 1);
        } else if (mWhichChild == index) {
            // Displayed was removed, so show the new child living in its place
            displayChild(mWhichChild);
        }
    }

    public void removeViewInLayout(View view) {
        removeView(view);
    }

    public void removeViews(int start, int count) {
        super.removeViews(start, count);
        if (getChildCount() == 0) {
            mWhichChild = 0;
        } else if (mWhichChild >= start && mWhichChild < start + count) {
            // Try showing new displayed child, wrapping if needed
            displayChild(mWhichChild);
        }
    }

    public void showNext() {
        displayChild(mWhichChild + 1);
    }

    public void showPrevious() {
        displayChild(mWhichChild - 1);
    }

    public View getCurrentView() {
        return getChildAt(mWhichChild);
    }

    public void displayChild(int whichChild) {
        mWhichChild = whichChild;
        int count = getChildCount();
        if (whichChild >= count) {
            mWhichChild = 0;
        } else if (whichChild < 0) {
            mWhichChild = count - 1;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builder = null;
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            AnimateStrategy strategy = (AnimateStrategy) child.getTag();
            if (i == mWhichChild) {
                Log.e("wlq","in : "+i);
                child.setVisibility(View.VISIBLE);
                Animator in = strategy.animatorIn(child);
                in.setDuration(1000);
                builder = (builder == null) ? animatorSet.play(in) : builder.with(in);
            } else {
                if (child.getVisibility() == View.VISIBLE) {
                    Log.e("wlq","out : "+i);
                    Animator out = strategy.animatorOut(child);
                    out.setDuration(1000);
                    out.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            child.setVisibility(View.GONE);
                        }
                    });
                    builder = (builder == null) ? animatorSet.play(out) : builder.with(out);
                } else {
                    child.setVisibility(View.GONE);
                }
            }
        }
        animatorSet.start();
    }


    @Override
    public int getBaseline() {
        return (getCurrentView() != null) ? getCurrentView().getBaseline() : super.getBaseline();
    }

    private final Runnable mFlipRunnable = new Runnable() {
        @Override
        public void run() {
            showNext();
            if (mVisible) {
                postDelayed(mFlipRunnable, mFlipInterval);
            }
        }
    };
}
