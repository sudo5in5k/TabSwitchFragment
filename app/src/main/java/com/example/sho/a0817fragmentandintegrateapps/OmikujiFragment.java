package com.example.sho.a0817fragmentandintegrateapps;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * TODO クラス説明
 * <p>
 * Created by sho on 2017/08/17.
 */

public class OmikujiFragment extends Fragment {

    private TextView tv;
    private Button omikujiButton;
    final private String[] results = {"大吉", "吉", "凶"};
    final private String TAG = "OFRAGMENT_TEST";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.omikuji_fragment, container, false);
        tv = view.findViewById(R.id.myTextView);
        omikujiButton = view.findViewById(R.id.omikujiButton);
        omikujiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getOmikuji();
            }
        });

        return view;
    }

    public void getOmikuji() {
        Random randomGenerator = new Random();
        int num = randomGenerator.nextInt(results.length);

        if (num == 0) {
            tv.setTextColor(Color.parseColor("#ff0000"));
        } else {
            tv.setTextColor(Color.parseColor("#000000"));
        }
        tv.setText(results[num]);
    }

}

