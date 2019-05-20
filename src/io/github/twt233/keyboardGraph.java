package io.github.twt233;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzw.data.Input;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class keyboardGraph {

    private CategoryDataset dataSet;
    private JFreeChart chart;

    public keyboardGraph(String filePath) throws IOException {
        StringBuilder json = new StringBuilder();
        BufferedReader buf = new BufferedReader(new FileReader(filePath));
        String temp;
        while ((temp = buf.readLine()) != null) json.append(temp);
        List<Input> inputData = new Gson().fromJson(json.toString(), new TypeToken<ArrayList<Input>>() {
        }.getType());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        for (Input inputDatum : inputData) {
            temp = sdf.format(inputDatum.getBeginTime());
            dataset.addValue(inputDatum.getKeystrokeNum(), "Key", temp);
            dataset.addValue(inputDatum.getMouseClickNum(), "Mouse", temp);
        }
        dataSet = dataset;

        chart = ChartFactory.createBarChart("input Data", "Time", "Frequency",
                dataSet, PlotOrientation.VERTICAL, true, true, false);
        CategoryItemRenderer render = chart.getCategoryPlot().getRenderer();

        // GRP ADD
        CategoryPlot myPlot = (CategoryPlot)chart.getPlot();
        myPlot.setBackgroundPaint(Color.WHITE);
        // chart.setBackgroundPaint(new Color(0xffffff));
        render.setSeriesPaint(0, new Color(0xDD9225));
        render.setSeriesPaint(1, new Color(0x21598F));
    }


    public CategoryDataset getDataSet() {
        return dataSet;
    }

    public void setDataSet(CategoryDataset dataSet) {
        this.dataSet = dataSet;
    }

    public JFreeChart getChart() {
        return chart;
    }

    public void setChart(JFreeChart chart) {
        this.chart = chart;
    }


}
