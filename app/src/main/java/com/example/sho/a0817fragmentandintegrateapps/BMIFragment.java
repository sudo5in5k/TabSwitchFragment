package com.example.sho.a0817fragmentandintegrateapps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * TODO クラス説明
 * <p>
 * Created by sho on 2017/08/17.
 */

public class BMIFragment extends Fragment {

    private EditText textHeight;
    private EditText textWeight;
    private Spinner heightSpinner;
    private Spinner weightSpinner;
    private String hItem, wItem;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bmi_fragment, container, false);

        textHeight = view.findViewById(R.id.editText1);
        textWeight = view.findViewById(R.id.editText2);

        initHeightSpinner(view);
        initWeightSpinner(view);

        bundle = new Bundle();

        heightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//今回は無名クラス
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                hItem = (String) spinner.getSelectedItem();
            }

            public void onNothingSelected(AdapterView parent) {
            }
        });

        weightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                wItem = (String) spinner.getSelectedItem();

            }

            public void onNothingSelected(AdapterView parent) {
            }
        });

        Button button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String stringHeight = textHeight.getText().toString();
                String stringWeight = textWeight.getText().toString();
                //ボタンを押す前の値チェック
                if (stringHeight.isEmpty() || stringWeight.isEmpty()) {
                    showAlertDialog("入力してください");
                } else if (Double.parseDouble(stringHeight) == 0) {
                    showAlertDialog("0は使用不可です");
                } else if (isDouble(stringHeight) && isDouble(stringWeight)) {
                    bundle.putDouble("HEIGHT_VAL", Double.parseDouble(stringHeight));
                    bundle.putDouble("WEIGHT_VAL", Double.parseDouble(stringWeight));
                    bundle.putString("HEIGHT_ITEM", hItem);
                    bundle.putString("WEIGHT_ITEM", wItem);

                    FragmentManager fmBMI = getFragmentManager();
                    FragmentTransaction ftBMI = fmBMI.beginTransaction();

                    BMIFragmentResult bmiFragmentResult = new BMIFragmentResult();
                    bmiFragmentResult.setArguments(bundle);

                    ftBMI.addToBackStack(null);
                    ftBMI.replace(R.id.container33, bmiFragmentResult).commit();

                } else {
                    showAlertDialog("数値を入力してください、もしくは入力した数値が大きすぎます");
                }
            }
        });

        return view;
    }

    private void initHeightSpinner(View view) {
        heightSpinner = view.findViewById(R.id.heightSpinner);
        String heightItems[] = {"センチ", "フィート", "インチ"};
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, heightItems);
        heightAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        heightSpinner.setAdapter(heightAdapter);
    }

    private void initWeightSpinner(View view) {
        weightSpinner = view.findViewById(R.id.weightSpinner);
        String weightItems[] = {"キログラム", "ポンド"};
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, weightItems);
        weightAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        weightSpinner.setAdapter(weightAdapter);
    }

    private void showAlertDialog(String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(msg);
        dialog.setPositiveButton("入力し直す", null);
        dialog.show();
    }

    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
