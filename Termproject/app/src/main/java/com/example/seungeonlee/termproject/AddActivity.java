package com.example.seungeonlee.termproject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.seungeonlee.termproject.R.id.datebtn;
import static com.example.seungeonlee.termproject.R.id.etcbtn;
import static com.example.seungeonlee.termproject.R.id.exercisebtn;
import static com.example.seungeonlee.termproject.R.id.studybtn;

public class AddActivity extends AppCompatActivity{
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private ImageView imgview;
    TextView myOutput;
    ImageButton myBtnStart;
    ImageButton myBtnRec;
    ImageButton myBtnStop;
    RadioButton BtnStudy;
    RadioButton BtnExercise;
    RadioButton Btn_Date;
    RadioButton BtnEtc;
    TextView location_view;

    // GPSTracker class
    private GpsInfo gps;

    final static int Init =0;
    final static int Run =1;
    final static int Pause =2;

    int cur_Status = Init; //현재의 상태를 저장할변수를 초기화함.
    int myCount=1;
    long myBaseTime;
    long myPauseTime;
    int type;
    long totalTime;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        imgview = (ImageView) findViewById(R.id.imageView2);
        ImageButton buttonCamera = (ImageButton) findViewById(R.id.btn_take_camera);
        ImageButton buttonGallery = (ImageButton) findViewById(R.id.btn_select_gallery);
        myOutput = (TextView) findViewById(R.id.time_out);
        myBtnStart = (ImageButton) findViewById(R.id.startbtn);
        myBtnRec = (ImageButton) findViewById(R.id.pausebtn);
        myBtnStop = (ImageButton) findViewById(R.id.stopbtn);
        ImageButton button01 = (ImageButton) findViewById(R.id.button01);

        BtnStudy = (RadioButton) findViewById(studybtn);
        BtnExercise = (RadioButton) findViewById(R.id.exercisebtn);
        Btn_Date = (RadioButton) findViewById(R.id.datebtn);
        BtnEtc = (RadioButton) findViewById(R.id.etcbtn);
        location_view = (TextView) findViewById(R.id.location_view);

        // GPS 정보를 보여주기 위한 이벤트 클래스 등록
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                gps = new GpsInfo(AddActivity.this);
                // GPS 사용유무 가져오기
                if (gps.isGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    String str = Double.toString(latitude);
                    String location = getAddress(latitude,longitude);
                    location_view.setText(location);

                } else {
                    // GPS 를 사용할수 없으므로
                    gps.showSettingsAlert();
                }
            }
        });

        buttonCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // 카메라 호출
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());

                // 이미지 잘라내기 위한 크기
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);

                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, PICK_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
            }
        });

        buttonGallery.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                // Gallery 호출
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 잘라내기 셋팅
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);
                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_GALLERY);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
            }
        });
    }

    private String getAddress(double lat, double lng) {
        Geocoder gcK = new Geocoder(getBaseContext(), Locale.KOREA);
        String res = "정보없음";
        try {
            List<Address> addresses = gcK.getFromLocation(lat, lng, 1);
            StringBuilder sb = new StringBuilder();

                Address address = addresses.get(0);
                sb.append(address.getCountryName()).append("/");
                sb.append(address.getPostalCode()).append("/");
                sb.append(address.getLocality()).append("/");
                sb.append(address.getThoroughfare()).append("/");
                sb.append(address.getFeatureName());
                res = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        try {
            if (requestCode == PICK_FROM_CAMERA) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    imgview.setImageBitmap(photo);
                }
            }
            if (requestCode == PICK_FROM_GALLERY) {
                Bundle extras2 = data.getExtras();
                if (extras2 != null) {
                    Bitmap photo = extras2.getParcelable("data");
                    imgview.setImageBitmap(photo);
                }
            }
        } catch (NullPointerException e){

        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    public void myOnClick(View v){
        switch(v.getId()){
            case R.id.startbtn: //시작버튼을 클릭했을때 현재 상태값에 따라 다른 동작을 할수있게끔 구현.
                switch(cur_Status){
                    case Init:
                        myBaseTime = SystemClock.elapsedRealtime();
                        System.out.println(myBaseTime);
                        myTimer.sendEmptyMessage(0);
                        myBtnRec.setEnabled(true);
                        myBtnStop.setEnabled(true);
                        myBtnStart.setEnabled(false);//기록버튼 활성
                        cur_Status = Run; //현재상태를 런상태로 변경
                        break;
                    case Run: //멈춤으로 바뀐 시작버튼 눌렀을때 ->stop버튼 눌렀을때
                        myTimer.removeMessages(0); //핸들러 메세지 제거
                        myPauseTime = SystemClock.elapsedRealtime();
                        myBtnRec.setEnabled(true);
                        myBtnStop.setEnabled(true);
                        myBtnStart.setEnabled(false);
                        cur_Status = Pause;
                        break;
                    case Pause:
                        long now = SystemClock.elapsedRealtime();
                        myTimer.sendEmptyMessage(0);
                        myBaseTime += (now- myPauseTime);
                        myBtnRec.setEnabled(true);
                        myBtnStop.setEnabled(true);
                        myBtnStart.setEnabled(false);
                        cur_Status = Run;
                        break;
                }
                break;
            case R.id.pausebtn:
                myTimer.removeMessages(0); //핸들러 메세지 제거
                myPauseTime = SystemClock.elapsedRealtime();
                myBtnStart.setEnabled(true);
                cur_Status = Pause;
                break;
            case R.id.stopbtn:
                myTimer.removeMessages(0); //핸들러 메세지 제거
                myPauseTime = SystemClock.elapsedRealtime();
                myOutput.setText("00:00:00");
                myBtnStart.setEnabled(true);
                cur_Status = Init;
                break;
        }
    }

    Handler myTimer = new Handler(){
        public void handleMessage(Message msg){
            myOutput.setText(getTimeOut());

            //sendEmptyMessage 는 비어있는 메세지를 Handler 에게 전송하는겁니다.
            myTimer.sendEmptyMessage(0);
        }
    };
    //현재시간을 계속 구해서 출력하는 메소드
    String getTimeOut(){
        long now = SystemClock.elapsedRealtime(); //애플리케이션이 실행되고나서 실제로 경과된 시간(??)^^;
        long outTime = now - myBaseTime;
        totalTime = outTime;
        String easy_outTime = String.format("%02d:%02d:%02d", outTime/1000 / 60, (outTime/1000)%60,(outTime%1000)/10);
        return easy_outTime;
    }

    public void SelectClick(View view){
        switch(view.getId()){
            case studybtn:
                type = 1;
                break;
            case exercisebtn:
                type = 2;
                break;
            case datebtn:
                type = 3;
                break;
            case etcbtn:
                type=4;
                break;
        }
    }


}

