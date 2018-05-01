package com.example.raza.total_logix_driver.recylerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.raza.total_logix_driver.DTO.settlementHistory;
import com.example.raza.total_logix_driver.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class settlementHistoryAdapter extends RecyclerView.Adapter<settlementHistoryAdapter.ViewHolder> {

    private final Context context;
    private final List<settlementHistory> dHistory;

    public settlementHistoryAdapter(Context context, List<settlementHistory> dHistory){

        this.context = context;
        this.dHistory = dHistory;
    }

    @NonNull
    @Override
    public settlementHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_history_singleitem,parent,false);
        return new settlementHistoryAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull settlementHistoryAdapter.ViewHolder holder, final int position) {



        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        NumberFormat numberFormat = new DecimalFormat("'Rs.'#.##");







        holder.employee.setText(dHistory.get(position).getTotallogixemployee());
        holder.totalcash.setText(numberFormat.format(dHistory.get(position).getTotalcash()));
        holder.totalfare.setText(numberFormat.format(dHistory.get(position).getTotalfare()));
        holder.totallogixshare.setText(numberFormat.format(dHistory.get(position).getTotallogixshare()));
        holder.totaldrivershare.setText(numberFormat.format(dHistory.get(position).getDrivershare()));
        holder.dateupdated.setText(formatter.format(dHistory.get(position).getSettlementdate()));


    }

    @Override
    public int getItemCount() {
        return dHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView totalcash;
        private TextView dateupdated;
        private TextView employee;
        private TextView totalfare;
        private TextView totallogixshare;
        private TextView totaldrivershare;
        View mView;


        public ViewHolder(View itemView) {
            super(itemView);
            mView= itemView;
            totalcash=mView.findViewById(R.id.ph_totalcash);
            employee=mView.findViewById(R.id.ph_tlemployee);
            totaldrivershare=mView.findViewById(R.id.ph_driverearning);
            totallogixshare=mView.findViewById(R.id.ph_totallogixsahre);
            totalfare=mView.findViewById(R.id.ph_totalfare);
            dateupdated=mView.findViewById(R.id.ph_dateupdated);
        }
    }
}



