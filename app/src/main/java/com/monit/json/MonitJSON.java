package com.monit.json;

import android.os.AsyncTask;

import com.monit.MonitActivity;
import com.monit.coordinate.Coordinate;
import com.monit.rest.JSONRest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MonitJSON extends AsyncTask<Void,Void,Void> {

    private static final String DATAPOINTS = "datapoints";
    private List<Coordinate> data;
    private MonitActivity monitActivity;
    private boolean refresh;

    public MonitJSON(MonitActivity monitActivity) {
        this.monitActivity = monitActivity;
        this.data = new ArrayList<>();
        this.refresh = false;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String json = new JSONRest().getJson();

        try {
            JSONArray root = new JSONArray(json);

            JSONObject object = root.getJSONObject(0);

            JSONArray datapoints = object.optJSONArray(DATAPOINTS);

            for (int i = 0; i < datapoints.length(); i++) {
                JSONArray coordinateArray = datapoints.getJSONArray(i);

                Long x = coordinateArray.get(0).toString().equals("null") ? 0 : coordinateArray.getLong(0);
                Long y = coordinateArray.get(1).toString().equals("null") ? 0 : coordinateArray.getLong(1);

                Coordinate coordinate = new Coordinate(x, y);
                this.data.add(coordinate);
            }

        } catch (JSONException e) {
            throw new RuntimeException("Erro ao realizar a conversão do JSON", e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (refresh){
            monitActivity.refreshGraph(this.data);
        }else {
            monitActivity.setGraph(this.data);
        }

    }

    public void refreshData() {
        this.data = new ArrayList<>();
        this.refresh = true;
        execute();
    }
}
