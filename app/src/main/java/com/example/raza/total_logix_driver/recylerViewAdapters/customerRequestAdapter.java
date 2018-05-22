package com.example.raza.total_logix_driver.recylerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raza.total_logix_driver.DTO.acceptRequest;
import com.example.raza.total_logix_driver.DTO.customerRequest;
import com.example.raza.total_logix_driver.DTO.driverAvailable;
import com.example.raza.total_logix_driver.DTO.driverHistory;
import com.example.raza.total_logix_driver.DTO.driverOnRide;
import com.example.raza.total_logix_driver.R;
import com.example.raza.total_logix_driver.activities.Current_Ride_Activity;
import com.example.raza.total_logix_driver.activities.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.google.firebase.firestore.DocumentChange.Type.ADDED;
import static com.google.firebase.firestore.DocumentChange.Type.MODIFIED;
import static com.google.firebase.firestore.DocumentChange.Type.REMOVED;

public class customerRequestAdapter extends RecyclerView.Adapter<customerRequestAdapter.ViewHolder> {


    //Firebase Start
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private String userID;
    //Firebase End
    private String TAG = "";
    public List<customerRequest> cRequests;
    public List<driverAvailable> cDriverInfo;
    public Context context;

    private GeoPoint driverLocation;
    private String drivername;
    private String driverdp;
    private String drivernic;
    private String driverphone;
    private String carregno;
    private Date date = Calendar.getInstance().getTime();
    private String uniqueID;
    private float starts;
    private float ridestars;
    private GeoPoint mPick;
    private float distance;
    private GeoPoint mCurrentLocation;
    private String mydate, mytime, gatepass;
    private ListenerRegistration driveravailablegetreqliststner;
    private ListenerRegistration driveronridelistner;

    public customerRequestAdapter(Context context, List<customerRequest> cRequests) {
        this.cRequests = cRequests;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.acceptedrequest_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            driverdp = user.getPhotoUrl().toString();

       }




        /*docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        CurrentLocatiofcn= Objects.requireNonNull(document.toObject(driverAvailable.class)).getDriverLocation();
                    }
                }
            }


        });
*/

        mPick = cRequests.get(position).getPickup();
        /* mCurrentLocation= ((HomeActivity) context).;*/
        drivername = ((HomeActivity) context).driverName;
        drivernic = ((HomeActivity) context).drivernic;
        driverphone = ((HomeActivity) context).driverphone;
        carregno = ((HomeActivity) context).carregno;
        ridestars = 0;
        gatepass = cRequests.get(position).getGatepass();
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a", Locale.getDefault());
        final DocumentReference docRef = db.collection("driveravailable").document(userID);
        driveravailablegetreqliststner=docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    driverAvailable driverAvailable = snapshot.toObject(com.example.raza.total_logix_driver.DTO.driverAvailable.class);
                    mCurrentLocation = driverAvailable.getDriverLocation();

                    driverLocation=mCurrentLocation;


                    Location loc1 = new Location("");
                    loc1.setLatitude(driverLocation.getLatitude());
                    loc1.setLongitude(driverLocation.getLongitude());

                    Location loc2 = new Location("");
                    loc2.setLatitude(mPick.getLatitude());
                    loc2.setLongitude(mPick.getLongitude());

                    distance = loc1.distanceTo(loc2)/1000;

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });



        mydate = DateFormat.getDateInstance().format(cRequests.get(position).getDate());
        mytime = formatter.format(cRequests.get(position).getDate());
        holder.mName.setText(cRequests.get(position).getName());
        holder.mPickup.setText(cRequests.get(position).getPickupaddress());
        holder.mDrop.setText(cRequests.get(position).getDropaddress());
        holder.mPhone.setText(cRequests.get(position).getPhone());
        holder.mRideDistance.setText(String.valueOf(cRequests.get(position).getRidedistance()));
        holder.mDate.setText(mydate);
        holder.mTime.setText(mytime);
        holder.mWeight.setText(cRequests.get(position).getWeight());
        holder.mDiscription.setText(cRequests.get(position).getDescription());
        holder.mBoxes.setText(cRequests.get(position).getBoxes());
        holder.mRatingBar.setRating(cRequests.get(position).getStars());
        final String user_id = cRequests.get(position).userId;
        holder.mDistance.setText(String.valueOf(distance) + "KM");
        uniqueID = cRequests.get(position).getUniqueID();




        holder.mSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cRequests.remove(position);
                //holder.itemView.setVisibility(View.GONE);
                notifyItemRangeChanged(position, cRequests.size());
                notifyItemRemoved(position);

            }
        });
        holder.mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Accept Button", Toast.LENGTH_SHORT).show();


                driveronridelistner = db.collection("OnRide").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                        if (documentSnapshot.exists()) {
                            // do something with the data
                            Toast.makeText(context, "Kindly Complete your Current Logix", Toast.LENGTH_SHORT).show();
                        } else {

                            DocumentReference docRef = db.collection("customerRequest").document(cRequests.get(position).userId);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document != null && document.exists()) {

                                            driverHistory driverHistory = new driverHistory(cRequests.get(position).getName(), cRequests.get(position).getPickup(), cRequests.get(position).getDrop(), null, null, cRequests.get(position).getPhone(), cRequests.get(position).getDate(), cRequests.get(position).getCID(), cRequests.get(position).getVT(), cRequests.get(position).getWeight(), cRequests.get(position).getBoxes(), cRequests.get(position).getDescription(), cRequests.get(position).getDriverloading(), cRequests.get(position).getRidedistance(), cRequests.get(position).getPickupaddress(), cRequests.get(position).getDropaddress(), cRequests.get(position).getEstFare(), drivername, driverdp, drivernic, driverphone, driverLocation, carregno, userID, "Pending", 0, null, null, 0, uniqueID, "Pending", ridestars,ridestars, cRequests.get(position).getRidedistance(), gatepass, cRequests.get(position).getArriveddate(),cRequests.get(position).getDroplocationUniqueID());
                                            acceptRequest acceptRequest = new acceptRequest(cRequests.get(position).getName(), cRequests.get(position).getPickup(), cRequests.get(position).getDrop(), null, null, cRequests.get(position).getPhone(), cRequests.get(position).getDate(), cRequests.get(position).getCID(), cRequests.get(position).getVT(), cRequests.get(position).getWeight(), cRequests.get(position).getBoxes(), cRequests.get(position).getDescription(), cRequests.get(position).getDriverloading(), cRequests.get(position).getRidedistance(), cRequests.get(position).getPickupaddress(), cRequests.get(position).getDropaddress(), cRequests.get(position).getEstFare(), drivername, driverdp, drivernic, driverphone, driverLocation, carregno, userID, "Pending", 0, null, null, date, 0, uniqueID, "Pending", ridestars, cRequests.get(position).getRidedistance(), gatepass, cRequests.get(position).getArriveddate(),cRequests.get(position).getDroplocationUniqueID());
                                            driverOnRide driverOnRide = new driverOnRide(userID);
                                            db.collection("acceptRequest").document(uniqueID).set(acceptRequest);
                                            db.collection("CustomerHistory").document(uniqueID).set(driverHistory);
                                            db.collection("DriverHistory").document(uniqueID).set(driverHistory);
                                            db.collection("OnRide").document(userID).set(driverOnRide);
                                            db.collection("customerRequest").document(uniqueID).delete()
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                            Log.d(TAG, "DocumentSnapshot successfully deleted!");

                                                        }

                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w(TAG, "Error deleting document", e);
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(context, "Ride Already Booked", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }

                                }
                            });


                        }


                    }


                });
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "User ID = " + user_id, Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return cRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public TextView mName, mPickup, mDrop, mPhone, mDistance, mRideDistance, mDate, mTime, mDiscription, mWeight, mBoxes;
        public Button mAccept, mSkip;
        public RatingBar mRatingBar;
        public RatingBar mRideRating;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mName = (TextView) mView.findViewById(R.id.txt_customername);
            mPickup = (TextView) mView.findViewById(R.id.txt_from_add);
            mDrop = (TextView) mView.findViewById(R.id.txt_to_add);
            mPhone = (TextView) mView.findViewById(R.id.txt_phone);
            mAccept = (Button) mView.findViewById(R.id.acceptbtn);
            mSkip = (Button) mView.findViewById(R.id.declinebtn);
            mDistance = (TextView) mView.findViewById(R.id.distancefrom);
            mRideDistance = (TextView) mView.findViewById(R.id.txt_est_distance);
            mDate = (TextView) mView.findViewById(R.id.date);
            mTime = (TextView) mView.findViewById(R.id.time);
            mDiscription = (TextView) mView.findViewById(R.id.description);
            mBoxes = (TextView) mView.findViewById(R.id.Boxes);
            mWeight = (TextView) mView.findViewById(R.id.weight);
            mRatingBar = (RatingBar) mView.findViewById(R.id.c_rating);

        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        driveravailablegetreqliststner.remove();

    }
}
