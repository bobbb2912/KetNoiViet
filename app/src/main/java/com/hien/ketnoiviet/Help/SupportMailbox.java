package com.hien.ketnoiviet.Help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.hien.ketnoiviet.R;

public class SupportMailbox extends AppCompatActivity {

    Toolbar close_support_mailbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_mailbox);
        event();
    }

    private void event() {
        close_support_mailbox = findViewById(R.id.close_support_mailbox);
        close_support_mailbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SupportMailbox.this.finish();
            }
        });
    }
}