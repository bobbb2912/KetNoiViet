package com.hien.ketnoiviet.Others;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Intro.IntroOneActivity;
import com.hien.ketnoiviet.Intro.IntroTwoActivity;
import com.hien.ketnoiviet.Login.LoginActivity;
import com.hien.ketnoiviet.Login.ResetPhoneActivity;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.CommentAdapter;
import com.hien.ketnoiviet.adapter.PostAdapter;
import com.hien.ketnoiviet.adapter.RecyclerViewAdapter;
import com.hien.ketnoiviet.adapter.RecyclerViewAdapterComment;
import com.hien.ketnoiviet.adapter.RecyclerViewAdapterImage;
import com.hien.ketnoiviet.model.Comment;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.model.Province;
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

public class PostDetail extends AppCompatActivity {

    int id;
    int totalLike = 0;
    int totalcomment = 0;
    int status = 0;
    int savepost = 0;
    String image1 ="", image2 ="", image3 = "", image4 = "";
    ImageView user_ava, img1, img2, img3, img4, icon_heart_no_details, send_content_comment_post, bookmark_post;
    TextView user_name, name_place, address_place, description_place, content_place, timeandplace_post_details, total_like_post_details, total_comment_post_details, btn_xemthem_comment;
    LinearLayout like_post_detail, xemthembinhluan, haylanguoithichdautien,comment_post_detail,share_post_detail;
    Toolbar toolbar_details_post;
    EditText comment_post_detail_edt;
    String phoneuser;
    ListView list_comment;
    public ArrayList<Comment> arrayComment;
    public CommentAdapter commentAdapter;
    public RecyclerViewAdapterComment recyclerViewAdapterComment;
    public ArrayList<String> mImageUrls1;
    public RecyclerView recyclerView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        Intent intent = this.getIntent();
        id = intent.getIntExtra("idpost", -1);
        AnhXa();
        arrayComment = new ArrayList<>();
        comment_post_detail_edt.clearFocus();

//        list_comment = findViewById(R.id.list_comment_post_details);
        arrayComment = new ArrayList<>();
//        commentAdapter = new CommentAdapter(arrayComment, getApplicationContext());
//        list_comment.setAdapter(commentAdapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView1 = findViewById(R.id.recyclerviewDetail);
        recyclerView1.setLayoutManager(layoutManager1);
        mImageUrls1 = new ArrayList<>();


        getData();
        getStatus();
        getTotalLike();
        getTotalComment();
        GetDataComment();

        commentAdapter = new CommentAdapter(arrayComment, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL
                , false);
        RecyclerView recyclerView = findViewById(R.id.listbinhluan_detailpost_rcv);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterComment = new RecyclerViewAdapterComment(this, arrayComment);
        recyclerView.setAdapter(recyclerViewAdapterComment);

        toolbar_details_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostDetail.this.finish();
            }
        });
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "" + phoneuser, Toast.LENGTH_SHORT).show();
                String phoneuserr = phoneuser;
                if (phoneuserr.equals(HomeActivity.phone_number_user)){
                    Intent intent = new Intent(getApplicationContext(), PersonalInfo.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), PersonalOtherInfo.class);
                    intent.putExtra("phoneuser", phoneuser);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        user_ava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "" + phoneuser, Toast.LENGTH_SHORT).show();
                String phoneuserr = phoneuser;
                if (phoneuserr.equals(HomeActivity.phone_number_user)){
                    Intent intent = new Intent(getApplicationContext(), PersonalInfo.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), PersonalOtherInfo.class);
                    intent.putExtra("phoneuser", phoneuser);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        like_post_detail.setOnClickListener(view -> {
            addheart();
            sound.playSound(PostDetail.this, R.raw.post_like_cmt);
            PostDetail.this.finish();
            startActivity(getIntent());
        });
        send_content_comment_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = comment_post_detail_edt.getText().toString();
                comment_post_detail_edt.clearFocus();
                if (content.equals("")) {
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng nhập nội dung!", "Đóng");
                } else {
                    comment_post_detail_edt.setText("");
                    comment_post_detail_edt.clearFocus();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest request = new StringRequest(Request.Method.POST, Server.addcommnent
                        , response -> {
                            sound.playSound(PostDetail.this, R.raw.post_like_cmt);
                            getTotalComment();
                            GetDataComment();
                        }, error -> {
                            Show_SnackBar(R.drawable.icon_toast_warning, "Lỗi!", "Đóng");
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("idpost", String.valueOf(id));
                        params.put("phoneuser", HomeActivity.phone_number_user);
                        params.put("content", content + "");
                        return params;
                        }
                    };
                    requestQueue.add(request);
                }
            }
        });
        btn_xemthem_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Hiện danh sách comment!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ListCommentDetails.class);
                intent.putExtra("idpost", id);
                startActivity(intent);
            }
        });
        share_post_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Chức năng chưa cập nhật!", Toast.LENGTH_SHORT).show();
//                sound.playSound(PostDetail.this, R.raw.toast);
                Show_SnackBar(R.drawable.icon_toast_warning, "Chức năng chưa cập nhật!", "Đóng");
            }
        });
        comment_post_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment_post_detail_edt.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(comment_post_detail_edt, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        findViewById(R.id.directions_post_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:13.771297, 109.291292");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
        bookmark_post = findViewById(R.id.bookmark_post);
        findViewById(R.id.point_post_details).setOnClickListener(view -> Toast.makeText(getApplicationContext(), "Điểm fake, điểm real chưa cập nhật chức năng!", Toast.LENGTH_SHORT).show());
        findViewById(R.id.bookmark_post).setOnClickListener(view -> {
            if (savepost == 0){
                Toast.makeText(getApplicationContext(), "Đã lưu bài viết!", Toast.LENGTH_SHORT).show();
                bookmark_post.setImageResource(R.drawable.ic_bookmark);
                savepost = 1;
            }else {
                Toast.makeText(getApplicationContext(), "Đã bỏ lưu bài viết!", Toast.LENGTH_SHORT).show();
                bookmark_post.setImageResource(R.drawable.ic_bookmark_border);
                savepost = 0;
            }

        });
//        img1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
//                intent.putExtra("url1", Server.imagesget + image1);
//                startActivity(intent);
//            }
//        });
//        img2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
//                intent.putExtra("url2", Server.imagesget + image2);
//                startActivity(intent);
//            }
//        });
//        img3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
//                intent.putExtra("url3", Server.imagesget + image3);
//                startActivity(intent);
//            }
//        });
//        img4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
//                intent.putExtra("url4", Server.imagesget + image4);
//                startActivity(intent);
//            }
//        });
    }

    private void GetDataComment() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getCommentDesc, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayComment.clear();
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                if (!response.isEmpty()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        if (jsonArray.length() >= 5) {
                            xemthembinhluan.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < 5; i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String nameuser = jsonObject.getString("nameuser");
                            String phonenumber = jsonObject.getString("phonenumber");
                            String imageuser = jsonObject.getString("imageuser");
                            String content = jsonObject.getString("content");
                            arrayComment.add(new Comment(nameuser, phonenumber, imageuser, content));
                            commentAdapter.notifyDataSetChanged();
                            recyclerViewAdapterComment.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }//php Thang oi
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idpost", String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void getTotalComment() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getComment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    totalcomment = jsonArray.length();
                    total_comment_post_details.setText(totalcomment + " bình luận");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu...");
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idpost", String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void addheart() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Server.addreact
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
                params.put("idpost", String.valueOf(id));
                params.put("phoneuser", HomeActivity.phone_number_user);
                return params;
            }
        };
        requestQueue.add(request);
    }
    private void getTotalLike() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getTotalLike, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    totalLike = jsonArray.length();
                    if (totalLike < 1) {
                        haylanguoithichdautien.setVisibility(View.VISIBLE);
                    }
                    total_like_post_details.setText(totalLike + " thích");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu...");
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idpost", String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void getStatus() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Server.getStatusReact
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                status = Integer.parseInt(response);
                checkstt();
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
                params.put("idpost", String.valueOf(id));
                params.put("phoneuser", HomeActivity.phone_number_user);
                return params;
            }
        };
        requestQueue.add(request);
    }
    private void checkstt() {
        if (status == 1) {
            icon_heart_no_details.setImageResource(R.drawable.icon_heart);
        }
        if (status == 0) {
            icon_heart_no_details.setImageResource(R.drawable.icon_heart_no);
        }
    }
    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getPostById, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    int idpost = jsonObject.getInt("id");
                    String nameplace = jsonObject.getString("nameplace");

                    String province = jsonObject.getString("province");
                    String district = jsonObject.getString("district");
                    String ward = jsonObject.getString("ward");
                    String address = jsonObject.getString("address");

                    String description = jsonObject.getString("description");
                    String content = jsonObject.getString("content");
                    image1 = jsonObject.getString("image1");
                    image2 = jsonObject.getString("image2");
                    image3 = jsonObject.getString("image3");
                    image4 = jsonObject.getString("image4");
                    String imageuser = jsonObject.getString("imageuser");
                    String nameuser = jsonObject.getString("nameuser");
                    phoneuser = jsonObject.getString("phoneuser");
                    String datepost = jsonObject.getString("datepost");

                    String url1 = Server.imagesget + image1;
                    String url2 = Server.imagesget + image2;
                    String url3 = Server.imagesget + image3;
                    String url4 = Server.imagesget + image4;


                    String urlimage = Server.userget + imageuser;
                    user_name.setText(nameuser);
                    name_place.setText(nameplace);
                    timeandplace_post_details.setText(" • Lúc " + datepost + " • Tại " + province);
                    description_place.setText("Địa chỉ: " + address);
                    content_place.setText(content);
//                    Glide.with(getApplicationContext()).load(url1).into(img1);
//                    Glide.with(getApplicationContext()).load(url2).into(img2);
//                    Glide.with(getApplicationContext()).load(url3).into(img3);
//                    Glide.with(getApplicationContext()).load(url4).into(img4);
                    Glide.with(getApplicationContext()).load(urlimage).into(user_ava);
                    toolbar_details_post.setTitle("Bài viết " + nameuser);

                    mImageUrls1.add(url1);
                    mImageUrls1.add(url2);
                    mImageUrls1.add(url3);
                    mImageUrls1.add(url4);
                    RecyclerViewAdapterImage recyclerViewAdapter1 = new RecyclerViewAdapterImage(mImageUrls1, getApplicationContext());
                    recyclerView1.setAdapter(recyclerViewAdapter1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "Lỗi " + error.toString(), Toast.LENGTH_SHORT).show();
                Show_SnackBar(R.drawable.icon_toast_warning, "Lỗi!", "Đóng");
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idpost", String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void AnhXa() {
        user_ava = (ImageView) findViewById(R.id.img_user_post_details);
//        img1 = (ImageView) findViewById(R.id.img1_post_details);
//        img2 = (ImageView) findViewById(R.id.img2_post_details);
//        img3 = (ImageView) findViewById(R.id.img3_post_details);
//        img4 = (ImageView) findViewById(R.id.img4_post_details);
        like_post_detail = findViewById(R.id.like_post_detail);
        total_like_post_details = findViewById(R.id.total_like_post_details);
        share_post_detail = (LinearLayout) findViewById(R.id.share_post_detail);
        comment_post_detail = (LinearLayout) findViewById(R.id.comment_post_detail);
        icon_heart_no_details = findViewById(R.id.icon_heart_no_details);
        timeandplace_post_details = findViewById(R.id.timeandplace_post_details);
        user_name = (TextView) findViewById(R.id.user_name_post_details);
        name_place = (TextView) findViewById(R.id.ten_dia_diem_post_details);
        address_place = (TextView) findViewById(R.id.timeandplace_post_details);
        description_place = (TextView) findViewById(R.id.mo_ta_post_details);
        content_place = (TextView) findViewById(R.id.noi_dung_post_details);
        toolbar_details_post = findViewById(R.id.toolbar_details_post);
//        scroller = findViewById(R.id.scroller);
        comment_post_detail_edt = findViewById(R.id.comment_post_detail_edt);
        total_comment_post_details = findViewById(R.id.total_comment_post_details);
        send_content_comment_post = findViewById(R.id.send_content_comment_post);
        btn_xemthem_comment = findViewById(R.id.btn_xemthem_comment);
        xemthembinhluan = findViewById(R.id.xemthembinhluan);
        xemthembinhluan.setVisibility(View.GONE);
        haylanguoithichdautien = findViewById(R.id.haylanguoithichdautien);
        haylanguoithichdautien.setVisibility(View.GONE);
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

        View view = findViewById(R.id.scroller);
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
        sound.playSound(PostDetail.this, R.raw.toast);
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