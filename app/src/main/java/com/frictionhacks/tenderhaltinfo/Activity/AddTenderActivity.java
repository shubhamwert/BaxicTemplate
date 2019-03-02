package com.frictionhacks.tenderhaltinfo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.frictionhacks.tenderhaltinfo.R;
import com.frictionhacks.tenderhaltinfo.Util.Methods;

import java.util.Calendar;

public class AddTenderActivity extends AppCompatActivity {
    String unixDate;
    Button startDate,stopDate;
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


    }

}
