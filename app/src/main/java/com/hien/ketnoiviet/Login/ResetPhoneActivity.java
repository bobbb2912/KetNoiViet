package com.hien.ketnoiviet.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hien.ketnoiviet.Personal.ChangePassword;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.LoadingDialog;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ResetPhoneActivity extends AppCompatActivity {

    Button continue_reset_phone, cancel_resetphone;
    Toolbar close_reset_phone;
    EditText phone_number_reset_phone;
    EditText so1phone,so2phone,so3phone,so4phone,so5phone,so6phone;
    TextView send_verifi_code_reset_phone,send_again_verifi_code_reset, verifi_code_reset_phone;
    private Dialog dialog;

    //region Khai báo authencation
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    //endregion
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_phone);
        viewbinding();
        send_again_verifi_code_reset.setVisibility(View.GONE);
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            event();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối!");
            return;
        }
    }
    //region Sự kiện
    private void event() {
        CreateDialog();
        firebaseAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        loadingBar.setTitle("Vui lòng chờ");
        loadingBar.setCanceledOnTouchOutside(false);
        close_reset_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Popup("Bạn có muốn hủy quá trình thay đổi mật khẩu?");
//                Intent i = new Intent(ResetPhoneActivity.this, LoginActivity.class);
//                startActivity(i);
//                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                loadingBar.dismiss();
//                Toast.makeText(getApplicationContext(), "Lỗi Firebase!", Toast.LENGTH_SHORT).show();
                Show_SnackBar(R.drawable.icon_toast_warning, "Lỗi Firebase!", "Đóng");

            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, forceResendingToken);
                Log.d(TAG, "onCodeSent: " + verificationId);
                mVerificationId = verificationId;
                forceResendingToken = token;
            }
        };
        send_verifi_code_reset_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phone_number_reset_phone.getText().toString();
                if (phone.equals("")){
//                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
//                    sound.playSound(ResetPhoneActivity.this, R.raw.toast);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng nhập số điện thoại!", "Đóng");

                }else {
                    phone = phone_number_reset_phone.getText().toString();
                    GetData(phone);
                    continue_reset_phone.setBackgroundResource(R.drawable.custom_button_border_round);
                    continue_reset_phone.setEnabled(true);
                }
            }
        });

        continue_reset_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone_number_reset_phone.getText().toString().equals("")){
//                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
//                    sound.playSound(ResetPhoneActivity.this, R.raw.toast);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng nhập số điện thoại!", "Đóng");
                    return;
                }
                String ip1phone = so1phone.getText().toString().trim();
                String ip2phone = so2phone.getText().toString().trim();
                String ip3phone = so3phone.getText().toString().trim();
                String ip4phone = so4phone.getText().toString().trim();
                String ip5phone = so5phone.getText().toString().trim();
                String ip6phone = so6phone.getText().toString().trim();
                String code = ip1phone + ip2phone + ip3phone + ip4phone + ip5phone + ip6phone ;
//                String code = verifi_code_reset_phone.getText().toString();
                if (TextUtils.isEmpty(code)){
//                    Toast.makeText(getApplicationContext(), "Vui lòng nhập mã xác nhận!", Toast.LENGTH_SHORT).show();
//                    sound.playSound(ResetPhoneActivity.this, R.raw.toast);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng nhập mã xác nhận!", "Đóng");

                }else {
                    verifiPhoneNumberWithCode(mVerificationId, code);
                }
            }
        });

        cancel_resetphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Popup("Bạn có muốn hủy quá trình thay đổi mật khẩu?");
            }
        });
    }
    //endregion

    //region Custom a notification
    public void Show_Popup(String text){

        sound.playSound(ResetPhoneActivity.this, R.raw.thongbao);

        Dialog dialog = new Dialog(ResetPhoneActivity.this);

        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title = dialog.findViewById(R.id.content_alert);
        Button ok = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setText("Thoát");

        title.setText(text);
        cancel.setOnClickListener(v -> dialog.dismiss());

        ok.setOnClickListener(view -> ResetPhoneActivity.this.finish());
        dialog.show();
    }
    //endregion

    //region Ghi đè nút back trên điện thoại, vô hiệu hóa quay lại màn hình trước
    @Override
    public void onBackPressed() {
        Show_Popup("Bạn có muốn hủy quá trình thay đổi mật khẩu?");
    }
    //endregion


    //region Tạo authencation
    private void startPhoneNumberVerification(String phone) {
//        loadingBar.setMessage("Mã xác nhận số điện thoại");
//        loadingBar.show();
        final LoadingDialog loadingDialog = new LoadingDialog(ResetPhoneActivity.this);
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dissmissDialog();
            }
        },4000);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void resendVerification(String phone, PhoneAuthProvider.ForceResendingToken token) {
//        loadingBar.setMessage("Gửi lại mã");
//        loadingBar.show();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L,TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .setForceResendingToken(token)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifiPhoneNumberWithCode(String verificationId, String code) {
//        loadingBar.setMessage("Mã xác nhận");
//        loadingBar.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        loadingBar.setMessage("Xác thực thành công");
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        loadingBar.dismiss();
                        String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
                        Intent i = new Intent(getApplicationContext(),ResetPasswordActivity.class);
                        i.putExtra("phonenumber_user", phone_number_reset_phone.getText().toString());
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingBar.dismiss();
//                        Toast.makeText(getApplicationContext(), "Mã xác thực không chính xác!Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
//                        sound.playSound(ResetPhoneActivity.this, R.raw.toast);
                        Show_SnackBar(R.drawable.icon_toast_warning, "Mã xác thực không chính xác!Vui lòng nhập lại!", "Đóng");
                    }
                });
    }
    //endregion

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
        Content_alert.setText("Tài khoản chưa tồn tại. Thực hiện đăng ký tài khoản?");

        Okay.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            dialog.dismiss();
        });

        Cancel.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            dialog.dismiss();
        });
    }

    private void viewbinding() {
        cancel_resetphone = findViewById(R.id.cancel_resetphone);
        continue_reset_phone = findViewById(R.id.continue_reset_phone);
        close_reset_phone = findViewById(R.id.toolbar_close_reset_phone);
        phone_number_reset_phone = findViewById(R.id.phone_number_reset_phone);
        send_verifi_code_reset_phone = findViewById(R.id.send_verifi_code_reset_phone);
        send_again_verifi_code_reset = findViewById(R.id.send_again_verifi_code_reset);
        continue_reset_phone.setBackgroundResource(R.drawable.custom_button_enable_border_round);
        continue_reset_phone.setEnabled(false);
        so1phone = findViewById(R.id.input_one_phone);
        so2phone = findViewById(R.id.input_tow_phone);
        so3phone = findViewById(R.id.input_three_phone);
        so4phone = findViewById(R.id.input_four_phone);
        so5phone = findViewById(R.id.input_five_phone);
        so6phone = findViewById(R.id.input_six_phone);
        ChuyenNut();
    }

    private void GetData(String phonenb) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.checkaccount;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, response -> {
            if(response.equals("Chuatontai")){
                dialog.show();
                sound.playSound(ResetPhoneActivity.this, R.raw.thongbao);
            }else {
                String phone = "+84" + phone_number_reset_phone.getText().toString().substring(1);
                startPhoneNumberVerification(phone);
            }
        }, error -> {
            Show_SnackBar(R.drawable.icon_toast_warning, "Mã xác thực không chính xác!Vui lòng nhập lại!", "Đóng");
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phonenumber",phonenb);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ChuyenNut() {
        so1phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    so2phone.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        so2phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    so3phone.requestFocus();
                }
                if (charSequence.toString().trim().equals("")){
                    so1phone.requestFocus();
                }
                if (!charSequence.toString().trim().equals("")){
                    so3phone.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        so3phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    so4phone.requestFocus();
                }
                if (charSequence.toString().trim().equals("")){
                    so2phone.requestFocus();
                }
                if (!charSequence.toString().trim().equals("")){
                    so4phone.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        so4phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    so5phone.requestFocus();
                }
                if (charSequence.toString().trim().equals("")){
                    so3phone.requestFocus();
                }
                if (!charSequence.toString().trim().equals("")){
                    so5phone.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        so5phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    so6phone.requestFocus();
                }
                if (charSequence.toString().trim().equals("")){
                    so4phone.requestFocus();
                }
                if (!charSequence.toString().trim().equals("")){
                    so6phone.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        so6phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().equals("")){
                    so5phone.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

        View view = findViewById(R.id.layout_reset_phone);
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
        sound.playSound(ResetPhoneActivity.this, R.raw.toast);
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