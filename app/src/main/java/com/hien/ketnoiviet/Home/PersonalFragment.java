package com.hien.ketnoiviet.Home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Help.HelpCenter;
import com.hien.ketnoiviet.Help.ReportProblem;
import com.hien.ketnoiviet.Help.Safe;
import com.hien.ketnoiviet.Help.SupportMailbox;
import com.hien.ketnoiviet.Help.TermsAndPolicies;
import com.hien.ketnoiviet.Login.LoginActivity;
import com.hien.ketnoiviet.Personal.Payment;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.Personal.RatePersonal;
import com.hien.ketnoiviet.Personal.SettingPersonal;
import com.hien.ketnoiviet.Personal.UnapprovedPosts;
import com.hien.ketnoiviet.Personal.UpdateInformation;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.sound;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalFragment extends Fragment {
    CircleImageView image_person;
    TextView personl_personal,update_information,unapprovedposts,payment_personal,rate_personal, supportmailbox, helpcenter,support_personal, reportproblem, safe, terms_and_policies,setting_personal,log_out_persional,see_personl_personal;
    LinearLayout hide_or_show_list_personal;
    ImageButton button_logout_personal;
    Button cancel_logout, confirm_logout;
    private boolean isHide = true;
    private static final String FILE_CHECK = "mycheck";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        image_person = view.findViewById(R.id.image_person);
        log_out_persional = view.findViewById(R.id.log_out_persional);
        hide_or_show_list_personal = view.findViewById(R.id.hide_or_show_list_personal);
        setting_personal = view.findViewById(R.id.setting_personal);
        support_personal = view.findViewById(R.id.support_personal);
        terms_and_policies = view.findViewById(R.id.termsandpolicies);
        personl_personal = view.findViewById(R.id.personl_personal);
        see_personl_personal = view.findViewById(R.id.see_personl_personal);
        update_information = view.findViewById(R.id.update_information);
        payment_personal = view.findViewById(R.id.payment_personal);
        rate_personal = view.findViewById(R.id.rate_personal);
        helpcenter = view.findViewById(R.id.helpcenter);
        reportproblem = view.findViewById(R.id.reportproblem);
        safe = view.findViewById(R.id.safe);
        supportmailbox = view.findViewById(R.id.supportmailbox);
        unapprovedposts = view.findViewById(R.id.unapprovedposts);
        button_logout_personal = view.findViewById(R.id.button_logout_personal);

        cancel_logout = view.findViewById(R.id.btn_cancel);
        confirm_logout = view.findViewById(R.id.btn_okay);

        event();
        personal();
        return view;
    }

    private void personal() {
        String phone_number_person = HomeActivity.phone_number_user;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getuserinfo, response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String  name     = jsonObject.getString("nameuser");
                String  img = jsonObject.getString("imageuser");
                String url_image = Server.userget + img;
                Glide.with(getContext()).load(url_image).into(image_person);
                personl_personal.setText(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> CheckConnection.ShowToast_Short(getContext(), "Lỗi kết nối dữ liệu..." + error.toString())){
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

    private void event() {
        update_information.setOnClickListener(view -> startActivity(new Intent(getContext(), UpdateInformation.class)));

        log_out_persional.setOnClickListener(view -> Show_Popup("Bạn có muốn đăng xuất?"));
        button_logout_personal.setOnClickListener(view -> Show_Popup("Bạn có muốn đăng xuất?"));
        hide_or_show_list_personal.setVisibility(View.GONE);
        support_personal.setOnClickListener(view -> {
            if (isHide==true){
                isHide = false;
                hide_or_show_list_personal.setVisibility(View.VISIBLE);
            }
            else {
                isHide = true;
                hide_or_show_list_personal.setVisibility(View.GONE);
            }
        });
        setting_personal.setOnClickListener(view -> startActivity(new Intent(getContext(), SettingPersonal.class)));
        helpcenter.setOnClickListener(view -> startActivity(new Intent(getContext(), HelpCenter.class)));
        reportproblem.setOnClickListener(view -> startActivity(new Intent(getContext(), ReportProblem.class)));
        safe.setOnClickListener(view -> startActivity(new Intent(getContext(), Safe.class)));
        supportmailbox.setOnClickListener(view -> startActivity(new Intent(getContext(), SupportMailbox.class)));
        terms_and_policies.setOnClickListener(view -> startActivity(new Intent(getContext(), TermsAndPolicies.class)));
        personl_personal.setOnClickListener(view -> startActivity(new Intent(getContext(), PersonalInfo.class)));
        see_personl_personal.setOnClickListener(view -> startActivity(new Intent(getContext(), PersonalInfo.class)));
        payment_personal.setOnClickListener(view -> startActivity(new Intent(getContext(), Payment.class)));
        rate_personal.setOnClickListener(view -> startActivity(new Intent(getContext(), RatePersonal.class)));
        unapprovedposts.setOnClickListener(view -> startActivity(new Intent(getContext(), UnapprovedPosts.class)));
    }

    //region Custom a notification
    public void Show_Popup(String text){

        sound.playSound(getContext(), R.raw.thongbao);

        Dialog dialog = new Dialog(getContext());

        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title = dialog.findViewById(R.id.content_alert);
        Button ok = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        title.setText(text);
        ok.setText("Chấp nhận");
        cancel.setOnClickListener(v -> dialog.dismiss());

        ok.setOnClickListener(view -> {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(FILE_CHECK, Context.MODE_PRIVATE).edit();
            editor.putString("isDangxuat","yes");
            editor.apply();
            startActivity(new Intent(getContext(), LoginActivity.class));
        });
        dialog.show();
    }
    //endregion

}
