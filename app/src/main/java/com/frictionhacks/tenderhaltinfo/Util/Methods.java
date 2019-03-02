package com.frictionhacks.tenderhaltinfo.Util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;

public class Methods {


    public static String getDateDialog(Context activity){

        final String[] unixDate = new String[1];
        unixDate[0] =Long.toString(Calendar.getInstance().getTimeInMillis());

        DatePickerDialog datePick=new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                long epoch=calendar.getTimeInMillis()/ 1000;
                unixDate[0] =Long.toString(epoch);
                //getDarkSky(31.6798,76.5026,unixDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePick.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePick.getDatePicker().setMinDate(System.currentTimeMillis()-345600000);
        datePick.show();

        return unixDate[0] ==null?"No_Date": unixDate[0];


    }
}