package com.geakw.carousel;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by wlq on 2017/1/24
*/
public class ViewsAnimator extends FrameLayout {
    int mWhichChild = 0;
    boolean mFirstTime = true;
    boolean mAnimateFirstTime = true;

    public ViewsAnimator(Context context) {
        super(context);
    }

    public ViewsAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisplayedChild(int whichChild) {
        mWhichChild = whichChild;
        if (whichChild >= getChildCount()) {
            mWhichChild = 0;
        } else if (whichChild < 0) {
            mWhichChild = getChildCount() - 1;
        }
        boolean hasFocus = getFocusedChild() != null;
        // This will clear old focus if we had it
        showOnly(mWhichChild);
        if (hasFocus) {
            // Try to retake focus if we had it
            requestFocus(FOCUS_FORWARD);
        }
    }


    public int getDisplayedChild() {
        return mWhichChild;
    }

    public void showNext() {
        setDisplayedChild(mWhichChild + 1);
    }

    /**
     * Manually shows the previous child.
     */
    public void showPrevious() {
        setDisplayedChild(mWhichChild - 1);
    }

    void showOnly(int childIndex, boolean animate) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (i == childIndex) {
                Animator inAnimator = (Animator) child.getTag(R.id.views_animation_in);
                if (animate && inAnimator != null) {
                    inAnimator.start();
                }
                child.setVisibility(View.VISIBLE);
                mFirstTime = false;
            } else {
                Animator outAnimator = (Animator) child.getTag(R.id.views_animation_out);
                if (animate && outAnimator != null) {
                    outAnimator.start();
                }
//                child.setVisibility(View.GONE);
            }
        }
    }

    void showOnly(int childIndex) {
        final boolean animate = (!mFirstTime || mAnimateFirstTime);
        showOnly(childIndex, animate);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (getChildCount() == 1) {
            child.setVisibility(View.VISIBLE);
        } else {
            child.setVisibility(View.GONE);
        }
        if (index >= 0 && mWhichChild >= index) {
            // Added item above current one, increment the index of the displayed child
            setDisplayedChild(mWhichChild + 1);
        }
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        mWhichChild = 0;
        mFirstTime = true;
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
            mFirstTime = true;
        } else if (mWhichChild >= childCount) {
            // Displayed is above child count, so float down to top of stack
            setDisplayedChild(childCount - 1);
        } else if (mWhichChild == index) {
            // Displayed was removed, so show the new child living in its place
            setDisplayedChild(mWhichChild);
        }
    }

    public void removeViewInLayout(View view) {
        removeView(view);
    }

    public void removeViews(int start, int count) {
        super.removeViews(start, count);
        if (getChildCount() == 0) {
            mWhichChild = 0;
            mFirstTime = true;
        } else if (mWhichChild >= start && mWhichChild < start + count) {
            // Try showing new displayed child, wrapping if needed
            setDisplayedChild(mWhichChild);
        }
    }

    public void removeViewsInLayout(int start, int count) {
        removeViews(start, count);
    }

    public View getCurrentView() {
        return getChildAt(mWhichChild);
    }

    public static void setInAnimator(final View view, Animator animator) {
        animator.setDuration(300);
        view.setTag(R.id.views_animation_in, animator);
    }


    public static void setOutAnimator(View view, Animator animator) {
        animator.setDuration(300);
        view.setTag(R.id.views_animation_out, animator);
    }

    public boolean getAnimateFirstView() {
        return mAnimateFirstTime;
    }

    public void setAnimateFirstView(boolean animate) {
        mAnimateFirstTime = animate;
    }

    @Override
    public int getBaseline() {
        return (getCurrentView() != null) ? getCurrentView().getBaseline() : super.getBaseline();
    }
}
