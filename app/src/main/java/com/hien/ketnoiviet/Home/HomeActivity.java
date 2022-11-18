package com.hien.ketnoiviet.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.XmlResourceParser;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hien.ketnoiviet.Login.LoginActivity;
import com.hien.ketnoiviet.Others.NewsDetail;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.NewsAdapter;
import com.hien.ketnoiviet.adapter.PostAdapter;
import com.hien.ketnoiviet.adapter.ProvinceAdapter;
import com.hien.ketnoiviet.adapter.WardAdapter;
import com.hien.ketnoiviet.model.News;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.model.Province;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.XMLDOMParser;
import com.hien.ketnoiviet.ultil.networkChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HomeActivity extends AppCompatActivity {


    public static NewsAdapter newsAdapter;
    public static ArrayList<News> arrNews;

    private static final String FILE_NAME = "myFile";
    private BottomNavigationView mNavigationView;
    private ViewPager mViewPager;

    private static final String FILE_CHECK = "mycheck";
    public static String phone_number_user = "";
    private static final String FILE_POST = "isPost";
    public static String isPost = "";
    public static Location location;
    public static  List<Address> addresses;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewBinding();
        event();

        SharedPreferences sharedPreferences = getSharedPreferences(FILE_POST, MODE_PRIVATE);
        isPost = sharedPreferences.getString("isPost", "");
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            arrNews = new ArrayList<>();
            newsAdapter = new NewsAdapter(HomeActivity.this, arrNews);
            getNewsJSON();
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 44);
        }
    }

    private void getNewsJSON() {
        String url = "https://api.rss2json.com/v1/api.json?rss_url=https://vnexpress.net/rss/du-lich.rss";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String title = object.getString("title");
                        String date = object.getString("pubDate");
                        String link = object.getString("link");
                        String description = object.getString("description");
                        String thumbnail = object.getString("thumbnail");

                        //region Cắt chuỗi Description
                        String[] parts = description.split("</a>");
                        String part1 = parts[0]; // 004
                        String part2 = parts[1]; // 034556
                        //endregion

//                        Toast.makeText(getApplicationContext(), "Thumbnail: " + thumbnail, Toast.LENGTH_SHORT).show();
                        arrNews.add(new News(title,link,date,thumbnail,part2));
                    }
                    newsAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
//                location = task.getResult();
//                if (location!=null){
//                    try {
//                        Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
//                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
////                        Toast.makeText(getApplicationContext(), "Vĩ độ: " + addresses.get(0).getLatitude()
////                                                                        + "\nTung độ: "+ addresses.get(0).getLongitude()
////                                                                        + "\nQuốc gia: "+ addresses.get(0).getCountryName() //Việt Nam
////                                                                        + "\nQuận: "+ addresses.get(0).getSubAdminArea() //Quận, Huyện, Thị Xã
////                                                                        + "\nTỉnh: "+ addresses.get(0).getAdminArea() //Tỉnh, Thành Phố
////                                                                        + "\nSố nhà: "+ addresses.get(0).getFeatureName() //Phường, xã, thị trấn
////                                                                        + "\nTên đường: "+ addresses.get(0).getThoroughfare() //Tên đường
////                                                                        +"\nSố nhà: "+ addresses.get(0).getSubThoroughfare() //Số nhà
////                                                                        + "\nĐịa chỉ: "+ addresses.get(0).getAddressLine(0)
////                                                                        ,Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        });
    }

    private void event() {
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.action_add:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.action_here:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.action_notify:
                    //case R.layout.activity_read_news:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.action_personal:
                        mViewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });

        setUpViewPager();
    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        mNavigationView.getMenu().findItem(R.id.action_add).setChecked(true);
                        break;
                    case 2:
                        mNavigationView.getMenu().findItem(R.id.action_here).setChecked(true);
                        break;
                    case 3:
                        mNavigationView.getMenu().findItem(R.id.action_notify).setChecked(true);
                        //mNavigationView.getMenu().findItem(R.layout.activity_read_news);
                        break;
                    case 4:
                        mNavigationView.getMenu().findItem(R.id.action_personal).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void viewBinding() {
        mNavigationView = findViewById(R.id.nav_home);
        mViewPager = findViewById(R.id.viewpager_home);
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_CHECK, MODE_PRIVATE);
        phone_number_user = sharedPreferences.getString("phone", "");
//        Toast.makeText(getApplicationContext(), phone_number_user, Toast.LENGTH_SHORT).show();
    }

    //region Ghi đè nút back trên điện thoại, vô hiệu hóa quay lại màn hình trước
    @Override
    public void onBackPressed() {

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