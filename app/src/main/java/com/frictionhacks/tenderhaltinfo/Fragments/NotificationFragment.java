package com.frictionhacks.tenderhaltinfo.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frictionhacks.tenderhaltinfo.Adapter.ContractorNotificationAdapter;
import com.frictionhacks.tenderhaltinfo.DataModel.ContractorNotificationModel;
import com.frictionhacks.tenderhaltinfo.R;


public class NotificationFragment extends Fragment {

    public NotificationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView rvNotificationContractor=v.findViewById(R.id.rv_notification_contractor);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rvNotificationContractor.setHasFixedSize(true);
        rvNotificationContractor.setLayoutManager(linearLayoutManager);

        ContractorNotificationAdapter contractorNotificationAdapter=new ContractorNotificationAdapter();
        rvNotificationContractor.setAdapter(contractorNotificationAdapter);




        return v;
    }


}
