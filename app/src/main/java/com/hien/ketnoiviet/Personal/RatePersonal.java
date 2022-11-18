package com.hien.ketnoiviet.Personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import java.util.HashMap;
import java.util.Map;

public class RatePersonal extends AppCompatActivity {

    ImageButton rate_star_1,rate_star_2,rate_star_3,rate_star_4,rate_star_5;
    Button rate_button;
    TextView rate_text;
    EditText rate_input;
    String ratePoint = "1", rateinput = "";
    Toolbar toolbar_rate_app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        anhXa();
        event();
    }

    private void resetStar() {
        rate_star_1.setImageResource(R.drawable.icon_star_gray);
        rate_star_2.setImageResource(R.drawable.icon_star_gray);
        rate_star_3.setImageResource(R.drawable.icon_star_gray);
        rate_star_4.setImageResource(R.drawable.icon_star_gray);
        rate_star_5.setImageResource(R.drawable.icon_star_gray);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void event() {
        rate_star_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound.playSound(RatePersonal.this, R.raw.click_mouse);
                resetStar();
                rate_star_1.setImageResource(R.drawable.icon_star_yellow);
                ratePoint = "1";
            }
        });
        rate_star_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound.playSound(RatePersonal.this, R.raw.click_mouse);
                resetStar();
                rate_star_1.setImageResource(R.drawable.icon_star_yellow);
                rate_star_2.setImageResource(R.drawable.icon_star_yellow);
                ratePoint = "2";
            }
        });
        rate_star_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound.playSound(RatePersonal.this, R.raw.click_mouse);
                resetStar();
                rate_star_1.setImageResource(R.drawable.icon_star_yellow);
                rate_star_2.setImageResource(R.drawable.icon_star_yellow);
                rate_star_3.setImageResource(R.drawable.icon_star_yellow);
                ratePoint = "3";
            }
        });
        rate_star_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound.playSound(RatePersonal.this, R.raw.click_mouse);
                resetStar();
                rate_star_1.setImageResource(R.drawable.icon_star_yellow);
                rate_star_2.setImageResource(R.drawable.icon_star_yellow);
                rate_star_3.setImageResource(R.drawable.icon_star_yellow);
                rate_star_4.setImageResource(R.drawable.icon_star_yellow);
                ratePoint = "4";
            }
        });
        rate_star_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound.playSound(RatePersonal.this, R.raw.click_mouse);
                resetStar();
                rate_star_1.setImageResource(R.drawable.icon_star_yellow);
                rate_star_2.setImageResource(R.drawable.icon_star_yellow);
                rate_star_3.setImageResource(R.drawable.icon_star_yellow);
                rate_star_4.setImageResource(R.drawable.icon_star_yellow);
                rate_star_5.setImageResource(R.drawable.icon_star_yellow);
                ratePoint = "5";
            }
        });
        rate_button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                rateinput = rate_input.getText().toString();
                if (rateinput.equals("")){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập nội dung!", Toast.LENGTH_SHORT).show();
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng nhập nội dung!", "Đóng");
                    sound.playSound(getApplicationContext(), R.raw.toast);
                }
                else {
//                    Toast.makeText(getApplicationContext(), "Đánh giá: " + ratePoint + rateinput, Toast.LENGTH_SHORT).show();
                    rate_text.setTextColor(R.color.purple_primary);
                    rate_text.setTextSize(16);
                    rate_input.clearFocus();
                    updateRate();
                    Show_Toast("Gửi đánh giá thành công.",R.drawable.icon_check_success);
                    sound.playSound(getApplicationContext(), R.raw.thongbao);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    },2000);
                    RatePersonal.this.finish();
                }
            }
        });
        rate_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        toolbar_rate_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatePersonal.this.finish();
            }
        });
    }

    private void updateRate() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Server.addRateapp
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Lỗi" + error.getMessage(), Toast.LENGTH_LONG).show();
                Show_SnackBar(R.drawable.icon_toast_warning, "Lỗi!", "Đóng");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phoneuser", HomeActivity.phone_number_user);
                params.put("star", ratePoint);
                params.put("content", rateinput);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void anhXa() {
        rate_star_1 = findViewById(R.id.rate_star_1);
        rate_star_2 = findViewById(R.id.rate_star_2);
        rate_star_3 = findViewById(R.id.rate_star_3);
        rate_star_4 = findViewById(R.id.rate_star_4);
        rate_star_5 = findViewById(R.id.rate_star_5);
        rate_button = findViewById(R.id.rate_button);
        rate_text = findViewById(R.id.rate_text);
        rate_input = findViewById(R.id.rate_input);
        toolbar_rate_app = findViewById(R.id.toolbar_rate_app);
    }

    public  final void Show_SnackBar( int i, String t, String a){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_snackbar,findViewById(R.id.snack_constraint), false);

        ImageView icon = layout.findViewById(R.id.im_icon);
        icon.setImageResource(i);
        TextView text = layout.findViewById(R.id.tv_message);
        text.setText(t.toString());
        TextView action = layout.findViewById(R.id.tv_action);
        action.setText(a.toString());

        View view = findViewById(R.id.layout_rate_personal);
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
    }

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
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
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