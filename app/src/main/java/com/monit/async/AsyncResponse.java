package com.monit.async;

import com.monit.coordinate.Coordinate;

import java.util.List;

public interface AsyncResponse {

    void processFinish(List<Coordinate> coordinates, boolean refresh);

    void showErrorMsg();
}
