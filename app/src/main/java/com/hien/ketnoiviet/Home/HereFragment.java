package com.hien.ketnoiviet.Home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.hien.ketnoiviet.Here.RecommendVisitActivity;
import com.hien.ketnoiviet.R;

public class HereFragment extends Fragment implements  OnMapReadyCallback{
    LinearLayout diemthamquan, nhahang, khachsan, giaitri, phuongtiendilai, khac;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    Location currentLocation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_here, container, false);
        diemthamquan = view.findViewById(R.id.diemthamquan);
        nhahang = view.findViewById(R.id.nhahang);
        khachsan = view.findViewById(R.id.khachsan);
        giaitri = view.findViewById(R.id.giaitri);
        phuongtiendilai = view.findViewById(R.id.phuongtiendilai);
        khac = view.findViewById(R.id.khac);
        diemthamquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RecommendVisitActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
            }
        });
        nhahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chức năng chưa cập nhật", Toast.LENGTH_SHORT).show();
            }
        });
        khachsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chức năng chưa cập nhật", Toast.LENGTH_SHORT).show();
            }
        });
        giaitri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chức năng chưa cập nhật", Toast.LENGTH_SHORT).show();
            }
        });
        phuongtiendilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chức năng chưa cập nhật", Toast.LENGTH_SHORT).show();
            }
        });
        khac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Chức năng chưa cập nhật", Toast.LENGTH_SHORT).show();
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fetchLastLocation();

        return view;
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    SupportMapFragment supportMapFragment =
                            (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(HereFragment.this);
                }
            }
        });
    }
//14.424772, 109.004357
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //LatLng tripsviet = new LatLng(HomeActivity.location.getLatitude(), HomeActivity.location.getLongitude());
        LatLng tripsviet = new LatLng(14.424772, 109.004357);
        googleMap.addMarker(new MarkerOptions().position(tripsviet).title("Bạn đang ở đây"));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //map.moveCamera(CameraUpdateFactory.newLatLng(nhatui));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        },3000);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tripsviet, 12));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
                break;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(HereFragment.this);
        }
    }
}
