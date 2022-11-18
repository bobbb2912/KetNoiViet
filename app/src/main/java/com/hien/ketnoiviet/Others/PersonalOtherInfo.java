package com.hien.ketnoiviet.Others;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Discover.PostActivity;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Login.LoginActivity;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.PostAdapter;
import com.hien.ketnoiviet.adapter.RecyclerViewAdapterPost;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalOtherInfo extends AppCompatActivity {

    Toolbar toolbar_personal_info_ct;
    String phonenumber_user = "";
    ImageView cover_personal_info_ct,image_personal_info_ot_mini;
    CircleImageView image_personal_info_ct;
    TextView username_personal_info_ct,status_personal_info_ct,hometown_personal_ct,gender_personal_ct,datecreate_personal_ct,birthday_personal_ct,phonenumber_personal_ct,email_personal_ct,follow_info_ot;
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
    int status = 0;
    public ArrayList<Post> arrayPostInfot;
    public RecyclerViewAdapterPost recyclerViewAdapterPostt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_other_info);
        getphone();
        anhxa();
        event();
        getData();
        getPost();
        personal();
        getStatus();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.listbaiviet_personalot_rcv);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterPostt = new RecyclerViewAdapterPost(this, arrayPostInfot);
        recyclerView.setAdapter(recyclerViewAdapterPostt);
    }

    private void checkstt() {
        if (status == 1) {
            follow_info_ot.setText("Đang theo dõi");
        }
        if (status == 0) {
            follow_info_ot.setText("Theo dõi");
        }
    }

    private void getStatus() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Server.checkfollow
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "Trạng thái: " + response, Toast.LENGTH_SHORT).show();
                status = Integer.parseInt(response);
                checkstt();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("users", HomeActivity.phone_number_user);
                params.put("userfollow", phonenumber_user);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void anhxa() {
        cover_personal_info_ct = findViewById(R.id.cover_personal_info_ot);
        image_personal_info_ct = findViewById(R.id.image_personal_info_ot);
        image_personal_info_ot_mini = findViewById(R.id.image_personal_info_ot_mini);
        username_personal_info_ct = findViewById(R.id.username_personal_info_ot);
        status_personal_info_ct = findViewById(R.id.status_personal_info_ot);
        hometown_personal_ct = findViewById(R.id.hometown_personal_ot);
        gender_personal_ct = findViewById(R.id.gender_personal_ot);
        datecreate_personal_ct = findViewById(R.id.datecreate_personal_ot);
        birthday_personal_ct = findViewById(R.id.birthday_personal_ot);
//        phonenumber_personal_ct = findViewById(R.id.phonenumber_personal_ct);
        email_personal_ct = findViewById(R.id.email_personal_ot);
        toolbar_personal_info_ct = findViewById(R.id.toolbar_personal_info_ot);
        arrayPostInfot = new ArrayList<>();
        follow_info_ot = findViewById(R.id.follow_info_ot);
    }

    private void getphone() {
        Intent intent = this.getIntent();
        phonenumber_user = intent.getStringExtra("phoneuser");
    }

    private void event() {
        toolbar_personal_info_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalOtherInfo.this.finish();
            }
        });
        image_personal_info_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
                intent.putExtra("avatar",  url_image);
                startActivity(intent);
            }
        });
        cover_personal_info_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
                intent.putExtra("cover",  url_cover);
                startActivity(intent);
            }
        });
        follow_info_ot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status == 0){
                    Show_Popup("Bạn có muốn theo dõi người dùng này!");
                }
                if (status == 1){
                    Show_Popup("Bạn có muốn hủy theo dõi người dùng này!");
                }
            }
        });
    }

    private void Follow() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Server.addfollow
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("users", HomeActivity.phone_number_user);
                params.put("userfollow", phonenumber_user);
                return params;
            }
        };
        requestQueue.add(request);
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
                    String  img = jsonObject.getString("imageuser");
                        String url_image = Server.userget + img;
                    Glide.with(getApplicationContext()).load(url_image).into(image_personal_info_ot_mini);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
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

    private void getData() {
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
                    if (date.isEmpty()){
                        birthday_personal_ct.setText("Sinh nhật: Bí mật");
                    }else {
                        String[] dates = date.split("/");
                        String date1 = dates[0];
                        String date2 = dates[1];
                        String date3 = dates[2];
                        birthday_personal_ct.setText("Sinh nhật ngày " + date1 + " tháng " + date2 + ", " + date3);
                    }
                    username_personal_info_ct.setText(name);
//                    phonenumber_personal_ct.setText(phone);
                    if (mail.isEmpty()){
                        email_personal_ct.setText("Email: Bí mật");
                    }else {
                        email_personal_ct.setText(mail + " ");
                    }

                    status_personal_info_ct.setText(stt + " ");
                    datecreate_personal_ct.setText(create + " ");
                    String[]  creates = create.split("/");
                    String create1 = creates[0];
                    String create2 = creates[1];
                    String create3 = creates[2];
                    datecreate_personal_ct.setText("Đã tham gia vào tháng " + create1 + " năm " + create3);
                    if (home.isEmpty()){
                        hometown_personal_ct.setText("Đến từ " + "Bí mật");
                    }else {
                        hometown_personal_ct.setText("Đến từ " + home + " ");
                    }
                    if (sex.isEmpty()){
                        gender_personal_ct.setText("Giới tính " + "Bí mật");
                    }else {
                        gender_personal_ct.setText("Giới tính " + sex + " ");
                    }
                    Glide.with(getApplicationContext()).load(url_image).into(image_personal_info_ct);
                    Glide.with(getApplicationContext()).load(url_cover).into(cover_personal_info_ct);
                    toolbar_personal_info_ct.setTitle(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phone_number",phonenumber_user);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getPost() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getPostByPhoneUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                Log.d("response", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idpost = jsonObject.getInt("id");
                        String nameplace = jsonObject.getString("nameplace");
                        String province  =  jsonObject.getString("province");
                        String district  =  jsonObject.getString("district");
                        String ward  =  jsonObject.getString("ward");
                        String address  =  jsonObject.getString("address");
                        String description = jsonObject.getString("description");
                        String content = jsonObject.getString("content");
                        String image1 = jsonObject.getString("image1");
                        String image2 = jsonObject.getString("image2");
                        String image3 = jsonObject.getString("image3");
                        String image4 = jsonObject.getString("image4");
                        String phoneuser = jsonObject.getString("phoneuser");
                        String datepost = jsonObject.getString("datepost");
                        int status = jsonObject.getInt("status");
                        String nameuser = jsonObject.getString("nameuser");
                        String imageuser = jsonObject.getString("imageuser");

//                              Toast.makeText(getContext(), "Bài viết:" + idpost + "\n" + nameplace + "\n" + address + "\n" + image1 + "\n" + phoneuser + "\n" + datepost + "\n" + status + "\n", Toast.LENGTH_SHORT).show();
                        arrayPostInfot.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));
                        recyclerViewAdapterPostt.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phoneuser",phonenumber_user);
                return param;
            }
        };
        requestQueue.add(stringRequest);
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

        sound.playSound(PersonalOtherInfo.this, R.raw.thongbao);

        Dialog dialog = new Dialog(PersonalOtherInfo.this);

        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title = dialog.findViewById(R.id.content_alert);
        Button ok = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setText("Đồng ý");

        title.setText(text);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Follow();
                sound.playSound(PersonalOtherInfo.this, R.raw.post_like_cmt);
                PersonalOtherInfo.this.finish();
                startActivity(getIntent());
            }
        });
        dialog.show();
    }
    //endregion

}