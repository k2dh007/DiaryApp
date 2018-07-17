package com.example.q.diaryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlanActivity extends AppCompatActivity {
    ListView plan;
    LayoutInflater inflater;
    Intent inte = new Intent();
    listAdapter adapter = new listAdapter();
    String year;
    String month;
    String mdate;
    String day;
    DBManager_plan dbManager;
    String yoil;
    String date;
    String content;
    String hour;
    String min;
    Image icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        dbManager = new DBManager_plan(this, "Calender.db", null, 1);

        Intent intent = new Intent(this.getIntent());
        plan = (ListView) findViewById(R.id.plan);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        plan.setAdapter(adapter);
        year = intent.getExtras().getString("year");
        month = intent.getExtras().getString("month");
        mdate = intent.getExtras().getString("date");
        day = intent.getExtras().getString("day");

        if(dbManager.doesExist(year, month, mdate)){
        yoil = day ;
        content = dbManager.getPlanResult(year,month,mdate);
        hour = dbManager.getHourResult(year, month, mdate);
        min = dbManager.getMinResult(year, month, mdate);
        int id = dbManager.getIconResult(year, month, mdate);
        calendarlistItem items = new calendarlistItem(yoil, month+" / "+ mdate, content, hour, min, id);
        adapter.addItem(items);}
        adapter.notifyDataSetChanged();


        plan.setOnLongClickListener(new ListView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("일정 삭제");
                alert.setMessage("해당 일정을 지우시겠습니까?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //리스트에서 선택한 일정이 삭제되는 코드가 들어감
                        inte.putExtra("삭제", 1);
                        finish();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return false;
            }
        });
    }

    class listAdapter extends BaseAdapter {
        ArrayList<calendarlistItem> items = new ArrayList<calendarlistItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(calendarlistItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        //데이터 관리하는 어뎁터가 화면에 보일 각 아이템에 대한 뷰를 만들어라 요구한 것
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();
            int viewType = getItemViewType(position) ;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ; }

            convertView = inflater.inflate(R.layout.calendar_list_item,
                    parent, false);
            TextView yoil = (TextView) convertView.findViewById(R.id.yoil) ;
            TextView hour = (TextView) convertView.findViewById(R.id.hour) ;
            TextView min = (TextView) convertView.findViewById(R.id.min) ;
            TextView date = (TextView) convertView.findViewById(R.id.date) ;
            TextView content = (TextView) convertView.findViewById(R.id.content) ;
            ImageView icon = (ImageView) convertView.findViewById(R.id.icon) ;
            yoil.setText(items.get(0).yoil);
            hour.setText(items.get(0).hour);
            date.setText(items.get(0).date);
            content.setText(items.get(0).content);
            min.setText(items.get(0).min);
            icon.setImageDrawable(getResources().getDrawable( items.get(0).icon ));

            /*listItem item=items.get(position);
            listItemView view = new listItemView(getApplicationContext(), item.getYoil(), item.getDate(), item.getContent());
            int res = 0;

            // TODO Auto-generated method stub



            //최초 호출이면 항목 뷰를 생성한다.

            //타입별로 뷰를 다르게 디자인 할 수 있으며 높이가 달라도 상관없다.
            if(convertView == null) {
                res = getItemViewType(position);//현재 위치의 Type을 조사해보고
                switch(res) {
                    case 0://0이면 textedit
                        res = R.layout.main_list_item_null;
                        break;
                    case 1://1이면 btnicon으로 R.layout값을 넣어주고
                        res = R.layout.main_list_item;
                        break;
                }
                //인플레이트합니다. 즉 화면에 뿌립니다.
                convertView = inflater.inflate(res, parent, false);
            }*/
            return convertView;
        }
    }
}


