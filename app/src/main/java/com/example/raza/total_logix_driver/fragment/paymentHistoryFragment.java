package com.example.raza.total_logix_driver.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.raza.total_logix_driver.DTO.DriverTransactionHistory;
import com.example.raza.total_logix_driver.DTO.settlementHistory;
import com.example.raza.total_logix_driver.R;
import com.example.raza.total_logix_driver.recylerViewAdapters.settlementHistoryAdapter;
import com.example.raza.total_logix_driver.recylerViewAdapters.transactionhistoryAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class paymentHistoryFragment extends android.app.Fragment {

    private FirebaseFirestore firestoreDB;
    private RecyclerView mDhistory;
    private List<settlementHistory> dHistory;
    private settlementHistory driverHistory;
    private settlementHistoryAdapter driverHistoryAdapter;
    private String driverID;
    private String TAG;
    private FirebaseAuth mAuth;

    public paymentHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_payment_history, container, false);


        firestoreDB = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        driverID = mAuth.getCurrentUser().getUid();



        dHistory = new ArrayList<>();
        driverHistoryAdapter = new settlementHistoryAdapter(getActivity(),dHistory);
        mDhistory = (RecyclerView)view.findViewById(R.id.phHistoryRV);
        mDhistory.setHasFixedSize(true);
        mDhistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDhistory.setAdapter(driverHistoryAdapter);

        firestoreDB.collection("settlementHistory").whereEqualTo("driverID", driverID).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error:" + e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (doc.getType()) {
                        case ADDED:
                            driverHistory = doc.getDocument().toObject(settlementHistory.class);
                            dHistory.add(driverHistory);
                            driverHistoryAdapter.notifyDataSetChanged();
                            break;
                        case MODIFIED:
                            driverHistory = doc.getDocument().toObject(settlementHistory.class);
                            dHistory.add(driverHistory);
                            driverHistoryAdapter.notifyDataSetChanged();
                            break;
                        case REMOVED:
                            dHistory.remove(driverHistory);
                            driverHistoryAdapter.notifyDataSetChanged();
                            break;
                    }

                }
            }
        });





        return view;
    }

}
