package com.example.q.diaryapp;

import android.media.Image;

public class calendarlistItem {
    String yoil;
    String date;
    String content;
    String hour;
    String min;
    int icon;

    public calendarlistItem(String yoil1, String date1, String content1, String hour1, String min1, int icon1) {   //생성자
        this.yoil = yoil1;
        this.date = date1;
        this.content = content1;
        this.hour = hour1;
        this.min = min1;
        this.icon = icon1;
    }

    @Override
    public String toString() {
        return "요일: "+yoil+"\n날짜: "+date+"\n내용: "+content+"\n시간: "+hour+"\n분: "+min+"아이콘: "+icon;
    }

    /*캡슐화*/
    public String getYoil() {
        return yoil;
    }

    public void setYoil(String yoil1) {
        this.yoil = yoil1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date1) {
        this.date = date1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content1) {
        this.content = content1;
    }

    public String getHour() { return hour;}

    public void setHour(String hour1) { this.hour = hour1;}

    public String getMin() { return min;}

    public void setMin(String min1) { this.min = min1;}

    public int getIcon() { return icon;}

    public void setIcon(int icon1) { this.icon = icon1;}
}
