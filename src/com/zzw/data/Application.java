package com.zzw.data;



public class Application
{
    private long ideTime;   // time spent on IntelliJ IDEA IDE
    private long webTime;   // time spent on browsers
    private long docTime;   // time spent on documents
    private long msgTime;   // time spent on instant messaging
    private long othTime;   // time spent on others

    public Application(long ideTime, long webTime, long docTime, long msgTime, long othTime) {
        this.ideTime = ideTime;
        this.webTime = webTime;
        this.docTime = docTime;
        this.msgTime = msgTime;
        this.othTime = othTime;
    }

    public long getIdeTime() { return ideTime; }

    public long getWebTime() { return webTime; }

    public long getDocumentTime() {
        return docTime;
    }

    public long getMessageTime() {
        return msgTime;
    }

    public long getOtherTime() {
        return othTime;
    }

    @Override // 作为一个提示，强制为对父类的重写，如果父类中没有这个函数，则会报错，同时也是对程序员和阅读者的提示
    public String toString() {
        return String.format("{ideTime:%d,webTime:%d,docTime:%d,msgTime:%d,othTime:%d}",
                ideTime, webTime, docTime, msgTime, othTime);
    }
}
