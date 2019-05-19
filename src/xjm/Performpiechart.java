package xjm;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.io.*;

import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.*;

import org.jfree.chart.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.util.Date;
import java.util.Calendar;
public class Performpiechart {
    public static void main(String[] args)
    {
        String dir = "statistics\\data\\20190517";
        OkTextReader reader = new OkTextReader();
        Gson gson = new Gson();

        System.out.println("\napplication data:");
        Application ApplicationData = readApplicationData(dir, reader, gson);
        System.out.println(ApplicationData.toString());


       DrawApplicationPieChart(ApplicationData);
        // 写图表对象到文件，参照柱状图生成源码
    }
    private static Application readApplicationData(String dir, OkTextReader reader, Gson gson)
    {
        reader.open(dir + File.separator + "application.json"); // 中间加入分隔符
        String json = reader.readLine();
        reader.close();

        Application ApplicationData = gson.fromJson(json, Application.class);
        return ApplicationData;
    }
    private static DefaultPieDataset getDataSet(Application ApplicationData) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("IDE",ApplicationData.getIdeTime()/60000);
        dataset.setValue("Browser",ApplicationData.getWebTime()/60000);
        dataset.setValue("Document",ApplicationData.getDocumentTime()/60000);
        dataset.setValue("Message",ApplicationData.getMessageTime()/60000);
        dataset.setValue("Others",ApplicationData.getOtherTime()/60000);
        return dataset;
    }
    private static void DrawApplicationPieChart(Application ApplicationData)
    {
        StandardChartTheme myChartTheme = new StandardChartTheme("CN");
        // 设置主题样式
        myChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
        // 设置标题的字体
        myChartTheme.setRegularFont(new Font("楷体", Font.PLAIN, 15));
        // 设置图例的字体
        // PLAIN为普通样式 BLOD为加粗

        ChartFactory.setChartTheme(myChartTheme);
        DefaultPieDataset data = getDataSet(ApplicationData);

        JFreeChart chart = ChartFactory.createPieChart3D(
                "time used yesterday(min)",  // 图表标题
                data,
                true, // 是否显示图例
                false,
                false
        );
        PiePlot plot = (PiePlot) chart.getPlot();
        Color color1=new Color(240,48,48);
        Color color2=new Color(255,240,16);
        Color color3=new Color(238,64,0);
        Color color4=new Color(141,238,238);
        Color color5=new Color(137,104,205);
        //设置每个块的颜色
        plot.setSectionOutlinesVisible(false);
        plot.setNoDataMessage("没有可供使用的数据！");
        plot.setSectionPaint("IDE", color1);
        plot.setSectionPaint("Browser", color2);
        plot.setSectionPaint("Document", color3);
        plot.setSectionPaint("Message", color4);
        plot.setSectionPaint("Others", color5);
        plot.setBackgroundAlpha(0.0f);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{1}min({2})", NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));
        //显示时间占比
        ChartFrame PieFrame = new ChartFrame("昨日采集数据反馈报告", chart);
        PieFrame.pack();
        PieFrame.setVisible(true);


        return;
    }

}
