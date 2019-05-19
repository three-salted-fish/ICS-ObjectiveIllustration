package grp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzw.data.Application;
import com.zzw.data.Input;
import com.zzw.data.Performance;
import com.zzw.tools.io.OkTextReader;

import java.awt.*;
import java.io.File;
import java.lang.reflect.Type;
import java.security.cert.PolicyNode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

// GRP NEED
import java.util.Date;
import java.util.Calendar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.*;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class PerformanceIllustration
{
    public static void main(String[] args)
    {
        String dir = getDir(DirMode.TEST); //"statistics\\data\\20190517";
        // 通过参数的不同取值，决定取到的文件目录是测试用的还是实际上前一天的
        // System.out.println(dir);

        OkTextReader reader = new OkTextReader();
        Gson gson = new Gson();

        System.out.println("\nperformance point data:");
        List<Performance> performanceData = readPerformanceData(dir, reader, gson);
        List<PerformancePoint> Points = getPoint(performanceData);

        /*for (Performance performance : performanceData) {
            System.out.println(performance.toString());
        }*/

        for (PerformancePoint point : Points) // 进行测试，测试是否得到了
        {
            System.out.println(point.toString());
        }

        DrawPerformanceLineChart(Points);

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

    private static void DrawPerformanceLineChart(List<PerformancePoint> Points)
    {
        StandardChartTheme myChartTheme = new StandardChartTheme("CN");
        // 设置主题样式
        myChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
        // 设置标题的字体
        myChartTheme.setRegularFont(new Font("楷体", Font.PLAIN, 15));
        // 设置图例的字体
        // PLAIN为普通样式 BLOD为加粗

        ChartFactory.setChartTheme(myChartTheme);
        XYSeriesCollection myCollection = setCollection(Points);

        ChartFactory.setChartTheme(myChartTheme);
        CategoryDataset myDataset = setDataset(Points);

        /*JFreeChart myChart = ChartFactory.createLineChart(
                "Performance",
                "Hour",
                "Engagement/Efficiency",
                myDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        CategoryPlot myPlot = (CategoryPlot)myChart.getPlot();
        myPlot.setBackgroundPaint(Color.WHITE);
        myPlot.setRangeGridlinePaint(Color.WHITE);
        myPlot.setOutlinePaint(Color.WHITE);*/

        JFreeChart myChart = ChartFactory.createXYLineChart(
                "Performance",
                "Hour",
                "Engagement/Efficiency",
                myCollection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        // CategoryPlot myPlot = myChart.getCategoryPlot();
        // myPlot.setBackgroundPaint(Color.WHITE);
        myChart.setBackgroundPaint(ChartColor.WHITE);


        ChartFrame myChartFrame = new ChartFrame("折线图", myChart);
        myChartFrame.pack();
        myChartFrame.setVisible(true);





        return;
    }

    private static CategoryDataset setDataset(List<PerformancePoint> Points)
    {
        DefaultCategoryDataset myDataset = new DefaultCategoryDataset();
        for (PerformancePoint point : Points)
        {
            /*if (point.getHour_x() == 2)
                continue;*/
            myDataset.addValue(point.getEngagement_y(), "Engagement", String.valueOf(point.getHour_x()));
            myDataset.addValue(point.getEfficiency_y(), "Efficiency", String.valueOf(point.getHour_x()));
        }

        return myDataset;
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

    /*private static String nowDate(ChooseDay day)
    {

    }*/
}
