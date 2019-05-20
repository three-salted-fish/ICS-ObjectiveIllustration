package grp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grp.ChartToFile;
import com.zzw.data.Performance;
import com.zzw.tools.io.OkTextReader;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// GRP NEED
import java.util.Date;
import java.util.Calendar;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.LegendTitle;
import org.jfree.ui.RectangleEdge;

import java.lang.Exception;

/**
 * @author GRP
 */
public class PerformanceIllustration
{
    public static JFreeChart main(DirMode mode) throws Exception
    {
        String dir = getDir(mode); //"statistics\\data\\20190517";
        // 通过参数的不同取值，决定取到的文件目录是测试用的还是实际上前一天的
        // System.out.println(dir);

        OkTextReader reader = new OkTextReader();
        Gson gson = new Gson();

        // System.out.println("\nperformance point data:");
        List<Performance> performanceData = readPerformanceData(dir, reader, gson);
        List<PerformancePoint> Points = getPoint(performanceData);

        JFreeChart myChart = DrawPerformanceLineChart(Points);

        return myChart;

    }
    private static List<Performance> readPerformanceData(String dir, OkTextReader reader, Gson gson)
            // 这段代码来自zzw学长，独立处理以便自定义或者代码的解耦，但是略微不利于维护
    {
        reader.open(dir + File.separator + "performance.json"); // 中间加入分隔符
        String json = reader.readLine();
        reader.close();

        Type type = new TypeToken<ArrayList<Performance>>(){}.getType();
        List<Performance> performanceData = gson.fromJson(json, type);
        return performanceData;
    }



    private static String getDir(DirMode mode)
    {
        String dir = "statistics\\data\\";
        if (mode == DirMode.TEST)
        {
            dir += "20190517";
        }

        else if (mode == DirMode.RUN)
        {
            Date nowDate = new Date();
            Date beforeDate = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);

            calendar.add(Calendar.DAY_OF_MONTH, -1); // 向前回溯一天
            beforeDate = calendar.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            dir += sdf.format(beforeDate);
        }
        return dir;
    }

    private static List<PerformancePoint> getPoint(List<Performance> performances)
    {
        List<PerformancePoint> pointList = new ArrayList<PerformancePoint>();
        for (Performance performance : performances)
        {
            PerformancePoint performancePoint = new PerformancePoint(performance);
            pointList.add(performancePoint);
        }
        return pointList;
    }

    private static JFreeChart DrawPerformanceLineChart(List<PerformancePoint> Points)
    {
        StandardChartTheme myChartTheme = new StandardChartTheme("EN");
        // 设置主题样式
        myChartTheme.setExtraLargeFont(new Font("Arial", Font.BOLD, 20));
        // 设置标题的字体
        myChartTheme.setRegularFont(new Font("Arial", Font.BOLD, 15));
        // 设置图例的字体
        // PLAIN为普通样式 BLOD为加粗

        ChartFactory.setChartTheme(myChartTheme);
        XYSeriesCollection myCollection = setCollection(Points);

        ChartFactory.setChartTheme(myChartTheme);

        JFreeChart myChart = ChartFactory.createXYLineChart(
                "Performance",
                "Hour",
                "Engagement/Efficiency",
                myCollection,
                PlotOrientation.VERTICAL,
                true,
                false,
                false
        );
        LegendTitle legend = myChart.getLegend();
        legend.setPosition(RectangleEdge.RIGHT);
        // 设置图例

        XYPlot plot = (XYPlot)myChart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        plot.setBackgroundAlpha(0f);
        // 前景色 透明度
        plot.setForegroundAlpha(0.8f);
        plot.setOutlinePaint(Color.WHITE);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        /**
         * 调色部分
         * @author YMY
         */
        Color color1 = new Color(0,191,255);
        Color color2 = new Color(254,206,48);
        renderer.setSeriesPaint( 0 , color1);
        renderer.setSeriesPaint( 1 , color2);
        // renderer.setSeriesPaint( 2 , Color.YELLOW );
        renderer.setSeriesStroke( 0 , new BasicStroke( 1.8f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 1.8f ) );
        // renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
        plot.setRenderer(renderer);

        return myChart;
    }

    private static XYSeriesCollection setCollection(List<PerformancePoint> Points)
    {
        XYSeriesCollection myCollection = new XYSeriesCollection();
        XYSeries mySeriesEngagement = new XYSeries("Engagement");
        XYSeries mySeriesEfficiency = new XYSeries("Efficiency");

        for (PerformancePoint point : Points)
        {
            mySeriesEfficiency.add(point.getHour_x(), point.getEfficiency_y());
            mySeriesEngagement.add(point.getHour_x(), point.getEngagement_y());
        }

        myCollection.addSeries(mySeriesEfficiency);
        myCollection.addSeries(mySeriesEngagement);

        return myCollection;
    }
}
