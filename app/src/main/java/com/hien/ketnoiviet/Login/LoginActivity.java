package com.hien.ketnoiviet.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.hien.ketnoiviet.Discover.PostActivity;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Personal.UpdateInformation;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.LoadingDialog;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    //region Khai báo các controls
    //


    EditText edittext_email_login,edittext_password_login;
    TextView term_privacy_policy, register_login, reset_password_login;
    Button login_login;
    ImageButton facebook_login,instagram_login,twitter_login;
    CheckBox remember_me;
    private static final String FILE_NAME = "myFile";
    private static final String FILE_CHECK = "mycheck";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewbinding();
        event();
    }

    //region Sự kiện
    private void event() {
        term_privacy_policy.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://bobbb2912.github/"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,    Uri.parse("https://bobbb2912.github/")));
            }
        });
        register_login.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
        });
        reset_password_login.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, ResetPhoneActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_top);
        });
        facebook_login.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,    Uri.parse("http://www.facebook.com/")));
            }
        });
        instagram_login.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,    Uri.parse("http://www.instagram.com/")));
            }
        });
        twitter_login.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW,    Uri.parse("http://www.twitter.com/")));
            }
        });
        login_login.setOnClickListener(view -> {
            String edittext_phone = edittext_email_login.getText().toString();
            String edittext_password = edittext_password_login.getText().toString();
            if(edittext_phone.equals("")||edittext_password.equals("")){
                Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng điền đủ thông tin!","Đóng");
            }
            else {
                edittext_phone = edittext_email_login.getText().toString();
                edittext_password = edittext_password_login.getText().toString();
                Login(edittext_phone, edittext_password);
            }
        });
    }

    private void Check() {
        SharedPreferences.Editor editor = getSharedPreferences(FILE_CHECK, MODE_PRIVATE).edit();
        editor.putString("isDangxuat","no");
        editor.apply();
    }

    //Kiểm tra đăng nhập
    private void Login(String edittext_phone, String edittext_password) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.login;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, response -> {
            if(response.equals("1")){
                Remember(edittext_phone,edittext_password);
                Check();
                SharedPreferences.Editor editor1 = getSharedPreferences(FILE_CHECK, MODE_PRIVATE).edit();
                editor1.putString("phone",edittext_phone);
                editor1.apply();
                Intent i = new Intent(getApplicationContext(),HomeActivity.class);
                i.putExtra("phonenumber_user", edittext_phone);
                final LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);
                loadingDialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dissmissDialog();
                        //startActivity(i);
                    }
                },2000);

                Handler handlerx = new Handler();
                handlerx.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Show_Toast("Đăng nhập thành công.", R.drawable.icon_check_success);
                        startActivity(i);
                    }
                },2010);
//                Show_Toast("Đăng nhập thành công.", R.drawable.icon_check_success);

            }else {
                Show_Toast("Sai thông tin đăng nhập!",R.drawable.icon_error);
                sound.playSound(LoginActivity.this, R.raw.thongbao);
            }
        }, error -> Show_Toast("Sai thông tin đăng nhập!",R.drawable.icon_error)){
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();
                param.put("phonenumber",edittext_phone);
                param.put("password",edittext_password);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    //Chức năng ghi nhớ mật khẩu
    private void Remember(String edittext_phone, String edittext_password) {
        if (remember_me.isChecked()){
            SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
            editor.putString("edittext_phone",edittext_phone);
            editor.putString("edittext_password",edittext_password);
            editor.apply();
        }else {
            SharedPreferences.Editor editor = getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
            editor.putString("edittext_phone","");
            editor.putString("edittext_password","");
            editor.apply();
        }
    }
    //endregion

    //region Ánh xạ
    private void viewbinding() {
        edittext_email_login = findViewById(R.id.edittext_email_login);
        edittext_password_login = findViewById(R.id.edittext_password_login);
        term_privacy_policy = findViewById(R.id.term_privacy_policy);
        register_login = findViewById(R.id.register_login);
        reset_password_login = findViewById(R.id.reset_password_login);
        login_login = findViewById(R.id.login_login);
        facebook_login = findViewById(R.id.facebook_login);
        instagram_login = findViewById(R.id.instagram_login);
        twitter_login = findViewById(R.id.twitter_login);
        remember_me = findViewById(R.id.remember_me);
        remember_me.setChecked(true);

        SharedPreferences sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        String edittext_phone = sharedPreferences.getString("edittext_phone", "");
        String edittext_password = sharedPreferences.getString("edittext_password", "");

        edittext_email_login.setText(edittext_phone);
        edittext_password_login.setText(edittext_password);
    }
    //endregion

    //region Custom a toast
    public final void Show_Toast(String t, int s){
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.toast,
                findViewById(R.id.toast_layout_root));

        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(s);
        TextView text = layout.findViewById(R.id.text);
        text.setText(t);//
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    //endregion

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

        View view = findViewById(R.id.layout_login);
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
        sound.playSound(LoginActivity.this, R.raw.toast);
    }

    //region check internet
    networkChangeListener networkChangeListener = new networkChangeListener();
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