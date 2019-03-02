package com.frictionhacks.tenderhaltinfo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;

import com.frictionhacks.tenderhaltinfo.R;

import java.util.Calendar;

public class AddTenderActivity extends AppCompatActivity {
    String unixDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tender);
    }


    private void getDateDialog(){


        DatePickerDialog datePick=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                long epoch=calendar.getTimeInMillis()/ 1000;
                unixDate =Long.toString(epoch);

                //getDarkSky(31.6798,76.5026,unixDate);
            }
        },
                2019,1,15 );
        datePick.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePick.getDatePicker().setMinDate(System.currentTimeMillis()-345600000);
        datePick.show();



    }
}
