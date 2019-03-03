package com.frictionhacks.tenderhaltinfo.Fragments;


import android.content.Intent;
import android.os.Build;
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
import com.frictionhacks.tenderhaltinfo.Util.Methods;
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
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

public class DashboardFragment extends Fragment implements onItemClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;

    private ContractorTenderDetailAdapter ContractDashTenderAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

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
        final LinearLayoutManager linearLayout=new LinearLayoutManager(getActivity());
        DashContract.setHasFixedSize(true);
        DashContract.setLayoutManager(linearLayout);
        swipeRefreshLayout = v.findViewById(R.id.srl_dash);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                onResume();
            }
        });
        ContractDashTenderAdapter = new ContractorTenderDetailAdapter();
        ContractDashTenderAdapter.setClickListener(new onItemClickListener() {
            @Override
            public void onHaltRequest(View v, int position) {


             Intent intent=new Intent(getActivity(),TenderDetailActivity.class);
             intent.putExtra("position",position);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(Objects.requireNonNull(getActivity()), v, getString(R.string.animations));
                    startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }

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
        swipeRefreshLayout.setRefreshing(true);
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
                                swipeRefreshLayout.setRefreshing(false);

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
