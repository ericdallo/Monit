package com.monit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.monit.R;
import com.monit.coordinate.Coordinate;
import com.monit.graph.MonitGraph;
import com.monit.json.JsonRest;

import java.util.List;

public class GraphFragment extends Fragment {

    private MonitGraph monitGraph;
    private Button btUpdate, btUrl;
    private JsonRest jsonRest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_fragment, container, false);

        jsonRest = new JsonRest(this, false);
        jsonRest.execute();

        btUpdate = (Button) view.findViewById(R.id.bt_update);
        btUpdate.setOnClickListener(v -> {
            jsonRest = new JsonRest(this, true);
            jsonRest.execute();
        });

        btUrl = (Button) view.findViewById(R.id.bt_url);
        btUrl.setOnClickListener(v -> askUrl());

        return view;
    }

    private void askUrl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter the URL");

        final EditText input = new EditText(getActivity());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String urlToJson = input.getText().toString();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });

        builder.show();
    }

    public void refreshGraph(List<Coordinate> data) {
        monitGraph.refresh(data);
    }

    public void setGraph(List<Coordinate> data) {
        monitGraph = new MonitGraph(getView(), data);
        monitGraph.show();
    }
}
