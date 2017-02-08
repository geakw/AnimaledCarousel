package com.geakw.carousel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnimateCarousel animateCarousel = (AnimateCarousel) findViewById(R.id.carousel);
        final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
        for (int i = 0; i < datas.size(); i++) {
            String text = datas.get(i);
            final TextView textView = (TextView) View.inflate(this,R.layout.notice_item,null);
            textView.setText(text);
//            textView.setTag(i%2 == 0 ? AnimateStrategy.LEFT:AnimateStrategy.UP);
            textView.setTag(AnimateStrategy.MARQUEE);
            animateCarousel.addView(textView);
        }
        animateCarousel.start();
    }
}
