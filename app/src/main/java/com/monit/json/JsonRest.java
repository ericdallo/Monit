package com.monit.json;

import android.os.AsyncTask;

import com.monit.api.ApiMethods;
import com.monit.coordinate.Coordinate;
import com.monit.fragment.GraphFragment;

import java.util.List;

import retrofit.RestAdapter;

public class JsonRest extends AsyncTask<Void,Void,List<CoordinateJson>> {

    private final static String API_URL = "http://54.68.38.254";
    private final static String TARGET = "summarize(stats.teste,'1min','avg')";
    private final static String FORMAT = "json";
    private final static String FROM = "-30 min";
    private RestAdapter restAdapter;
    private boolean refresh;
    private GraphFragment graphFragment;

    public JsonRest(GraphFragment graphFragment, boolean refresh) {
        this.graphFragment = graphFragment;
        this.refresh = refresh;
    }

    @Override
    protected void onPreExecute() {

        this.restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(API_URL)
                .build();
    }

    @Override
    protected List<CoordinateJson> doInBackground(Void... params) {
        ApiMethods apiMethods = restAdapter.create(ApiMethods.class);
        List<CoordinateJson> coordinateJsons =  apiMethods.getCoordinates(TARGET,FORMAT,FROM);
        return coordinateJsons;
    }

    @Override
    protected void onPostExecute(List<CoordinateJson> coordinateJson) {

        List<Coordinate> coordinates = coordinateJson.get(0).getData();

        if (refresh){
            graphFragment.refreshGraph(coordinates);
        }else {
            graphFragment.setGraph(coordinates);
        }

    }
}
