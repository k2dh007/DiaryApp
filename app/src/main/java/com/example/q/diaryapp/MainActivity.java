package com.example.q.diaryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    public DBManager dbManager;
    public String currentYear;
    public String currentMonth;
    public String currentDate;
    public String currentDay;
    ListView listView;
    listAdapter adapter = new listAdapter();
    LayoutInflater inflater;
    ArrayList<listItem> items2 = new ArrayList<listItem>();
    TextView yoil;
    TextView date;
    TextView content;
    int intDate;
    boolean modi = false;
    ArrayList<String> days = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this, "Diary.db", null, 1);
        SharedPreferences pref = getSharedPreferences("DIARY", Activity.MODE_PRIVATE);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-E", Locale.KOREA);
        String str_date = df.format(new Date());
        String[] arr_date = str_date.split("-");
        days.add("월");
        days.add("화");
        days.add("수");
        days.add("목");
        days.add("금");
        days.add("토");
        days.add("일");

        listView = (ListView) findViewById(R.id.listView);
        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        yoil = (TextView) findViewById(R.id.yoil);
        date = (TextView) findViewById(R.id.date);
        content = (TextView) findViewById(R.id.content);

        listView.bringToFront();
        listView.setAdapter(adapter);

        if(pref.getBoolean("usepsw",false)){
        Intent intent = new Intent(MainActivity.this, PSWActivity.class);
        startActivity(intent);}
        currentYear = arr_date[0];
        currentMonth = arr_date[1];
        currentDate = arr_date[2];
        currentDay = arr_date[3];

        int intYear = Integer.parseInt(currentYear);
        int intMonth = Integer.parseInt(currentMonth);
        intDate = Integer.parseInt(currentDate);

        //db에 오늘자 diary가 있는지 없는지 확인, 없으면 current true상태로 contentactivity열기
        if(!dbManager.doesExist(currentYear,currentMonth,currentDate)){

            Intent intent = new Intent(MainActivity.this, ContentActivity.class);
            intent.putExtra("isCurrent",true);
            intent.putExtra("year", currentYear);
            intent.putExtra("month",currentMonth);
            intent.putExtra("date",currentDate);
            intent.putExtra("day",currentDay);
            startActivity(intent);

        }
        items2.clear();
        intDate = intDate-5;
        for(int i = 0; i<6; i++){
            String Date = String.valueOf(intDate);
            if(dbManager.doesExist(currentYear, currentMonth, Date)) {
                items2.add(new listItem(1, dbManager.getDayResult(currentYear,currentMonth,Date), currentMonth+"/"+Date, dbManager.getContentResult(currentYear, currentMonth, Date)));
            }
            else {
                items2.add(new listItem(0,"","",""));
            }

            intDate++;
        }

        for(int i=0;i<items2.size();i++){
            if(!modi){
            adapter.addItem(items2.get(i));}
            else{
                adapter.modify(i,items2.get(i));
            }
        }
        modi=true;
        adapter.notifyDataSetChanged();
        //글쓰기 창으로 이동
        TextView addButton = (TextView) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                intent.putExtra("isCurrent",true);
                intent.putExtra("year", currentYear);
                intent.putExtra("month",currentMonth);
                intent.putExtra("date",currentDate);
                startActivity(intent);
            }
        });

        //캘린더 창으로 이동
        ImageView calendar = (ImageView) findViewById(R.id.calender);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        //설정 창으로 이동
        ImageView setting = (ImageView) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        //리스트뷰에 있는 특정 글 클릭 시 입력(수정)창으로 이동
        //리스트가 비어있는 상태여서 실행시 자꾸 null 에러로 프로그램이 종료돼서 주석상태로 해둠!
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                intent.putExtra("isCurrent",false);
                intent.putExtra("year", currentYear);
                intent.putExtra("month",currentMonth);
                intent.putExtra("date",String.valueOf(intDate-6+position));
                intent.putExtra("day",days.get((days.indexOf(currentDay)+2+position)%7));
                startActivity(intent);
            }
        });

        //예시

    }

    class listAdapter extends BaseAdapter {
        ArrayList<listItem> items = new ArrayList<listItem>();
        private static final int notNull = 1;
        private static final int Null = 0;
        private static final int ITEM_VIEW_TYPE_MAX = 2 ;

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(listItem item) {
            items.add(item);
        }

        public void modify(int i, listItem item){
            items.set(i,item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_VIEW_TYPE_MAX ;
        }

        // position 위치의 아이템 타입 리턴.
        @Override
        public int getItemViewType(int position) {
            return items.get(position).getType();
        }

        //데이터 관리하는 어뎁터가 화면에 보일 각 아이템에 대한 뷰를 만들어라 요구한 것
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();
            int viewType = getItemViewType(position) ;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

                // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
                listItem item=items.get(position);
                listItemView view = new listItemView(getApplicationContext(), item.getYoil(), item.getDate(), item.getContent());

                switch (viewType) {
                    case notNull:
                        convertView = inflater.inflate(R.layout.main_list_item,
                                parent, false);
                        TextView yoil = (TextView) convertView.findViewById(R.id.yoil) ;
                        TextView date = (TextView) convertView.findViewById(R.id.date) ;
                        TextView content = (TextView) convertView.findViewById(R.id.content);

                        yoil.setText(item.getYoil());
                        date.setText(item.getDate());
                        content.setText(item.getContent());

                        break;

                    case Null:
                        convertView = inflater.inflate(R.layout.main_list_item_null,
                                parent, false);

                        break;
                }
            }
            return convertView;
        }
    }
}
