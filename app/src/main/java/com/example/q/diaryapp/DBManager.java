package com.example.q.diaryapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.Text;

public class DBManager extends SQLiteOpenHelper{

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 DIARY이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
       int형 year, month, date, text형 content . */
        db.execSQL("CREATE TABLE IF NOT EXISTS DIARY (_id INTEGER PRIMARY KEY AUTOINCREMENT,  year INTEGER, month INTEGER, date INTEGER, content String, imgPath String);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //저장
    public void insert(int year, int month, int date, String content, String imgPath) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO DIARY VALUES(null, '" + year + "', '" + month + "', '" + date + "', '" + content + "','" + imgPath + "');");
        db.close();
    }

    //수정
    public void update(int year, int month, int date, String content, String imgPath) {
        SQLiteDatabase db = getWritableDatabase();
        //입력한 년,월,일 에 해당하는 content와 imgpath 변경
        db.execSQL("UPDATE DIARY SET content='" + content + "' WHERE year='" + year + "' AND month='" + month + "'AND date='" + date + "';");
        db.execSQL("UPDATE DIARY SET imgPath='" + imgPath + "' WHERE year='" + year + "' AND month='" + month + "'AND date='" + date + "';");
        db.close();
    }

    //삭제
    public void delete(int year, int month, int date) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 년, 월, 일과 일치하는 행 삭제
        db.execSQL("DELETE FROM DIARY WHERE year='" + year + "' AND month='" + month + "'AND date='" + date + "' ;");
        db.close();
    }

    //찾아서 있으면 불러오기, 없으면 null return
    public String getContentResult(int year, int month, int date) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM DIARY", null);
        while (cursor.moveToNext()) {
            if(cursor.getInt(1)==year&&cursor.getInt(2)==month&&cursor.getInt(3)==date)
            return cursor.getString(4); }
        return null;
    }
    public String getImgPathResult(int year, int month, int date) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM DIARY", null);
        while (cursor.moveToNext()) {
            if(cursor.getInt(1)==year&&cursor.getInt(2)==month&&cursor.getInt(3)==date)

                return cursor.getString(5); }

        return null;
    }
}
