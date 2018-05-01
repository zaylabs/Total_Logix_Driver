package com.example.raza.total_logix_driver.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.raza.total_logix_driver.DTO.currentCash;
import com.example.raza.total_logix_driver.DTO.overallcash;
import com.example.raza.total_logix_driver.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class walletFragment extends android.app.Fragment {

    private TableLayout mSettledTable;
    private TableLayout mTotalTable;
    private TextView mCashinHand,mSettlefare,mSettleEarning,mSettleLogixShare,mSettledRide,mEarningTodate,mRidetoDate;
    private AppCompatButton mSettled,mTotal;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;
    private NumberFormat RsFormat = new DecimalFormat("'Rs.'#");

    public walletFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_wallet, container, false);

    mSettledTable=view.findViewById(R.id.unsettled_table);
    mTotalTable=view.findViewById(R.id.totalearningtable);
    mCashinHand=view.findViewById(R.id.wallet_cashinhand);
    mSettleEarning=view.findViewById(R.id.wallet_driversharepending);
    mSettlefare=view.findViewById(R.id.wallet_farepending);
    mSettleLogixShare=view.findViewById(R.id.wallet_logixsharepending);
    mSettledRide=view.findViewById(R.id.wallet_logixpending);
    mRidetoDate=view.findViewById(R.id.wallet_overalllogixride);
    mEarningTodate=view.findViewById(R.id.wallet_overallearning);
    mSettled=view.findViewById(R.id.btn_unsettled);
    mTotal=view.findViewById(R.id.btn_totalearning);


        mAuth = FirebaseAuth.getInstance();

        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        db.collection("currentCash").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                currentCash currentCash=documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.currentCash.class);
                mCashinHand.setText(RsFormat.format(currentCash.getCurrentcash()));
                mSettleEarning.setText(RsFormat.format(currentCash.getTotaldriversharepending()));
                mSettleLogixShare.setText(RsFormat.format(currentCash.getTotlalogixsharepending()));
                mSettledRide.setText(String.valueOf(currentCash.getTotaljourney()));
                mSettlefare.setText(RsFormat.format(currentCash.getTotalfarepending()));


            }
        });

        db.collection("overallcash").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                overallcash overallcash = documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.overallcash.class);
                mEarningTodate.setText(RsFormat.format(overallcash.getDrivershare()));
                mRidetoDate.setText(String.valueOf(overallcash.getTotaljourney()));
            }
        });





mSettled.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mSettledTable.setVisibility(View.GONE);
        mSettled.setVisibility(View.GONE);
        mTotalTable.setVisibility(View.VISIBLE);
        mTotal.setVisibility(View.VISIBLE);


    }
});

mTotal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        mTotalTable.setVisibility(View.GONE);
        mTotal.setVisibility(View.GONE);
        mSettledTable.setVisibility(View.VISIBLE);
        mSettled.setVisibility(View.VISIBLE);



    }
});

        return view;
    }

}
