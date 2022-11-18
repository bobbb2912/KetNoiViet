package com.hien.ketnoiviet.Help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.google.rpc.Help;
import com.hien.ketnoiviet.R;

public class HelpCenter extends AppCompatActivity {

    Toolbar close_helpCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        event();
    }

    private void event() {
        close_helpCenter = findViewById(R.id.close_helpCenter);
        close_helpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpCenter.this.finish();
            }
        });
    }
}