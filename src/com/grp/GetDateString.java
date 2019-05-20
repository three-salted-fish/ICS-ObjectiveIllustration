package com.grp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDateString {

    ChooseDay day;

    public static void main(String[] args){

        GetDateString getString = new GetDateString(ChooseDay.TEST_DAY);
        System.out.println(getString.toString());

        getString.setDay(ChooseDay.YESTERDAY);
        System.out.println(getString.toString());

        return;
    }

    /**
     * 初始化，功能类似setDay
     * @param day 传入的参数决定了获取时间的模式，TEST_DAY代表了测试文件中的20190517
     */
    public GetDateString(ChooseDay day){
        this.day = day;
    }

    /**
     * 设定日期，可以是昨天，今天或者测试模式
     * @param day 传入的参数决定了获取时间的模式，TEST_DAY代表了测试文件中的20190517
     */
    public void setDay(ChooseDay day){
        this.day = day;
    }

    /**
     * 重载了toString函数，将选定的日期转换为String类型返回
     * @return 返回值为设定的天的String格式yyyyMMdd
     */
    @Override
    public String toString(){
        String dateString = "";

        if (day == ChooseDay.TEST_DAY)
            dateString = "20190517";
        else if (day == ChooseDay.TODAY){
            Date nowDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            dateString = sdf.format(nowDate);
        }
        else if (day == ChooseDay.YESTERDAY){
            Date nowDate = new Date();
            Date beforeDate = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowDate);

            calendar.add(Calendar.DAY_OF_MONTH, -1); // 向前回溯一天
            beforeDate = calendar.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            dateString = sdf.format(beforeDate);
        }

        return dateString;
    }
}
