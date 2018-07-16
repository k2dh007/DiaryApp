package com.example.q.diaryapp;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

public class PSWActivity extends AppCompatActivity {

    int[] idArray = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9};
    int[] imgArray = {R.id.imageView, R.id.imageView2, R.id.imageView3, R.id.imageView4};

    private Button[] button = new Button[idArray.length];
    private ImageView[] imageviews = new ImageView[imgArray.length];

    private int count=0;
    private int insertedPSW =0;
    private SharedPreferences pref;
    private TextView subText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psw);
        subText = findViewById(R.id.subText);
        pref = getSharedPreferences("DIARY", Activity.MODE_PRIVATE);
        for (int i=0; i<imgArray.length; i++){
            imageviews[i] = (ImageView)findViewById(imgArray[i]);
        }

        ImageView delete = (ImageView)findViewById(R.id.delete);

        for(int i=0; i<idArray.length; i++){

            button[i] = (Button)findViewById(idArray[i]);

            button[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    imageviews[count].setImageDrawable(getDrawable(R.drawable.pencil_used));
                    count++;
                    switch(v.getId()) {
                        case R.id.button0:
                            insertedPSW = insertedPSW + (int) Math.pow(10, (4-count))*0;
                            break;
                        case R.id.button1:
                            insertedPSW = insertedPSW + (int) Math.pow(10, (4-count))*1;
                            break;
                        case R.id.button2:
                            insertedPSW = insertedPSW + (int) Math.pow(10, (4-count))*2;
                            break;
                        case R.id.button3:
                            insertedPSW = insertedPSW + (int) Math.pow(10, (4-count))*3;
                            break;
                        case R.id.button4:
                            insertedPSW = insertedPSW + (int) Math.pow(10, (4-count))*4;
                            break;
                        case R.id.button5:
                            insertedPSW = insertedPSW + (int) Math.pow(10, (4-count))*5;
                            break;
                        case R.id.button6:
                            insertedPSW = insertedPSW + (int) Math.pow(10, (4-count))*6;
                            break;
                        case R.id.button7:
                            insertedPSW = insertedPSW + (int) Math.pow(10, (4-count))*7;
                            break;
                        case R.id.button8:
                            insertedPSW = insertedPSW + (int) Math.pow(10, (4-count))*8;
                            break;
                        case R.id.button9:
                            insertedPSW = insertedPSW + (int) Math.pow(10, (4-count))*9;
                            break;
                    }
                    if(count==4){
                        if (insertedPSW == loadPSW())
                            finish();
                        else if(loadPSW()==10000){
                            savePSW();
                            subText.setText("확인을 위해 한 번 더 입력해주세요");
                            count = 0;
                            for (int i=0; i<imgArray.length; i++){
                                imageviews[i].setImageDrawable(getDrawable(R.drawable.pencil));
                            }
                            insertedPSW =0;
                        }
                        else {
                            for (int i=0; i<imgArray.length; i++){
                                imageviews[i].setImageDrawable(getDrawable(R.drawable.pencil));
                            }
                            subText.setText("비밀번호가 틀립니다");
                            count = 0;
                            insertedPSW =0;
                        }
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(count>0){
                        count--;
                        imageviews[count].setImageDrawable(getDrawable(R.drawable.pencil));
                        insertedPSW = insertedPSW/10;}
                }
            });
        }

    }


    public void savePSW() {

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("psw", insertedPSW);
        editor.commit();
    }

    public int loadPSW(){
        int psw = pref.getInt("psw",10000);
        Toast.makeText(this, "psw : " + psw, Toast.LENGTH_LONG).show();
        return psw;
    }
}