package com.hien.ketnoiviet.Discover;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
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
import com.hien.ketnoiviet.Others.imgFitScreen;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoByProvince extends AppCompatActivity {

    String idprovince = "";
    TextView province_number_vehicle,province_popular,province_area,province_location,province_name_info,province_danhlam,province_dacsan;
    ImageView province_image_info;
    Toolbar toolbar_info_province;
    Button province_list_post;
    int id = 0;
    String name = "";
    String imageprovince = "";
    String location = "";
    String area = "";
    String population = "";
    String numbervehicle = "";
    String danhlam = "";
    String dacsan = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_by_province);
        Intent intent = this.getIntent();
        idprovince = intent.getStringExtra("idprovince");
        anhxa();
        getDataProvince();
        event();
    }

    private void event() {
        toolbar_info_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoByProvince.this.finish();
            }
        });
        province_list_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Danh sách các bài viết ở " + name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ListPostByProvince.class);
                intent.putExtra("nameprovince", name);
                startActivity(intent);
            }
        });
        province_image_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
                intent.putExtra("url1", Server.provinceget + imageprovince + ".jpg");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    private void getDataProvince() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getProvinceById, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        name = jsonObject.getString("name");
                        imageprovince = jsonObject.getString("imageprovince");
                        location = jsonObject.getString("location");
                        area = jsonObject.getString("area");
                        population = jsonObject.getString("population");
                        numbervehicle = jsonObject.getString("numbervehicle");
                        danhlam = jsonObject.getString("danhlam");
                        dacsan = jsonObject.getString("dacsan");

                        province_name_info.setText(name);
                        String url_province = Server.provinceget + imageprovince + ".jpg";
                        Glide.with(getApplicationContext()).load(url_province).into(province_image_info);
                        province_location.setText(location);
                        province_area.setText(area);
                        province_popular.setText(population + "(năm 2020)");
                        province_number_vehicle.setText(numbervehicle);
                        province_danhlam.setText(danhlam);
                        province_dacsan.setText(dacsan);
                        toolbar_info_province.setTitle("Thông tin " + name);
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
                param.put("idprovince",idprovince);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void anhxa() {
        province_name_info = findViewById(R.id.province_name_info);
        province_image_info = findViewById(R.id.province_image_info);
        province_location = findViewById(R.id.province_location);
        province_area = findViewById(R.id.province_area);
        province_popular = findViewById(R.id.province_popular);
        province_number_vehicle = findViewById(R.id.province_number_vehicle);
        toolbar_info_province = findViewById(R.id.toolbar_info_province);
        province_list_post = findViewById(R.id.province_list_post);
        province_danhlam = findViewById(R.id.province_danhlam);
        province_dacsan = findViewById(R.id.province_dacsan);
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