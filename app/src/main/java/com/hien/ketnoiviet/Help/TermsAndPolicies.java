package com.hien.ketnoiviet.Help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.hien.ketnoiviet.R;

public class TermsAndPolicies extends AppCompatActivity {

    Toolbar close_termnpolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_policies);
        event();
    }

    private void event() {
        close_termnpolicy = findViewById(R.id.close_termnpolicy);
        close_termnpolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TermsAndPolicies.this.finish();
            }
        });
    }
}