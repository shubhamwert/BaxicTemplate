package com.frictionhacks.tenderhaltinfo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.Objects;

public class TenderDetailActivity extends AppCompatActivity {
Button btnDate,btnSubmit;
String date;
int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender_detail);
        pos= Objects.requireNonNull(getIntent().getExtras()).getInt("position");
        btnDate=findViewById(R.id.btn_tender_detail_date);
        btnSubmit=findViewById(R.id.btn_tender_detail_submit);

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
                                  //  rainDetails.add("Date is:"+unixDate+"\n precipIntensity "+jsonObject1.getString("precipIntensity")+"\nprecipType "+jsonObject1.getString("precipType"));
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
      //  requestQueue.add(jsonObjectRequest);
    }

}
