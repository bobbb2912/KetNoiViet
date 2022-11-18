package com.hien.ketnoiviet.Others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Intro.IntroOneActivity;
import com.hien.ketnoiviet.Intro.IntroTwoActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class imgFitScreen extends AppCompatActivity implements GestureDetector.OnGestureListener{

    PhotoView imgDisplay;
    Button btnClose;
    //Vuốt trái, phải màn hình
    private float x1;
    private float y1;
    private GestureDetector gestureDetector;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_fit_screen);

        Anhxa();

        Bundle extras = getIntent().getExtras();
        String url1 = extras.getString("url1");
        String url2 = extras.getString("url2");
        String url3 = extras.getString("url3");
        String url4 = extras.getString("url4");
        String avatar = extras.getString("avatar");
        String cover = extras.getString("cover");

        Glide.with(getApplicationContext()).load(url3).into(imgDisplay);
        Glide.with(getApplicationContext()).load(url4).into(imgDisplay);

        if(url1 == null) {
            imgDisplay.setImageResource(R.drawable.no_image);
            if (url2 == null) {
                imgDisplay.setImageResource(R.drawable.no_image);
                if (url3 == null){
                    imgDisplay.setImageResource(R.drawable.no_image);
                    if (url4 == null){
                        imgDisplay.setImageResource(R.drawable.no_image);
                        if(avatar == null){
                            imgDisplay.setImageResource(R.drawable.no_image);
                            if(cover == null){
                                imgDisplay.setImageResource(R.drawable.no_image);
                            }else Glide.with(getApplicationContext()).load(cover).into(imgDisplay);
                        }else Glide.with(getApplicationContext()).load(avatar).into(imgDisplay);
                    }else Glide.with(getApplicationContext()).load(url4).into(imgDisplay);
                }else Glide.with(getApplicationContext()).load(url3).into(imgDisplay);
            }else Glide.with(getApplicationContext()).load(url2).into(imgDisplay);
        }else Glide.with(getApplicationContext()).load(url1).into(imgDisplay);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFitScreen.this.finish();
            }
        });

        //Vuốt màn hình trái phải
        this.gestureDetector = new GestureDetector(imgFitScreen.this, this);
    }

    public void Anhxa(){
        imgDisplay =  findViewById(R.id.imgDisplay);
        btnClose = (Button) findViewById(R.id.btnClose);
    }

    //region Sự kiện Vuốt màn hình trái phải
    //Overide on touch event
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        int MIN_DISTANCE = 150;
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float x2 = event.getX();
                float y2 = event.getY();
                float valueX = x2 - x1;
                float valueY = y2 - y1;
                if(Math.abs(valueX) > MIN_DISTANCE){
                    if (x2 > x1){
//                            Intent i = new Intent(this, MainActivity.class);
//                            startActivity(i);
                    }
                    else {
//                        Intent i = new Intent(this, IntroTwoActivity.class);
//                        startActivity(i);
//                        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
                    }
                }
                else if (Math.abs(valueY) > MIN_DISTANCE){
                    if (y2 > y1){
                        //Toast.makeText(this, "Keo xuong", Toast.LENGTH_SHORT).show();
                        imgFitScreen.this.finish();
                    }
                    else {
                        //Toast.makeText(this, "Ket len", Toast.LENGTH_SHORT).show();
                        imgFitScreen.this.finish();
                    }
                }
        }
        return super.onTouchEvent(event);
    }
    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
    //Chuyển màn hình vuốt phải,trái
    //endregion

    //region check internet
    com.hien.ketnoiviet.ultil.networkChangeListener networkChangeListener = new networkChangeListener();
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    //endregion
}