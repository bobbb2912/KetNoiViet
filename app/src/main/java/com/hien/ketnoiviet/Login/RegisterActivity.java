package com.hien.ketnoiviet.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.util.Listener;
import com.hien.ketnoiviet.Discover.PostActivity;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Intro.IntroThreeActivity;
import com.hien.ketnoiviet.Intro.MainActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.LoadingDialog;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    //region Khai b??o controls
    Button continue_register, cancel_register;
    Toolbar close_register;
    EditText phone_number;
    EditText so1,so2,so3,so4,so5,so6;
    EditText verifi_code_register;
    TextView send_verifi_code_register,send_again_verifi_code_register;
    private boolean isSend_code_one = false;
    boolean isExist = false;
    private Dialog dialog;
    //endregion

    //region Khai b??o authencation
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
        setContentView(R.layout.activity_register);
        viewbinding();
        send_again_verifi_code_register.setVisibility(View.INVISIBLE);
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            event();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "B???n h??y ki???m tra l???i k???t n???i!");
            return;
        }
    }

    //region ??nh x???
    private void viewbinding() {
        continue_register = findViewById(R.id.continue_register);
        close_register = findViewById(R.id.toolbar_close_register);
        cancel_register = findViewById(R.id.cancel_register);
        phone_number = findViewById(R.id.phone_number_register);
//        verifi_code_register = findViewById(R.id.verifi_code_register);
        send_verifi_code_register = findViewById(R.id.send_verifi_code_register);
        send_again_verifi_code_register = findViewById(R.id.send_again_verifi_code_register);
        continue_register.setBackgroundResource(R.drawable.custom_button_enable_border_round);
        continue_register.setEnabled(false);
        so1 = findViewById(R.id.input_one);
        so2 = findViewById(R.id.input_tow);
        so3 = findViewById(R.id.input_three);
        so4 = findViewById(R.id.input_four);
        so5 = findViewById(R.id.input_five);
        so6 = findViewById(R.id.input_six);
        ChuyenNut();
    }

    private void ChuyenNut() {
        so1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    so2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        so2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    so3.requestFocus();
                }
                if (charSequence.toString().trim().equals("")){
                    so1.requestFocus();
                }
                if (!charSequence.toString().trim().equals("")){
                    so3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        so3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    so4.requestFocus();
                }
                if (charSequence.toString().trim().equals("")){
                    so2.requestFocus();
                }
                if (!charSequence.toString().trim().equals("")){
                    so4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        so4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    so5.requestFocus();
                }
                if (charSequence.toString().trim().equals("")){
                    so3.requestFocus();
                }
                if (!charSequence.toString().trim().equals("")){
                    so5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        so5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().equals("")){
                    so6.requestFocus();
                }
                if (charSequence.toString().trim().equals("")){
                    so4.requestFocus();
                }
                if (!charSequence.toString().trim().equals("")){
                    so6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        so6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().equals("")){
                    so5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    //endregion

    //region S??? ki???n
    private void event() {
        CreateDialog();
        firebaseAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        loadingBar.setTitle("Vui l??ng ch???");
        loadingBar.setCanceledOnTouchOutside(false);

        cancel_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Popup("B???n c?? mu???n h???y b??? qu?? tr??nh ????ng k???");
            }
        });

        close_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                //startActivity(i);
                Show_Popup("B???n c?? mu???n h???y b??? qu?? tr??nh ????ng k???");
                //overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
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
                //Toast.makeText(getApplicationContext(), "L???i Firebase!", Toast.LENGTH_SHORT).show();
                Show_SnackBar(R.drawable.icon_toast_warning, "L???i Firebase!", "????ng");
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, forceResendingToken);
                Log.d(TAG, "onCodeSent: " + verificationId);
                mVerificationId = verificationId;
                forceResendingToken = token;
            }
        };
        send_verifi_code_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phone_number.getText().toString();
                if (phone.equals("")){
//                    Toast.makeText(getApplicationContext(), "Vui l??ng nh???p s??? ??i???n tho???i!", Toast.LENGTH_SHORT).show();
//                    sound.playSound(RegisterActivity.this, R.raw.toast);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui l??ng nh???p s??? ??i???n tho???i!", "????ng");
                }else {
                    phone = phone_number.getText().toString();
                    GetData(phone);
                    continue_register.setBackgroundResource(R.drawable.custom_button_border_round);
                    continue_register.setEnabled(true);
                }
            }
        });

        if(isSend_code_one == true){
            send_again_verifi_code_register.setVisibility(View.VISIBLE);
        }
        send_again_verifi_code_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phone_number.getText().toString();
                if (phone.equals("")){
//                    Toast.makeText(getApplicationContext(), "Vui l??ng nh???p s??? ??i???n tho???i!", Toast.LENGTH_SHORT).show();
//                    sound.playSound(RegisterActivity.this, R.raw.toast);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui l??ng nh???p s??? ??i???n tho???i!", "????ng");
                }else {
                    GetData(phone);
                }
            }
        });
        continue_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone_number.getText().toString().equals("")){
//                    Toast.makeText(getApplicationContext(), "Vui l??ng nh???p s??? ??i???n tho???i!", Toast.LENGTH_SHORT).show();
//                    sound.playSound(RegisterActivity.this, R.raw.toast);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui l??ng nh???p s??? ??i???n tho???i!", "????ng");
                    return;
                }
                String ip1 = so1.getText().toString().trim();
                String ip2 = so2.getText().toString().trim();
                String ip3 = so3.getText().toString().trim();
                String ip4 = so4.getText().toString().trim();
                String ip5 = so5.getText().toString().trim();
                String ip6 = so6.getText().toString().trim();
                String code = ip1 + ip2 + ip3 + ip4 + ip5 + ip6 ;
//                String code = verifi_code_register.getText().toString();
//                Toast.makeText(getApplicationContext(), "Hi" + code + " "+ code.length(), Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(code)){
//                    Toast.makeText(getApplicationContext(), "Vui l??ng nh???p m?? x??c nh???n!", Toast.LENGTH_SHORT).show();
//                    sound.playSound(RegisterActivity.this, R.raw.toast);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui l??ng nh???p m?? x??c nh???n!", "????ng");
                    return;
                }else {
                    verifiPhoneNumberWithCode(mVerificationId, code);
                }
                ///////////////
//                Intent i = new Intent(getApplicationContext(),RegisterInfoActivity.class);
//                i.putExtra("phonenumber_user", phone_number.getText().toString());
//                startActivity(i);
            }
        });
    }
    //endregion

    //region T???o authencation
    private void startPhoneNumberVerification(String phone) {
//        loadingBar.setMessage("M?? x??c nh???n s??? ??i???n tho???i");
//        loadingBar.show();
        final LoadingDialog loadingDialog = new LoadingDialog(RegisterActivity.this);
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dissmissDialog();
            }
        },3000);

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L,TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void resendVerification(String phone, PhoneAuthProvider.ForceResendingToken token) {
        loadingBar.setMessage("G???i l???i m??");
        loadingBar.show();

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
        loadingBar.setMessage("M?? x??c nh???n");
        loadingBar.show();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        loadingBar.setMessage("X??c th???c th??nh c??ng");
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        loadingBar.dismiss();
                        String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
                        Intent i = new Intent(getApplicationContext(),RegisterInfoActivity.class);
                        i.putExtra("phonenumber_user", phone_number.getText().toString());
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingBar.dismiss();
//                        Toast.makeText(getApplicationContext(), "M?? x??c th???c kh??ng ch??nh x??c!Vui l??ng nh???p l???i", Toast.LENGTH_SHORT).show();
//                        sound.playSound(RegisterActivity.this, R.raw.toast);
                        Show_SnackBar(R.drawable.icon_toast_warning, "M?? x??c th???c kh??ng ch??nh x??c!Vui l??ng nh???p l???i!", "????ng");
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
        Okay.setText("?????ng ??");
        Cancel.setText("Kh??ng");
        Content_alert.setText("T??i kho???n t???n t???i. Th???c hi???n thay ?????i m???t kh???u?");

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPhoneActivity.class));
                dialog.dismiss();
            }
        });

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show_Popup("b???n c?? mu???n h???y b??? qu?? tr??nh ????ng k???");
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                dialog.dismiss();
            }
        });
    }

    private void GetData(String phonenb) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.checkaccount;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Tontai")){
                    dialog.show();
                    sound.playSound(RegisterActivity.this, R.raw.thongbao);

                }else {
                    String phone = "+84" + phone_number.getText().toString().substring(1);
                    startPhoneNumberVerification(phone);
                    isSend_code_one = true;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Bye" + error.toString(), Toast.LENGTH_SHORT).show();
            }
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

    public  final void Show_SnackBar( int i, String t, String a){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_snackbar,findViewById(R.id.snack_constraint), false);

        ImageView icon = layout.findViewById(R.id.im_icon);
        icon.setImageResource(i);
        TextView text = layout.findViewById(R.id.tv_message);
        text.setText(t.toString());
        TextView action = layout.findViewById(R.id.tv_action);
        action.setText(a.toString());

        View view = findViewById(R.id.layout_register);
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
        sound.playSound(RegisterActivity.this, R.raw.toast);
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

        sound.playSound(RegisterActivity.this, R.raw.thongbao);

        Dialog dialog = new Dialog(RegisterActivity.this);

        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title = dialog.findViewById(R.id.content_alert);
        Button ok = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setText("Tho??t");

        title.setText(text);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
        dialog.show();
    }
    //endregion
    @Override
    public void onBackPressed() {
        Show_Popup("b???n c?? mu???n h???y b??? qu?? tr??nh ????ng k???");
    }
}