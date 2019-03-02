package com.frictionhacks.tenderhaltinfo.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.DataWord.TenderDetailWord;
import com.frictionhacks.tenderhaltinfo.Fragments.MapFragment;
import com.frictionhacks.tenderhaltinfo.R;
import com.frictionhacks.tenderhaltinfo.Util.Methods;
import com.google.android.gms.maps.model.LatLng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//Todo device location

public class AddTenderActivity extends AppCompatActivity implements MapFragment.LatLngClickListener{
    String unixDate;
    Button startDate,stopDate;

    MapFragment mapFragment;
    LatLng location;
    FrameLayout fm;
    EditText etTenderId;
    Button btnLoctSelect;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fm.setVisibility(View.GONE);
        btnLoctSelect.setVisibility(View.INVISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tender);

        startDate=findViewById(R.id.btn_tender_start_date);
        stopDate=findViewById(R.id.btn_tender_stop_date);


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods.getDateDialog(AddTenderActivity.this);
            }
        });


        stopDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods.getDateDialog(AddTenderActivity.this);
            }
        });




            mapFragment = new MapFragment();
        Button button=findViewById(R.id.bt_add_tender_detail_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });


        btnLoctSelect = findViewById(R.id.btn_add_tender_open_location);

        fm = findViewById(R.id.mapContainer);
        fm.setVisibility(View.GONE);
        etTenderId=findViewById(R.id.ed_add_activity_tender_name);
        Button btnMapOpen=findViewById(R.id.btn_map_tender_open);
        btnMapOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
btnLoctSelect.setVisibility(View.VISIBLE);
                fm.setVisibility(View.VISIBLE);
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.add(R.id.mapContainer,mapFragment);
                ft.addToBackStack("file");
                ft.commit();

            }
        });
    btnLoctSelect.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            btnLoctSelect.setVisibility(View.GONE);
            ft.remove(mapFragment);
            ft.commit();
        }
    });

    mapFragment.setLatLng(AddTenderActivity.this);
    }

    private void submitData() {
        ContractorTenderDetailsDashboardModel.mData.add(new TenderDetailWord(etTenderId.getText().toString(),location.latitude,location.longitude,"dateStart","dateEnd",1,"image url"));
    finish();

    }

    @Override
    public void onGetLatLng(LatLng latLng) {
        TextView tv=findViewById(R.id.tv_map_location);
        tv.setText(String.format("%s", latLng.toString()));

        location= latLng;
    }
}
