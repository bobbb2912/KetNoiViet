package com.hien.ketnoiviet.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import com.hien.ketnoiviet.Login.LoginActivity;
import com.hien.ketnoiviet.R;

public class IntroOneActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    //Vuốt trái, phải màn hình
    private float x1;
    private float y1;
    private GestureDetector gestureDetector;
    //
    TextView skip_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_one);

        //Vuốt màn hình trái phải
        this.gestureDetector = new GestureDetector(IntroOneActivity.this, this);

        skip_intro = findViewById(R.id.skip_intro_introone);
        skip_intro.setOnClickListener(view -> {
            Intent i = new Intent(IntroOneActivity.this, LoginActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
        });
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
                            Intent i = new Intent(this, IntroTwoActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
                        }
                    }
                    else if (Math.abs(valueY) > MIN_DISTANCE){
                        if (y2 > y1){
//                            Toast.makeText(this, "Keo xuong", Toast.LENGTH_SHORT).show();
                        }
                        else {
//                            Toast.makeText(this, "Ket len", Toast.LENGTH_SHORT).show();
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
}