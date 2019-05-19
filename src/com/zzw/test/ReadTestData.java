package com.zzw.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzw.data.Application;
import com.zzw.data.Input;
import com.zzw.data.Performance;
import com.zzw.tools.io.OkTextReader;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReadTestData {
    public static void main(String[] args) {
        String dir = "statistics\\data\\20190517";
        OkTextReader reader = new OkTextReader();
        Gson gson = new Gson();

        System.out.println("\nperformance data:");
        List<Performance> performanceData = readPerformanceData(dir, reader, gson);
        for (Performance performance : performanceData) {
            System.out.println(performance.toString());
        }

        System.out.println("\napplication data:");
        Application application = readApplicationData(dir, reader, gson);
        System.out.println(application.toString());

        System.out.println("\ninput data:");
        List<Input> inputData = readInputData(dir, reader, gson);
        for (Input input : inputData) {
            System.out.println(input.toString());
        }
    }

    private static List<Performance> readPerformanceData(String dir, OkTextReader reader, Gson gson) {
        reader.open(dir + File.separator + "performance.json");
        String json = reader.readLine();
        reader.close();

        Type type = new TypeToken<ArrayList<Performance>>(){}.getType();
        List<Performance> performanceData = gson.fromJson(json, type);
        return performanceData;
    }

    private static Application readApplicationData(String dir, OkTextReader reader, Gson gson) {
        reader.open(dir + File.separator + "application.json");
        String json = reader.readLine();
        reader.close();

        Application application = gson.fromJson(json, Application.class);
        return application;
    }

    private static List<Input> readInputData(String dir, OkTextReader reader, Gson gson) {
        reader.open(dir + File.separator + "input.json");
        String json = reader.readLine();
        reader.close();

        Type type = new TypeToken<ArrayList<Input>>(){}.getType();
        List<Input> inputData = gson.fromJson(json, type);
        return inputData;
    }
}
