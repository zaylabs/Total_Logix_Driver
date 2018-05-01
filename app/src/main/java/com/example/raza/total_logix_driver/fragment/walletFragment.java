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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
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
    private NumberFormat rideFormat=new DecimalFormat("#");
    private String cashinhand;
    private String settleearning;
    private String settlelogixshare;
    private String settleRide;
    private String settlefare;
    private String earningtodate;
    private String ridetodate;


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

        db=FirebaseFirestore.getInstance();
        getPendingData();
        getTotalData();



mSettled.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mSettledTable.setVisibility(View.INVISIBLE);
        mSettled.setVisibility(View.INVISIBLE);
        mTotalTable.setVisibility(View.VISIBLE);
        mTotal.setVisibility(View.VISIBLE);


    }
});

mTotal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        mTotalTable.setVisibility(View.INVISIBLE);
        mTotal.setVisibility(View.INVISIBLE);
        mSettledTable.setVisibility(View.VISIBLE);
        mSettled.setVisibility(View.VISIBLE);



    }
});

        return view;
    }

    private void getPendingData() {
        DocumentReference docRef = db.collection("currentCash").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                currentCash currentCash = documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.currentCash.class);
                cashinhand = RsFormat.format(currentCash.getCurrentcash());
                settleearning = RsFormat.format(currentCash.getTotaldriversharepending());
                settlelogixshare = RsFormat.format(currentCash.getTotlalogixsharepending());
                settleRide = rideFormat.format(currentCash.getTotaljourney());
                settlefare = RsFormat.format(currentCash.getTotalfarepending());
                if(cashinhand!=null) {
                    mCashinHand.setText(cashinhand);
                }
                if(settleearning!=null) {
                    mSettleEarning.setText(settleearning);
                }
                if (settlelogixshare!=null) {
                    mSettleLogixShare.setText(settlelogixshare);
                }
                if (settleRide!=null) {
                    mSettledRide.setText(settleRide);
                }
                if (settlefare!=null){
                    mSettlefare.setText(settlefare);
                }



            }
        });

    }
    private void getTotalData() {
        DocumentReference docRef = db.collection("overallcash").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                overallcash overallcash = documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.overallcash.class);
                earningtodate=RsFormat.format(overallcash.getDrivershare());
                ridetodate=rideFormat.format(overallcash.getTotaljourney());
                if (earningtodate!=null){
                    mEarningTodate.setText(earningtodate);
                }
                if (ridetodate!=null){
                    mRidetoDate.setText(ridetodate);
                }



            }
        });

    }

            }
