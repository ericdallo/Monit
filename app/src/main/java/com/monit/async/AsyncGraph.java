package com.monit.async;

import android.os.Handler;

import com.monit.configuration.MonitConfig;
import com.monit.json.JsonRest;

public class AsyncGraph {

    public static Runnable runnable;
    public static Handler handler = new Handler();
    public static boolean isRunning = false;

    public AsyncGraph(AsyncResponse asyncResponse) {
        runnable = new Runnable() {
            @Override
            public void run() {
                JsonRest jsonRest = new JsonRest(asyncResponse, true);
                jsonRest.execute();
                handler.postDelayed(this, MonitConfig.getTimeRefresh());
            }
        };
    }

    public void start() {
        if (!isRunning){
            handler = new Handler();
            handler.post(runnable);
            isRunning = true;
        }
    }

    public void stop() {
        isRunning = false;
        handler.removeCallbacks(runnable);
    }

    public static void comeback() {
        handler.post(runnable);
    }
}
