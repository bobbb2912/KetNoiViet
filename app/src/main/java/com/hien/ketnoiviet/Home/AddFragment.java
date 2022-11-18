package com.hien.ketnoiviet.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.Discover.PostActivity;
import com.hien.ketnoiviet.adapter.RecyclerViewAdapter;
import com.hien.ketnoiviet.ultil.Server;

import java.util.ArrayList;

public class AddFragment extends Fragment {
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        toolbar = view.findViewById(R.id.add_discover);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PostActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
            }
        });

        //region Tây Bắc Bộ
        ArrayList<String> mId1 = new ArrayList<>();
        ArrayList<String> mNames1 = new ArrayList<>();
        ArrayList<String> mImageUrls1 = new ArrayList<>();

        mId1.add("29");
        mNames1.add("Hòa Bình");
        mImageUrls1.add(Server.provinceget + "hoabinh.jpg");

        mId1.add("54");
        mNames1.add("Sơn La");
        mImageUrls1.add(Server.provinceget + "sonla.jpg");

        mId1.add("58");
        mNames1.add("Điện Biên");
        mImageUrls1.add(Server.provinceget + "dienbien.jpg");

        mId1.add("59");
        mNames1.add("Lai Châu");
        mImageUrls1.add(Server.provinceget + "laichau.jpg");

        mId1.add("34");
        mNames1.add("Lào Cai");
        mImageUrls1.add(Server.provinceget + "laocai.jpg");

        mId1.add("56");
        mNames1.add("Yên Bái");
        mImageUrls1.add(Server.provinceget + "yenbai.jpg");


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView1 = view.findViewById(R.id.recyclerview1);
        recyclerView1.setLayoutManager(layoutManager1);
        RecyclerViewAdapter recyclerViewAdapter1 = new RecyclerViewAdapter(mId1,mNames1,mImageUrls1, getContext());
        recyclerView1.setAdapter(recyclerViewAdapter1);
        //endregion
        //region Bắc Trung Bộ
        ArrayList<String> mId2 = new ArrayList<>();
        ArrayList<String> mNames2 = new ArrayList<>();
        ArrayList<String> mImageUrls2 = new ArrayList<>();

        mId2.add("19");
        mNames2.add("Thanh Hóa");
        mImageUrls2.add(Server.provinceget + "thanhhoa.jpg");

        mId2.add("20");
        mNames2.add("Nghệ An");
        mImageUrls2.add(Server.provinceget + "nghean.jpg");

        mId2.add("46");
        mNames2.add("Hà Tĩnh");
        mImageUrls2.add(Server.provinceget + "hatinh.jpg");

        mId2.add("50");
        mNames2.add("Quảng Bình");
        mImageUrls2.add(Server.provinceget + "quangbinh.jpg");

        mId2.add("51");
        mNames2.add("Quảng Trị");
        mImageUrls2.add(Server.provinceget + "quangtri.jpg");

        mId2.add("15");
        mNames2.add("Thừa Thiên Huế");
        mImageUrls2.add(Server.provinceget + "hue.jpg");


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView2 = view.findViewById(R.id.recyclerview2);
        recyclerView2.setLayoutManager(layoutManager2);
        RecyclerViewAdapter recyclerViewAdapter2 = new RecyclerViewAdapter(mId2,mNames2,mImageUrls2, getContext());
        recyclerView2.setAdapter(recyclerViewAdapter2);
        //endregion
        //region Bắc Trung Bộ
        ArrayList<String> mId3 = new ArrayList<>();
        ArrayList<String> mNames3 = new ArrayList<>();
        ArrayList<String> mImageUrls3 = new ArrayList<>();

        mId3.add("42");
        mNames3.add("Phú Thọ");
        mImageUrls3.add(Server.provinceget + "phutho.jpg");

        mId3.add("61");
        mNames3.add("Hà Giang");
        mImageUrls3.add(Server.provinceget + "hagiang.jpg");

        mId3.add("57");
        mNames3.add("Tuyên Quang");
        mImageUrls3.add(Server.provinceget + "tuyenquang.jpg");

        mId3.add("63");
        mNames3.add("Cao Bằng");
        mImageUrls3.add(Server.provinceget + "caobang.jpg");

        mId3.add("62");
        mNames3.add("Bắc Kan");
        mImageUrls3.add(Server.provinceget + "backan.jpg");

        mId3.add("33");
        mNames3.add("Thái Nguyên");
        mImageUrls3.add(Server.provinceget + "thainguyen.jpg");


        mId3.add("60");
        mNames3.add("Lạng Sơn");
        mImageUrls3.add(Server.provinceget + "langson.jpg");

        mId3.add("28");
        mNames3.add("Bắc Giang");
        mImageUrls3.add(Server.provinceget + "bacgiang.jpg");

        mId3.add("18");
        mNames3.add("Quảng Ninh");
        mImageUrls3.add(Server.provinceget + "quangninh.jpg");


        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView3 = view.findViewById(R.id.recyclerview3);
        recyclerView3.setLayoutManager(layoutManager3);
        RecyclerViewAdapter recyclerViewAdapter3 = new RecyclerViewAdapter(mId3,mNames3,mImageUrls3, getContext());
        recyclerView3.setAdapter(recyclerViewAdapter3);
        //endregion
        //region Nam Trung Bộ
        ArrayList<String> mId4 = new ArrayList<>();
        ArrayList<String> mNames4 = new ArrayList<>();
        ArrayList<String> mImageUrls4 = new ArrayList<>();

        mId4.add("3");
        mNames4.add("Đà Nẵng");
        mImageUrls4.add(Server.provinceget + "danang.jpg");

        mId4.add("9");
        mNames4.add("Quảng Nam");
        mImageUrls4.add(Server.provinceget + "quangnam.jpg");

        mId4.add("36");
        mNames4.add("Quảng Ngãi");
        mImageUrls4.add(Server.provinceget + "quangngai.jpg");

        mId4.add("25");
        mNames4.add("Bình Định");
        mImageUrls4.add(Server.provinceget + "binhdinh.jpg");

        mId4.add("44");
        mNames4.add("Phú Yên");
        mImageUrls4.add(Server.provinceget + "phuyen.jpg");

        mId4.add("6");
        mNames4.add("Khánh Hòa");
        mImageUrls4.add(Server.provinceget + "khanhhoa.jpg");

        mId4.add("43");
        mNames4.add("Ninh Thuận");
        mImageUrls4.add(Server.provinceget + "ninhthuan.jpg");

        mId4.add("13");
        mNames4.add("Bình Thuận");
        mImageUrls4.add(Server.provinceget + "binhthuan.jpg");

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView4 = view.findViewById(R.id.recyclerview4);
        recyclerView4.setLayoutManager(layoutManager4);
        RecyclerViewAdapter recyclerViewAdapter4 = new RecyclerViewAdapter(mId4,mNames4,mImageUrls4, getContext());
        recyclerView4.setAdapter(recyclerViewAdapter4);
        //endregion
        //region Đồng Bằng Sông Hồng
        ArrayList<String> mId5 = new ArrayList<>();
        ArrayList<String> mNames5 = new ArrayList<>();
        ArrayList<String> mImageUrls5 = new ArrayList<>();

        mId5.add("2");
        mNames5.add("Hà Nội");
        mImageUrls5.add(Server.provinceget + "hanoi.jpg");

        mId5.add("17");
        mNames5.add("Bắc Ninh");
        mImageUrls5.add(Server.provinceget + "bacninh.jpg");

        mId5.add("45");
        mNames5.add("Hà Nam");
        mImageUrls5.add(Server.provinceget + "hanam.jpg");

        mId5.add("21");
        mNames5.add("Hải Dương");
        mImageUrls5.add(Server.provinceget + "haiduong.jpg");

        mId5.add("7");
        mNames5.add("Hải Phòng");
        mImageUrls5.add(Server.provinceget + "haiphong.jpg");

        mId5.add("24");
        mNames5.add("Hưng Yên");
        mImageUrls5.add(Server.provinceget + "hungyen.jpg");

        mId5.add("35");
        mNames5.add("Nam Định");
        mImageUrls5.add(Server.provinceget + "namdinh.jpg");

        mId5.add("27");
        mNames5.add("Thái Bình");
        mImageUrls5.add(Server.provinceget + "thaibinh.jpg");

        mId5.add("31");
        mNames5.add("Vĩnh Phúc");
        mImageUrls5.add(Server.provinceget + "vinhphuc.jpg");

        mId5.add("41");
        mNames5.add("Ninh Bình");
        mImageUrls5.add(Server.provinceget + "ninhbinh.jpg");



        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView5 = view.findViewById(R.id.recyclerview5);
        recyclerView5.setLayoutManager(layoutManager5);
        RecyclerViewAdapter recyclerViewAdapter5 = new RecyclerViewAdapter(mId5,mNames5,mImageUrls5, getContext());
        recyclerView5.setAdapter(recyclerViewAdapter5);
        //endregion
        //region Đông Nam Bộ
        ArrayList<String> mId6 = new ArrayList<>();
        ArrayList<String> mNames6 = new ArrayList<>();
        ArrayList<String> mImageUrls6 = new ArrayList<>();

        mId6.add("1");
        mNames6.add("TP Hồ Chí Minh");
        mImageUrls6.add(Server.provinceget + "tphcm.jpg");

        mId6.add("4");
        mNames6.add("Bình Dương");
        mImageUrls6.add(Server.provinceget + "binhduong.jpg");

        mId6.add("5");
        mNames6.add("Đồng Nai");
        mImageUrls6.add(Server.provinceget + "dongnai.jpg");

        mId6.add("10");
        mNames6.add("Bà Rịa - Vũng Tàu");
        mImageUrls6.add(Server.provinceget + "vungtau.jpg");

        mId6.add("23");
        mNames6.add("Bình Phước");
        mImageUrls6.add(Server.provinceget + "binhphuoc.jpg");

        mId6.add("23");
        mNames6.add("Tây Ninh");
        mImageUrls6.add(Server.provinceget + "tayninh.jpg");

        LinearLayoutManager layoutManager6 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView6 = view.findViewById(R.id.recyclerview6);
        recyclerView6.setLayoutManager(layoutManager6);
        RecyclerViewAdapter recyclerViewAdapter6 = new RecyclerViewAdapter(mId6,mNames6,mImageUrls6, getContext());
        recyclerView6.setAdapter(recyclerViewAdapter6);
        //endregion
        //region Tây Nguyên
        ArrayList<String> mId7 = new ArrayList<>();
        ArrayList<String> mNames7 = new ArrayList<>();
        ArrayList<String> mImageUrls7 = new ArrayList<>();

        mId7.add("11");
        mNames7.add("Đăk Lăk");
        mImageUrls7.add(Server.provinceget + "daklak.jpg");

        mId7.add("14");
        mNames7.add("Lâm Đồng");
        mImageUrls7.add(Server.provinceget + "lamdong.jpg");

        mId7.add("22");
        mNames7.add("Gia Lai");
        mImageUrls7.add(Server.provinceget + "gialai.jpg");

        mId7.add("38");
        mNames7.add("Đăk Nông");
        mImageUrls7.add(Server.provinceget + "daknong.jpg");

        mId7.add("49");
        mNames7.add("Kon Tum");
        mImageUrls7.add(Server.provinceget + "kontum.jpg");

        LinearLayoutManager layoutManager7 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView7 = view.findViewById(R.id.recyclerview7);
        recyclerView7.setLayoutManager(layoutManager7);
        RecyclerViewAdapter recyclerViewAdapter7 = new RecyclerViewAdapter(mId7,mNames7,mImageUrls7, getContext());
        recyclerView7.setAdapter(recyclerViewAdapter7);
        //endregion
        //region Đồng Bằng Sống Cửu Long
        ArrayList<String> mId8 = new ArrayList<>();
        ArrayList<String> mNames8 = new ArrayList<>();
        ArrayList<String> mImageUrls8 = new ArrayList<>();

        mId8.add("8");
        mNames8.add("Long An");
        mImageUrls8.add(Server.provinceget + "longan.jpg");

        mId8.add("12");
        mNames8.add("Cần Thơ");
        mImageUrls8.add(Server.provinceget + "cantho.jpg");

        mId8.add("16");
        mNames8.add("Kiên Giang");
        mImageUrls8.add(Server.provinceget + "kiengiang.jpg");

        mId8.add("26");
        mNames8.add("Tiền Giang");
        mImageUrls8.add(Server.provinceget + "tiengiang.jpg");

        mId8.add("30");
        mNames8.add("An Giang");
        mImageUrls8.add(Server.provinceget + "angiang.jpg");

        mId8.add("37");
        mNames8.add("Bến Tre");
        mImageUrls8.add(Server.provinceget + "bentre.jpg");

        mId8.add("39");
        mNames8.add("Cà Mau");
        mImageUrls8.add(Server.provinceget + "camau.jpg");

        mId8.add("40");
        mNames8.add("Vĩnh Long");
        mImageUrls8.add(Server.provinceget + "vinhlong.jpg");

        mId8.add("47");
        mNames8.add("Đồng Tháp");
        mImageUrls8.add(Server.provinceget + "dongthap.jpg");

        mId8.add("48");
        mNames8.add("Sóc Trăng");
        mImageUrls8.add(Server.provinceget + "soctrang.jpg");

        mId8.add("52");
        mNames8.add("Trà Vinh");
        mImageUrls8.add(Server.provinceget + "travinh.jpg");

        mId8.add("53");
        mNames8.add("Hậu Giang");
        mImageUrls8.add(Server.provinceget + "haugiang.jpg");

        mId8.add("55");
        mNames8.add("Bạc Liêu");
        mImageUrls8.add(Server.provinceget + "baclieu.jpg");

        LinearLayoutManager layoutManager8 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView8 = view.findViewById(R.id.recyclerview8);
        recyclerView8.setLayoutManager(layoutManager8);
        RecyclerViewAdapter recyclerViewAdapter8 = new RecyclerViewAdapter(mId8,mNames8,mImageUrls8, getContext());
        recyclerView8.setAdapter(recyclerViewAdapter8);
        //endregion

        return view;
    }
}
