package com.hien.ketnoiviet.Personal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.hien.ketnoiviet.Discover.PostActivity;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Login.ResetPhoneActivity;
import com.hien.ketnoiviet.Others.Helper;
import com.hien.ketnoiviet.Others.imgFitScreen;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.PostAdapter;
import com.hien.ketnoiviet.adapter.RecyclerViewAdapter;
import com.hien.ketnoiviet.adapter.RecyclerViewAdapterPost;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.model.Ward;
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

public class PersonalInfo extends AppCompatActivity {

    Toolbar toolbar_personal_info_ct;
    String phonenumber_user = "";
    ImageView cover_personal_info_ct;
    public ArrayList<Post> arrayPostInfo;
    public PostAdapter postAdapterInfo;
    public RecyclerViewAdapterPost recyclerViewAdapterPost;
    CircleImageView image_personal_info_ct,image_personal_info_mini_ct;
    TextView username_personal_info_ct,status_personal_info_ct,hometown_personal_ct,gender_personal_ct,datecreate_personal_ct,birthday_personal_ct,phonenumber_personal_ct,email_personal_ct,edit_info_ct,edit_info_ct_public,uppost_personal_ct;
    int     id       = 0;
    String  name     = " ";
    String  date     = "";
    String  sex      = " ";
    String  img = " ";
    String  bia = " ";
    String  mail = " ";
    String  phone = "";
    String  stt = " ";
    String  home = " ";
    String url_image = "";
    String url_cover = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        viewbinding();
        phonenumber_user = HomeActivity.phone_number_user;
        arrayPostInfo = new ArrayList<>();
        getData();
        event();
        getPost();
        postAdapterInfo = new PostAdapter(this, arrayPostInfo);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL
                , false);
        RecyclerView recyclerView = findViewById(R.id.listbaiviet_personal_rcv);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterPost = new RecyclerViewAdapterPost(this, arrayPostInfo);
        recyclerView.setAdapter(recyclerViewAdapterPost);
    }

    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter==null) {
            return;
        }

        int totalHeight=0;
        for (int size=0; size < myListAdapter.getCount(); size++) {
            View listItem=myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight+=listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params= myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);

    }

    private void event() {
        toolbar_personal_info_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalInfo.this.finish();
            }
        });
        edit_info_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UpdateInformation.class));
            }
        });
        edit_info_ct_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Hiện lên cài đặt hiện ẩn thông tin cá nhân!", Toast.LENGTH_SHORT).show();
                Show_SnackBar(R.drawable.icon_toast_warning, "Hiện lên cài đặt hiện ẩn thông tin cá nhân!", "Đóng");
            }
        });
        uppost_personal_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PostActivity.class));
            }
        });

        image_personal_info_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(PersonalInfo.this, ""+Server.imagesget + img, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
                intent.putExtra("avatar", Server.userget + img);
                startActivity(intent);
            }
        });

        cover_personal_info_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(PersonalInfo.this, ""+Server.imagesget + bia, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
                intent.putExtra("cover", Server.userget + bia);
                startActivity(intent);
            }
        });
    }

    private void getData() {
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
                    String  create = jsonObject.getString("datecreate");

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
                    datecreate_personal_ct.setText("Đã tham gia vào tháng " + create2 + " năm " + create3);
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
                    Glide.with(getApplicationContext()).load(url_image).into(image_personal_info_mini_ct);
                    Glide.with(getApplicationContext()).load(url_cover).into(cover_personal_info_ct);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString())){
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

    private void viewbinding() {
        cover_personal_info_ct = findViewById(R.id.cover_personal_info_ct);
        image_personal_info_ct = findViewById(R.id.image_personal_info_ct);
        image_personal_info_mini_ct = findViewById(R.id.image_personal_info_mini_ct);
        username_personal_info_ct = findViewById(R.id.username_personal_info_ct);
        status_personal_info_ct = findViewById(R.id.status_personal_info_ct);
        hometown_personal_ct = findViewById(R.id.hometown_personal_ct);
        gender_personal_ct = findViewById(R.id.gender_personal_ct);
        datecreate_personal_ct = findViewById(R.id.datecreate_personal_ct);
        birthday_personal_ct = findViewById(R.id.birthday_personal_ct);
        email_personal_ct = findViewById(R.id.email_personal_ct);
        edit_info_ct = findViewById(R.id.edit_info_ct);
        edit_info_ct_public = findViewById(R.id.edit_info_ct_public);
        uppost_personal_ct = findViewById(R.id.uppost_personal_ct);
        toolbar_personal_info_ct = findViewById(R.id.toolbar_personal_info_ct);
    }

    private void getPost() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getPostByPhoneUser, response -> {
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

                    arrayPostInfo.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));
                    recyclerViewAdapterPost.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString())){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phoneuser",HomeActivity.phone_number_user);
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

        View view = findViewById(R.id.layout_pesonal_infor);
        int duration = 5000;
        String mess = "";
        Snackbar s = Snackbar.make(view, mess, duration);

        s.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout sl = (Snackbar.SnackbarLayout) s.getView();
        sl.setPadding(0,0,0,0);

        //for dismiss
        action.setOnClickListener(view1 -> s.dismiss());
        sl.addView(layout,0);
        s.show();
        sound.playSound(PersonalInfo.this, R.raw.toast);
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