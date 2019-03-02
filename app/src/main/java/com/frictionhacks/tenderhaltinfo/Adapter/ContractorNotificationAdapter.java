package com.frictionhacks.tenderhaltinfo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frictionhacks.tenderhaltinfo.DataModel.ContractorNotificationModel;
import com.frictionhacks.tenderhaltinfo.DataModel.ContractorTenderDetailsDashboardModel;
import com.frictionhacks.tenderhaltinfo.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContractorNotificationAdapter extends RecyclerView.Adapter<ContractorNotificationAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenderId,tvNotification;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenderId=itemView.findViewById(R.id.tv_rv_notification_tender);
            tvNotification=itemView.findViewById(R.id.tv_rv_notification_noti);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_notification_adapter_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTenderId.setText(String.format("%s", ContractorNotificationModel.mData.get(position).getmTenderId()));
        holder.tvNotification.setText(String.format("%s", ContractorNotificationModel.mData.get(position).getmNotificationStatus()));


    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
