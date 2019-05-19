package com.zzw.data;

public class Input {
    private long begTime;       // begin time
    private long endTime;       // end time
    private int keystrokeNum;   // number of keystroke
    private int mouseClickNum;  // number of mouse click

    public Input(long begTime, long endTime, int keystrokeNum, int mouseClickNum) {
        this.begTime = begTime;
        this.endTime = endTime;
        this.keystrokeNum = keystrokeNum;
        this.mouseClickNum = mouseClickNum;
    }

    public long getBeginTime() {
        return begTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public int getKeystrokeNum() {
        return keystrokeNum;
    }

    public int getMouseClickNum() {
        return mouseClickNum;
    }

    @Override
    public String toString() {
        return String.format("{begTime:%d,endTime:%d,keystrokeNum:%d,mouseClickNum:%d}",
                begTime, endTime, keystrokeNum, mouseClickNum);
    }
}
