package com.example.raza.total_logix_driver.recylerViewAdapters;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.raza.total_logix_driver.DTO.acceptRequest;
import com.example.raza.total_logix_driver.DTO.driverAvailable;
import com.example.raza.total_logix_driver.DTO.driverHistory;
import com.example.raza.total_logix_driver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.Objects;

public class currentRideAdapter extends RecyclerView.Adapter<currentRideAdapter.ViewHolder> {

private final Context context;
private final List<acceptRequest> dHistory;
private Location mCurrentLocation;
private GeoPoint CurrentLocation;
private GeoPoint OldLocation;
private FirebaseFirestore db;
private FirebaseAuth mAuth;
private String userID;
private String UniqueID;
private Date date =  Calendar.getInstance().getTime();
private Date waitDate1;
private Date waitDate2;
private Float waiting;
Float distance;


public currentRideAdapter(Context context, List<acceptRequest> dHistory){

        this.context = context;
        this.dHistory = dHistory;
        }

@NonNull
@Override
public currentRideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_ride_single_item,parent,false);
        return new currentRideAdapter.ViewHolder(view);


        }

@Override
public void onBindViewHolder(@NonNull final currentRideAdapter.ViewHolder holder, final int position) {

    db = FirebaseFirestore.getInstance();

    mAuth = FirebaseAuth.getInstance();

    userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

    final DocumentReference docRef = db.collection("driveravailable").document(userID);
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    CurrentLocation= Objects.requireNonNull(document.toObject(driverAvailable.class)).getDriverLocation();
                }
            }
        }


    });

    holder.mName.setText(dHistory.get(position).getName());
        holder.mPickup.setText(dHistory.get(position).getPickupaddress());
        holder.mDrop.setText(dHistory.get(position).getDropaddress());
        holder.mPhone.setText(dHistory.get(position).getPhone());
        holder.mstatus.setText(dHistory.get(position).getStatus());
        holder.mRideDistance.setText(String.valueOf(dHistory.get(position).getRidedistance()));
        holder.mDiscription.setText(dHistory.get(position).getDescription());
        holder.mBoxes.setText(dHistory.get(position).getBoxes());
        holder.mWeight.setText(dHistory.get(position).getWeight());

        holder.mPaidVia.setText(dHistory.get(position).getPaidvia());
        holder.mfare.setText(dHistory.get(position).getRidefare());
        holder.mEstRideDistance.setText(String.valueOf(dHistory.get(position).getEstDistance()));
        holder.mDatetime.setText(String.valueOf(dHistory.get(position).getDate()));

        holder.mReached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mReached.setVisibility(View.GONE);
                holder.mStart.setVisibility(View.VISIBLE);
                String Status = "Waiting";
                driverHistory driverHistory = new driverHistory(
                        dHistory.get(position).getName(),
                        dHistory.get(position).getOriginalpickup(),
                        dHistory.get(position).getOriginaldrop(),
                        dHistory.get(position).getActualpickup(),
                        dHistory.get(position).getActualpickup(),
                        dHistory.get(position).getPhone(),
                        dHistory.get(position).getDate(),
                        dHistory.get(position).getCID(),
                        dHistory.get(position).getVT(),
                        dHistory.get(position).getWeight(),
                        dHistory.get(position).getBoxes(),
                        dHistory.get(position).getDescription(),
                        dHistory.get(position).getDriverloading(),
                        dHistory.get(position).getRidedistance(),
                        dHistory.get(position).getPickupaddress(),
                        dHistory.get(position).getDropaddress(),
                        dHistory.get(position).getEstFare(),
                        dHistory.get(position).getDrivername(),
                        dHistory.get(position).getDriverdp(),
                        dHistory.get(position).getDrivernic(),
                        dHistory.get(position).getDriverphone(),
                        dHistory.get(position).getDriverlocation(),
                        dHistory.get(position).getCarregno(),
                        dHistory.get(position).getDriverid(),
                        Status,
                        null,
                        null,
                        null,
                        null,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance());
                acceptRequest acceptRequest = new acceptRequest(
                        dHistory.get(position).getName(),
                        dHistory.get(position).getOriginalpickup(),
                        dHistory.get(position).getOriginaldrop(),
                        dHistory.get(position).getActualpickup(),
                        dHistory.get(position).getActualdrop(),
                        dHistory.get(position).getPhone(),
                        dHistory.get(position).getDate(),
                        dHistory.get(position).getCID(),
                        dHistory.get(position).getVT(),
                        dHistory.get(position).getWeight(),
                        dHistory.get(position).getBoxes(),
                        dHistory.get(position).getDescription(),
                        dHistory.get(position).getDriverloading(),
                        dHistory.get(position).getRidedistance(),
                        dHistory.get(position).getPickupaddress(),
                        dHistory.get(position).getDropaddress(),
                        dHistory.get(position).getEstFare(),
                        dHistory.get(position).getDrivername(),
                        dHistory.get(position).getDriverdp(),
                        dHistory.get(position).getDrivernic(),
                        dHistory.get(position).getDriverphone(),
                        dHistory.get(position).getDriverlocation(),
                        dHistory.get(position).getCarregno(),
                        dHistory.get(position).getDriverid(),
                        Status,
                        null,
                        null,
                        null,
                        date,
                        null,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance()
                );
                db.collection("acceptRequest").document(dHistory.get(position).getUniqueID()).set(acceptRequest);
                db.collection("CustomerHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);
                db.collection("DriverHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);

            }
            });

        holder.mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mStart.setVisibility(View.GONE);
                holder.mFinish.setVisibility(View.VISIBLE);
                waitDate1 = dHistory.get(position).getStatusdate();
                waitDate2 = date;
                Float wait = (float) (waitDate1.getTime() - waitDate2.getTime());
                waiting = (wait % (1000*60*60));

                String Status = "On-Ride";

                driverHistory driverHistory = new driverHistory(
                        dHistory.get(position).getName(),
                        dHistory.get(position).getOriginalpickup(),
                        dHistory.get(position).getOriginaldrop(),
                        CurrentLocation,
                        dHistory.get(position).getActualdrop(),
                        dHistory.get(position).getPhone(),
                        dHistory.get(position).getDate(),
                        dHistory.get(position).getCID(),
                        dHistory.get(position).getVT(),
                        dHistory.get(position).getWeight(),
                        dHistory.get(position).getBoxes(),
                        dHistory.get(position).getDescription(),
                        dHistory.get(position).getDriverloading(),
                        dHistory.get(position).getRidedistance(),
                        dHistory.get(position).getPickupaddress(),
                        dHistory.get(position).getDropaddress(),
                        dHistory.get(position).getEstFare(),
                        dHistory.get(position).getDrivername(),
                        dHistory.get(position).getDriverdp(),
                        dHistory.get(position).getDrivernic(),
                        dHistory.get(position).getDriverphone(),
                        dHistory.get(position).getDriverlocation(),
                        dHistory.get(position).getCarregno(),
                        dHistory.get(position).getDriverid(),
                        Status,
                        null,
                        null,
                        null,
                        waiting,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance());
                acceptRequest acceptRequest = new acceptRequest(
                        dHistory.get(position).getName(),
                        dHistory.get(position).getOriginalpickup(),
                        dHistory.get(position).getOriginaldrop(),
                        CurrentLocation,
                        dHistory.get(position).getActualdrop(),
                        dHistory.get(position).getPhone(),
                        dHistory.get(position).getDate(),
                        dHistory.get(position).getCID(),
                        dHistory.get(position).getVT(),
                        dHistory.get(position).getWeight(),
                        dHistory.get(position).getBoxes(),
                        dHistory.get(position).getDescription(),
                        dHistory.get(position).getDriverloading(),
                        dHistory.get(position).getRidedistance(),
                        dHistory.get(position).getPickupaddress(),
                        dHistory.get(position).getDropaddress(),
                        dHistory.get(position).getEstFare(),
                        dHistory.get(position).getDrivername(),
                        dHistory.get(position).getDriverdp(),
                        dHistory.get(position).getDrivernic(),
                        dHistory.get(position).getDriverphone(),
                        dHistory.get(position).getDriverlocation(),
                        dHistory.get(position).getCarregno(),
                        dHistory.get(position).getDriverid(),
                        Status,
                        null,
                        null,
                        null,
                        dHistory.get(position).getStatusdate(),
                        waiting,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance()
                );

                db.collection("acceptRequest").document(dHistory.get(position).getUniqueID()).set(acceptRequest);
                db.collection("CustomerHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);
                db.collection("DriverHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);
            }

        });

        holder.mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mFinish.setVisibility(View.GONE);
                holder.mPayment.setVisibility(View.VISIBLE);

                String Status = "Waiting for Payment";
                Location loc1 = new Location("");
                loc1.setLatitude(dHistory.get(position).getActualpickup().getLatitude());
                loc1.setLongitude(dHistory.get(position).getActualpickup().getLongitude());

                Location loc2 = new Location("");
                loc2.setLatitude(CurrentLocation.getLatitude());
                loc2.setLongitude(CurrentLocation.getLongitude());

                distance = loc1.distanceTo(loc2)/1000;

                Float result = null;
                Float b;
                Float a = distance;
                if (dHistory.get(position).getVT().contains("Riksha")) {
                    b = (a * 200) + 600;
                    if (dHistory.get(position).getDriverloading().contains("Diver Loading Needed")) {
                        result = b + 150;
                    } else {
                        result = b;
                    }}
                else if (dHistory.get(position).getVT().contains("Suzuki")) {
                    b = (a * 90) + 270;
                    if (dHistory.get(position).getDriverloading().contains("Diver Loading Needed")) {
                        result = b + 150;
                    } else {
                        result = b;
                    }
                }
                final String results = (String.valueOf(result));
                                driverHistory driverHistory = new driverHistory(
                                        dHistory.get(position).getName(),
                                        dHistory.get(position).getOriginalpickup(),
                                        dHistory.get(position).getOriginaldrop(),
                                        dHistory.get(position).getActualpickup(),
                                        CurrentLocation,
                                        dHistory.get(position).getPhone(),
                                        dHistory.get(position).getDate(),
                                        dHistory.get(position).getCID(),
                                        dHistory.get(position).getVT(),
                                        dHistory.get(position).getWeight(),
                                        dHistory.get(position).getBoxes(),
                                        dHistory.get(position).getDescription(),
                                        dHistory.get(position).getDriverloading(),
                                        distance,
                                        dHistory.get(position).getPickupaddress(),
                                        dHistory.get(position).getDropaddress(),
                                        dHistory.get(position).getEstFare(),
                                        dHistory.get(position).getDrivername(),
                                        dHistory.get(position).getDriverdp(),
                                        dHistory.get(position).getDrivernic(),
                                        dHistory.get(position).getDriverphone(),
                                        dHistory.get(position).getDriverlocation(),
                                        dHistory.get(position).getCarregno(),
                                        dHistory.get(position).getDriverid(),
                                        Status,
                                        results,
                                        null,
                                        Status,
                                        dHistory.get(position).getWaitingtime(),
                                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance());
                                acceptRequest acceptRequest = new acceptRequest(
                                        dHistory.get(position).getName(),
                                        dHistory.get(position).getOriginalpickup(),
                                        dHistory.get(position).getOriginaldrop(),
                                        dHistory.get(position).getActualpickup(),
                                        CurrentLocation,
                                        dHistory.get(position).getPhone(),
                                        dHistory.get(position).getDate(),
                                        dHistory.get(position).getCID(),
                                        dHistory.get(position).getVT(),
                                        dHistory.get(position).getWeight(),
                                        dHistory.get(position).getBoxes(),
                                        dHistory.get(position).getDescription(),
                                        dHistory.get(position).getDriverloading(),
                                        distance,
                                        dHistory.get(position).getPickupaddress(),
                                        dHistory.get(position).getDropaddress(),
                                        dHistory.get(position).getEstFare(),
                                        dHistory.get(position).getDrivername(),
                                        dHistory.get(position).getDriverdp(),
                                        dHistory.get(position).getDrivernic(),
                                        dHistory.get(position).getDriverphone(),
                                        dHistory.get(position).getDriverlocation(),
                                        dHistory.get(position).getCarregno(),
                                        dHistory.get(position).getDriverid(),
                                        Status,
                                        results,
                                        null,
                                        Status,
                                        dHistory.get(position).getStatusdate(),
                                        dHistory.get(position).getWaitingtime(),
                                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance()
                                );
                                db.collection("acceptRequest").document(dHistory.get(position).getUniqueID()).set(acceptRequest);
                                db.collection("CustomerHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);
                                db.collection("DriverHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);

                            }


        });

        holder.mPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
}

@Override
public int getItemCount() {
        return dHistory.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder{

    View mView;
    TextView mName,mPickup,mDrop,mPhone, mstatus, mDiscription, mBoxes, mRideDistance,mWeight, mPaymentStatus,mPaidVia, mfare, mDatetime,mEstRideDistance;
    public Button mReached,mStart, mPayment, mFinish;

    ViewHolder(View itemView) {
        super(itemView);
        mView= itemView;
        mName= mView.findViewById(R.id.cr_customername);
        mPickup= mView.findViewById(R.id.cr_from_add);
        mDrop= mView.findViewById(R.id.cr_to_add);
        mPhone= mView.findViewById(R.id.cr_phone);
        mstatus= mView.findViewById(R.id.cr__status);
        mRideDistance= mView.findViewById(R.id.cr__totaldistance);
        mDiscription= mView.findViewById(R.id.cr_description);
        mDatetime= mView.findViewById(R.id.cr_datetime);
        mPaidVia= mView.findViewById(R.id.cr_paidvia);
        mWeight= mView.findViewById(R.id.cr_weight);
        mBoxes= mView.findViewById(R.id.cr_boxes);
        mfare= mView.findViewById(R.id.cr_fare);
        mReached= mView.findViewById(R.id.arrivedbtn);
        mStart= mView.findViewById(R.id.startbtn);
        mPayment= mView.findViewById(R.id.getPaymentbtn);
        mFinish = mView.findViewById(R.id.completebtn);
        mEstRideDistance=mView.findViewById(R.id.cr_estdistance);
    }
}

 }


