package com.example.seungeonlee.termproject;

/**
 * Created by seungeonlee on 2016. 12. 15..
 */
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
public class DBHelper2 extends SQLiteOpenHelper {
    ArrayList mInfoArray = new ArrayList<GoalList>();
    public static SQLiteDatabase mDB;
    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper2(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db1) {
        // 새로운 테이블 생성
        /* 이름은 EVENTMANAGE이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, content 문자열 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db1.execSQL("CREATE TABLE EVENTMANAGE (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, content TEXT, create_at TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {

    }
    public void update(String item, String content) {
        SQLiteDatabase db1 = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db1.execSQL("UPDATE EVENTMANAGE SET content=" + content + " WHERE item='" + item + "';");
        db1.close();
    }

    public void insert(String create_at, String item, String content) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db1 = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db1.execSQL("INSERT INTO EVENTMANAGE VALUES(null, '" + item + "', '" + content + "', '" + create_at + "');");
        Log.e("insert","성공");
        db1.close();
    }

    public void delete(String item) {
        SQLiteDatabase db1 = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db1.execSQL("DELETE FROM EVENTMANAGE WHERE item='" + item + "';");
        db1.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db1 = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db1.rawQuery("SELECT * FROM EVENTMANAGE", null);
        while (cursor.moveToNext()) {
            result +=
                    cursor.getString(1)
                            + " | "
                            + cursor.getString(2)
                            + "-> "
                            + cursor.getString(3)
                            + "까지\n";
            Log.e("getResult","성공");
        }

        return result;
    }
    public ArrayList<EventView> getView(){
        EventView goal;
        SQLiteDatabase db1 = getReadableDatabase();
        ArrayList<EventView> array = new ArrayList<>();

        Cursor cursor = db1.rawQuery("SELECT * FROM EVENTMANAGE ORDER BY create_at", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).toString().equals("Study")){
                goal=new EventView(cursor.getString(3),R.mipmap.study,cursor.getString(2));

            }
            else if(cursor.getString(1).toString().equals("Exercise")){
                goal=new EventView(cursor.getString(3),R.mipmap.exercise,cursor.getString(2));

            }
            else if(cursor.getString(1).toString().equals("Date")){
                goal=new EventView(cursor.getString(3),R.mipmap.date,cursor.getString(2));

            }
            else{
                goal=new EventView(cursor.getString(3),R.mipmap.etc,cursor.getString(2));

            }
            array.add(goal);
        }
        return array;
    }
}

