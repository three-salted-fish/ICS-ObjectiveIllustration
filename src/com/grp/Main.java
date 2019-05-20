package com.grp;

import grp.PerformanceIllustration;
import org.jfree.chart.JFreeChart;
import io.github.twt233.keyboardGraph;
import xjm.Performpiechart;

public class Main
{
    public static void main(String[] args) throws Exception {
        PerformanceIllustration performanceChart = new PerformanceIllustration();
        JFreeChart per_chart = performanceChart.main(null);
        ChartToFile per_chart2file = new ChartToFile(per_chart);
        per_chart2file.toPNG(ChartToFile.Part.PERFORMANCE, 1600, 400);
        // 生成Performance的图表并且写出到文件

        keyboardGraph keyboardChart = new keyboardGraph("statistics/data/20190517/input.json");
        keyboardChart.main(null);
        JFreeChart key_chart = keyboardChart.getChart();
        ChartToFile key_chart2file = new ChartToFile(key_chart);
        key_chart2file.toPNG(ChartToFile.Part.INPUT, 1200, 600);

        Performpiechart performpiecChart = new Performpiechart();
        JFreeChart app_chart = performpiecChart.main(null);
        ChartToFile app_chart2file = new ChartToFile(app_chart);
        app_chart2file.toPNG(ChartToFile.Part.APPLICATION, 600, 400);
    }
}
