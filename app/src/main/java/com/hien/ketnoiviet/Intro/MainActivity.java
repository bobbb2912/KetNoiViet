package com.hien.ketnoiviet.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Login.LoginActivity;
import com.hien.ketnoiviet.R;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_CHECK = "mycheck";
    ProgressBar progress_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress_bar = findViewById(R.id.progress_bar);
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_CHECK, MODE_PRIVATE);
        String Dangxuat = sharedPreferences.getString("isDangxuat", "");
//        Toast.makeText(getApplicationContext(), ""  + Dangxuat, Toast.LENGTH_SHORT).show();
        if (Dangxuat.equals("no")){
            progress_bar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
                finish();
                progress_bar.setVisibility(View.GONE);
            },4000);
        }
        else if (Dangxuat.equals("yes")) {
            progress_bar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
                finish();
                progress_bar.setVisibility(View.GONE);
            },4000);
        }
        else {
            progress_bar.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                Intent i = new Intent(getApplicationContext(), IntroOneActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
                finish();
                progress_bar.setVisibility(View.GONE);
            },4000);

        }
    }
}