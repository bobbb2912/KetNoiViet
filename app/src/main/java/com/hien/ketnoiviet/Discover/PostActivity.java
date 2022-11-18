package com.hien.ketnoiviet.Discover;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Login.LoginActivity;
import com.hien.ketnoiviet.Others.PostDetail;
import com.hien.ketnoiviet.Personal.UnapprovedPosts;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.DistrictAdapter;
import com.hien.ketnoiviet.adapter.ProvinceAdapter;
import com.hien.ketnoiviet.adapter.WardAdapter;
import com.hien.ketnoiviet.model.District;
import com.hien.ketnoiviet.model.Province;
import com.hien.ketnoiviet.model.Ward;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {



    //region Address Khai báo

    TextView province_post,ward_post,district_post, province_close_post,district_close_post,ward_close_post;
    FrameLayout framelayout_province;
    FrameLayout framelayout_district;
    FrameLayout framelayout_ward;

    ListView listview_province;
    ArrayList<Province> mangprovince;
    ProvinceAdapter provinceAdapter;
    int     idtinh   = 0;
    String  nametinh = "";
    String  codetinh = "";
    int     id_tinh = 0;


    ListView listview_district;
    ArrayList<District> mangdistrict;
    DistrictAdapter districtAdapter;
    int     idhuyen   = 0;
    String  namehuyen = "";
    String  prefixhuyen = "";
    int     provinceidhuyen = 0;
    int     id_huyen = 0;

    ListView listview_ward;
    ArrayList<Ward> mangward;
    WardAdapter wardAdapter;
    int     idphuong   = 0;
    String  namephuong = "";
    String  prefixphuong = "";
    int     provinceidphuong = 0;
    int     districtid = 0;
    //endregion
    LinearLayout c1, c2, c3, c4;
    ImageView i1, i2, i3, i4;
    private EditText tenDiaDiem, diaChi, moTa, noiDung;
    Button xacNhan, huyBo, dang_post;
    Bitmap bitmap;
    String encodeImage1,encodeImage3,encodeImage2,encodeImage4;
    Toolbar post_backto_home;
    private static final String FILE_POST = "isPost";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        viewBinding();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            event();
            GetDuLieuProvince();
//            isPost();
        }else {
            //Show_Toast("Bạn hãy kiểm tra lại kết nối!", R.drawable.icon_toast_warning);
            Show_SnackBar(R.drawable.icon_toast_warning,"Bạn hãy kiểm tra lại kết nối!", "Thử lại");
            return;
        }
        //region Đẩy ảnh
        //select image 1
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PostActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        //select image 2
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PostActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select image"),2 );
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        //select image 3
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PostActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select image"),3 );
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        //select image 4
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PostActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select image"),4 );
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        //endregion va bai viet va
    }

    private void huyBoBackground() {
        c1.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        c2.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        c3.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        c4.setBackgroundResource(R.drawable.custom_dashed_boder_none);
    }

    private void postContent() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MM/yyyy");
        Format f = new SimpleDateFormat("dd/MM/yyyy");
        String datecreate = f.format(new Date());

        String NameOfPlace = tenDiaDiem.getText().toString().trim();
        String Address = diaChi.getText().toString().trim();
        String Description = moTa.getText().toString().trim();
        String Content = noiDung.getText().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Server.addPost
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Done")){
                    sound.playSound(getApplicationContext(),R.raw.thongbao);
                    Show_Toast("Bài viết đang được chờ duyệt.", R.drawable.icon_check_success);
                    Thongbaoday();
//                    Toast.makeText(getApplicationContext(), "Thêm bài viết thành công", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> {
                        PostActivity.this.finish();
                        startActivity(getIntent());
                    },4000);
//                    isPost();
                }else {
                    Show_Toast("Đăng bài không thành công.", R.drawable.icon_error);
//                    Toast.makeText(getApplicationContext(), "Thêm bài viết không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Lỗi" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nameplace", NameOfPlace.trim());
                params.put("province", province_post.getText().toString().trim());
                params.put("district", district_post.getText().toString().trim());
                params.put("ward", ward_post.getText().toString().trim());
                params.put("address", Address.trim());
                params.put("description", Description.trim());
                params.put("content", Content.trim());
                params.put("image1", encodeImage1);
                params.put("image2", encodeImage2);
                params.put("image3", encodeImage3);
                params.put("image4", encodeImage4);
                params.put("phoneuser", HomeActivity.phone_number_user);
                params.put("datepost", datecreate);
                params.put("status", "0");
                return params;
            }
        };

        requestQueue.add(request);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //for image1
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                i1.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                encodeImage1 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            c1.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        }
        //for image2
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                i2.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                encodeImage2 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            c2.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        }
        //for image3
        if(requestCode == 3 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                i3.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                encodeImage3 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            c3.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        }
        //for image4
        if(requestCode == 4 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                i4.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                encodeImage4 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            c4.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void event() {
        //region Address
        province_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dulieutinh = province_post.getText().toString().trim();
                if(dulieutinh.equals("")){
                    listview_province.setVisibility(View.VISIBLE);
                    framelayout_province.setVisibility(View.VISIBLE);
                }
                else {
                    district_post.setText("");
                    ward_post.setText("");
                    mangdistrict.clear();
                    mangward.clear();
                    listview_province.setVisibility(View.VISIBLE);
                    framelayout_province.setVisibility(View.VISIBLE);
                }
            }
        });
        province_close_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                framelayout_province.setVisibility(View.GONE);
            }
        });
        listview_province.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tinh = mangprovince.get(i).getName().toString();
                province_post.setText(tinh);
                id_tinh = mangprovince.get(i).getId();
                framelayout_province.setVisibility(View.GONE);
            }
        });
        district_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isTinhNull = province_post.getText().toString().trim();
                if(isTinhNull.equals("")){
                    //Show_Toast("Vui lòng chọn tỉnh/thành phố!", R.drawable.icon_toast_warning);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng chọn tỉnh/thành phố!", "Đóng");
                }else {
                    GetDuLieuDistrict();
                    listview_district.setVisibility(View.VISIBLE);
                    framelayout_district.setVisibility(View.VISIBLE);
                }
            }
        });
        district_close_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                framelayout_district.setVisibility(View.GONE);
            }
        });
        listview_district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String huyen = mangdistrict.get(i).getName().toString();
                district_post.setText(huyen);
                id_huyen = mangdistrict.get(i).getId();
                framelayout_district.setVisibility(View.GONE);
            }
        });
        ward_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isHuyenNull = district_post.getText().toString().trim();
                if(isHuyenNull.equals("")){
                    //Show_Toast("Vui lòng chọn quận/huyện!", R.drawable.icon_toast_warning);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng chọn quận/huyện!", "Đóng");
                }else {
                    ward_post.setText("");
                    mangward.clear();
                    GetDuLieuWard();
                    listview_ward.setVisibility(View.VISIBLE);
                    framelayout_ward.setVisibility(View.VISIBLE);
                }
            }
        });
        ward_close_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                framelayout_ward.setVisibility(View.GONE);
            }
        });
        listview_ward.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phuong = mangward.get(i).getName().toString();
                ward_post.setText(phuong);
                framelayout_ward.setVisibility(View.GONE);
            }
        });
        //endregion
        post_backto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Popup("Bạn có muốn hủy bài viết? Quá trình này không thể hoàn tác!");
            }
        });
        huyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Popup("Bạn có muốn hủy bài viết? Quá trình này không thể hoàn tác!");
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Thông báo","Thông báo", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thongbaoday();
                sound.playSound(PostActivity.this, R.raw.toast);
//                //region Đăng bài
//                if(tenDiaDiem.getText().toString().equals("") ||
//                        diaChi.getText().toString().equals("") ||
//                        moTa.getText().toString().equals("") ||
//                        noiDung.getText().toString().equals("") ||
//                        province_post.getText().toString().trim().equals("") ||
//                        district_post.getText().toString().trim().equals("")){
//                    //Show_Toast("Vui lòng điền đủ thông tin!", R.drawable.icon_toast_warning);
//                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng điền đủ thông tin!", "Đóng");
//                    sound.playSound(PostActivity.this, R.raw.toast);
//                }
//                else {
//                    if (encodeImage1 == null || encodeImage2 == null || encodeImage3 == null || encodeImage4 == null){
//                        //Show_Toast("Vui lòng chọn đủ hình ảnh!", R.drawable.icon_toast_warning);
//                        Show_SnackBar(R.drawable.icon_toast_warning,"Vui lòng chọn đủ hình ảnh!", "Đóng");
//                        sound.playSound(PostActivity.this, R.raw.toast);
//                    }
//                    else {
//                        huyBoBackground();
//                        postContent();
//                        sound.playSound(PostActivity.this, R.raw.post_like_cmt);
//                    }
//                }
//                //endregion

            }
        });
        dang_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Kiểm tra điều kiện đủ
                if(tenDiaDiem.getText().toString().equals("") ||
                        diaChi.getText().toString().equals("") ||
                        moTa.getText().toString().equals("") ||
                        noiDung.getText().toString().equals("") ||
                        province_post.getText().toString().trim().equals("") ||
                        district_post.getText().toString().trim().equals("")){
                    //Show_Toast("Vui lòng điền đủ thông tin!", R.drawable.icon_toast_warning);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng điền đủ thông tin!", "Đóng");
                    sound.playSound(PostActivity.this, R.raw.toast);
                }
                else {
                    if (encodeImage1 == null || encodeImage2 == null || encodeImage3 == null || encodeImage4 == null){
                        //Show_Toast("Vui lòng chọn đủ hình ảnh!", R.drawable.icon_toast_warning);
                        Show_SnackBar(R.drawable.icon_toast_warning,"Vui lòng chọn đủ hình ảnh!", "Đóng");
                        sound.playSound(PostActivity.this, R.raw.toast);
                    }
                    else {
                        huyBoBackground();
                        postContent();
                        sound.playSound(PostActivity.this, R.raw.post_like_cmt);
                    }
                }

            }
        });
    }

    private void Thongbaoday() {

        Intent intent = new Intent(this, UnapprovedPosts.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(getNotificationId(),PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(PostActivity.this, "Thông báo");
        builder.setContentTitle("Đăng bài viết thành công.");
        builder.setContentText("Bài viết đang được chờ quản trị viên duyệt!");
        builder.setSmallIcon(R.drawable.play_store_512_round);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);


        NotificationManagerCompat manager = NotificationManagerCompat.from(PostActivity.this);
        manager.notify(getNotificationId(),builder.build());
    }

    private int getNotificationId() {
        return  (int) new Date().getTime();
    }


    //region Address Code
    private void GetDuLieuProvince() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.province, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                        if(response != null){
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    idtinh = jsonObject.getInt("id");
                                    nametinh = jsonObject.getString("name");
                                    codetinh = jsonObject.getString("code");
                                    mangprovince.add(new Province(idtinh, nametinh, codetinh));
                                    provinceAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuWard() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ward, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        idphuong = jsonObject.getInt("id");
                        namephuong = jsonObject.getString("name");
                        prefixphuong = jsonObject.getString("prefix");
                        provinceidphuong = jsonObject.getInt("provinceid");
                        districtid = jsonObject.getInt("districtid");
                        mangward.add(new Ward(idphuong,namephuong,prefixphuong,provinceidphuong,districtid));
                        wardAdapter.notifyDataSetChanged();
                    }
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
                param.put("idhuyen",String.valueOf(id_huyen));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetDuLieuDistrict() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.district, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        idhuyen = jsonObject.getInt("id");
                        namehuyen = jsonObject.getString("name");
                        prefixhuyen = jsonObject.getString("prefix");
                        provinceidhuyen = jsonObject.getInt("provinceid");
                        mangdistrict.add(new District(idhuyen,namehuyen,prefixhuyen,provinceidhuyen));
                        districtAdapter.notifyDataSetChanged();
                    }
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
                param.put("idtinh",String.valueOf(id_tinh));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    //endregion

    private void viewBinding() {
        //region Anh xa Address
        ward_post = findViewById(R.id.ward_post);
        listview_ward = findViewById(R.id.listview_ward);
        framelayout_ward = findViewById(R.id.framelayout_ward);
        ward_close_post = findViewById(R.id.ward_close_post);
        framelayout_ward.setVisibility(View.GONE);
        listview_ward.setVisibility(View.GONE);
        mangward = new ArrayList<>();
        wardAdapter = new WardAdapter(mangward, getApplicationContext());
        listview_ward.setAdapter(wardAdapter);

        district_post = findViewById(R.id.district_post);
        listview_district = findViewById(R.id.listview_district);
        framelayout_district = findViewById(R.id.framelayout_district);
        district_close_post = findViewById(R.id.district_close_post);
        framelayout_district.setVisibility(View.GONE);
        listview_district.setVisibility(View.GONE);
        mangdistrict = new ArrayList<>();
        districtAdapter = new DistrictAdapter(mangdistrict, getApplicationContext());
        listview_district.setAdapter(districtAdapter);

        province_post = findViewById(R.id.province_post);
        listview_province = findViewById(R.id.listview_province);
        framelayout_province = findViewById(R.id.framelayout_province);
        province_close_post = findViewById(R.id.province_close_post);
        framelayout_province.setVisibility(View.GONE);
        listview_province.setVisibility(View.GONE);
        mangprovince = new ArrayList<>();
        provinceAdapter = new ProvinceAdapter(mangprovince, getApplicationContext());
        listview_province.setAdapter(provinceAdapter);
        //endregion
        c1 =  findViewById(R.id.addPhoto1);
        c2 =  findViewById(R.id.addPhoto2);
        c3 =  findViewById(R.id.addPhoto3);
        c4 =  findViewById(R.id.addPhoto4);

        i1 = (ImageView) findViewById(R.id.img1);
        i2 = (ImageView) findViewById(R.id.img2);
        i3 = (ImageView) findViewById(R.id.img3);
        i4 = (ImageView) findViewById(R.id.img4);

        tenDiaDiem = (EditText) findViewById(R.id.ten_dia_diem);
        diaChi = (EditText) findViewById(R.id.dia_chi);
        moTa = (EditText) findViewById(R.id.mo_ta);
        noiDung = (EditText) findViewById(R.id.noi_dung);

        xacNhan = (Button) findViewById(R.id.xac_nhan_post);
        huyBo = (Button) findViewById(R.id.huy_bo_post);
        dang_post = (Button) findViewById(R.id.dang_post);

        post_backto_home = findViewById(R.id.post_backto_home);
    }

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

    public  final void Show_SnackBar( int i, String t, String a){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_snackbar,findViewById(R.id.snack_constraint), false);

        ImageView icon = layout.findViewById(R.id.im_icon);
        icon.setImageResource(i);
        TextView text = layout.findViewById(R.id.tv_message);
        text.setText(t.toString());
        TextView action = layout.findViewById(R.id.tv_action);
        action.setText(a.toString());

        View view = findViewById(R.id.layout_add_post);
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

    //region Custom a notification
    public void Show_Popup(String text){

        sound.playSound(PostActivity.this, R.raw.thongbao);

        Dialog dialog = new Dialog(PostActivity.this);

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
                PostActivity.this.finish();
            }
        });
        dialog.show();
    }
    //endregion

    //region Ghi đè nút back trên điện thoại, vô hiệu hóa quay lại màn hình trước
    @Override
    public void onBackPressed() {
        Show_Popup("Bạn có muốn hủy bài viết? Quá trình này không thể hoàn tác!");
    }
    //endregion

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