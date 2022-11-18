package com.hien.ketnoiviet.Personal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hien.ketnoiviet.Login.ResetPhoneActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

public class ChangePassword extends AppCompatActivity {

    Button huy, xacNhan;
    TextView open_forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        event();
    }

    private void event() {
        huy = findViewById(R.id.cancel_changePass);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Popup("Bạn có muốn hủy quá trình thay đổi mật khẩu?");
            }
        });

        open_forgot_pass = findViewById(R.id.open_forgot_pass);
        open_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangePassword.this, ResetPhoneActivity.class);
                startActivity(i);
            }
        });

        xacNhan = findViewById(R.id.submit_changePass);



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

    //region Custom a notification
    public void Show_Popup(String text){

        sound.playSound(ChangePassword.this, R.raw.thongbao);

        Dialog dialog = new Dialog(ChangePassword.this);

        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title = dialog.findViewById(R.id.content_alert);
        Button ok = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setText("Thoát");

        title.setText(text);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword.this.finish();
            }
        });
        dialog.show();
    }
    //endregion

    //region Ghi đè nút back trên điện thoại, vô hiệu hóa quay lại màn hình trước
    @Override
    public void onBackPressed() {
        Show_Popup("Bạn có muốn hủy quá trình thay đổi mật khẩu?");
    }
    //endregion
}