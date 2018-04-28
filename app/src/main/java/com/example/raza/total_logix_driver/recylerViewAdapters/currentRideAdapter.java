package com.example.raza.total_logix_driver.recylerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raza.total_logix_driver.DTO.acceptRequest;
import com.example.raza.total_logix_driver.DTO.driverAvailable;
import com.example.raza.total_logix_driver.DTO.driverHistory;
import com.example.raza.total_logix_driver.R;
import com.example.raza.total_logix_driver.activities.Current_Ride_Activity;
import com.example.raza.total_logix_driver.activities.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

public class currentRideAdapter extends RecyclerView.Adapter<currentRideAdapter.ViewHolder> {

private final Context context;
private final List<acceptRequest> dHistory;


private GeoPoint DriverLocation;
private GeoPoint CurrentLocation;
private GeoPoint OldLocation;
private FirebaseFirestore db;
private FirebaseAuth mAuth;
private String userID;
private String UniqueID;

private Date waitDate1;
private Date waitDate2;
private Float waiting;
private String StringWaiting;
private String gatepass;
private Float distance;
private String StringRideDistance;
private String StringEstDistance;
private int SuzukiRate;
private int RikshaRate;
private int SuzukiBase;
private int RikshaBase;
private int DriverLoadingRate;
private String StringDateTime;

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


    Location mLocationNow=((HomeActivity)context).mCurrentLocation;

    DriverLocation= new GeoPoint (mLocationNow.getLatitude(),mLocationNow.getLongitude());

    CurrentLocation= new GeoPoint(mLocationNow.getLatitude(),mLocationNow.getLongitude());

    SuzukiRate= 200;
    RikshaRate=90;
    SuzukiBase=600;
    RikshaBase=270;
    DriverLoadingRate=150;




        NumberFormat numberFormat = new DecimalFormat("#'Mins'");
        StringWaiting=numberFormat.format(dHistory.get(position).getWaitingtime());
        NumberFormat distanceFormat = new DecimalFormat("#.##'KM'");
        StringEstDistance=distanceFormat.format(dHistory.get(position).getEstDistance());
        StringRideDistance=distanceFormat.format(dHistory.get(position).getRidedistance());
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy h:mm a");
        StringDateTime = dateFormat.format(dHistory.get(position).getDate());



        holder.mName.setText(dHistory.get(position).getName());
        holder.mPickup.setText(dHistory.get(position).getPickupaddress());
        holder.mDrop.setText(dHistory.get(position).getDropaddress());
        holder.mPhone.setText(dHistory.get(position).getPhone());
        holder.mstatus.setText(dHistory.get(position).getStatus());
        holder.mRideDistance.setText(distanceFormat.format(dHistory.get(position).getRidedistance()));
        holder.mDiscription.setText(dHistory.get(position).getDescription());
        holder.mBoxes.setText(dHistory.get(position).getBoxes());
        holder.mWeight.setText(dHistory.get(position).getWeight());
        holder.mWaiting.setText(numberFormat.format(dHistory.get(position).getWaitingtime()));
        holder.mPaidVia.setText(dHistory.get(position).getPaidvia());
        holder.mfare.setText(dHistory.get(position).getRidefare());
        holder.mEstRideDistance.setText(distanceFormat.format(dHistory.get(position).getEstDistance()));
        holder.mDatetime.setText(dateFormat.format(dHistory.get(position).getDate()));


        holder.mReached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                holder.mReached.setVisibility(View.GONE);
                holder.mStart.setVisibility(View.VISIBLE);
                String Status = "Waiting";
                Location mLocationNow=((HomeActivity)context).mCurrentLocation;

                DriverLocation= new GeoPoint (mLocationNow.getLatitude(),mLocationNow.getLongitude());

                CurrentLocation= new GeoPoint(mLocationNow.getLatitude(),mLocationNow.getLongitude());
                Date date =  Calendar.getInstance().getTime();
                waitDate1=date;
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
                        DriverLocation,
                        dHistory.get(position).getCarregno(),
                        dHistory.get(position).getDriverid(),
                        Status,
                        null,
                        null,
                        null,
                        0,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass());
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
                        DriverLocation,
                        dHistory.get(position).getCarregno(),
                        dHistory.get(position).getDriverid(),
                        Status,
                        null,
                        null,
                        null,
                        date,
                        0,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass()
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

                Location mLocationNow=((HomeActivity)context).mCurrentLocation;

                DriverLocation= new GeoPoint (mLocationNow.getLatitude(),mLocationNow.getLongitude());

                CurrentLocation= new GeoPoint(mLocationNow.getLatitude(),mLocationNow.getLongitude());


                Long min=diffTime();
                waiting = Float.valueOf(diffTime());

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
                        DriverLocation,
                        dHistory.get(position).getCarregno(),
                        dHistory.get(position).getDriverid(),
                        Status,
                        null,
                        null,
                        null,
                        waiting,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass());
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
                        DriverLocation,
                        dHistory.get(position).getCarregno(),
                        dHistory.get(position).getDriverid(),
                        Status,
                        null,
                        null,
                        null,
                        dHistory.get(position).getStatusdate(),
                        waiting,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass()
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

                Location mLocationNow=((HomeActivity)context).mCurrentLocation;

                DriverLocation= new GeoPoint (mLocationNow.getLatitude(),mLocationNow.getLongitude());

                CurrentLocation= new GeoPoint(mLocationNow.getLatitude(),mLocationNow.getLongitude());

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
                    b = (a * RikshaRate) + RikshaBase;
                    if (dHistory.get(position).getDriverloading().contains("Diver Loading Needed")) {
                        result = b + DriverLoadingRate;
                    } else {
                        result = b;
                    }}
                else if (dHistory.get(position).getVT().contains("Suzuki")) {
                    b = (a * SuzukiRate) + SuzukiBase;
                    if (dHistory.get(position).getDriverloading().contains("Diver Loading Needed")) {
                        result = b + DriverLoadingRate;
                    } else {
                        result = b;
                    }
                }
                NumberFormat numberFormat = new DecimalFormat("'Rs.'#");

                final String results = (numberFormat.format(result));

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
                                        DriverLocation,
                                        dHistory.get(position).getCarregno(),
                                        dHistory.get(position).getDriverid(),
                                        Status,
                                        results,
                                        null,
                                        Status,
                                        dHistory.get(position).getWaitingtime(),
                                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass());
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
                                        DriverLocation,
                                        dHistory.get(position).getCarregno(),
                                        dHistory.get(position).getDriverid(),
                                        Status,
                                        results,
                                        null,
                                        Status,
                                        dHistory.get(position).getStatusdate(),
                                        dHistory.get(position).getWaitingtime(),
                                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass()
                                );
                                db.collection("acceptRequest").document(dHistory.get(position).getUniqueID()).set(acceptRequest);
                                db.collection("CustomerHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);
                                db.collection("DriverHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);

                            }


        });

        holder.mPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("acceptRequest").document(dHistory.get(position).getUniqueID()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                Intent intent = new Intent(context, HomeActivity.class);
                                context.startActivity(intent);
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

            }
        });
}

@Override
public int getItemCount() {
        return dHistory.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder{

    View mView;
    TextView mName,mPickup,mDrop,mPhone, mstatus, mDiscription, mBoxes, mRideDistance,mWeight, mPaymentStatus,mPaidVia, mfare, mDatetime,mEstRideDistance,mWaiting;
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
        mWaiting=mView.findViewById(R.id.cr_waiting);
    }
}

    public long diffTime() {
        long min = 0;
        long difference ;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa"); // for 12-hour system, hh should be used instead of HH
            // There is no minute different between the two, only 8 hours difference. We are not considering Date, So minute will always remain 0
            /*Date date1 = simpleDateFormat.parse("08:00 AM");
            Date date2 = simpleDateFormat.parse("04:00 PM");
*/
            Date date =  Calendar.getInstance().getTime();
            waitDate2=date;
            difference = (waitDate2.getTime() - date.getTime()) / 1000;
            long hours = difference % (24 * 3600) / 3600; // Calculating Hours
            long minute = difference % 3600 / 60; // Calculating minutes if there is any minutes difference
            min = minute + (hours * 60); // This will be our final minutes. Multiplying by 60 as 1 hour contains 60 mins
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return min;
    }
}


