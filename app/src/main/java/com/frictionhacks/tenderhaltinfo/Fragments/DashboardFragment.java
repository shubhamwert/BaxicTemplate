package com.frictionhacks.tenderhaltinfo.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frictionhacks.tenderhaltinfo.Activity.AddTenderActivity;
import com.frictionhacks.tenderhaltinfo.Activity.MainActivity;
import com.frictionhacks.tenderhaltinfo.Activity.TenderDetailActivity;
import com.frictionhacks.tenderhaltinfo.Adapter.ContractorTenderDetailAdapter;
import com.frictionhacks.tenderhaltinfo.Adapter.onItemClickListener;
import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.DataWord.ContractorDataWord;
import com.frictionhacks.tenderhaltinfo.DataWord.TenderDetailWord;
import com.frictionhacks.tenderhaltinfo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

public class DashboardFragment extends Fragment implements onItemClickListener {

    ContractorTenderDetailAdapter ContractDashTenderAdapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

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
        db=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        RecyclerView DashContract=v.findViewById(R.id.dashboard_rv_tender_details_contractor);
        LinearLayoutManager linearLayout=new LinearLayoutManager(getActivity());
        DashContract.setHasFixedSize(true);
        DashContract.setLayoutManager(linearLayout);
        ContractDashTenderAdapter = new ContractorTenderDetailAdapter();
        ContractDashTenderAdapter.setClickListener(new onItemClickListener() {
            @Override
            public void onHaltRequest(View v, int position) {
                Toast.makeText(getActivity(),"sood yaha daal",Toast.LENGTH_SHORT).show();
 startActivity(new Intent(getActivity(), TenderDetailActivity.class));
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
        String number= Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber();

        assert number != null;
        db.collection("Contractor").document(number).collection("Tenders").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            ContractorTenderDetailsDashboardModel.mData.clear();
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                               TenderDetailWord contractorDataWord=document.toObject(TenderDetailWord.class);

                                ContractorTenderDetailsDashboardModel.mData.add(contractorDataWord);

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        ContractDashTenderAdapter.notifyDataSetChanged();
                    }
                });





    }

    @Override
    public void onHaltRequest(View v, int position) {

    }
    public void ShowMessage(String message){
        Toast.makeText(getActivity(),""+message,Toast.LENGTH_SHORT).show();

    }
}
