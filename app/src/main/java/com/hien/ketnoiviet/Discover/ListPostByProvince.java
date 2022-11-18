package com.hien.ketnoiviet.Discover;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hien.ketnoiviet.Others.SearchPlace;
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

public class ListPostByProvince extends AppCompatActivity {

    String nameprovince = "";
    ListView list_search_province_name;
    TextView chua_co_bai_viet_province_name;
    Toolbar toolbar_province_name_post;
    public ArrayList<Post> arrayPostProvince;
    public PostAdapter postAdapterProvince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_post_by_province);
        Intent intent = this.getIntent();
        nameprovince = intent.getStringExtra("nameprovince");
        anhxa();
        getPostbyNameProvince();
        sukien();
    }

    private void sukien() {
        toolbar_province_name_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListPostByProvince.this.finish();
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
            }
        });
    }

    private void anhxa() {
        list_search_province_name = findViewById(R.id.list_search_province_name);
        chua_co_bai_viet_province_name = findViewById(R.id.chua_co_bai_viet_province_name);
        chua_co_bai_viet_province_name.setVisibility(View.GONE);
        toolbar_province_name_post = findViewById(R.id.toolbar_province_name_post);
        arrayPostProvince = new ArrayList<>();
        postAdapterProvince = new PostAdapter(this, arrayPostProvince);
        list_search_province_name.setAdapter(postAdapterProvince);
    }

    private void getPostbyNameProvince() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getPostByNameProvince, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayPostProvince.clear();
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length()>0){
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

                            arrayPostProvince.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));
                            postAdapterProvince.notifyDataSetChanged();
                        }
                    }
                    else {
                        chua_co_bai_viet_province_name.setVisibility(View.VISIBLE);
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
                param.put("nameprovince", nameprovince);
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