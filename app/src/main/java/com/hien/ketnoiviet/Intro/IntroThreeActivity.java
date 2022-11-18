package com.hien.ketnoiviet.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageButton;

import com.hien.ketnoiviet.Login.LoginActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.Login.RegisterActivity;

public class IntroThreeActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    //region Khai báo controls
    //Vuốt trái, phải màn hình
    private float x1;
    private float y1;
    private GestureDetector gestureDetector;
    //
    Button register_introthree,login_introthree;
    ImageButton instagram_intro,facebook_intro,twitter_intro;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_three);

        //Vuốt màn hình trái phải
        this.gestureDetector = new GestureDetector(IntroThreeActivity.this, this);
        //
        viewBinding();
        event();


    }

    //region Sự kiện các nút
    private void event() {
        login_introthree.setOnClickListener(view -> {
            Intent i = new Intent(IntroThreeActivity.this, LoginActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        });
        register_introthree.setOnClickListener(view -> {
            Intent i = new Intent(IntroThreeActivity.this, RegisterActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        });
        facebook_intro.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,    Uri.parse("http://www.facebook.com/")));
            }
        });
        instagram_intro.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,    Uri.parse("http://www.instagram.com/")));
            }
        });
        twitter_intro.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,    Uri.parse("http://www.twitter.com/")));
            }
        });
    }
    //endregion

    //region Ánh xạ các thuộc tính
    private void viewBinding() {
        login_introthree = findViewById(R.id.login_introthree);
        register_introthree = findViewById(R.id.register_introthree);
        facebook_intro = findViewById(R.id.facebook_intro);
        instagram_intro = findViewById(R.id.instagram_intro);
        twitter_intro = findViewById(R.id.twitter_intro);
    }
    //endregion

    //region Sự kiện Vuốt màn hình trái phải
    //Overide on touch
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
                        Intent i = new Intent(this, IntroTwoActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                    }
                    else {
//                        Toast.makeText(this, "Man hinh Dang nhap", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent(this, IntroThreeActivity.class);
//                        startActivity(i);
//                        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                    }
                }
                else if (Math.abs(valueY) > MIN_DISTANCE){
                    if (y2 > y1){
//                        Toast.makeText(this, "Keo xuong", Toast.LENGTH_SHORT).show();
                    }
                    else {
//                        Toast.makeText(this, "Ket len", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        return super.onTouchEvent(event);
    }
    //Chuyển màn hình vuốt phải,trái
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
}