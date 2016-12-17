package com.example.seungeonlee.termproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.os.AsyncTask;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    TextView textView_longWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_longWeather = (TextView)findViewById(R.id.longWeather);

        new ReceiveLongWeather().execute();
    }
    public class ReceiveLongWeather extends AsyncTask<URL, Integer, Long> {

        ArrayList<Weather> longWeathers = new ArrayList<Weather>();

        protected Long doInBackground(URL... urls) {
            String url = "http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=109";

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                parseWeekXML(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Long result) {
            String data = "";

                data += longWeathers.get(0).gettodate() + " 기준 \n" +"최저온도: "+
                        longWeathers.get(0).getmin() + " " +"최고온도: "+
                        longWeathers.get(0).getmax() + " " +
                        longWeathers.get(0).getWf() + "\n";


            textView_longWeather.setText(data);
        }

        void parseWeekXML(String xml) {
            try {
                String tagName = "";
                boolean onCity = false;
                boolean ontodate = false;
                boolean onWf = false;
                boolean onmin = false;
                boolean onmax = false;
                boolean onEnd = false;
                boolean isItemTag1 = false;
                int i = 0;

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(new StringReader(xml));

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        tagName = parser.getName();

                        if (tagName.equals("city")) {
                            eventType = parser.next();

                            if (parser.getText().equals("서울")) {
                                onCity = true;
                            } else {
                                if (onCity) {
                                    break;
                                } else {
                                    onCity = false;
                                }
                            }
                        }

                        if (tagName.equals("data") && onCity) {
                            longWeathers.add(new Weather());
                            onEnd = false;
                            isItemTag1 = true;
                        }
                    } else if (eventType == XmlPullParser.TEXT && isItemTag1 && onCity) {
                        if (tagName.equals("todate") && !ontodate) {
                            longWeathers.get(i).settodate(parser.getText());
                            ontodate = true;
                        }
                        if (tagName.equals("wf") && !onWf) {
                            longWeathers.get(i).setWf(parser.getText());
                            onWf = true;
                        }
                        if (tagName.equals("min") && !onmin) {
                            longWeathers.get(i).setmin(parser.getText());
                            onmin = true;
                        }
                        if (tagName.equals("max") && !onmax) {
                            longWeathers.get(i).setmax(parser.getText());
                            onmax = true;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (tagName.equals("reliability") && onEnd == false) {
                            i++;
                            ontodate = false;
                            onWf = false;
                            onmin = false;
                            onmax = false;
                            isItemTag1 = false;
                            onEnd = true;
                        }
                    }

                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
