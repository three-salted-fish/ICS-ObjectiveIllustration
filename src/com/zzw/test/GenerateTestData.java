package com.zzw.test;

import com.google.gson.Gson;
import com.zzw.data.Application;
import com.zzw.data.Input;
import com.zzw.data.Performance;
import com.zzw.tools.io.OkTextWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class GenerateTestData {
    public static void main(String[] args) {
        String dir = "statistics\\data\\20190517";
        OkTextWriter writer = new OkTextWriter();
        Gson gson = new Gson();

        writer.open(dir + File.separator + "performance.json");
        List<Performance> performanceData = generatePerformanceData();
        String performanceJson = gson.toJson(performanceData);
        writer.println(performanceJson);
        writer.close();

        writer.open(dir + File.separator + "application.json");
        Application application = generateApplicationData();
        String appJson = gson.toJson(application);
        writer.println(appJson);
        writer.close();

        writer.open(dir + File.separator + "input.json");
        List<Input> inputData = generateInputData();
        String inputJson = gson.toJson(inputData);
        writer.println(inputJson);
        writer.close();
    }

    private static List<Performance> generatePerformanceData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long time = calendar.getTimeInMillis();

        long intervalTime = 60 * 60 * 1000;
        int perturbation = 10 * 60 * 1000;
        Random timeRandom = new Random();
        Random engagementRandom = new Random();
        Random efficiencyRandom = new Random();

        List<Performance> result = new ArrayList<>();
        for (long t = time; t < time + 24 * intervalTime; t += intervalTime) {
            t += timeRandom.nextInt(perturbation);
            int engagement = engagementRandom.nextInt(7) + 1;
            int efficiency = efficiencyRandom.nextInt(7) + 1;
            Performance performance = new Performance(t, engagement, efficiency);
            result.add(performance);
        }
        return result;
    }

    private static Application generateApplicationData() {
        long workTime = 10 * 60 * 60 * 1000;
        long ideTime = (long) (0.4 * workTime);
        long webTime = (long) (0.2 * workTime);
        long docTime = (long) (0.2 * workTime);
        long msgTime = (long) (0.1 * workTime);
        long othTime = (long) (0.1 * workTime);
        return new Application(ideTime, webTime, docTime, msgTime, othTime);
    }

    private static List<Input> generateInputData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long time = calendar.getTimeInMillis();

        long oneHour = 60 * 60 * 1000;
        Random keyRandom = new Random();
        Random mouseRandom = new Random();
        List<Input> result = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            long begTime = time + i * oneHour;
            long endTime = begTime + oneHour;
            int keystrokeNum = keyRandom.nextInt(100);
            int mouseClickNum = mouseRandom.nextInt(100);
            Input input = new Input(begTime, endTime, keystrokeNum, mouseClickNum);
            result.add(input);
        }
        return result;
    }
}
