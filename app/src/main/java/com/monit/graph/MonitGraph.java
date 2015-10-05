package com.monit.graph;

import android.graphics.Color;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.monit.R;
import com.monit.coordinate.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class MonitGraph {

    public static final int LINE_WIDTH = 4;
    public static final float CUBIC_INTENSITY = 0.2f;
    private View view;
    private List<Coordinate> coordinates;
    private List<Entry> entries;
    private LineChart lineChart;
    private LineDataSet lineDataSet;
    private LineData lineData;


    public MonitGraph(View view, List<Coordinate> coordinates){
        this.view = view;
        this.coordinates = coordinates;
    }

    public void show() {
        lineChart = (LineChart) view.findViewById(R.id.chart);
        lineChart.animateX(1000);
        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getLegend().setTextSize(14);
        lineChart.getAxisLeft().setTextSize(12);
        entries = parseDataToEntry(coordinates);
        lineChart.setDescription("Monit Test");

        lineDataSet = new LineDataSet(entries,"number of views");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setCubicIntensity(CUBIC_INTENSITY);
        lineDataSet.setLineWidth(LINE_WIDTH);
        lineDataSet.setValueTextSize(0.5f);

        //lineDataSet.setDrawCubic(true);

        lineData = new LineData(getXAxis(),lineDataSet);
        lineChart.setData(lineData);
    }

    private List<String> getXAxis() {
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
        this.coordinates = data;
        lineDataSet.clear();
        entries = parseDataToEntry(data);

        for (int i = 0; i < data.size(); i++){
            lineDataSet.addEntry(entries.get(i));
        }
        lineData = new LineData(getXAxis(),lineDataSet);

        lineChart.setData(lineData);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }
}
