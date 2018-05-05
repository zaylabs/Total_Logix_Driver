package com.example.raza.total_logix_driver.recylerViewAdapters;

import android.app.Dialog;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raza.total_logix_driver.DTO.DriverTransactionHistory;
import com.example.raza.total_logix_driver.DTO.Userid;
import com.example.raza.total_logix_driver.DTO.acceptRequest;
import com.example.raza.total_logix_driver.DTO.currentCash;
import com.example.raza.total_logix_driver.DTO.driverAvailable;
import com.example.raza.total_logix_driver.DTO.driverHistory;
import com.example.raza.total_logix_driver.DTO.driverRating;
import com.example.raza.total_logix_driver.DTO.overallcash;
import com.example.raza.total_logix_driver.DTO.ratingUpdate;
import com.example.raza.total_logix_driver.DTO.settings;
import com.example.raza.total_logix_driver.DTO.totalearning;
import com.example.raza.total_logix_driver.DTO.transactionhistory;
import com.example.raza.total_logix_driver.DTO.userProfile;
import com.example.raza.total_logix_driver.DTO.wallet;
import com.example.raza.total_logix_driver.R;
import com.example.raza.total_logix_driver.activities.Current_Ride_Activity;
import com.example.raza.total_logix_driver.activities.HomeActivity;
import com.example.raza.total_logix_driver.suportclasses.dialogbox;
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
import com.google.firebase.firestore.ListenerRegistration;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
private int waitingrate;
private String StringDateTime;
private NumberFormat RsFormat = new DecimalFormat("'Rs.'#");
private NumberFormat oneFormat = new DecimalFormat("#");
private Dialog myDialog;

//payButton
private  float customerdriverriderating;
private float enteredamount;
private float cashinhandcalculate;
private float cashinhand;

private ListenerRegistration walletgetlistner;

    private float drivershare;
    private float logixshare;

    Date nowdate = Calendar.getInstance().getTime();

    //Currrnt Cash

    private float currentcasholdcash;
    private float currentcasholdfare;
    private float currentcasholdride;
    private float currentcasholddrivershare;
    private float currentcasholdlogixshare;

    private float currentcashnowcash;
    private float currentcashnowfare;
    private float currentcashnowride;
    private float currentcashnowdrivershare;
    private float currentcashnowlogix;

    //totalearning
    private float totalearningoldlogixshare;
    private float totalearningoldride;
    private float totalearningoldfare;
    private float totalearningnewlogixshare;
    private float totalearningnewdride;
    private float totalearningnewfare;
    private float totalearningmoneyinwallets;
    private float totalearningmoneyinwalletsnew;
    private float totalearningmoneyinwalletsold;

    //overallcash

    private float overalloldcash;
    private float overalloldfare;
    private float overalloldlogixshare;
    private float overallolddrivershare;
    private float overalloldride;

    private float overallnewcash;
    private float overallnewfare;
    private float overallnewlogixshare;
    private float overallnewdrivershare;
    private float overallnewride;


    //customer stars and rides
    private float customeroldrating;
    private float customernewrating;
    private float customeroldnew;
    private float customeroldride;
    private float customernewride;

    //driverstars and rides
    private float driveroldrating;
    private float driveroldride;
    private float drivernewride;

    //Wallet-TransactionHitory-DriverTransactionHistory
    private float walletnew;
    private float walletafterpaid;


    private float waitfinecalculation;
    private float finedtime;
    private float totalfarepaymentfloat;
    private float walletpaymentfloat;
    private float remainingbalancefloat;


    private float totallogixsharepercent;


    private ListenerRegistration driveravailablegetlistner;
    private ListenerRegistration driveravailablesecondgetlistner;
    private ListenerRegistration drivergetListner;
    private ListenerRegistration customergetlistener;
    private ListenerRegistration overallcashgetlistner;
    private ListenerRegistration totalearninggetlistner;
    private ListenerRegistration currentcashgetlistner;
    private ListenerRegistration driveravailablethirdgetlistner;
    private ListenerRegistration driveravailablefourthgetlistner;
    private  ListenerRegistration settingsgetlistner;


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


    driveravailablesecondgetlistner=db.collection("driveravailable").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    driverAvailable driverAvailable = documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.driverAvailable.class);
                    DriverLocation=driverAvailable.getDriverLocation();
        }
    });

    /*Location mLocationNow=((HomeActivity)context).mCurrentLocation;

    DriverLocation= new GeoPoint (mLocationNow.getLatitude(),mLocationNow.getLongitude());
*/
    CurrentLocation= DriverLocation;

   settingsgetlistner= db.collection("settings").document("rates").addSnapshotListener(new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
            settings settings = documentSnapshot.toObject(settings.class);
            SuzukiRate = settings.getSuzukirate();
            RikshaRate = settings.getRiksharate();
            SuzukiBase = settings.getSuzukibase();
            RikshaBase = settings.getRikshabase();
            DriverLoadingRate = settings.getDriverloadingRate();
            totallogixsharepercent = settings.getTotallogixsharepercent();
            waitingrate=settings.getWaitingrate();
        }
    });


        NumberFormat numberFormat = new DecimalFormat("#'Mins'");
        StringWaiting=numberFormat.format(dHistory.get(position).getWaitingtime());
        NumberFormat distanceFormat = new DecimalFormat("#.##'KM'");
        StringEstDistance=distanceFormat.format(dHistory.get(position).getEstDistance());
        StringRideDistance=distanceFormat.format(dHistory.get(position).getRidedistance());
        final DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy h:mm a");
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
        holder.mfare.setText(RsFormat.format(dHistory.get(position).getRidefare()));
        holder.mEstRideDistance.setText(distanceFormat.format(dHistory.get(position).getEstDistance()));
        holder.mDatetime.setText(dateFormat.format(dHistory.get(position).getDate()));




        holder.mReached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driveravailablefourthgetlistner= db.collection("driveravailable").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        driverAvailable driverAvailable = documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.driverAvailable.class);
                        DriverLocation=driverAvailable.getDriverLocation();
                    }
                });

                CurrentLocation= DriverLocation;

                Date arriveddate= nowdate;
                holder.mReached.setVisibility(View.GONE);
                holder.mStart.setVisibility(View.VISIBLE);
                String Status = "Waiting";
                /*Location mLocationNow=((HomeActivity)context).mCurrentLocation;

                DriverLocation= new GeoPoint (mLocationNow.getLatitude(),mLocationNow.getLongitude());

                CurrentLocation= new GeoPoint(mLocationNow.getLatitude(),mLocationNow.getLongitude());
*/                Date date =  Calendar.getInstance().getTime();
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
                        0,
                        null,
                        null,
                        0,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass(),arriveddate);
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
                        0,
                        null,
                        null,
                        date,
                        0,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass(),arriveddate
                );
                db.collection("acceptRequest").document(dHistory.get(position).getUniqueID()).set(acceptRequest);
                db.collection("CustomerHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);
                db.collection("DriverHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);

            }
            });

        holder.mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               driveravailablethirdgetlistner= db.collection("driveravailable").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        driverAvailable driverAvailable = documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.driverAvailable.class);
                        DriverLocation=driverAvailable.getDriverLocation();
                    }
                });

                CurrentLocation= DriverLocation;


                holder.mStart.setVisibility(View.GONE);
                holder.mFinish.setVisibility(View.VISIBLE);
/*

                Location mLocationNow=((HomeActivity)context).mCurrentLocation;

                DriverLocation= new GeoPoint (mLocationNow.getLatitude(),mLocationNow.getLongitude());

                CurrentLocation= new GeoPoint(mLocationNow.getLatitude(),mLocationNow.getLongitude());
*/

                long diff = nowdate.getTime() - dHistory.get(position).getArriveddate().getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;


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
                        0,
                        null,
                        null,
                        minutes,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass(),dHistory.get(position).getArriveddate());
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
                        0,
                        null,
                        null,
                        dHistory.get(position).getStatusdate(),
                        minutes,
                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass(),dHistory.get(position).getArriveddate()
                );

                db.collection("acceptRequest").document(dHistory.get(position).getUniqueID()).set(acceptRequest);
                db.collection("CustomerHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);
                db.collection("DriverHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);
            }

        });

        holder.mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                driveravailablegetlistner=db.collection("driveravailable").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        driverAvailable driverAvailable = documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.driverAvailable.class);
                        DriverLocation=driverAvailable.getDriverLocation();
                    }
                });

                CurrentLocation= DriverLocation;

                holder.mFinish.setVisibility(View.GONE);
                holder.mPayment.setVisibility(View.VISIBLE);

/*
                Location mLocationNow=((HomeActivity)context).mCurrentLocation;

                DriverLocation= new GeoPoint (mLocationNow.getLatitude(),mLocationNow.getLongitude());

                CurrentLocation= new GeoPoint(mLocationNow.getLatitude(),mLocationNow.getLongitude());
*/

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
                double c = 3.0;
                if (dHistory.get(position).getVT().contains("Suzuki")) {
                    if (a<c){
                        b= Float.valueOf(SuzukiBase);
                        if (dHistory.get(position).getDriverloading().contains("Diver Loading Needed")) {
                            result = b + DriverLoadingRate;
                        } else {
                            result = b;
                        }}else {
                        b = (a * SuzukiRate);
                        if (dHistory.get(position).getDriverloading().contains("Diver Loading Needed")) {
                            result = b + DriverLoadingRate;
                        } else {
                            result = b;
                        }
                    }}
                else if (dHistory.get(position).getVT().contains("Riksha")) {
                    if (a<c){
                        b= Float.valueOf(RikshaBase);
                        if (dHistory.get(position).getDriverloading().contains("Diver Loading Needed")) {
                            result = b + DriverLoadingRate;
                        } else {
                            result = b;
                        }}else {
                        b = (a * RikshaRate);
                        if (dHistory.get(position).getDriverloading().contains("Diver Loading Needed")) {
                            result = b + DriverLoadingRate;
                        } else {
                            result = b;
                        }
                    }}

                final float results = result;

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
                                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass(),dHistory.get(position).getArriveddate());
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
                                        dHistory.get(position).getUniqueID(),dHistory.get(position).getSettlement(),dHistory.get(position).getRidestars(),dHistory.get(position).getEstDistance(),dHistory.get(position).getGatepass(),dHistory.get(position).getArriveddate()
                                );
                                db.collection("acceptRequest").document(dHistory.get(position).getUniqueID()).set(acceptRequest);
                                db.collection("CustomerHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);
                                db.collection("DriverHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);

                            }


        });

        holder.mPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dHistory.get(position).getWaitingtime()>20){
                    finedtime=dHistory.get(position).getWaitingtime()-20;
                    waitfinecalculation=finedtime*waitingrate;
                    totalfarepaymentfloat=dHistory.get(position).getRidefare()+waitfinecalculation;
                }else {
                    totalfarepaymentfloat = dHistory.get(position).getRidefare();
                }
                logixshare=totalfarepaymentfloat*(totallogixsharepercent/100);
                drivershare=totalfarepaymentfloat-logixshare;
                myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.additional_detail_dialog);
                myDialog.show();
                myDialog.setCancelable(false);
                myDialog.setCanceledOnTouchOutside(false);

                TextView mTotalFare = (TextView) myDialog.findViewById(R.id.totalfarepayment);
                final TextView mWalletBalance = (TextView) myDialog.findViewById(R.id.walletbalancepayment);
                final TextView mReaminingBalance = (TextView) myDialog.findViewById(R.id.remainingpayment);
                final EditText mAddAmount = (EditText) myDialog.findViewById(R.id.addtowalletEditPayment);
                final TextView mWaitFine=(TextView) myDialog.findViewById(R.id.waitfine);
                if (dHistory.get(position).getWaitingtime()>20) {
                    mWaitFine.setText("Minutes" + oneFormat.format(finedtime) + "Fine" + oneFormat.format(waitfinecalculation));
                }

                Button mPay = (Button) myDialog.findViewById(R.id.btn_pay);
                final RatingBar mRatingbar=(RatingBar)myDialog.findViewById(R.id.payCustomerRating);
                mTotalFare.setText(RsFormat.format(totalfarepaymentfloat));
                walletgetlistner=db.collection("wallet").document(dHistory.get(position).getCID()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        wallet wallet = documentSnapshot.toObject(wallet.class);
                        walletpaymentfloat = wallet.getAmount();
                        mWalletBalance.setText(RsFormat.format(walletpaymentfloat));
                        remainingbalancefloat = totalfarepaymentfloat - walletpaymentfloat;
                        mReaminingBalance.setText(RsFormat.format(remainingbalancefloat));
                    }
                });





                mPay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String Status = "Completed";
                        String PaymentStatus = "Paid";
                        String PaidVia = "Driver";
                        UniqueID = userID + nowdate.toString();
                        enteredamount = Float.valueOf(mAddAmount.getText().toString());
                        customerdriverriderating = mRatingbar.getRating();
                        cashinhand = enteredamount;



                        driverHistory driverHistory = new driverHistory(
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
                                totalfarepaymentfloat,
                                PaidVia,
                                PaymentStatus,
                                dHistory.get(position).getWaitingtime(),
                                dHistory.get(position).getUniqueID(), dHistory.get(position).getSettlement(), customerdriverriderating, dHistory.get(position).getEstDistance(), dHistory.get(position).getGatepass(),dHistory.get(position).getArriveddate());
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
                                totalfarepaymentfloat,
                                PaidVia,
                                PaymentStatus,
                                dHistory.get(position).getStatusdate(),
                                dHistory.get(position).getWaitingtime(),
                                dHistory.get(position).getUniqueID(), dHistory.get(position).getSettlement(), customerdriverriderating, dHistory.get(position).getEstDistance(), dHistory.get(position).getGatepass(),dHistory.get(position).getArriveddate()
                        );


                        currentcashgetlistner=db.collection("currentCash").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                currentCash currentCash = documentSnapshot.toObject(currentCash.class);
                                currentcasholdcash = currentCash.getCurrentcash();
                                currentcasholdfare = currentCash.getTotalfarepending();
                                currentcasholdride = currentCash.getTotaljourney();
                                currentcasholddrivershare = currentCash.getTotaldriversharepending();
                                currentcasholdlogixshare = currentCash.getTotlalogixsharepending();

                            }
                        });

                        currentcashnowcash = currentcasholdcash + cashinhand;
                        currentcashnowfare = currentcasholdfare + totalfarepaymentfloat;
                        currentcashnowdrivershare = currentcasholddrivershare + drivershare;
                        currentcashnowlogix = currentcasholdlogixshare + logixshare;
                        currentcashnowride = currentcasholdride + 1;
                        currentCash currentCash = new currentCash(currentcashnowcash, currentcashnowfare, currentcashnowdrivershare, currentcashnowlogix, currentcashnowride, nowdate);
                        db.collection("currentCash").document(userID).set(currentCash);

//overallcash

                       overallcashgetlistner=db.collection("overallcash").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                overallcash overallcash = documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.overallcash.class);
                                overalloldcash = overallcash.getCash();
                                overalloldfare = overallcash.getFare();
                                overalloldlogixshare = overallcash.getLogixshare();
                                overallolddrivershare = overallcash.getDrivershare();
                                overalloldride = overallcash.getTotaljourney();


                            }
                        });

                        overallnewcash = overalloldcash + cashinhand;
                        overallnewfare = overalloldfare + totalfarepaymentfloat;
                        overallnewlogixshare = overalloldlogixshare + logixshare;
                        overallnewdrivershare = overallolddrivershare + drivershare;
                        overallnewride = overalloldride + 1;
                        overallcash overallcash = new overallcash(overallnewcash, overallnewfare, overallnewdrivershare, overallnewlogixshare, overallnewride, nowdate);
                        db.collection("overallcash").document(userID).set(overallcash);


                        //CustomerStars and Ride

                        customergetlistener=db.collection("customers").document(dHistory.get(position).getCID()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                userProfile userProfile = documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.userProfile.class);
                                customeroldrating = userProfile.getStars();
                                customeroldride = userProfile.getTotalrides();

                            }
                        });
                        customeroldnew = customeroldrating + customerdriverriderating;
                        customernewride = customeroldride + 1;
                        if (customernewride > 0) {
                            customernewrating = customeroldnew / customernewride;
                        } else {
                            customernewrating = customeroldnew;
                        }
                        Map<String, Object> customerUpdates = new HashMap<>();
                        customerUpdates.put("totalrides", customernewride);
                        customerUpdates.put("stars", customernewrating);
                        db.collection("customers").document(dHistory.get(position).getCID()).update(customerUpdates);


                        //Driver Stars and Ride

                       drivergetListner= db.collection("drivers").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                userProfile userProfile=documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.userProfile.class);
                                driveroldrating=userProfile.getStars();
                                driveroldride=userProfile.getTotalrides();

                            }
                        });
                        drivernewride=driveroldride+1;
                        driverRating driverRating = new driverRating(driveroldrating,0, 0,drivernewride,dHistory.get(position).getDriverid(),dHistory.get(position).getCID(), dHistory.get(position).getUniqueID());
                        db.collection("driverRating").document(dHistory.get(position).getCID()).set(driverRating);

                        String Source = "Driver";
                        walletnew = walletpaymentfloat + enteredamount;

                        walletafterpaid=walletnew-totalfarepaymentfloat;

                        DriverTransactionHistory driverTransactionHistory = new DriverTransactionHistory(nowdate, dHistory.get(position).getCID(),dHistory.get(position).getName(), enteredamount, currentcasholdcash, currentcashnowcash, userID);


                        transactionhistory transactionhistory = new transactionhistory(totalfarepaymentfloat, nowdate, dHistory.get(position).getCID(), Source, walletpaymentfloat, walletnew);

                        transactionhistory transactionpaidhistory = new transactionhistory(totalfarepaymentfloat, nowdate, dHistory.get(position).getCID(), Source, walletnew, walletafterpaid);

                        db.collection("driverTransactionHistory").document(dHistory.get(position).getUniqueID()).set(driverTransactionHistory);
                        db.collection("transactionhistory").document(UniqueID).set(transactionhistory);
                        db.collection("transactionhistory").document(UniqueID).set(transactionpaidhistory);

                        //wallet
                        wallet wallet = new wallet(walletafterpaid,nowdate);
                        db.collection("wallet").document(dHistory.get(position).getCID()).set(wallet);


                        //totalearning


                        totalearninggetlistner= db.collection("totalearning").document("admin").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                totalearning totalearning = documentSnapshot.toObject(totalearning.class);
                                totalearningoldfare = totalearning.getTotalearning();
                                totalearningoldlogixshare = totalearning.getTotallogixearning();
                                totalearningoldride = totalearning.getTotalrides();
                                totalearningmoneyinwalletsold=totalearning.getMoneyinwallets();

                            }
                        });

                        totalearningnewfare = totalearningoldfare + totalfarepaymentfloat;
                        totalearningnewlogixshare = totalearningoldlogixshare + logixshare;
                        totalearningnewdride = totalearningoldride + 1;
                        totalearningmoneyinwalletsnew=totalearningmoneyinwalletsold+enteredamount;
                        totalearningmoneyinwallets= totalearningmoneyinwalletsnew-totalfarepaymentfloat;
                        totalearning totalearning = new totalearning(totalearningnewfare, totalearningnewdride, totalearningnewlogixshare,totalearningmoneyinwallets, nowdate);

                        db.collection("totalearning").document("admin").set(totalearning);


                        //historyupdate

                        db.collection("acceptRequest").document(dHistory.get(position).getUniqueID()).set(acceptRequest);
                        db.collection("CustomerHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);
                        db.collection("DriverHistory").document(dHistory.get(position).getUniqueID()).set(driverHistory);

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

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        walletgetlistner.remove();
        driveravailablegetlistner.remove();
        drivergetListner.remove();
        customergetlistener.remove();
        overallcashgetlistner.remove();
        totalearninggetlistner.remove();
        currentcashgetlistner.remove();
        driveravailablesecondgetlistner.remove();
        driveravailablethirdgetlistner.remove();
        driveravailablefourthgetlistner.remove();
        settingsgetlistner.remove();


    }
}


