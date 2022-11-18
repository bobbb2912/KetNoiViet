package com.hien.ketnoiviet.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.hien.ketnoiviet.Intro.IntroOneActivity;
import com.hien.ketnoiviet.Personal.Payment;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResetPasswordActivity extends AppCompatActivity {

    private boolean mIsShowPass = false;
    EditText password_again_reset_password,password_reset_password;
    Button confirm_reset_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        viewbinding();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            event();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối!");
        }

    }


    private void event() {
        confirm_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = password_reset_password.getText().toString();
                String password_again = password_again_reset_password.getText().toString();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                checkPassword();
            }
        });


    }

    private void viewbinding() {
        password_again_reset_password = findViewById(R.id.password_again_reset_password);
        password_reset_password = findViewById(R.id.password_reset_password);
        confirm_reset_password = findViewById(R.id.confirm_reset_password);
    }

    private void checkPassword() {
        String password_first = password_reset_password.getText().toString();
        String password_again = password_again_reset_password.getText().toString();
        if (password_first.equals("") || password_again.equals("")){
//            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//            sound.playSound(ResetPasswordActivity.this, R.raw.toast);
            Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng nhập đầy đủ thông tin!", "Đóng");
        }
        else {
            if (!password_first.equals(password_again)){
//                Toast.makeText(getApplicationContext(), "Mật khẩu không khớp !", Toast.LENGTH_SHORT).show();
//                sound.playSound(ResetPasswordActivity.this, R.raw.toast);
                Show_SnackBar(R.drawable.icon_toast_warning, "Mật khẩu không khớp !", "Đóng");

            }
            else {
                if ((password_first.length() >= 8) &&
                        (password_first.length() <= 20) &&
                        (password_first.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&.,;'\"]{8,}$"))){
                    ResetAccount();
                }
                else{
//                    Toast.makeText(getApplicationContext(), "Thử lại mật khẩu khác!", Toast.LENGTH_LONG).show();
//                    sound.playSound(ResetPasswordActivity.this, R.raw.toast);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Thử lại mật khẩu khác!", "Đóng");

                }
            }
        }
    }
    private void ResetAccount() {
        //Tiến hành đăng ký
        Intent intent = getIntent();
        String phonenumber_user = intent.getStringExtra("phonenumber_user");
        String phonenumber = phonenumber_user;
        String password = password_reset_password.getText().toString().trim();


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.resetpw, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Done")){
                    //Xuất hiện alert dialog tiến hành đăng nhập lại
                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    sound.playSound(ResetPasswordActivity.this, R.raw.thongbao);
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Đổi mật khẩu không thành công!", Toast.LENGTH_SHORT).show();
//                    sound.playSound(ResetPasswordActivity.this, R.raw.toast);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Đổi mật khẩu không thành công!", "Đóng");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Lỗi " + error.toString(), Toast.LENGTH_SHORT).show();
                Show_SnackBar(R.drawable.icon_toast_warning, "Lỗi!", "Đóng");
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phonenumber", phonenumber);
                param.put("password", password);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    //region Custom a notification
    public void Show_Popup(String text){

        sound.playSound(ResetPasswordActivity.this, R.raw.thongbao);

        Dialog dialog = new Dialog(ResetPasswordActivity.this);

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
                ResetPasswordActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
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

    public  final void Show_SnackBar( int i, String t, String a){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_snackbar,findViewById(R.id.snack_constraint), false);

        ImageView icon = layout.findViewById(R.id.im_icon);
        icon.setImageResource(i);
        TextView text = layout.findViewById(R.id.tv_message);
        text.setText(t.toString());
        TextView action = layout.findViewById(R.id.tv_action);
        action.setText(a.toString());

        View view = findViewById(R.id.layout_reset_pass);
        int duration = 5000;
        String mess = "";
        Snackbar s = Snackbar.make(view, mess, duration);

        s.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout sl = (Snackbar.SnackbarLayout) s.getView();
        sl.setPadding(0,0,0,0);

        //for dismiss
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.dismiss();
            }
        });
        sl.addView(layout,0);
        s.show();
        sound.playSound(ResetPasswordActivity.this, R.raw.toast);
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