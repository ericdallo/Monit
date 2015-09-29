package com.monit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.monit.coordinate.Coordinate;
import com.monit.graph.MonitGraph;
import com.monit.json.MonitJSON;

import java.util.List;

public class MonitActivity extends AppCompatActivity {

    private MonitGraph monitGraph;
    private MonitJSON monitJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monit);

        monitJson = new MonitJSON(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_monit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setGraph(List<Coordinate> data) {
        monitGraph = new MonitGraph(this,"Graphite test",data);

        LinearLayout layout = (LinearLayout) findViewById(R.id.chart_layout);
        layout.addView(monitGraph.getGraph(), 0);
    }
}
