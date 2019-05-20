package com.grp;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChartToFile
{

    public enum Part{PERFORMANCE, INPUT, APPLICATION, ALL};
    JFreeChart myChart;

    /**
     *
     * @param myChart JFreeChart类型
     */
    public ChartToFile(JFreeChart myChart) // 请使用自己生成的JFreeChart变量进行初始化
    {
        this.myChart = myChart;
    }

    private static String nowDate(ChooseDay day) // 返回的格式为yyyyMMdd
    {
        String date = "";
        if (day == ChooseDay.YESTERDAY)
        {
            Date nowDate = new Date();
            Date beforeDate = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);

            calendar.add(Calendar.DAY_OF_MONTH, -1); // 向前回溯一天
            beforeDate = calendar.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            date += sdf.format(beforeDate);
        }
        else if (day == ChooseDay.TODAY)
        {
            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            date += sdf.format(nowDate);
        }

        return date;
    }


    /**
     *
     * @param part 图片所属的部分 PERFORMANCE INPUT APPLICATION ALL
     * @param width 输出图片的宽度
     * @param height 输出图片的高度
     */
    public void toPNG(Part part, int width, int height)
    {
        String tail = "";
        String dir = "statistics\\images\\";
        String file = dir + nowDate(ChooseDay.YESTERDAY);

        switch (part)
        {
            case PERFORMANCE: tail = "_performance.png"; break;
            case INPUT: tail = "_input.png"; break;
            case APPLICATION: tail = "_application.png"; break;
            case ALL: tail = ".png"; break;
            default: break;
        }

        file += tail;

        try {
        OutputStream os = new FileOutputStream(file);
        ChartUtilities.writeChartAsJPEG(os, myChart, width, height);
        //使用一个面向application的工具类，将chart转换成PNG格式的图片。第3个参数是宽度，第4个参数是高度
        os.close();
        }
        catch (FileNotFoundException e) {
        e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
