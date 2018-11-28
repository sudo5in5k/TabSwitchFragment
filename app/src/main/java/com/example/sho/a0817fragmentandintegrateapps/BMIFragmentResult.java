package com.example.sho.a0817fragmentandintegrateapps;

import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * TODO クラス説明
 *
 * Created by sho on 2017/08/18.
 */

public class BMIFragmentResult extends android.support.v4.app.Fragment{

    final static String yaseText = "痩せ気味です！";
    final static String hutuText = "標準です！";
    final static String himanText = "肥満気味です！";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bmi_result, container, false);

        //Intent intent = getActivity().getIntent();
        final TextView bmiTextView = (TextView) view.findViewById(R.id.bmiText);
        final TextView adviceTextView = (TextView) view.findViewById(R.id.adviceText);

        double height = getArguments().getDouble("HEIGHT_VAL");
        double weight = getArguments().getDouble("WEIGHT_VAL");
        String heightUnit = getArguments().getString("HEIGHT_ITEM");
        String weightUnit = getArguments().getString("WEIGHT_ITEM");

        switch (getArguments().getString("HEIGHT_ITEM")) {
            case "センチ":
                break;
            case "フィート":
                height = height * 30.48;
                break;
            case "インチ":
                height = height * 2.54;
                break;
        }

        switch (getArguments().getString("WEIGHT_ITEM")) {
            case "キログラム":
                break;
            case "ポンド":
                weight = weight * 0.453592;
                break;
        }

        float bmi = (float) (10000.00 * weight / height / height);

        ValueAnimator bmiAnimator = new ValueAnimator();
        bmiAnimator.setFloatValues(0f, bmi);
        bmiAnimator.setDuration(2000).start();
        bmiAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator anim) {
                float val = (float) anim.getAnimatedValue();
                bmiTextView.setText(String.format("%.2f", val));
            }
        });

        if (bmi < 18.5) {
            adviceTextView.setText(yaseText);
            adviceTextView.setTextColor(getResources().getColor(R.color.yaseTextColor));
        } else if (bmi < 25) {
            adviceTextView.setText(hutuText);
            adviceTextView.setTextColor(getResources().getColor(R.color.hutuTextColor));
        } else {
            adviceTextView.setText(himanText);
            adviceTextView.setTextColor(getResources().getColor(R.color.himanTextColor));
        }

        return view;
    }

}
