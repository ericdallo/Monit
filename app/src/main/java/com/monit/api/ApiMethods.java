package com.monit.api;


import com.monit.json.CoordinateJson;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiMethods {

    @GET("/render")
    List<CoordinateJson> getCoordinates(
            @Query("target") String target,
            @Query("format") String format,
            @Query("from") String from
    );
}
