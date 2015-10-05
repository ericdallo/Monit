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
import android.widget.EditText;

import com.github.clans.fab.FloatingActionButton;
import com.monit.R;
import com.monit.async.AsyncGraph;
import com.monit.async.AsyncResponse;
import com.monit.configuration.MonitConfig;
import com.monit.coordinate.Coordinate;
import com.monit.graph.MonitGraph;
import com.monit.json.JsonRest;

import java.util.List;

public class GraphFragment extends Fragment implements AsyncResponse {

    public static final String HTTP_URL_INIT = "http://";
    public static final int URL = 0;
    public static final int TARGET = 1;
    private MonitGraph monitGraph;
    private FloatingActionButton btUpdate, btUrl, btTarget;
    private JsonRest jsonRest;
    private View parent;
    private AsyncGraph asyncGraph;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_fragment, container, false);
        this.parent = ((View)container.getParent());
        this.asyncGraph = new AsyncGraph(this);

        jsonRest = new JsonRest(this, false);
        jsonRest.execute();

        btUpdate = (FloatingActionButton) parent.findViewById(R.id.bt_update);
        btUpdate.setOnClickListener(v -> {
            if (MonitConfig.snackbarInfo != null) {
                MonitConfig.snackbarInfo.dismiss();
            }
            MonitConfig.isUpdated = true;
            jsonRest = new JsonRest(this, true);
            jsonRest.execute();
        });

        btUrl = (FloatingActionButton) parent.findViewById(R.id.bt_url);
        btUrl.setOnClickListener(v -> ask(URL));

        btTarget = (FloatingActionButton) parent.findViewById(R.id.bt_target);
        btTarget.setOnClickListener(v -> ask(TARGET));

        return view;
    }

    private void ask(int option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
        builder.setView(input);

        switch (option){
            case URL:
                builder.setTitle("Enter the URL");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    String newUrl = input.getText().toString();
                    if (!newUrl.contains(HTTP_URL_INIT)) {
                        newUrl = HTTP_URL_INIT.concat(newUrl);
                    }
                    MonitConfig.setBaseUrl(newUrl);
                    jsonRest = new JsonRest(this, true);
                    jsonRest.execute();

                });
                break;
            case TARGET:
                builder.setTitle("Enter the graph target");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    String target = input.getText().toString();

                    MonitConfig.setTarget(target);
                    jsonRest = new JsonRest(this, true);
                    jsonRest.execute();

                });
                break;
        }

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });

        builder.show();
    }

    private void refreshGraph(List<Coordinate> data) {
        if (MonitConfig.isAutoRefresh()){
            this.asyncGraph = new AsyncGraph(this);
            asyncGraph.start();
        }else{
            asyncGraph.stop();
        }
        monitGraph.refresh(data);

    }

    private void setGraph(List<Coordinate> data) {
        monitGraph = new MonitGraph(getView(), data);
        monitGraph.show();
    }

    @Override
    public void showErrorMsg() {
        MonitConfig.rollback();
        Snackbar.make(parent,R.string.unknow_host,Snackbar.LENGTH_SHORT).show();
        jsonRest = new JsonRest(this, true);
        jsonRest.execute();
    }

    @Override
    public void processFinish(List<Coordinate> coordinates, boolean refresh) {
        if (refresh){
            refreshGraph(coordinates);
        }else{
            setGraph(coordinates);
        }
    }
}
