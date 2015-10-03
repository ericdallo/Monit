package com.monit.json;

import com.google.gson.annotations.SerializedName;
import com.monit.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class CoordinateJson {

    @SerializedName("datapoints")
    private List< List<String> > datapoints;

    public List<Coordinate> getData(){
        List<Coordinate> coordinates = new ArrayList<>();

        for (int i = 0; i < datapoints.size(); i++){
            Double doubleX = 0.0;
            if(datapoints.get(i).get(0) != null) {
                doubleX  = Double.parseDouble(datapoints.get(i).get(0));
            }
            Coordinate coordinate = new Coordinate(doubleX,new Long(i + 1));
            coordinates.add(coordinate);
        }
        return coordinates;
    }
}
