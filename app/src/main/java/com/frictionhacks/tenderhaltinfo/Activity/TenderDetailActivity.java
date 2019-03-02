package com.frictionhacks.tenderhaltinfo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.R;
import com.frictionhacks.tenderhaltinfo.Util.Methods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class TenderDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button btnDate,btnSubmit;
String date,weatherType;
Spinner spnWeatherType;
TextView tvIsVerify;
int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender_detail);
        pos= Objects.requireNonNull(getIntent().getExtras()).getInt("position");
        btnDate=findViewById(R.id.btn_tender_detail_date);
        btnSubmit=findViewById(R.id.btn_tender_detail_submit);
spnWeatherType=findViewById(R.id.spn_tender_detail_main);
tvIsVerify=findViewById(R.id.tv_tender_detail_is_verify);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = Methods.getDateDialog(TenderDetailActivity.this);
                Log.d("DATE",date);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDarkSky(ContractorTenderDetailsDashboardModel.mData.get(pos).getmLat(),ContractorTenderDetailsDashboardModel.mData.get(pos).getmLong(),date);
            }
        });

    spnWeatherType.setOnItemSelectedListener(this);

        ArrayList<String> categories=new ArrayList<>();
        categories.add("rain");
        categories.add("snow");
        categories.add("other");


        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,categories);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnWeatherType.setAdapter(stringArrayAdapter);
    }


    private void getDarkSky(double lat, double lng, final String unixDate) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://api.darksky.net/forecast/4ca85b12437d1c366ea7512e2a91307d/"+lat+","+lng+","+unixDate;

        Log.v("DARK SKY",url);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject("daily");
                                    JSONArray jsonArray=jsonObject.getJSONArray("data");
                                    JSONObject jsonObject1=jsonArray.getJSONObject(0);

                                        Log.d("WEATHER DATA",jsonObject1.getString("precipType"));
                                 //   tvIsVerify.setText("Date is:"+unixDate+"\n precipIntensity "+jsonObject1.getString("precipIntensity")+"\nprecipType "+jsonObject1.getString("precipType"));
                                  if(jsonObject1.getString("precipType").equals(weatherType)){
                                      tvIsVerify.setText("Yes Verified");
                                  }
                                  else{
                                      tvIsVerify.setText("False request");
                                  }

                                    //  listViewMain.setAdapter(arrayAdapter);

                                } catch (JSONException e) {

                                    e.printStackTrace();

                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "DARK sky Error", Toast.LENGTH_SHORT).show();

                    }
                });
      requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        weatherType = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + weatherType, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
