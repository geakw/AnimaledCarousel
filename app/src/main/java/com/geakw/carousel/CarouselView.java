package com.geakw.carousel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by wlq on 2017/1/24
 */

public class CarouselView extends ViewsFlipper {
    public CarouselView(Context context) {
        super(context);
    }

    public CarouselView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMarqueeFactory(MarqueeFactory factory) {
        factory.setAttachedToMarqueeView(this);
        removeAllViews();
        List<View> mViews = factory.getMarqueeViews();
        if (mViews != null) {
            for (int i = 0; i < mViews.size(); i++) {
                addView(mViews.get(i));
            }
        }
    }
}
