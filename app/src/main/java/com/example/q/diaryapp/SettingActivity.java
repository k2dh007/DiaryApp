package com.example.q.diaryapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class SettingActivity  extends AppCompatActivity {
    public  SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("DIARY", Activity.MODE_PRIVATE);

        setContentView(R.layout.activity_setting);
        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setChecked(pref.getBoolean("usepsw",false));

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("usepsw", isChecked);
                editor.commit();
                if(isChecked){
                    Intent intent = new Intent(SettingActivity.this, PSWActivity.class);
                    startActivity(intent);
                }
                else{
                    editor.remove("psw");
                    editor.putInt("psw", 10000);
                    editor.commit();
                }

            }
        });
    }
}
