package com.example.q.diaryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class AddPlanActivity extends AppCompatActivity {

    ImageView iconList;
    TextView addButton;
    ImageView icon;
    RelativeLayout relativeLayout;
    EditText month;
    EditText day;
    EditText planname;
    EditText hour;
    EditText min;
    DBManager_plan dbManager;
    int resId;
    String[] icons = new String[]{"trip", "meeting", "exam", "plans", "ticketing", "important", "movie"};
    //final TypedArray icons2 = getResources().obtainTypedArray(R.array.iconlist);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addplan);
        dbManager = new DBManager_plan(this, "Calender.db", null, 1);

        Intent intent = new Intent(this.getIntent());

        iconList = (ImageView) findViewById(R.id.iconList);
        icon = (ImageView) findViewById(R.id.icon);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout);
        month = (EditText) findViewById(R.id.month);
        day = (EditText) findViewById(R.id.day);
        hour = (EditText) findViewById(R.id.hour);
        min = (EditText) findViewById(R.id.min);
        planname = (EditText) findViewById(R.id.planname);
        addButton = (TextView) findViewById(R.id.addButton2);
        resId= getResources().getIdentifier(icons[0], "drawable", "com.example.q.diaryapp");

        iconList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AddPlanActivity.this);
                dialog.setTitle("아이콘 선택");
                dialog.setItems(icons, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resId = getResources().getIdentifier(icons[which], "drawable", "com.example.q.diaryapp");
                        iconList.setImageResource(resId);
                    }
                }).create().show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.insert("2018",month.getText().toString(),day.getText().toString(),planname.getText().toString(),hour.getText().toString(),min.getText().toString(), resId);
                finish();

            }
        });
    }
}
