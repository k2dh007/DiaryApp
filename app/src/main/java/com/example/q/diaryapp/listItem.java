package com.example.q.diaryapp;

public class listItem {
    int type;
    String yoil;
    String date;
    String content;

    public listItem(int type1, String yoil1, String date1, String content1) {   //생성자
        this.type = type1;
        this.yoil = yoil1;
        this.date = date1;
        this.content = content1;
    }

    @Override
    public String toString() { return "요일: "+yoil+"\n날짜: "+date+"\n내용: "+content; }

    /*캡슐화*/
    public String getYoil() { return yoil; }

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

    public int getType() { return this.type ; }

    public void setType(int type1) { this.type = type1 ; }
}

