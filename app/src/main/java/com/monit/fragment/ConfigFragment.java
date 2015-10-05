package com.monit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.monit.R;
import com.monit.configuration.MonitConfig;
import com.monit.json.JsonRest;
import com.monit.util.Util;

public class ConfigFragment extends Fragment {

    private Switch switchRefresh;
    private Spinner spinnerRefreshTime, spinnerRelativeTime, spinnerRelativeType;
    private View fragmentView;
    private int spinnerCount = 0;
    private Button btGraphTarget;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.config_fragment, container, false);

        switchRefresh = (Switch) fragmentView.findViewById(R.id.switch_auto_refresh);
        switchRefresh.setOnCheckedChangeListener((button,isChecked) -> {
            spinnerRefreshTime.setEnabled(isChecked);
            MonitConfig.setAutoRefresh(isChecked);
            showMsg();
        });

        spinnerRefreshTime = createSpinner(fragmentView, R.id.spinner_time, R.array.time);
        spinnerRefreshTime.setEnabled(MonitConfig.isAutoRefresh());

        spinnerRelativeTime = createSpinner(fragmentView, R.id.spinner_relative_number, R.array.relative_time);
        spinnerRelativeType = createSpinner(fragmentView, R.id.spinner_relative_type, R.array.relative_type);

        btGraphTarget = (Button) fragmentView.findViewById(R.id.bt_graph_target);
        btGraphTarget.setOnClickListener(v -> askTarget());

        return fragmentView;
    }

    private void askTarget() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.graph_target_parameter));

        final EditText input = new EditText(getActivity());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newTarget = input.getText().toString();

            MonitConfig.setTarget(newTarget);
            showMsg();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });

        builder.show();
    }

    private Spinner createSpinner(View view, int spinnerId, int array) {
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        ArrayAdapter<CharSequence> items = ArrayAdapter.createFromResource(view.getContext(),array,R.layout.spinner_item);
        items.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(items);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerCount > 3){
                    updateRefreshTime(spinnerRefreshTime.getSelectedItem().toString());

                    String itemType = spinnerRelativeType.getSelectedItem().toString();
                    String itemTime = spinnerRelativeTime.getSelectedItem().toString();

                    MonitConfig.setFrom(itemTime, itemType);
                    showMsg();
                }
                spinnerCount++;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        return spinner;
    }

    private void showMsg() {
        if (MonitConfig.isUpdated) {
            MonitConfig.snackbarInfo = Snackbar.make(fragmentView, R.string.update_graph_msg, Snackbar.LENGTH_INDEFINITE);
            MonitConfig.snackbarInfo.show();
            MonitConfig.isUpdated = false;
        }

    }

    private void updateRefreshTime(String item) {
        String[] splitNumber = item.split("\\W[a-z]+");
        String[] splitTime = item.split("[0-9]+\\W");

        String number = Util.getStringFromArray(splitNumber);
        String time = Util.getStringFromArray(splitTime);

        MonitConfig.setTimeRefresh(number, time);
    }
}
