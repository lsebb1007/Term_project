package com.example.seungeonlee.termproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordActivity extends AppCompatActivity {
    ArrayList<EventView> ViewArray2 = new ArrayList<>();
    SQLiteDatabase db1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        final DBHelper2 dbHelper2 = new DBHelper2(getApplicationContext(), "Event2.db", null, 1);
        ViewArray2 = dbHelper2.getView();
        Eventadapter adapter2 = new Eventadapter(this, R.layout.event_item, ViewArray2) ;
        ListView listview2 = (ListView) findViewById(R.id.listview2) ;
        listview2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview2.setAdapter(adapter2);

    }
}

class Eventadapter extends BaseAdapter {
    Context con;
    LayoutInflater inflacter;
    ArrayList<EventView> arD;
    int layout;

    public Eventadapter(Context context, int alayout, ArrayList<EventView> aard){
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
        return arD.get(i).time1;
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
        ImageView img = (ImageView) convertView.findViewById(R.id.img1);
        img.setImageResource(arD.get(position).item1);

        TextView time= (TextView) convertView.findViewById(R.id.times);
        long outTime = Long.parseLong(arD.get(position).time1);
        String Time = String.format("%02d:%02d:%02d", outTime/1000 / 60, (outTime/1000)%60,(outTime%1000)/10);
        time.setText(Time);

        TextView event1 = (TextView) convertView.findViewById(R.id.event1);
        event1.setText(arD.get(position).event1);

        return convertView;
    }
}