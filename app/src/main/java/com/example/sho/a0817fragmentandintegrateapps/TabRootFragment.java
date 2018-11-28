package com.example.sho.a0817fragmentandintegrateapps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * TODO クラス説明
 * childFragmentのRoot(parent)を作成
 * Created by sho on 2017/08/22.
 */

public class TabRootFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.container_for_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        String rootClass = args.getString("root");

        initFragment(rootClass);
    }

    private void initFragment(String cls) {
        FragmentManager fragmentManager = getChildFragmentManager(); //child!
        if (fragmentManager.findFragmentById(R.id.container33) != null) {
            // すでにFragmentを設定してある場合は何もしない
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = Fragment.instantiate(getActivity(), cls);
        fragmentTransaction.replace(R.id.container33, fragment);

        fragmentTransaction.commit();
    }

    public boolean popBackStack() {
        return getChildFragmentManager().popBackStackImmediate();
    }

}
