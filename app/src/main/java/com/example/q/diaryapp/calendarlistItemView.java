package com.example.q.diaryapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class calendarlistItemView extends RelativeLayout {
    TextView yoil;
    TextView date;
    TextView content;
    TextView hour;
    TextView min;
    ImageView icon;

    public calendarlistItemView(Context context, String yoil, String date, String content1, String hour, String min, Drawable icon) {
        super(context);
        init(context, yoil, date, content1, hour, min, icon);
    }

    private void init(Context context, String yoil1, final String date1, String content1, String hour1, String min1, Drawable icon1) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_list_item, this, true);
        //View view = inflater.inflate(R.layout.list_item, null);

        yoil = (TextView) findViewById(R.id.yoil);
        date = (TextView) findViewById(R.id.date);
        content = (TextView) findViewById(R.id.content);
        hour = (TextView) findViewById(R.id.hour);
        min = (TextView) findViewById(R.id.min);
        icon = (ImageView) findViewById(R.id.icon);

        yoil.setText(yoil1);
        date.setText(date1);
        content.setText(content1);
        hour.setText(hour1);
        min.setText(min1);
        icon.setImageDrawable(icon1);

    }

    public void setYoil(String yoil1) {
        yoil.setText(yoil1);
    }
    public void setDate(String date1) {
        date.setText(date1);
    }
    public void setContent(String content1) {
        content.setText(content1);
    }
    public void setHour(String hour1) { hour.setText(hour1);}
    public void setMin(String min1) { min.setText(min1);}
    public void setIcon(Drawable icon1) { icon.setImageDrawable(icon1);};
}


