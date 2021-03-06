package com.dev.ada.parkingbuddy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dev.ada.parkingbuddy.model.Block;
import com.dev.ada.parkingbuddy.model.PayStation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.socrata.android.client.Callback;
import com.socrata.android.client.Consumer;
import com.socrata.android.client.Response;
import com.socrata.android.soql.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dev.ada.parkingbuddy.R.id.carDesc;
import static com.dev.ada.parkingbuddy.R.id.map;
import static com.dev.ada.parkingbuddy.R.id.userName;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Consumer consumer;

    // Firebase location of map data
    public static final String MAP_PATH = "/map/";

    // A cache of map points displayed on map
    private HashMap<String, Marker> markers = new HashMap<String, Marker>();

    public static final String TAG = MapsActivity.class.getSimpleName();

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    //NavDrawer variables

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private TextView textViewUserName;
    private TextView textViewCarDesc;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        // 1
        mMap.setMyLocationEnabled(true);
// 2
        LocationAvailability locationAvailability =
                LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            // 3
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            // 4
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                        .getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17));
            }
        }
    }

    private void selectItemFromDrawer(int position) {
        Fragment fragment = null;

        switch(position) {
            case 0:
                startActivity(new Intent(this, LegendActivity.class));
                break;
            case 1:
                Intent intent = new Intent(this, LevelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
            case 2:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
        }


        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);


        //FIREBASE STUFF
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(MAP_PATH);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }



        textViewUserName = (TextView) findViewById(userName);
        textViewCarDesc = (TextView) findViewById(carDesc);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                String nameOfCurrentUser = (String) dataSnapshot.child(user.getUid()).child("name").getValue();
                String carOfCurrentUser = (String) dataSnapshot.child(user.getUid()).child("address").getValue();

                textViewUserName.setText(nameOfCurrentUser);
                textViewCarDesc.setText(carOfCurrentUser);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LatLng myLatLng = dataSnapshot.getValue(MarkerLocation.class).toLatLng();

                Marker myMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.hydrant1)).position(myLatLng).draggable(true).title(dataSnapshot.getKey()));

                markers.put(dataSnapshot.getKey(), myMarker);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                LatLng myLatLng= dataSnapshot.getValue(MarkerLocation.class).toLatLng();


                Marker changedMarker = markers.get(dataSnapshot.getKey());
                changedMarker.setPosition(myLatLng);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot){
                Marker deadMarker = markers.get(dataSnapshot.getKey());
                deadMarker.remove();

                markers.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        // 1
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mNavItems.add(new NavItem("Legend", "Icon descriptions", R.drawable.legend));
        mNavItems.add(new NavItem("Level", "Accumulated points", R.drawable.crown));
        mNavItems.add(new NavItem("Help", "How to be a Parking Buddy", R.drawable.help));
        mNavItems.add(new NavItem("Settings", "Change profile settings/Logout", R.drawable.settings));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigation Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(adapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d(TAG, "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String firebaseId = marker.getTitle();
                if (mDatabaseReference.child(firebaseId) != null){
                    mDatabaseReference.child(firebaseId).removeValue();
                    return false;
                }
                return false;
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = getLayoutInflater().inflate(R.layout.markerinfo, null);

                TextView snippet= (TextView) v.findViewById(R.id.snippet);
                TextView title= (TextView) v.findViewById(R.id.mtitle);

                title.setText(marker.getTitle().toString());
                if(marker.getSnippet() != null) {
                    snippet.setText(marker.getSnippet().toString());
                }

                return v;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {

                mDatabaseReference.push().setValue(new MarkerLocation(latLng));
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                // not implemented
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                // not implemented
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mDatabaseReference.child(marker.getTitle()).setValue(new MarkerLocation(marker.getPosition()));
            }
        });

        // Add a marker in Seattle and move the camera
        LatLng myPlace = new LatLng(47.608013, -122.335167);  // this is Seattle
        mMap.addMarker(new MarkerOptions().position(myPlace).title("Seattle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 17));

        consumer = new Consumer("data.seattle.gov", "PPbVrBWPSMKNRZODjPZHKhI8x");

        Query query = new Query("wbng-6x9n", Block.class);
        query.setLimit(3000);

        consumer.getObjects(query, new Callback<List<Block>>() {
            @Override
            public void onResults(Response<List<Block>> response) {
                List<Block> blocks = response.getEntity();
                for(int i = 0; i < blocks.size(); i++) {
                    if (blocks.get(i).getShape().getLatitude() != null && blocks.get(i).getShape().getLongitude() != null ) {
                        if (blocks.get(i).getParkingCategory().equals("No Parking Allowed")) {
                            LatLng position = new LatLng(blocks.get(i).getShape().getLatitude(), blocks.get(i).getShape().getLongitude());
                            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.no_parking)).position(position).title(blocks.get(i).getParkingCategory()));
                        } else if (blocks.get(i).getParkingCategory().equals("Unrestricted Parking")) {
                            LatLng position = new LatLng(blocks.get(i).getShape().getLatitude(), blocks.get(i).getShape().getLongitude());
                            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.unrestricted)).position(position).title(blocks.get(i).getParkingCategory()));
                        } else if (blocks.get(i).getParkingCategory().equals("Time Limited Parking") && blocks.get(i).getParkingTimeLimit() != null) {
                            LatLng position = new LatLng(blocks.get(i).getShape().getLatitude(), blocks.get(i).getShape().getLongitude());
                            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.time)).position(position).title(blocks.get(i).getParkingCategory()).snippet("Time Limit: " + blocks.get(i).getParkingTimeLimit()/60 + "Hours"));
                        } else if (blocks.get(i).getParkingCategory().equals("Restricted Parking Zone")) {
                            LatLng position = new LatLng(blocks.get(i).getShape().getLatitude(), blocks.get(i).getShape().getLongitude());
                            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.restricted)).position(position).title(blocks.get(i).getParkingCategory()));
                        }
                    }
                }
            }
        });

        Query query2 = new Query("aa4z-2beb", PayStation.class);
        query2.setLimit(2000);

        consumer.getObjects(query2, new Callback<List<PayStation>>() {
            @Override
            public void onResults(Response<List<PayStation>> response) {
                List<PayStation> payStations = response.getEntity();
                for(int i = 0; i < payStations.size(); i++) {
                    if (payStations.get(i).getShape().getLatitude() != null && payStations.get(i).getShape().getLongitude() != null) {
                            LatLng position = new LatLng(payStations.get(i).getShape().getLatitude(), payStations.get(i).getShape().getLongitude());
                            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.paystation)).position(position).title("Pay Station").snippet("Hours: " + payStations.get(i).getStartTimeWkd() + "-" + payStations.get(i).getEndTimeWkd() +"\n"+
                                    "Hourly Rate: $" + payStations.get(i).getWeekDayRate() +"\n"+"Pay By Phone: "+ payStations.get(i).getPayByPhone()));
                    }
                }
            }
        });


//        consumer.getObjects("erv6-k5zv", "select *", Sign.class, new Callback<List<Sign>>() {
//
//            @Override
//            public void onResults(Response<List<Sign>> response) {
//                List<Sign> signs = response.getEntity();
//                for(int i = 0; i < signs.size(); i++) {
//                    if (signs.get(i).getShape() != null) {
//                        LatLng position = new LatLng(signs.get(i).getShape().getLatitude(), signs.get(i).getShape().getLongitude());
//                        mMap.addMarker(new MarkerOptions().position(position).title(signs.get(i).getStandardText() + signs.get(i).getShape().getLongitude()));
//                    }
//                }
//                LatLng myPlace = new LatLng(47.65, -122.35);  // this is New York
//                mMap.addMarker(new MarkerOptions().position(myPlace).title("Seattle2"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 12));
//            }
//
//        });
    }

    public void onMapSearch(View view) {
        EditText locationSearch = (EditText) findViewById(R.id.editText);
        String location = locationSearch.getText().toString();
        List<Address>addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setUpMap();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 2
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 3
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    //Inner Class for Navigation Drawer
    class NavItem {
        String mTitle;
        String mSubtitle;
        int mIcon;

        public NavItem(String title, String subtitle, int icon) {
            mTitle = title;
            mSubtitle = subtitle;
            mIcon = icon;
        }
    }

    class DrawerListAdapter extends BaseAdapter {

        Context mContext;
        ArrayList<NavItem> mNavItems;

        public DrawerListAdapter(Context context, ArrayList < NavItem > navItems) {
        mContext = context;
        mNavItems = navItems;
        }

        @Override
        public int getCount () {
        return mNavItems.size();
        }

        @Override
        public Object getItem ( int position){
        return mNavItems.get(position);
        }

        @Override
        public long getItemId ( int position){
        return 0;
        }

        @Override
        public View getView ( int position, View convertView, ViewGroup parent){
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_item, null);
        } else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText(mNavItems.get(position).mTitle);
        subtitleView.setText(mNavItems.get(position).mSubtitle);
        iconView.setImageResource(mNavItems.get(position).mIcon);

        return view;
        }
    }
}