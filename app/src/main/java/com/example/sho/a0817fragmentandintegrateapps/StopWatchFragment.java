package com.example.sho.a0817fragmentandintegrateapps;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * TODO STOPWATCHフラグメント
 * XMLはlist_item, stopwatch_fragment, strings が対応
 * Created by sho on 2017/08/17.
 */

public class StopWatchFragment extends Fragment {

    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private Button rapButton;

    private long startTime;
    private TextView timerLabel;

    private Handler handler = new Handler();
    private Runnable updateTimer;

    private long elapsedTime = 0l;

    private int mRapCount = 0;
    private List<String> rapList = new ArrayList<>();//RAP用のリストのデータ格納
    private ListView rapView;//lap_listからのデータを受け渡される

    private List<String> rapResetList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stopwatch_fragment, container, false);

        timerLabel = (TextView) view.findViewById(R.id.timerLabel);

        rapView = (ListView) view.findViewById(R.id.myListView);

        startButton = (Button) view.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startTimer(v);
            }
        });

        stopButton = (Button) view.findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stopTimer(v);
            }
        });

        resetButton = (Button) view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                resetTimer(v);
            }
        });

        rapButton = (Button) view.findViewById(R.id.rapButton);
        rapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rapTimer(v);
            }
        });

        setButtonState(true, false, false, false);

        return view;
    }

    public void setButtonState(boolean start, boolean stop, boolean reset, boolean rap) {
        startButton.setEnabled(start);
        stopButton.setEnabled(stop);
        resetButton.setEnabled(reset);
        rapButton.setEnabled(rap);
    }

    public void startTimer(View view) {
        //startTimeの取得
        startTime = SystemClock.elapsedRealtime();// 起動してからの経過時間ms

        //一定時間ごとに現在の経過時間表示
        // UIに大して割り込み処理をする時はHundler
        //Hundler -> Runnable(処理) -> UI
        updateTimer = new Runnable() {//runnableオブジェクトにして処理を投げなさい

            @Override
            public void run() {
                long t = SystemClock.elapsedRealtime() - startTime + elapsedTime;
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS", Locale.US);
                timerLabel.setText(sdf.format(t));

                handler.removeCallbacks(updateTimer);
                handler.postDelayed(updateTimer, 10);

            }
        };
        handler.postDelayed(updateTimer, 10);


        //ボタンの操作
        setButtonState(false, true, false, true);
    }

    public void stopTimer(View view) {
        elapsedTime += SystemClock.elapsedRealtime() - startTime;

        handler.removeCallbacks(updateTimer);
        setButtonState(true, false, true, false);

    }

    public void resetTimer(View view) {
        elapsedTime = 0;
        mRapCount = 0;
        timerLabel.setText(R.string.timer_label);
        rapList.clear();//リストのクリア
        rapView.setAdapter(null);//ビューを空にする

        setButtonState(true, false, false, false);
    }

    public void rapTimer(View view) {
        ArrayAdapter<String> adapter;
        if (mRapCount < 5) {
            mRapCount++;
            setButtonState(false, true, false, true);
            long lt = SystemClock.elapsedRealtime() - startTime;
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SSS", Locale.US);
            rapList.add("RAP" + mRapCount + ":" + String.valueOf(sdf.format(lt)));//listに追加
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item,
                    rapList);//間をかえすアダプターにlistを入れる
            rapView.setAdapter(adapter);//セットする
        } else {
            Toast.makeText(getActivity(), "これ以上記録できません", Toast.LENGTH_SHORT).show();
            setButtonState(false, true, false, false);
        }

    }

}
