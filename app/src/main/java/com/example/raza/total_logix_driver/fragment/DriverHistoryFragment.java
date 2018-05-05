package com.example.raza.total_logix_driver.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raza.total_logix_driver.DTO.driverHistory;
import com.example.raza.total_logix_driver.R;
import com.example.raza.total_logix_driver.recylerViewAdapters.driverHistoryAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverHistoryFragment extends android.app.Fragment {

        private FirebaseFirestore firestoreDB;
        private RecyclerView mDhistory;
        private List<driverHistory> dHistory;
        private driverHistory driverHistory;
        private driverHistoryAdapter driverHistoryAdapter;
        private String driverID;
        private String TAG;
        private FirebaseAuth mAuth;
        public ListenerRegistration driverHistoryListner;

    public DriverHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_driver_history, container, false);
        firestoreDB = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        driverID = mAuth.getCurrentUser().getUid();



        dHistory = new ArrayList<>();
        driverHistoryAdapter = new driverHistoryAdapter(getActivity(),dHistory);
        mDhistory = (RecyclerView)view.findViewById(R.id.driverHistoryRV);
        mDhistory.setHasFixedSize(true);
        mDhistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDhistory.setAdapter(driverHistoryAdapter);

        // firestoreDB.collection("DriverHistory").whereEqualTo("driverid", driverID).
        driverHistoryListner=firestoreDB.collection("DriverHistory").whereEqualTo("driverid", driverID).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error:" + e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (doc.getType()) {
                        case ADDED:
                            driverHistory = doc.getDocument().toObject(driverHistory.class);
                            dHistory.add(driverHistory);
                            driverHistoryAdapter.notifyDataSetChanged();
                            break;
                        case MODIFIED:
                            driverHistory = doc.getDocument().toObject(driverHistory.class);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        driverHistoryListner.remove();

    }
}
