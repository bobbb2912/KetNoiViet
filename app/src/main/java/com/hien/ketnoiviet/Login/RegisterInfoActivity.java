package com.hien.ketnoiviet.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterInfoActivity extends AppCompatActivity {

    Button confirm_register_info, cancel_register;
    EditText name_register_info,password_register_info,password_again_register_info;
    TextView term_privacy_register, password_request_register;

    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        viewbinding();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            event();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối!");
        }
    }

    private void event() {
        CreateDialog();
        term_privacy_register.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bobbb2912.github.io/"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,    Uri.parse("https://bobbb2912.github.io/")));
            }
        });

        confirm_register_info.setOnClickListener(view -> checkPassword());

        cancel_register.setOnClickListener(view -> startActivity(new Intent(RegisterInfoActivity.this, LoginActivity.class)));
    }

    private void CreateDialog() {
        //Create the Dialog here
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button Okay = dialog.findViewById(R.id.btn_okay);
        Button Cancel = dialog.findViewById(R.id.btn_cancel);
        TextView Content_alert = dialog.findViewById(R.id.content_alert);
        Okay.setText("Đồng ý");
        Cancel.setText("Không");
        Content_alert.setText("Đăng ký thành công! Trở về trang đăng nhập");


        Okay.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            dialog.dismiss();
        });

        Cancel.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            dialog.dismiss();
        });
    }

    @SuppressLint("ResourceAsColor")
    private void checkPassword() {
        String name = name_register_info.getText().toString();
        String password_first = password_register_info.getText().toString();
        String password_again = password_again_register_info.getText().toString();
        if (name.equals("") || password_first.equals("") || password_again.equals("")){
            sound.playSound(RegisterInfoActivity.this, R.raw.toast);
            Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng nhập đầy đủ thông tin", "Đóng");

        }
        else {
            if (!password_first.equals(password_again)){
                sound.playSound(RegisterInfoActivity.this, R.raw.toast);
                Show_SnackBar(R.drawable.icon_toast_warning, "Mật khẩu không khớp !", "Đóng");
            }
            else {
                if ((password_first.length() >= 8) &&
                        (password_first.length() <= 20) &&
                        (password_first.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&.,;'\"]{8,}$"))){
                        RegisterAccount();
                }
                else{
                    sound.playSound(RegisterInfoActivity.this, R.raw.toast);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Thử lại mật khẩu khác!", "Đóng");
                }
            }
        }
    }

    private void RegisterAccount() {
        //Tiến hành đăng ký
        Intent intent = getIntent();
        String phonenumber_user = intent.getStringExtra("phonenumber_user");
        String  phonenumber = phonenumber_user;
        String nameuser = name_register_info.getText().toString().trim();

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MM/yyyy");
        Format f = new SimpleDateFormat("dd/MM/yyyy");
        String datecreate = f.format(new Date());
        String password = password_register_info.getText().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.register, response -> {
            if(response.equals("Done")){
                dialog.show();
                sound.playSound(RegisterInfoActivity.this, R.raw.thongbao);
            }
            else {
                Show_SnackBar(R.drawable.icon_toast_warning, "Đăng ký không thành công!", "Đóng");
            }
        }, error -> Show_SnackBar(R.drawable.icon_toast_warning, "Lỗi!", "Đóng")){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phonenumber", phonenumber);
                param.put("nameuser", nameuser);
                param.put("datecreate", datecreate);
                param.put("password", password);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void viewbinding() {
        confirm_register_info = findViewById(R.id.confirm_register_info);
        cancel_register = findViewById(R.id.cancel_register_info);
        name_register_info = findViewById(R.id.name_register_info);
        password_register_info = findViewById(R.id.password_register_info);
        term_privacy_register = findViewById(R.id.term_privacy_register);
        password_request_register = findViewById(R.id.password_request_register);
        password_again_register_info = findViewById(R.id.password_again_register_info);

    }

    //region Ghi đè nút back trên điện thoại, vô hiệu hóa quay lại màn hình trước
    @Override
    public void onBackPressed() {
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

        View view = findViewById(R.id.layout_register_infor);
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
        sound.playSound(RegisterInfoActivity.this, R.raw.toast);
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