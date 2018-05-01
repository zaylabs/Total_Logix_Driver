package com.example.raza.total_logix_driver.recylerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raza.total_logix_driver.DTO.DriverTransactionHistory;
import com.example.raza.total_logix_driver.DTO.driverAvailable;
import com.example.raza.total_logix_driver.DTO.userProfile;
import com.example.raza.total_logix_driver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;


public class transactionhistoryAdapter extends RecyclerView.Adapter<transactionhistoryAdapter.ViewHolder> {

    private final Context context;
    private final List<DriverTransactionHistory> dHistory;

    public transactionhistoryAdapter(Context context, List<DriverTransactionHistory> dHistory){

        this.context = context;
        this.dHistory = dHistory;
    }

    @NonNull
    @Override
    public transactionhistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactionhistory_single_item,parent,false);
        return new transactionhistoryAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull transactionhistoryAdapter.ViewHolder holder, final int position) {



        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        NumberFormat numberFormat = new DecimalFormat("'Rs.'#.##");







        holder.source.setText(dHistory.get(position).getCustomerName());
        holder.currentbalance.setText(numberFormat.format(dHistory.get(position).getAmountadded()));
        holder.oldbalance.setText(numberFormat.format(dHistory.get(position).getOldamount()));
        holder.amount.setText(numberFormat.format(dHistory.get(position).getNewtotal()));
        holder.dateupdated.setText(formatter.format(dHistory.get(position).getTransactiondate()));


    }

    @Override
    public int getItemCount() {
        return dHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView amount;
        private TextView dateupdated;
        private TextView source;
        private TextView oldbalance;
        private TextView currentbalance;
        View mView;


        public ViewHolder(View itemView) {
            super(itemView);
            mView= itemView;
        amount=mView.findViewById(R.id.th_addedbalance);
        dateupdated=mView.findViewById(R.id.th_dateupdated);
        source=mView.findViewById(R.id.th_source);
        oldbalance=mView.findViewById(R.id.th_oldbalance);
        currentbalance=mView.findViewById(R.id.th_updatedbalance);
        }
    }
}



