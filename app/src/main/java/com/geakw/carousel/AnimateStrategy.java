package com.geakw.carousel;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by wlq on 2017/2/7
 */

public enum AnimateStrategy {
    UP {
        @Override
        Animator animatorIn(View view) {
            return ObjectAnimator.ofFloat(view, "y", translation(view), 0);
        }

        @Override
        Animator animatorOut(View view) {
            return ObjectAnimator.ofFloat(view, "y", 0, -translation(view));
        }

        @Override
        int translation(View view) {
            return 800;
        }
    },
    LEFT {
        @Override
        Animator animatorIn(View view) {
            return ObjectAnimator.ofFloat(view, "x", translation(view), 0);
        }

        @Override
        Animator animatorOut(View view) {
            return ObjectAnimator.ofFloat(view, "x", 0, -translation(view));
        }

        @Override
        int translation(View view) {
            return DeviceUtils.getScreenWidth(view.getContext());
        }
    },
    MARQUEE {
        @Override
        Animator animatorIn(View view) {
            return ObjectAnimator.ofFloat(view, "x", translation(view), -translation(view));
        }

        @Override
        Animator animatorOut(View view) {
            return null;
        }

        @Override
        int translation(View view) {
            return DeviceUtils.getScreenWidth(view.getContext());
        }
    };

    abstract Animator animatorIn(View view);

    abstract Animator animatorOut(View view);

    abstract int translation(View view);
}
