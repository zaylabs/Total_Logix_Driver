package com.example.raza.total_logix_driver.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raza.total_logix_driver.BuildConfig;
import com.example.raza.total_logix_driver.DTO.currentCash;
import com.example.raza.total_logix_driver.DTO.customerRequest;
import com.example.raza.total_logix_driver.DTO.driverAvailable;
import com.example.raza.total_logix_driver.DTO.driverProfile;
import com.example.raza.total_logix_driver.R;
import com.example.raza.total_logix_driver.fragment.DriverHistoryFragment;
import com.example.raza.total_logix_driver.fragment.currentRideFragment;
import com.example.raza.total_logix_driver.fragment.helpFragment;
import com.example.raza.total_logix_driver.fragment.paymentHistoryFragment;
import com.example.raza.total_logix_driver.fragment.profileFragment;
import com.example.raza.total_logix_driver.fragment.transactionhistoryFragment;
import com.example.raza.total_logix_driver.fragment.walletFragment;
import com.example.raza.total_logix_driver.recylerViewAdapters.customerRequestAdapter;
import com.example.raza.total_logix_driver.suportclasses.PermissionUtils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.view.View.GONE;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,ActivityCompat.OnRequestPermissionsResultCallback {

    private Marker mNow;

    /*Location Update*/
    private static final String TAGLoc = HomeActivity.class.getSimpleName();

    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    /**
     * Provides access to the Location Settings API.
     */
    private SettingsClient mSettingsClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private LocationSettingsRequest mLocationSettingsRequest;

    /**
     * Callback for Location events.
     */
    private LocationCallback mLocationCallback;

    /**
     * Represents a geographical location.
     */
    public Location mCurrentLocation;

    // UI Widgets.
    /*private Button mStartUpdatesButton;*/
    /*private Button mStopUpdatesButton;*/
    private TextView mLastUpdateTimeTextView;
    private TextView mLatitudeTextView;
    private TextView mLongitudeTextView;

    // Labels.
    private String mLatitudeLabel;
    private String mLongitudeLabel;
    private String mLastUpdateTimeLabel;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    private Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    private String mLastUpdateTime;

    //*******************************Location Update End********************************


    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    private FirebaseFirestore firestoreDB;


    private SupportMapFragment sMapFragment;

    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    public FrameLayout mJobList;
    public RelativeLayout mFooter;
    private FirebaseAuth mAuth;
    public String userID;

    public GeoPoint driverlocation;
    private String mVahicleType;
    private Switch mWorkingSwitch;
    public String driverdp;
    public String drivernic;
    public String driverphone;
    public String carregno;
    public String driverName;
    public String driverPhone;
    public String driverEmail;
    private TextView mName, mOnline;
    private de.hdodenhof.circleimageview.CircleImageView mUserdp;
    private List<customerRequest> cRequests;
    private customerRequestAdapter customerRequestAdapter;
    private RecyclerView mListview;
    private customerRequest request;
    private String user_id;
    private String TAG = "";
    private TextView mCash,mLogix,mEarned,mTotalFare;
    private ListenerRegistration CurrentCashDasboardListner;
    private ListenerRegistration customerrequestlistner;
    private NumberFormat RsFormat = new DecimalFormat("'Rs.'#");
    private NumberFormat logixformat=new DecimalFormat("#");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sMapFragment = SupportMapFragment.newInstance();

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firestoreDB = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(HomeActivity.this, "User is Signed In", Toast.LENGTH_SHORT).show();
                    getUserInfo();
                } else {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mFooter=findViewById(R.id.footer);
        mCash=findViewById(R.id.txt_cash);
        mLogix=findViewById(R.id.txt_totallogix);
        mEarned=findViewById(R.id.txt_earned);
        mTotalFare=findViewById(R.id.txt_totalfare);
        mJobList=findViewById(R.id.joblistframe);
        cRequests = new ArrayList<>();
        customerRequestAdapter = new customerRequestAdapter(this, cRequests);
        mListview = (RecyclerView) findViewById(R.id.mListView);
        mListview.setHasFixedSize(true);

        mListview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mListview.setAdapter(customerRequestAdapter);




// *********************Location Update ************************************


        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();

        CurrentCashDasboardListner=firestoreDB.collection("currentCash").document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    currentCash currentCash = documentSnapshot.toObject(com.example.raza.total_logix_driver.DTO.currentCash.class);
                    String stringcash=RsFormat.format(currentCash.getCurrentcash());
                    if (stringcash!=null){
                        mCash.setText(stringcash);
                    }
                String stringfare=RsFormat.format(currentCash.getTotalfarepending());
                if (stringfare!=null){
                    mTotalFare.setText(stringfare);
                }
                String stringearned=RsFormat.format(currentCash.getTotaldriversharepending());
                if (stringearned!=null){
                    mEarned.setText(stringearned);
                }
                String stringlogix=logixformat.format(currentCash.getTotaljourney());
                if (stringlogix!=null){
                    mLogix.setText(stringlogix);
                }



            }
        });

        //**************************************Location update End ****************************8




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        mWorkingSwitch = hView.findViewById(R.id.online);
        mName= hView.findViewById(R.id.txt_name);
        mUserdp= hView.findViewById(R.id.profile);
        mOnline=hView.findViewById(R.id.is_online);
        mWorkingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mWorkingSwitch.isChecked()) {
                    connectDriver();
                } else {
                    disconnectDriver();
                }
            }
        });


        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setCheckable(true);
        FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        if (!sMapFragment.isAdded()) {
            sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
        } else {
            sFm.beginTransaction().show(sMapFragment).commit();
        }

        sMapFragment.getMapAsync(this);


    }


    public void viewJobList() {

        customerrequestlistner=firestoreDB.collection("customerRequest").whereEqualTo("vt", mVahicleType).addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error:" + e.getMessage());
                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    switch (doc.getType()) {
                        case ADDED:
                            user_id = doc.getDocument().getId();
                            request = doc.getDocument().toObject(customerRequest.class).withID(user_id);
                            cRequests.add(request);
                            customerRequestAdapter.notifyDataSetChanged();
                            break;
                        case MODIFIED:
                            user_id = doc.getDocument().getId();
                            request = doc.getDocument().toObject(customerRequest.class).withID(user_id);
                            cRequests.add(request);
                            customerRequestAdapter.notifyDataSetChanged();
                            break;
                        case REMOVED:
                            cRequests.remove(request);
                            customerRequestAdapter.notifyDataSetChanged();

                  /*          FragmentTransaction ft = getFragmentManager().beginTransaction();
                            mFooter.setVisibility(GONE);


                            ft.replace(R.id.cm, new currentRideFragment());
                            ft.commit();

*/
                            break;
                    }

                }
                mJobList.setVisibility(View.VISIBLE);

            }
        });
    }

        private void connectDriver() {
        Toast.makeText(HomeActivity.this, "Driver Available" + mVahicleType, Toast.LENGTH_SHORT).show();
        mOnline.setText(R.string.online);
        mRequestingLocationUpdates = true;
        startLocationUpdates();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    viewJobList();
                }
        }, 10000);

    }


    private void saveLocation() {

        driverlocation = new GeoPoint(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        driverAvailable driveravailable = new driverAvailable(driverlocation);
        firestoreDB.collection("driveravailable").document(userID).set(driveravailable);

        if (mNow != null) {
            mNow.remove();
        }

        LatLng newLatLngCL = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        CameraUpdate mCameraCL = CameraUpdateFactory.newLatLngZoom(newLatLngCL, 18);
        mMap.moveCamera(mCameraCL);
        mMap.animateCamera(mCameraCL);
        mNow = mMap.addMarker(new MarkerOptions().position(newLatLngCL)
                .title(newLatLngCL.toString()).draggable(true));

    }


    private void disconnectDriver() {
        Toast.makeText(HomeActivity.this, "Driver Not Available", Toast.LENGTH_SHORT).show();
        mOnline.setText(R.string.driverOffline);
        if(mWorkingSwitch.isChecked()) {
            customerrequestlistner.remove();
        }
        firestoreDB.collection("driveravailable").document(userID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAGLoc, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAGLoc, "Error deleting document", e);
                    }
                });
        mJobList.setVisibility(View.GONE);
    }
    private void getUserInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            driverName = user.getDisplayName();
            mName.setText(driverName);


            DocumentReference docRef = firestoreDB.collection("drivers").document(userID);

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    driverProfile profile = documentSnapshot.toObject(driverProfile.class);
                    driverphone=profile.getPhone();
                    driverdp=profile.getDisplaypic();
                    carregno=profile.getReg_number();
                    drivernic=profile.getCnic();
                    mVahicleType=profile.getVt();

                }
            });
            if (user.getPhotoUrl()!=null){
                String photodp = user.getPhotoUrl().toString(
                );
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View hView =  navigationView.getHeaderView(0);
                navigationView.setNavigationItemSelectedListener(this);
                Picasso.with(hView.getContext()).load(photodp).resize(150,150).centerCrop().into(mUserdp);
            }}

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fm = getFragmentManager();
        android.support.v4.app.FragmentManager sFm = getSupportFragmentManager();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        int id = item.getItemId();
        if (sMapFragment.isAdded())
            sFm.beginTransaction().hide(sMapFragment).commit();

        switch (item.getItemId()) {
            case R.id.home:
                mAuth.addAuthStateListener(firebaseAuthListener);

                if (!sMapFragment.isAdded()){
                    sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
                }
                else{
                sFm.beginTransaction().show(sMapFragment).commit();
                }

                if (mWorkingSwitch.isChecked()) {
                    connectDriver();
                } else {
                    disconnectDriver();
                }
                mFooter.setVisibility(View.VISIBLE);
                break;
            case R.id.current_ride:
                hideFooter();
                ft.replace(R.id.cm, new currentRideFragment());
                ft.commit();
                break;
            case R.id.completed_rides:
                hideFooter();
                ft.replace(R.id.cm, new DriverHistoryFragment());
                ft.commit();
                break;
            case R.id.transactionhistory:
                hideFooter();
                ft.replace(R.id.cm, new transactionhistoryFragment());
                ft.commit();
                break;
            case R.id.payment_detail:
                hideFooter();
                ft.replace(R.id.cm, new paymentHistoryFragment());
                ft.commit();
                break;
            case R.id.wallet:
                hideFooter();
                ft.replace(R.id.cm, new walletFragment());
                ft.commit();

                break;

            case R.id.profile:
                hideFooter();
                ft.replace(R.id.cm, new profileFragment());
                ft.commit();
                break;

            case R.id.help:
                hideFooter();
                ft.replace(R.id.cm, new helpFragment());
                ft.commit();

                break;

            case R.id.logout:
                CurrentCashDasboardListner.remove();
                mAuth.signOut();
                break;

            default:
                if (!sMapFragment.isAdded()){
                    sFm.beginTransaction().add(R.id.map, sMapFragment).commit();
                }
                else{
                    sFm.beginTransaction().show(sMapFragment).commit();
                }
                if (mWorkingSwitch.isChecked()) {
                    connectDriver();
                } else {
                    disconnectDriver();
                }
                mFooter.setVisibility(View.VISIBLE);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideFooter() {
        mJobList.setVisibility(GONE);
        mFooter.setVisibility(GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
        startLocationUpdates();
    }
    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
        disconnectDriver();
        customerrequestlistner.remove();
        CurrentCashDasboardListner.remove();

        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        enableMyLocation();
        setOnMyLocationButtonClick();
        setOnMyLocationClick();



    }

    private void setOnMyLocationClick() {
        mMap.setOnMyLocationClickListener(new GoogleMap.OnMyLocationClickListener() {
            @Override
            public void onMyLocationClick(@NonNull Location location) {

                Toast.makeText(HomeActivity.this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

            }
        });
    }
    private void setOnMyLocationButtonClick() {
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(HomeActivity.this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
                // Return false so that we don't consume the event and the default behavior still occurs
                // (the camera animates to the user's current position).
                return false;
            }

        });
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(HomeActivity.this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        }
    }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    // ************************Location Update Code ********************************8

    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
            updateUI();
        }
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateLocationUI();

            }
        };
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAGLoc, "User agreed to make required location settings changes.");
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAGLoc, "User chose not to make required location settings changes.");
                        mRequestingLocationUpdates = false;
                        updateUI();
                        break;
                }
                break;
        }
    }

    /**
     * Handles the Start Updates button and requests start of location updates. Does nothing if
     * updates have already been requested.
     */
   /* public void startUpdatesButtonHandler(View view) {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            setButtonsEnabledState();
            startLocationUpdates();
        }
    }*/

    /**
     * Handles the Stop Updates button, and requests removal of location updates.
     */
   /* public void stopUpdatesButtonHandler(View view) {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        stopLocationUpdates();
    }*/

    /**
     * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
     * runtime permission has been granted.
     */
    public void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAGLoc, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateUI();


                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAGLoc, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAGLoc, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAGLoc, errorMessage);
                                Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                                mRequestingLocationUpdates = false;
                        }

                        updateUI();
                    }
                });
    }


    /**
     * Updates all UI fields.
     */
    private void updateUI() {
        setButtonsEnabledState();
        updateLocationUI();
    }

    /**
     * Disables both buttons when functionality is disabled due to insuffucient location settings.
     * Otherwise ensures that only one button is enabled at any time. The Start Updates button is
     * enabled if the user is not requesting location updates. The Stop Updates button is enabled
     * if the user is requesting location updates.
     */
    private void setButtonsEnabledState() {
        if (mRequestingLocationUpdates) {
            /* mStartUpdatesButton.setEnabled(false);*/
            /* mStopUpdatesButton.setEnabled(true);*/
        } else {
            /*mStartUpdatesButton.setEnabled(true);*/
            /*mStopUpdatesButton.setEnabled(false);*/
        }
    }

    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
         /*   mLatitudeTextView.setText(String.format(Locale.ENGLISH, "%f",
                    mCurrentLocation.getLatitude()));
            *//*mLatitudeTextView.setText(String.format(Locale.ENGLISH, "%s: %f", mLatitudeLabel,
                    mCurrentLocation.getLatitude()));*//*
             *//*mLongitudeTextView.setText(String.format(Locale.ENGLISH, "%s: %f", mLongitudeLabel,
                    mCurrentLocation.getLongitude()));*//*
            mLongitudeTextView.setText(String.format(Locale.ENGLISH, "%f",
                    mCurrentLocation.getLongitude()));
            mLastUpdateTimeTextView.setText(String.format(Locale.ENGLISH, "%s: %s",
                    mLastUpdateTimeLabel, mLastUpdateTime));

         */   saveLocation();





        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    private void stopLocationUpdates() {
        if (!mRequestingLocationUpdates) {
            Log.d(TAGLoc, "stopLocationUpdates: updates never requested, no-op.");
            return;
        }

        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mRequestingLocationUpdates = false;
                        setButtonsEnabledState();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
/*
        // Within {@code onPause()}, we remove location updates. Here, we resume receiving
        // location updates if the user has requested them.
        if (mRequestingLocationUpdates && checkPermissions()) {
            if (!(mWorkingSwitch.isChecked())){
                Toast.makeText(HomeActivity.this, "Driver Not Available", Toast.LENGTH_SHORT).show();
            }else
                startLocationUpdates();
            updateUI();
        } else if (!checkPermissions()) {
            requestPermissions();
        }

*/

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Remove location updates to save battery.
//        stopLocationUpdates();

    }


    /**
     * Stores activity data in the Bundle.
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAGLoc, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(HomeActivity.this,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAGLoc, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAGLoc, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAGLoc, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mRequestingLocationUpdates) {
                    Log.i(TAGLoc, "Permission granted, updates requested, starting location updates");
                    startLocationUpdates();
                }
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }




}
