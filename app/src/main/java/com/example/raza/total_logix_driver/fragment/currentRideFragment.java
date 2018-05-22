package com.example.raza.total_logix_driver.fragment;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raza.total_logix_driver.DTO.acceptRequest;
import com.example.raza.total_logix_driver.R;
import com.example.raza.total_logix_driver.recylerViewAdapters.currentRideAdapter;
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
public class currentRideFragment extends android.app.Fragment {

    private FirebaseFirestore firestoreDB;
    private RecyclerView mDhistory;

    private List<com.example.raza.total_logix_driver.DTO.acceptRequest> dHistory;
    private acceptRequest acceptRequest;
    private com.example.raza.total_logix_driver.recylerViewAdapters.currentRideAdapter currentRideAdapter;
    private String driverID;
    private String TAG;
    private FirebaseAuth mAuth;
    private ListenerRegistration currentrideinfolistner;


    public currentRideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_current_ride, container, false);
        firestoreDB = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        driverID = mAuth.getCurrentUser().getUid();
        dHistory = new ArrayList<>();

        currentRideAdapter = new currentRideAdapter(getContext(),dHistory);
        mDhistory = (RecyclerView)view.findViewById(R.id.currentRideRV);
        mDhistory.setHasFixedSize(true);
        mDhistory.setLayoutManager(new LinearLayoutManager(getContext()));
        mDhistory.setAdapter(currentRideAdapter);




        currentrideinfolistner= firestoreDB.collection("acceptRequest").whereEqualTo("driverid", driverID).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error:" + e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (doc.getType()) {
                        case ADDED:
                            acceptRequest = doc.getDocument().toObject(acceptRequest.class);
                            dHistory.add(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();
                            break;
                        case MODIFIED:
                            dHistory.remove(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();
                            acceptRequest = doc.getDocument().toObject(acceptRequest.class);
                            dHistory.add(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();
                            break;
                        case REMOVED:
                            dHistory.remove(acceptRequest);
                            currentRideAdapter.notifyDataSetChanged();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();

                            ft.replace(R.id.cm, new DriverHistoryFragment());
                            ft.commit();

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
        currentrideinfolistner.remove();
    }
}
