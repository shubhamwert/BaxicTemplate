package com.frictionhacks.tenderhaltinfo.Fragments;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frictionhacks.tenderhaltinfo.Adapter.ContractorNotificationAdapter;
import com.frictionhacks.tenderhaltinfo.DataModel.ContractorNotificationModel;
import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.DataWord.NotificationWord;
import com.frictionhacks.tenderhaltinfo.DataWord.TenderDetailWord;
import com.frictionhacks.tenderhaltinfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import static android.content.ContentValues.TAG;


public class NotificationFragment extends Fragment {
private FirebaseFirestore db;
    private ContractorNotificationAdapter contractorNotificationAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
private FirebaseAuth mAuth;
    public NotificationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_notification, container, false);
        db=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        RecyclerView rvNotificationContractor=v.findViewById(R.id.rv_notification_contractor);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        rvNotificationContractor.setHasFixedSize(true);
        rvNotificationContractor.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout=v.findViewById(R.id.srl_notif);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setDataBase();
            }
        });

        contractorNotificationAdapter = new ContractorNotificationAdapter();
        rvNotificationContractor.setAdapter(contractorNotificationAdapter);

        setDataBase();



        return v;
    }

    private void setDataBase() {
        String number= Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber();
        swipeRefreshLayout.setRefreshing(true);
        assert number != null;
        db.collection("Notification").document(number).collection("Notifications").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            ContractorNotificationModel.mData.clear();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                NotificationWord contractorDataWord=document.toObject(NotificationWord.class);

                                ContractorNotificationModel.mData.add(contractorDataWord);
                                swipeRefreshLayout.setRefreshing(false);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents : ", task.getException());
                        }

                    }
                });
    }


}
