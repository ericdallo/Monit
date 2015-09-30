package com.monit;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.monit.coordinate.Coordinate;
import com.monit.graph.MonitGraph;
import com.monit.json.MonitJSON;

import java.util.List;

public class MonitActivity extends AppCompatActivity {

    private MonitGraph monitGraph;
    private Button btUpdate,btUrl;
    private MonitJSON monitJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monit);
        
        monitJSON = new MonitJSON(this);
        monitJSON.execute();

        btUpdate = (Button) findViewById(R.id.bt_update);
        btUpdate.setOnClickListener(v -> {
            monitJSON = new MonitJSON(this);
            monitJSON.refreshData();
        });

        btUrl= (Button) findViewById(R.id.bt_url);
        btUrl.setOnClickListener(v -> askUrl());
    }

    private void askUrl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the URL");

        final EditText input = new EditText(this);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_monit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.update_monit) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshGraph(List<Coordinate> data) {
        monitGraph.refresh(data);
    }

    public void setGraph(List<Coordinate> data) {
        monitGraph = new MonitGraph(this,data);
        monitGraph.show();
    }
}
