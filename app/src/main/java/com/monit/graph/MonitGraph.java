package com.monit.graph;

import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.monit.MonitActivity;
import com.monit.R;
import com.monit.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class MonitGraph {

    public static final int LINE_WIDTH = 4;
    public static final float CUBIC_INTENSITY = 0.2f;
    private MonitActivity monitActivity;
    private List<Coordinate> coordinates;
    private List<Entry> entries;
    private LineChart lineChart;
    private LineDataSet lineDataSet;
    private LineData lineData;

    public MonitGraph(MonitActivity monitActivity, List<Coordinate> coordinates){
        this.monitActivity = monitActivity;
        this.coordinates = coordinates;
    }

    public void show() {
        lineChart = (LineChart) monitActivity.findViewById(R.id.chart);
        lineChart.getXAxis().setDrawLabels(false);

        entries = parseDataToEntry(coordinates);

        lineDataSet = new LineDataSet(entries,"number of views");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setCubicIntensity(CUBIC_INTENSITY);
        lineDataSet.setLineWidth(LINE_WIDTH);
        //lineDataSet.setDrawCubic(true);

        lineData = new LineData(getYAxis(),lineDataSet);
        lineChart.setData(lineData);
        //lineChart.setDescription("Number of views");

    }

    private List<String> getYAxis() {
        List<String> numbers = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++){
            numbers.add(i+"");
        }
        return numbers;
    }

    private List<Entry> parseDataToEntry(List<Coordinate> coordinates) {
        List<Entry> dataEntries = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++){
            Coordinate coordinate = coordinates.get(i);
            float x = coordinate.x.floatValue();

            dataEntries.add( new Entry(x,i) );
        }
        return dataEntries;
    }

    public void refresh(List<Coordinate> data) {
        lineDataSet.clear();

        entries = parseDataToEntry(data);

        for (int i = 0; i < data.size(); i++){
            lineDataSet.addEntry(entries.get(i));
        }
        lineData = new LineData(getYAxis(),lineDataSet);
        lineChart.setData(lineData);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }
}
