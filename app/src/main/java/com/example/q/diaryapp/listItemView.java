package com.example.q.diaryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class listItemView extends RelativeLayout {
    TextView yoil;
    TextView date;
    TextView content;

    public listItemView(Context context, String yoil, String date, String content1) {
        super(context);
        init(context, yoil, date, content1);
    }

    private void init(Context context, String yoil1, final String date1, String content1) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.main_list_item, this, true);
        //View view = inflater.inflate(R.layout.list_item, null);

        yoil = (TextView) findViewById(R.id.yoil);
        date = (TextView) findViewById(R.id.date);
        content = (TextView) findViewById(R.id.content);

        yoil.setText(yoil1);
        date.setText(date1);
        content.setText(content1);

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
}
