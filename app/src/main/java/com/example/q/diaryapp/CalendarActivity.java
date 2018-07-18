package com.example.q.diaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {

    CalendarView calender;
    TextView addButton;
    Intent inte = new Intent();
    public DBManager_plan dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        dbManager = new DBManager_plan(this, "Calender.db", null, 1);

        Intent intent = new Intent(this.getIntent());
        calender = (CalendarView) findViewById(R.id.calendar);
        addButton = (TextView) findViewById(R.id.addButton3);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {  //캘린더에서 날짜 터치 시 일정이 모아진 activity(planactivity)로 이동
                Intent intent = new Intent(CalendarActivity.this, PlanActivity.class);
                intent.putExtra("year", String.valueOf(year));
                intent.putExtra("month",String.valueOf(month+1));
                intent.putExtra("date",String.valueOf(dayOfMonth));
                intent.putExtra("day","요일");
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //'+'누를 시 일정 추가
                Intent intent = new Intent(CalendarActivity.this, AddPlanActivity.class);
                startActivity(intent);
            }
        });
    }
}
