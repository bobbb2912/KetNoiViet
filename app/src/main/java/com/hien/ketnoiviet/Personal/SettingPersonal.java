package com.hien.ketnoiviet.Personal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hien.ketnoiviet.Intro.MainActivity;
import com.hien.ketnoiviet.Login.ResetPasswordActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.networkChangeListener;

public class SettingPersonal extends AppCompatActivity {

    ToggleButton toggle_setting;
    TextView change_pass_setting;
    Switch switch_theme_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_personal);
        anhxa();
        event();
    }

    private void event() {
        change_pass_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
            }
        });
        switch_theme_setting.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton,
                                                 boolean b) {
                        if (switch_theme_setting.isChecked()){
                            Toast.makeText(getApplicationContext(), "Tối", Toast.LENGTH_SHORT).show();
                            switch_theme_setting.setText("Tối");
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Sáng", Toast.LENGTH_SHORT).show();
                            switch_theme_setting.setText("Sáng");
                        }
                    }
                });
        CompoundButton.OnCheckedChangeListener listener =
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (toggle_setting.isChecked()){
                            Toast.makeText(getApplicationContext(), "Tiếng Anh", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Tiếng Việt", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        toggle_setting.setOnCheckedChangeListener(listener);
    }

    private void anhxa() {
        change_pass_setting = findViewById(R.id.change_pass_setting);
        switch_theme_setting = findViewById(R.id.switch_theme_setting);
        toggle_setting = findViewById(R.id.toggle_setting);
        toggle_setting.setChecked(false);
    }

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