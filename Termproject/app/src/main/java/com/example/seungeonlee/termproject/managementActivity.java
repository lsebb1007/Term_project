package com.example.seungeonlee.termproject;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class managementActivity extends AppCompatActivity {
    ArrayList<GoalView> ViewArray = new ArrayList<>();
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CheckBox ischecked = (CheckBox) findViewById(R.id.check);
        Intent intent = getIntent();
        ArrayList GoalArray = new ArrayList<GoalList>();
        GoalList data = (GoalList)getIntent().getSerializableExtra("data");
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "Goal_2.db", null, 1);
        //dbHelper.insert(data.date.toString(), data.type1.toString(), data.mygoal.toString());

        ViewArray = dbHelper.getView();
        GoalAddapter adapter = new GoalAddapter(this, R.layout.items, ViewArray) ;
        ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent myIntent = new Intent(managementActivity.this,managementAdd.class);
                    startActivity(myIntent);
                    finish();
            }
        });
    }

}

class GoalAddapter extends BaseAdapter {
    Context con;
    LayoutInflater inflacter;
    ArrayList<GoalView> arD;
    int layout;

    public GoalAddapter(Context context, int alayout, ArrayList<GoalView> aard){
        con = context;
        layout = alayout;
        arD = aard;
        inflacter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arD.size();
    }

    @Override
    public Object getItem(int i) {
        return arD.get(i).date;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            convertView = inflacter.inflate(layout, parent, false);
        }
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        img.setImageResource(arD.get(position).item);

        TextView date = (TextView) convertView.findViewById(R.id.To_date);
        date.setText(arD.get(position).date);

        TextView content = (TextView) convertView.findViewById(R.id.goalContent);
        content.setText(arD.get(position).mygoal);

        return convertView;
    }
}

