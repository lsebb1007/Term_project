package com.example.seungeonlee.termproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickedButton1(View v) {
        Intent myIntent = new Intent(MainActivity.this,managementActivity.class);
        startActivity(myIntent);
    }
    public void onClickedButton2(View v){
        Intent myIntent = new Intent(MainActivity.this,RecordActivity.class);
        startActivity(myIntent);
    }
    public void onClickedButton3(View v){
        Intent myIntent = new Intent(MainActivity.this,AddActivity.class);
        startActivity(myIntent);
    }

}
