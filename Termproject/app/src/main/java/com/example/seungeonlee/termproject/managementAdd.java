package com.example.seungeonlee.termproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class managementAdd extends AppCompatActivity implements View.OnClickListener{
    private EditText toDateEtxt;

    //private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    RadioGroup rg;
    ArrayList GoalArray = new ArrayList<GoalList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_add2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Goal.db", null, 1);
        
        final EditText etgoal = (EditText) findViewById(R.id.editText);
        toDateEtxt = (EditText) findViewById(R.id.to_date);
        rg = (RadioGroup) findViewById(R.id.radioGroup1);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.KOREA);

        findViewsById();

        setDateTimeField();

        // 날짜는 현재 날짜로 고정
        // 현재 시간 구하기
        long now = System.currentTimeMillis();
        //Date date = new Date(now);
        // 출력될 포맷 설정
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = toDateEtxt.getText().toString();
                RadioButton rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                String item = rd.getText().toString();

                //String item = etItem.getText().toString();
                String goal = etgoal.getText().toString();

                dbHelper.insert(date, item, goal);

                GoalList data = new GoalList(date,item,goal);
                //  GoalArray.add(new GoalList(date,item,goal));
                // Intent에 Data객체 저장
                Intent intent = new Intent(managementAdd.this, managementActivity.class);
                intent.putExtra("data", data);

                // Main2Activity로 Activity전환
                startActivity(intent);
                finish();
            }
        });

        

    }
    private void findViewsById() {
        toDateEtxt = (EditText) findViewById(R.id.to_date);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }

    private void setDateTimeField() {
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {

        toDatePickerDialog.show();
    }
}

