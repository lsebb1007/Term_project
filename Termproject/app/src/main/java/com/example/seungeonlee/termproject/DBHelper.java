package com.example.seungeonlee.termproject;

/**
 * Created by seungeonlee on 2016. 12. 2..
 */
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
public class DBHelper extends SQLiteOpenHelper {
    ArrayList mInfoArray = new ArrayList<GoalList>();
    public static SQLiteDatabase mDB;
    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        /* 이름은 MONEYBOOK이고, 자동으로 값이 증가하는 _id 정수형 기본키 컬럼과
        item 문자열 컬럼, price 정수형 컬럼, create_at 문자열 컬럼으로 구성된 테이블을 생성. */
        db.execSQL("CREATE TABLE MONEYBOOK (_id INTEGER PRIMARY KEY AUTOINCREMENT, item TEXT, price TEXT, create_at TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String create_at, String item, String price) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MONEYBOOK VALUES(null, '" + item + "', '" + price + "', '" + create_at + "');");
        Log.e("insert","성공");
        db.close();
    }

    public void update(String item, String price) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        db.execSQL("UPDATE MONEYBOOK SET price=" + price + " WHERE item='" + item + "';");
        db.close();
    }

    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM MONEYBOOK WHERE item='" + item + "';");
        db.close();
    }

    public String getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MONEYBOOK", null);
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
    public ArrayList<GoalView> getView(){
        GoalView goal;
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<GoalView> array = new ArrayList<GoalView>();

        Cursor cursor = db.rawQuery("SELECT * FROM MONEYBOOK", null);
        while (cursor.moveToNext()) {
            if(cursor.getString(1).toString().equals("Study")){
                goal=new GoalView(cursor.getString(3),R.mipmap.study,cursor.getString(2));

            }
            else if(cursor.getString(1).toString().equals("Exercise")){
                goal=new GoalView(cursor.getString(3),R.mipmap.exercise,cursor.getString(2));

            }
            else if(cursor.getString(1).toString().equals("Date")){
                goal=new GoalView(cursor.getString(3),R.mipmap.date,cursor.getString(2));

            }
            else{
                goal=new GoalView(cursor.getString(3),R.mipmap.etc,cursor.getString(2));

            }
            array.add(goal);
        }
        return array;
    }
}

