package io.github.twt233;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class mergedChart {
    private BufferedImage mergedGraph;

    /**
     * @param date the date of the charts wanna be merged
     *             e.g. 20190519 stands for 2019-05-19
     */
    public mergedChart(String date) throws IOException {
        mergedGraph = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
        Graphics2D paint = mergedGraph.createGraphics();

        paint.setColor(Color.WHITE);
        paint.fillRect(0, 0, mergedGraph.getWidth(), mergedGraph.getHeight());

        String stringPathPrefix = "statistics/images/" + date + "_";

        BufferedImage appChart = ImageIO.read(new File(stringPathPrefix + "application.png"));
        BufferedImage inputChart = ImageIO.read(new File(stringPathPrefix + "input.png"));
        BufferedImage performanceChart = ImageIO.read(new File(stringPathPrefix + "performance.png"));

        paint.drawImage(performanceChart, 0, 0, null);
        paint.drawImage(inputChart, 0, performanceChart.getHeight(), null);
        paint.drawImage(appChart, inputChart.getWidth(), performanceChart.getHeight(), null);
    }

    public static void main(String[] args) throws IOException {
        mergedChart da = new mergedChart("20190519");
        da.writeGraph("statistics/images/" + "20190519" + "_merged.png");
    }

    public void writeGraph(String filePath) throws IOException {
        ImageIO.write(mergedGraph, "png", new File(filePath));
    }
}
