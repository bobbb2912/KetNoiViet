package com.hien.ketnoiviet.Personal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Home.HomeFragment;
import com.hien.ketnoiviet.Home.PersonalFragment;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateInformation extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener setListener;

    Bitmap bitmap;
    String encodeImage1,encodeImage2;
    String Gender = "Khác";
    private Spinner spinnerGender;
    private List<String> listGender;
    Button confirm_update_information,cancel_update_information;
    TextView birthday_update_person;
    CircleImageView imageuser_update_person;
    ImageView cover_update_person;
    EditText name_update_info, phone_update_info, mail_update_info, status_update_info,home_update_info;
    Toolbar icon_arrow_back_back;
    int     id       = 0;
    String  name     = "";
    String  date     = "";
    String  sex      = "";
    String  img = "";
    String  bia = "";
    String  mail = "";
    String  phone = "";
    String  stt = "";
    String  home = "";
    String url_image = "";
    String url_cover = "";
    int isClickImg = 0;
    int isClickCv = 0;

//    MediaPlayer toast_media = MediaPlayer.create(UpdateInformation.this, R.raw.toast);
//    MediaPlayer thongbao = MediaPlayer.create(this, R.raw.thongbao);
//    MediaPlayer post_like_cmt = MediaPlayer.create(this, R.raw.post_like_cmt);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        viewBinding();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            event();
            //thongbao.start();
        }else {
            //Show_Toast("Bạn hãy kiểm tra lại kết nối!", R.drawable.icon_toast_warning);
            Show_SnackBar(R.drawable.icon_toast_warning, "Bạn hãy kiểm tra lại kết nối!", "Thử lại" );
            return;
        }
        listGender = new ArrayList<>();
        listGender.add("Nam");
        listGender.add("Nữ");
        listGender.add("Khác");
        spinnerGender = findViewById(R.id.gender_update_person);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, listGender);
        spinnerGender.setAdapter(spinnerAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    Gender  = listGender.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng chọn giới tính!", "Đóng" );
            }
        });
        personal();
    }

    private void event() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        birthday_update_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateInformation.this, R.style.DatePickerTheme
                        , setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = (view, year1, month1, day1) -> {
            month1 = month1 + 1;
            String date = day1 + "/" + month1 + "/" + year1;

            LocalDate currentdate = LocalDate.now();
            int currentDay = currentdate.getDayOfMonth();
            int currentYear = currentdate.getYear();
//            if (year1<=year){
//                if (month1<=month){
//                    if (day1<=day){
//                        birthday_update_person.setText(date);
//                    }else{
//                        birthday_update_person.setText(day+"/"+month+"/"+year);
//                    }
//                }else {
//                    birthday_update_person.setText(day+"/"+month+"/"+year);
//                }
//            }else {
//                birthday_update_person.setText(day+"/"+month+"/"+year);
//            }

            if (year1<year){
                birthday_update_person.setText(date);
            } else if(year1==year) {
                if(month1<month){
                    birthday_update_person.setText(date);
                } else if (month1==month) {
                    if(day1<=day) {
                        birthday_update_person.setText(date);
                    } else {
                        birthday_update_person.setText(day+"/"+month+"/"+year);
                    }

                } else {
                    birthday_update_person.setText(day+"/"+month+"/"+year);
                }

            } else {
                birthday_update_person.setText(day+"/"+month+"/"+year);
            }
        };
        imageuser_update_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(UpdateInformation.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh đại diện!"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }
                        }).check();

            }
        });
        cover_update_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(UpdateInformation.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh bìa!"), 2);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }
                        }).check();
            }
        });
        cancel_update_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateInformation.this.finish();
//                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
        confirm_update_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name_update_info.getText().toString().trim().equals("")||
                birthday_update_person.getText().toString().trim().equals("")||
                mail_update_info.getText().toString().trim().equals("")||
                home_update_info.getText().toString().trim().equals("")||
                status_update_info.getText().toString().trim().equals("")){

                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng điền đầy đủ thông tin cá nhân!", "Đóng" );
                    return;
                }
                else {
                    sound.playSound(UpdateInformation.this, R.raw.thongbao);
                    if (isClickCv == 0 && isClickImg == 0){
                        NoupdateInformation();
                    }
                    else {
                        if (isClickCv == 1 && isClickImg == 1){
                            updateInformation();
                        }
                        else {
                            if (isClickImg == 1){
                                updateInformationonlyimg();
                            }
                            if (isClickCv == 1){
                                updateInformationonlycv();
                            }
                        }
                    }
                }
            }
        });
//        Toast.makeText(getApplicationContext(), "" + HomeActivity.phone_number_user, Toast.LENGTH_SHORT).show();
        icon_arrow_back_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateInformation.this.finish();
            }
        });
    }

    private void updateInformationonlycv() {
        String phone_number_person = HomeActivity.phone_number_user;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.updateuserinfoonlycv, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                birthday_update_person.clearFocus();
                name_update_info.clearFocus();
                phone_update_info.clearFocus();
                mail_update_info.clearFocus();
                status_update_info.clearFocus();
                home_update_info.clearFocus();
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                if(response.equals("Done")){
                    Toast.makeText(getApplicationContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    Show_Toast("Cập nhật thông tin thành công.", R.drawable.icon_check_success);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phone_number",phone_number_person);
                param.put("nameuser",name_update_info.getText().toString());
                param.put("birthday",birthday_update_person.getText().toString());
                param.put("gender",Gender);
                param.put("cover",encodeImage2);
                param.put("email",mail_update_info.getText().toString());
                param.put("status",status_update_info.getText().toString());
                param.put("hometown",home_update_info.getText().toString());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void updateInformationonlyimg() {
        String phone_number_person = HomeActivity.phone_number_user;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.updateuserinfoonlyimg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                birthday_update_person.clearFocus();
                name_update_info.clearFocus();
                phone_update_info.clearFocus();
                mail_update_info.clearFocus();
                status_update_info.clearFocus();
                home_update_info.clearFocus();
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                if(response.equals("Done")){
                    Show_Toast("Cập nhật thông tin thành công.", R.drawable.icon_check_success);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phone_number",phone_number_person);
                param.put("nameuser",name_update_info.getText().toString());
                param.put("birthday",birthday_update_person.getText().toString());
                param.put("gender",Gender);
                param.put("imageuser",encodeImage1);
                param.put("email",mail_update_info.getText().toString());
                param.put("status",status_update_info.getText().toString());
                param.put("hometown",home_update_info.getText().toString());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void NoupdateInformation() {
        String phone_number_person = HomeActivity.phone_number_user;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.noupdateuserinfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                birthday_update_person.clearFocus();
                name_update_info.clearFocus();
                phone_update_info.clearFocus();
                mail_update_info.clearFocus();
                status_update_info.clearFocus();
                home_update_info.clearFocus();
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                if(response.equals("Done")){
                    Show_Toast("Cập nhật thông tin thành công.", R.drawable.icon_check_success);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phone_number",phone_number_person);
                param.put("nameuser",name_update_info.getText().toString());
                param.put("birthday",birthday_update_person.getText().toString());
                param.put("gender",Gender);
                param.put("email",mail_update_info.getText().toString());
                param.put("status",status_update_info.getText().toString());
                param.put("hometown",home_update_info.getText().toString());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void updateInformation() {
        String phone_number_person = HomeActivity.phone_number_user;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.updateuserinfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                birthday_update_person.clearFocus();
                name_update_info.clearFocus();
                phone_update_info.clearFocus();
                mail_update_info.clearFocus();
                status_update_info.clearFocus();
                home_update_info.clearFocus();
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                if(response.equals("Done")){
                    Show_Toast("Cập nhật thông tin thành công.", R.drawable.icon_check_success);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phone_number",phone_number_person);
                param.put("nameuser",name_update_info.getText().toString());
                param.put("birthday",birthday_update_person.getText().toString());
                param.put("gender",Gender);
                param.put("imageuser",encodeImage1);
                param.put("cover",encodeImage2);
                param.put("email",mail_update_info.getText().toString());
                param.put("status",status_update_info.getText().toString());
                param.put("hometown",home_update_info.getText().toString());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void personal() {
        String phone_number_person = HomeActivity.phone_number_user;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getuserinfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    int     id       = jsonObject.getInt("idusers");
                      name     = jsonObject.getString("nameuser");
                      date     = jsonObject.getString("birthday");
                      sex      = jsonObject.getString("gender");
                      img = jsonObject.getString("imageuser");
                      bia = jsonObject.getString("cover");
                      mail = jsonObject.getString("email");
                      phone = jsonObject.getString("phonenumber");
                      stt = jsonObject.getString("status");
                      home = jsonObject.getString("hometown");
                    int     dollar = jsonObject.getInt("money");
                    String  create = jsonObject.getString("datecreate");
                    String  pw = jsonObject.getString("password");


                        url_image = Server.userget + img;
                         url_cover = Server.userget + bia;
//                        Toast.makeText(getApplicationContext(), ""+ url_image + "\n" + url_cover, Toast.LENGTH_SHORT).show();
                        birthday_update_person.setText(date);
                        name_update_info.setText(name);
                        phone_update_info.setText(phone);
                        mail_update_info.setText(mail);
                        status_update_info.setText(stt);
                        home_update_info.setText(home);
                        Gender = sex;

                        Glide.with(getApplicationContext()).load(url_image).into(imageuser_update_person);
                        Glide.with(getApplicationContext()).load(url_cover).into(cover_update_person);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phone_number",phone_number_person);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void viewBinding() {
        birthday_update_person = findViewById(R.id.birthday_update_person);
        imageuser_update_person = findViewById(R.id.imageuser_update_person);
        cover_update_person = findViewById(R.id.cover_update_person);
        name_update_info = findViewById(R.id.name_update_info);
        phone_update_info = findViewById(R.id.phone_update_info);

        mail_update_info = findViewById(R.id.mail_update_info);
        status_update_info = findViewById(R.id.status_update_info);
        home_update_info = findViewById(R.id.home_update_info);
        cancel_update_information = findViewById(R.id.cancel_update_information);
        confirm_update_information = findViewById(R.id.confirm_update_information);
        icon_arrow_back_back = findViewById(R.id.toolbar_update_info);
    }

    //region Custom a toast
//    public final void Show_Toast(String t, int s){
//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.toast,
//                findViewById(R.id.toast_layout_root));
//        ImageView image = layout.findViewById(R.id.image);
//        image.setImageResource(s);
//        TextView text = layout.findViewById(R.id.text);
//        text.setText(t);//
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setView(layout);
//        toast.show();
//    }
    //endregion

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageuser_update_person.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                encodeImage1 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                isClickImg = 1;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                cover_update_person.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                encodeImage2 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
                isClickCv = 1;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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

        View view = findViewById(R.id.layout_update_information);
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
        sound.playSound(UpdateInformation.this, R.raw.toast);
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