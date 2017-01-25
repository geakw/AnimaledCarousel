package com.geakw.carousel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CarouselView carouselView = (CarouselView) findViewById(R.id.carousel);
        final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
        MarqueeFactory<TextView, String> marqueeFactory1 = new NoticeMF(this);
        carouselView.setMarqueeFactory(marqueeFactory1);
        marqueeFactory1.setData(datas);
        carouselView.startFlipping();
    }
}
