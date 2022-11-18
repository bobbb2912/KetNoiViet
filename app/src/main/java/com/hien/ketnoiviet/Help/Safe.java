package com.hien.ketnoiviet.Help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.hien.ketnoiviet.R;

public class Safe extends AppCompatActivity {

    Toolbar close_Safe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        event();
    }

    private void event() {
        close_Safe = findViewById(R.id.close_Safe);
        close_Safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Safe.this.finish();
            }
        });
    }
}