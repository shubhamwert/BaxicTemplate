package com.frictionhacks.tenderhaltinfo.Util;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Methods {

private static String unixDate;
    public static String getDateDialog(Context activity){



        DatePickerDialog datePick=new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            //String unixDate ;

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar=Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                long epoch=calendar.getTimeInMillis()/ 1000;
                unixDate =Long.toString(epoch);

                //getDarkSky(31.6798,76.5026,unixDate);
            }
        }, Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePick.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePick.getDatePicker().setMinDate(System.currentTimeMillis()-345600000);
        datePick.show();

        return unixDate ==null?"No_Date": unixDate;


    }


    public static String  unixToFormat(long unixSeconds){
        Date date = new java.util.Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-YYYY");
        Log.d("Format Date",sdf.format(date));
        return sdf.format(date);
    }
}
