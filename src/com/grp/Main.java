package com.grp;

import grp.DirMode;
import grp.PerformanceIllustration;
import io.github.twt233.inputGraph;
import org.jfree.chart.JFreeChart;
import xjm.Performpiechart;
import io.github.twt233.mergedChart;

/**
 * @author GRP
 */
public class Main
{
    public static void main(String[] args) throws Exception {
        PerformanceIllustration performanceChart = new PerformanceIllustration();
        JFreeChart per_chart = performanceChart.main(DirMode.TEST); // 如果为TEST则为测试目录20190517，如果为RUN则为前一天的文件夹
        ChartToFile per_chart2file = new ChartToFile(per_chart);
        per_chart2file.toPNG(ChartToFile.Part.PERFORMANCE, 1800, 400);
        // 生成Performance的图表并且写出到文件

        inputGraph keyboardChart = new inputGraph("statistics/data/20190517/input.json"); // 可以通过调用com.grp.GetDateString来获得中间日期的字符串
        // keyboardChart.main(null);
        JFreeChart key_chart = keyboardChart.getChart();
        ChartToFile key_chart2file = new ChartToFile(key_chart);
        key_chart2file.toPNG(ChartToFile.Part.INPUT, 1200, 600);

        Performpiechart performpiecChart = new Performpiechart();
        JFreeChart app_chart = performpiecChart.main(null);
        ChartToFile app_chart2file = new ChartToFile(app_chart);
        app_chart2file.toPNG(ChartToFile.Part.APPLICATION, 600, 400);

        GetDateString yesterday = new GetDateString(ChooseDay.YESTERDAY);
        mergedChart ourChart = new mergedChart(yesterday.toString());
        ourChart.writeGraph("statistics/images/" + yesterday + ".png");
    }
}
