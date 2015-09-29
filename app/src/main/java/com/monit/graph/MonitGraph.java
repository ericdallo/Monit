package com.monit.graph;

import android.content.Context;
import android.graphics.Color;

import com.monit.coordinate.Coordinate;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

public class MonitGraph {

    private XYSeries series;
    private Context context;
    private List<Coordinate> coordinates;

    public MonitGraph(Context context, String name, List<Coordinate> coordinates){
        this.context = context;
        this.coordinates = coordinates;
        series = new XYSeries(name);
    }

    public GraphicalView getGraph() {

        for(int i =0;i < coordinates.size() ; i++){
            series.add(coordinates.get(i).x, coordinates.get(i).y);
        }

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        dataset.addSeries(series);

        XYSeriesRenderer renderer = new XYSeriesRenderer();
        renderer.setLineWidth(2);
        renderer.setColor(Color.BLUE);
        renderer.setPointStyle(PointStyle.SQUARE);
        renderer.setFillPoints(true);
        //renderer.setDisplayBoundingPoints(true);
        //renderer.setPointStrokeWidth(3);

        XYMultipleSeriesRenderer multipleRenderer = new XYMultipleSeriesRenderer();
        multipleRenderer.addSeriesRenderer(renderer);

        multipleRenderer.setZoomButtonsVisible(true);
        multipleRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
        multipleRenderer.setYLabels(10);
        multipleRenderer.setXLabels(5);
        multipleRenderer.setPanEnabled(false, false);
        multipleRenderer.setChartTitle("Graphite test");
        multipleRenderer.setShowGrid(true); // we show the grid

        return ChartFactory.getLineChartView(context, dataset, multipleRenderer);
    }
}
