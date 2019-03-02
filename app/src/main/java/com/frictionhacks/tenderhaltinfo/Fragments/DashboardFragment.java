package com.frictionhacks.tenderhaltinfo.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.frictionhacks.tenderhaltinfo.Activity.AddTenderActivity;
import com.frictionhacks.tenderhaltinfo.Activity.TenderDetailActivity;

import android.widget.LinearLayout;
import android.widget.Toast;

import com.frictionhacks.tenderhaltinfo.Adapter.ContractorTenderDetailAdapter;
import com.frictionhacks.tenderhaltinfo.Adapter.onItemClickListener;

import com.frictionhacks.tenderhaltinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class DashboardFragment extends Fragment implements onItemClickListener {



    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_dashboard, container, false);
        RecyclerView DashContract=v.findViewById(R.id.dashboard_rv_tender_details_contractor);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getActivity());
        DashContract.setHasFixedSize(true);
        DashContract.setLayoutManager(linearLayout);
        ContractorTenderDetailAdapter ContractDashTenderAdapter=new ContractorTenderDetailAdapter();
        ContractDashTenderAdapter.setClickListener(new onItemClickListener() {
            @Override
            public void onHaltRequest(View v, int position) {
                Toast.makeText(getActivity(),"sood yaha daal",Toast.LENGTH_SHORT).show();
 startActivity(new Intent(getActivity(), TenderDetailActivity.class));
            }
        });
        DashContract.setAdapter(ContractDashTenderAdapter);


        return v;

    }

    @Override
    public void onHaltRequest(View v, int position) {

    }
}
