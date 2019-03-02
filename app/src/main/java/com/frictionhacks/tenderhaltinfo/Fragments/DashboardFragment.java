package com.frictionhacks.tenderhaltinfo.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frictionhacks.tenderhaltinfo.Activity.AddTenderActivity;
import com.frictionhacks.tenderhaltinfo.Activity.MainActivity;
import com.frictionhacks.tenderhaltinfo.Activity.TenderDetailActivity;
import com.frictionhacks.tenderhaltinfo.Adapter.ContractorTenderDetailAdapter;
import com.frictionhacks.tenderhaltinfo.Adapter.onItemClickListener;
import com.frictionhacks.tenderhaltinfo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */

public class DashboardFragment extends Fragment implements onItemClickListener {

    ContractorTenderDetailAdapter ContractDashTenderAdapter;


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FloatingActionButton fabAddTender;
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_dashboard, container, false);
        fabAddTender=v.findViewById(R.id.fab_dash_add_tender);
        RecyclerView DashContract=v.findViewById(R.id.dashboard_rv_tender_details_contractor);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getActivity());
        DashContract.setHasFixedSize(true);
        DashContract.setLayoutManager(linearLayout);
        ContractDashTenderAdapter = new ContractorTenderDetailAdapter();
        ContractDashTenderAdapter.setClickListener(new onItemClickListener() {
            @Override
            public void onHaltRequest(View v, int position) {

             Intent intent=new Intent(getActivity(),TenderDetailActivity.class);
             intent.putExtra("position",position);
             startActivity(intent);
            }
        });

        fabAddTender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddTenderActivity.class));
            }
        });
        DashContract.setAdapter(ContractDashTenderAdapter);


        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        ContractDashTenderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHaltRequest(View v, int position) {

    }
}
