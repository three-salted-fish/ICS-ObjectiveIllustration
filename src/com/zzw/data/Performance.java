package com.zzw.data;

public class Performance {
    private long time;
    private int engagement;
    private int efficiency;

    public Performance(long time, int engagement, int efficiency) {
        this.time = time;
        this.engagement = engagement;
        this.efficiency = efficiency;
    }

    public long getTime() { return time; }

    public int getEngagement() {
        return engagement;
    }

    public int getEfficiency() {
        return efficiency;
    }

    @Override
    public String toString() {
        return String.format("{time:%d,engagement:%d,efficiency:%d}",
                time, engagement, efficiency);
    }
}
