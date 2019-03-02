package com.frictionhacks.tenderhaltinfo.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.frictionhacks.tenderhaltinfo.Activity.AddTenderActivity;
import com.frictionhacks.tenderhaltinfo.Activity.TenderDetailActivity;
import com.frictionhacks.tenderhaltinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
Button btnAddTender,btnHaltRequest;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
    btnAddTender=root.findViewById(R.id.btn_dash_date);
btnHaltRequest=root.findViewById(R.id.btn_dash_halt);

btnAddTender.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), AddTenderActivity.class));
    }
});

btnHaltRequest.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), TenderDetailActivity.class));
    }
});
        return root;
    }

}
