package com.hien.ketnoiviet.Others;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Intro.IntroOneActivity;
import com.hien.ketnoiviet.Login.ResetPhoneActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.PostAdapter;
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
import java.util.List;
import java.util.Map;

public class SearchPlace extends AppCompatActivity {

    TextView chua_co_bai_viet_search;
    ListView list_search_place;
    public ArrayList<Post> arrayPostSearch;
    public PostAdapter postAdapterSearch;
    ImageButton back_to_home_search;
    AutoCompleteTextView edt_search_place;
    ArrayList<String> strings = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        anhxa();
        getdsplace();
        event();
    }

    private void getdsplace() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.getNameplace, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response != null){
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String nameplace = jsonObject.getString("nameplace");
                                    strings.add(nameplace);
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
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,strings);
        edt_search_place.setThreshold(1);
        edt_search_place.setAdapter(adapter);
        edt_search_place.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edt_search_place.setText(adapter.getItem(i));
                edt_search_place.clearFocus();
                Search(edt_search_place.getText().toString().trim());
            }
        });
    }

    private void Search(String searchplace) {
        sound.playSound(SearchPlace.this, R.raw.post_like_cmt);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getPostByNamePlace, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arrayPostSearch.clear();
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

                            arrayPostSearch.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));
                            postAdapterSearch.notifyDataSetChanged();
                        }
                    }
                    else {
                        chua_co_bai_viet_search.setVisibility(View.VISIBLE);
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
                param.put("nameplace", searchplace);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void event() {
        back_to_home_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchPlace.this.finish();
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
            }
        });
        edt_search_place.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = edt_search_place.getText().toString();
                    if(search.equals("")){
//                        Toast.makeText(getApplicationContext(), "Vui lòng nhập địa điểm cần tìm kiếm!", Toast.LENGTH_SHORT).show();
//                        sound.playSound(SearchPlace.this, R.raw.toast);
                        Show_SnackBar(R.drawable.icon_toast_warning, "Vui lòng nhập địa điểm cần tìm kiếm!", "Đóng");

                    }
                    else {
                        arrayPostSearch.clear();
                        Search(search);
                        edt_search_place.clearFocus();
                    }
                    return true;
                }
                return false;
            }
        });
    }
    private void anhxa() {
        back_to_home_search = findViewById(R.id.back_to_home_search);
        edt_search_place = findViewById(R.id.edt_search_place);
        list_search_place = findViewById(R.id.list_search_place);
        arrayPostSearch = new ArrayList<>();
        postAdapterSearch = new PostAdapter(this, arrayPostSearch);
        list_search_place.setAdapter(postAdapterSearch);
        chua_co_bai_viet_search = findViewById(R.id.chua_co_bai_viet_search);
        chua_co_bai_viet_search.setVisibility(View.GONE);
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

        View view = findViewById(R.id.layout_search_place);
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
        sound.playSound(SearchPlace.this, R.raw.toast);
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