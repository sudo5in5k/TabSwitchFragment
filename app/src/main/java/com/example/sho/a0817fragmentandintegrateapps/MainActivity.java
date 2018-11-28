package com.example.sho.a0817fragmentandintegrateapps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener {

    private TabHost mTabHost;

    // タブ切り替え前の最終タブの情報保持
    private TabInfo mLastTabInfo;

    //タブのTagとタブ情報の連想配列を作成
    private Map<String, TabInfo> MapTagTabInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabs();
    }


    private void initTabs() {

        mTabHost = findViewById(R.id.tabhost);
        // タブ追加の前にセットアップを呼ぶ必要あり
        mTabHost.setup();

        MapTagTabInfo = new HashMap<>();
        addTab(new TabInfo("bmi", "BMI計算", BMIFragment.class.getName()));
        addTab(new TabInfo("omikuji", "おみくじ", OmikujiFragment.class.getName()));
        addTab(new TabInfo("stopwatch", "ストップウォッチ", StopWatchFragment.class.getName()));

        mTabHost.setOnTabChangedListener(this);

        // 初期タブ選択
        onTabChanged("bmi");
    }

    private void addTab(TabInfo tabinfo) {
        MapTagTabInfo.put(tabinfo.tag, tabinfo);
        TabSpec tabSpec = mTabHost.newTabSpec(tabinfo.tag)
                .setIndicator(tabinfo.label)
                .setContent(new TabContentFactory() {
                    @Override
                    public View createTabContent(String tag) {
                        if (tag == "bmi") {
                            return getLayoutInflater().inflate(R.layout.bmi_fragment, null);
                        } else if (tag == "omikuji") {
                            return getLayoutInflater().inflate(R.layout.omikuji_fragment, null);
                        } else if (tag == "stopwatch") {
                            return getLayoutInflater().inflate(R.layout.stopwatch_fragment, null);
                        }
                        return null;
                    }
                });
        mTabHost.addTab(tabSpec);
    }

    @Override
    public void onTabChanged(String tabID) {
        TabInfo newTab = MapTagTabInfo.get(tabID);// TabInfo
        Log.d("TAG", String.valueOf(mLastTabInfo));
        if (mLastTabInfo != newTab) {
            FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            if (mLastTabInfo != null) {
                fragmentTransaction.detach(mLastTabInfo.fragment);
            }
            if (newTab.fragment == null) {
                newTab.fragment = createTabRootFragment(newTab);
                fragmentTransaction.add(R.id.realtabcontent, newTab.fragment);
            } else {
                fragmentTransaction.attach(newTab.fragment);
            }
            mLastTabInfo = newTab; //更新
            fragmentTransaction.commit();
        }
    }

    private Fragment createTabRootFragment(TabInfo tabInfo) {
        Bundle args = new Bundle();
        args.putString("root", tabInfo.className);

        TabRootFragment fragment = new TabRootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private class TabInfo {
        private String tag;
        private String label;
        private String className;
        private Fragment fragment;

        TabInfo(String tag, String label, String className) {
            this.tag = tag;
            this.label = label;
            this.className = className;
        }
    }

    @Override
    public void onBackPressed() {
        if (!getCurrentFragment().popBackStack()) {
            super.onBackPressed();
        }
    }

    private TabRootFragment getCurrentFragment() {
        return (TabRootFragment) getSupportFragmentManager().findFragmentById(R.id.realtabcontent);
    }
}