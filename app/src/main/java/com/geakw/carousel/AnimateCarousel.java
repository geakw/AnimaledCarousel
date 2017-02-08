package com.geakw.carousel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by wlq on 2017/2/7
 */

public abstract class AnimateCarousel extends FrameLayout {
    protected boolean mVisible;

    public AnimateCarousel(Context context) {
        super(context);
    }

    public AnimateCarousel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        mVisible = screenState == SCREEN_STATE_ON;
        onVisibleChanged();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = visibility == View.VISIBLE;
        onVisibleChanged();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        mVisible = visibility == View.VISIBLE;
        onVisibleChanged();
    }

    private void onVisibleChanged() {
        if (mVisible) {
            start();
        } else {
            stop();
        }
    }

    abstract void start();

    abstract void stop();
}
