package com.example.farouk.roomx.ui.account;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.farouk.roomx.ui.main.IconTextTabsActivity;
import com.example.farouk.roomx.util.Const;
import com.example.farouk.roomx.util.Permissions;
import com.example.farouk.roomx.util.Utils;
import com.example.farouk.roomx.util.WorkaroundMapFragment;
import com.google.android.gms.location.LocationListener;
import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.util.List;

import pl.polak.clicknumberpicker.ClickNumberPickerListener;
import pl.polak.clicknumberpicker.PickerClickType;

public class BeHostt extends AppCompatActivity implements VolleyCallback,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    Button add;
    Button save;
    EditText nameo;
    pl.polak.clicknumberpicker.ClickNumberPickerView pickergust;
    pl.polak.clicknumberpicker.ClickNumberPickerView pickerbed;
    pl.polak.clicknumberpicker.ClickNumberPickerView pickerbathroom;
    pl.polak.clicknumberpicker.ClickNumberPickerView pickerRoom;

    EditText horzintl;
    EditText vertical;
    EditText deproperty;
    CheckBox air;
    CheckBox pool;
    CheckBox tv;
    CheckBox kitchen;
    CheckBox net;
    // Google Map
    int AddPicNumber;
    Toolbar toolbar;
    String guestValue, roomValue, bedValue, bathroomValue;
    Boolean tvv, wifi, airr, pooll, kitchenn;
    String lang, lat;
    private String location;
    EditText price;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    LatLng latLng;
    GoogleMap mGoogleMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_hostt);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.title_activity_edit_profile));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buildGoogleApiClient();
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        add = (Button) findViewById(R.id.bt_behost_add);
        save = (Button) findViewById(R.id.bt_behost_save);
        pickergust = (pl.polak.clicknumberpicker.ClickNumberPickerView) findViewById(R.id.clickNumberPickerView3);
        pickerbed = (pl.polak.clicknumberpicker.ClickNumberPickerView) findViewById(R.id.clickNumberPickerView2);
        pickerbathroom = (pl.polak.clicknumberpicker.ClickNumberPickerView) findViewById(R.id.clickNumberPickerView1);
        pickerRoom = (pl.polak.clicknumberpicker.ClickNumberPickerView) findViewById(R.id.clickNumberPickerViewRoom);
        nameo = (EditText) findViewById(R.id.ed_BeHost_Nowner);
        horzintl = (EditText) findViewById(R.id.ed_BeHost_H);
        vertical = (EditText) findViewById(R.id.ed_BeHost_V);
        deproperty = (EditText) findViewById(R.id.ed_BeHost_deproperty);
        price = (EditText) findViewById(R.id.ed_price);

        air = (CheckBox) findViewById(R.id.cb_behost_air);
        pool = (CheckBox) findViewById(R.id.cb_behost_pool);
        tv = (CheckBox) findViewById(R.id.cb_behost_tv);
        kitchen = (CheckBox) findViewById(R.id.cb_behost_kitchen);
        net = (CheckBox) findViewById(R.id.cb_behost_net);


        if (horzintl != null && vertical != null) {
            lat = vertical.getText().toString();
            lang = horzintl.getText().toString();
        }
        pickergust.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                guestValue = String.valueOf((int) currentValue);

            }
        });

        pickerbed.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                bedValue = String.valueOf((int) currentValue);
            }
        });
        pickerbathroom.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                bathroomValue = String.valueOf((int) currentValue);
            }
        });

        pickerRoom.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                roomValue = String.valueOf((int) currentValue);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!Permissions.checkWriteExternalPermission(BeHostt.this))
                    Permissions.verifyStoragePermissions(BeHostt.this);
                else openGallery();
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //save on click

            }

        });

        horzintl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(getApplication(), vertical.getText(), Toast.LENGTH_LONG).show();

            }
        });
        vertical.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Toast.makeText(getApplication(), vertical.getText(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void openGallery() {
        GalleryConfig config = new GalleryConfig.Build()
                .limitPickPhoto(8)
                .singlePhoto(true)
                .hintOfPick("this is pick hint")
                .filterMimeTypes(new String[]{"image/*"})
                .build();
        GalleryActivity.openActivity(BeHostt.this, 9, config);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_profile_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                onDoneClick();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onDoneClick() {
        if (air.isChecked()) airr = true;
        if (pool.isChecked()) pooll = true;
        if (tv.isChecked()) tvv = true;
        if (kitchen.isChecked()) kitchenn = true;
        if (net.isChecked()) wifi = true;
        PlaceObject placeObject = new PlaceObject();
        placeObject.setName(nameo.getText().toString() + "");
        placeObject.setDescription(deproperty.getText().toString() + "");
        placeObject.setAirCondition(String.valueOf(airr));
        placeObject.setTv(String.valueOf(tvv));
        placeObject.setWifi(String.valueOf(wifi));
        placeObject.setKitchen(String.valueOf(kitchenn));
        placeObject.setPool(String.valueOf(pooll));
        placeObject.setNumberOfGuests(guestValue + "");
        placeObject.setNumberOfBaths(bathroomValue + "");
        placeObject.setNumberOfBeds(bedValue + "");
        placeObject.setNumberOfRooms(roomValue + "");
        placeObject.setLocation(lat + "," + lang);
        placeObject.setPrice(price.getText().toString() + "");


        if(Utils.isInternetAvailable(this)){
            Requests requests = new Requests(this);
            requests.addRoom(this, this, placeObject);
        }else
             Toast.makeText(this, "لا يوجد انترنت", Toast.LENGTH_LONG).show();
    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        SupportMapFragment mapFragment = (WorkaroundMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mScrollView = (ScrollView) findViewById(R.id.sv_container);

        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                mScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

        // check if map is created successfully or not
        if (mapFragment == null) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            //list of photos of seleced
            List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
            for (int i = 0; i < photos.size(); i++) {
                AddPicNumber = photos.size();
            }
            save.setText(getString(R.string.NImages) + AddPicNumber);
        } catch (Exception e) {

            save.setText(getString(R.string.apictures));
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Setting a click event handler for the map
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                // markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                lat = String.valueOf(latLng.latitude);
                lang = String.valueOf(latLng.longitude);
                vertical.setText(lat);
                horzintl.setText(lang);
                // Clears the previously touched position
                mGoogleMap.clear();

                // Animating to the touched position
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mGoogleMap.addMarker(markerOptions);
            }
        });
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                //buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(getResources().getString(R.string.current_location));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));

        //optionally, stop location updates if only current location is needed
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(BeHostt.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case Const.REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Cannot Open gallery because you deny the permission", Toast.LENGTH_SHORT).show();
                } else {
                    openGallery();
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onSuccess(Response response) {

        Log.d("response", response.toString());

        if (response.getResult() == 1) {
            Utils.snakebar(getResources().getString(R.string.add_room_success), mScrollView.getRootView());
            finish();
            startActivity(new Intent(BeHostt.this, IconTextTabsActivity.class));
        } else if (response.getResult() == 0)
            Utils.snakebar(getResources().getString(R.string.add_room_fail), mScrollView.getRootView());


    }
}
