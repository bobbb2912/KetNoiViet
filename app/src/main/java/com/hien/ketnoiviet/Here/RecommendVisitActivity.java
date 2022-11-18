package com.hien.ketnoiviet.Here;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
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
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Others.PersonalOtherInfo;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.PostAdapter;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecommendVisitActivity extends AppCompatActivity {

    ListView list_dtq_lc, list_dtq_tc;
    Toolbar toolar_diemthamqaun;
    TextView btn_lancan_dtq,btn_tatca_dtq;
//    public static String adminArea = "tinh";
//    public static String subAdminArea = "huyen";
    public ArrayList<Post> arrayPostRecommendLc,arrayPostRecommendTc;
    public PostAdapter postAdapterRecommendLc,postAdapterRecommendTc;
    LinearLayout chua_co_bai_viet_recommend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_visit);

//        adminArea = HomeActivity.addresses.get(0).getAdminArea();
//        subAdminArea = HomeActivity.addresses.get(0).getSubAdminArea();
//        Toast.makeText(getApplicationContext(), "Tỉnh: " + adminArea + "\n"
//                + "Huyện: " + subAdminArea, Toast.LENGTH_SHORT).show();
        anhXa();
        suKien();
//        getPostbyNameProvince(adminArea);
        GetDataPost();
        list_dtq_tc.setVisibility(View.VISIBLE);
        list_dtq_lc.setVisibility(View.GONE);
    }

    private void GetDataPost() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.getPost, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arrayPostRecommendTc.clear();
//                    Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
                        if(response != null){
                            chua_co_bai_viet_recommend.setVisibility(View.GONE);
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
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

                                    arrayPostRecommendTc.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));
                                    postAdapterRecommendTc.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
//                        Toast.makeText(getContext(), "Độ dài:" + arrayPost.size(), Toast.LENGTH_SHORT).show();
                        }else {
                            chua_co_bai_viet_recommend.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    CheckConnection.ShowToast_Short(getContext(), "Lỗi kết nối dữ liệu..." + error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void suKien() {
        toolar_diemthamqaun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecommendVisitActivity.this.finish();
            }
        });
//        btn_tatca_dtq.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View view) {
//                btn_tatca_dtq.setTextColor(getResources().getColor(R.color.purple_primary));
//                btn_tatca_dtq.setBackgroundResource(R.color.background);
//                btn_lancan_dtq.setTextColor(getResources().getColor(R.color.white));
//                btn_lancan_dtq.setBackgroundResource(R.color.text_color_hint);
//                getPostbyNameProvince(adminArea);
//                list_dtq_tc.setVisibility(view.VISIBLE);
//                list_dtq_lc.setVisibility(View.GONE);
//            }
//        });
//        btn_lancan_dtq.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public void onClick(View view) {
//                btn_lancan_dtq.setTextColor(getResources().getColor(R.color.purple_primary));
//                btn_lancan_dtq.setBackgroundResource(R.color.background);
//                btn_tatca_dtq.setTextColor(getResources().getColor(R.color.white));
//                btn_tatca_dtq.setBackgroundResource(R.color.text_color_hint);
//                getPostbyNameDistricts(subAdminArea);
//                list_dtq_lc.setVisibility(view.VISIBLE);
//                list_dtq_tc.setVisibility(View.GONE);
//            }
//        });

        findViewById(R.id.sapxep_rcm).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
            intent.putExtra("data", "sapxep");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        findViewById(R.id.bankinh_rcm).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
            intent.putExtra("data", "bankinh");
            startActivity(intent);
        });
        findViewById(R.id.theloai_rcm).setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SelectActivity.class);
            intent.putExtra("data", "theloai");
            startActivity(intent);
        });
    }

    private void anhXa() {
        chua_co_bai_viet_recommend = findViewById(R.id.chua_co_bai_viet_recommend);
        toolar_diemthamqaun = findViewById(R.id.toolar_diemthamqaun);
        btn_lancan_dtq = findViewById(R.id.btn_lancan_dtq);
        btn_tatca_dtq = findViewById(R.id.btn_tatca_dtq);
        list_dtq_lc = findViewById(R.id.list_dtq_lc);
        arrayPostRecommendLc = new ArrayList<>();
        postAdapterRecommendLc = new PostAdapter(getApplicationContext(), arrayPostRecommendLc);
        list_dtq_lc.setAdapter(postAdapterRecommendLc);
        list_dtq_tc = findViewById(R.id.list_dtq_tc);
        arrayPostRecommendTc = new ArrayList<>();
        postAdapterRecommendTc = new PostAdapter(getApplicationContext(), arrayPostRecommendTc);
        list_dtq_tc.setAdapter(postAdapterRecommendTc);
    }
    private void getPostbyNameProvince(String tinh) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getPostByNameProvince, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayPostRecommendTc.clear();
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length()>0){
                        chua_co_bai_viet_recommend.setVisibility(View.GONE);
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
                            arrayPostRecommendTc.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));
                            postAdapterRecommendTc.notifyDataSetChanged();
                        }
                    }
                    else {
                        chua_co_bai_viet_recommend.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplicationContext(), "Bài viết của tỉnh...." + jsonArray.length(), Toast.LENGTH_SHORT).show();
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
                param.put("nameprovince", tinh);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void getPostbyNameDistricts(String huyen) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getPostByNameDistrict, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayPostRecommendLc.clear();
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length()>0){
                        chua_co_bai_viet_recommend.setVisibility(View.GONE);
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

                            arrayPostRecommendLc.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));
                            postAdapterRecommendLc.notifyDataSetChanged();
                        }
                    }
                    else {
                        chua_co_bai_viet_recommend.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplicationContext(), "Bài viết của huyện...." + jsonArray.length(), Toast.LENGTH_SHORT).show();
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
                param.put("nameprovince", huyen);
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
}