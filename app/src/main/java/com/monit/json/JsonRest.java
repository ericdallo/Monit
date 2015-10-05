package com.monit.json;

import android.os.AsyncTask;
import android.util.Log;

import com.monit.R;
import com.monit.api.ApiMethods;
import com.monit.async.AsyncResponse;
import com.monit.configuration.MonitConfig;
import com.monit.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class JsonRest extends AsyncTask<Void,Void,List<CoordinateJson>> {

    private RestAdapter restAdapter;
    private boolean refresh;
    private AsyncResponse delegate;

    public JsonRest(AsyncResponse delegate, boolean refresh) {
        this.delegate = delegate;
        this.refresh = refresh;
    }

    @Override
    protected void onPreExecute() {

        this.restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(MonitConfig.getBaseUrl())
                .build();
    }

    @Override
    protected List<CoordinateJson> doInBackground(Void... params) {
        ApiMethods apiMethods = restAdapter.create(ApiMethods.class);
        List<CoordinateJson> coordinates;
        try{
            coordinates = apiMethods.getCoordinates(
                    MonitConfig.getTarget(),
                    MonitConfig.getFormat(),
                    MonitConfig.getFrom()
            );
        }catch (RetrofitError error){
            Log.i(String.valueOf(R.string.error_label), String.valueOf(R.string.unknow_host));
            delegate.showErrorMsg();
            coordinates = new ArrayList<>();
        }
        return coordinates;
    }

    @Override
    protected void onPostExecute(List<CoordinateJson> coordinateJson) {
        //TODO make a better validation
        if (coordinateJson.size() == 0){
            return;
        }
        List<Coordinate> coordinates = coordinateJson.get(0).getData();

        delegate.processFinish(coordinates, refresh);
    }
}
