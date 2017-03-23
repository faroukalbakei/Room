package com.dev.farouk.roomx.ui.account;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.dev.farouk.roomx.util.Const;
import com.dev.farouk.roomx.util.CustomMapView;
import com.dev.farouk.roomx.util.Permissions;
import com.dev.farouk.roomx.util.Utils;
import com.google.android.gms.location.LocationListener;
import com.dev.farouk.roomx.R;
import com.dev.farouk.roomx.model.PlaceObject;
import com.dev.farouk.roomx.model.Response;
import com.dev.farouk.roomx.service.Requests;
import com.dev.farouk.roomx.service.VolleyCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import pl.polak.clicknumberpicker.ClickNumberPickerListener;
import pl.polak.clicknumberpicker.PickerClickType;
import timber.log.Timber;

import static android.app.Activity.RESULT_OK;
import static me.iwf.photopicker.PhotoPreview.REQUEST_CODE;

public class AddRoomFragment extends Fragment implements VolleyCallback,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int RESULT_LOAD_IMG = 1;
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
    int tvv =0, wifi=0, airr=0, pooll=0, kitchenn=0;
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
    CustomMapView mMapView;
    private boolean isDataLoaded=false;
    private Requests requests;
    private List<String> photos;
    private List<String> selectedPhotos = new ArrayList<>();
    private String imgDecodableString;
    private List<String> listOfImages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.activity_be_hostt, container, false);
        buildGoogleApiClient();

        requests = new Requests(getActivity());

        add = (Button)rootView.findViewById(R.id.bt_behost_add);
        save = (Button) rootView.findViewById(R.id.bt_behost_save);
        pickergust = (pl.polak.clicknumberpicker.ClickNumberPickerView) rootView.findViewById(R.id.clickNumberPickerView3);
        pickerbed = (pl.polak.clicknumberpicker.ClickNumberPickerView) rootView.findViewById(R.id.clickNumberPickerView2);
        pickerbathroom = (pl.polak.clicknumberpicker.ClickNumberPickerView) rootView.findViewById(R.id.clickNumberPickerView1);
        pickerRoom = (pl.polak.clicknumberpicker.ClickNumberPickerView) rootView.findViewById(R.id.clickNumberPickerViewRoom);
        nameo = (EditText) rootView.findViewById(R.id.ed_BeHost_Nowner);
        horzintl = (EditText) rootView.findViewById(R.id.ed_BeHost_H);
        vertical = (EditText) rootView.findViewById(R.id.ed_BeHost_V);
        deproperty = (EditText) rootView.findViewById(R.id.ed_BeHost_deproperty);
        price = (EditText) rootView.findViewById(R.id.ed_price);

        air = (CheckBox) rootView.findViewById(R.id.cb_behost_air);
        pool = (CheckBox) rootView.findViewById(R.id.cb_behost_pool);
        tv = (CheckBox) rootView.findViewById(R.id.cb_behost_tv);
        kitchen = (CheckBox) rootView.findViewById(R.id.cb_behost_kitchen);
        net = (CheckBox) rootView.findViewById(R.id.cb_behost_net);
        mScrollView = (ScrollView) rootView.findViewById(R.id.sv_container);

        mMapView = (CustomMapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);

        // check if map is created successfully or not
        if (mMapView == null) {
            Toast.makeText(getActivity(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();

        }
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
                if(!Permissions.checkWriteExternalPermission(getActivity()))
                    Permissions.verifyStoragePermissions(getActivity());
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
              //  Toast.makeText(getActivity(), vertical.getText(), Toast.LENGTH_LONG).show();

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

              //  Toast.makeText(getActivity(), vertical.getText(), Toast.LENGTH_LONG).show();

            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&& isAdded() && !isDataLoaded)
        {

            isDataLoaded = true;
            //getActivity().setTitle(getResources().getString(R.string.title_activity_add_room));
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
/*
    public void openGallery() {
        GalleryConfig config = new GalleryConfig.Build()
                .limitPickPhoto(8)
                .singlePhoto(false)
                .hintOfPick("this is pick hint")
                .filterMimeTypes(new String[]{"image*//*"})
                .build();
        GalleryActivity.openActivity(getActivity(), 9, config);
    }*/
/*
    public void openGallery(){
        PhotoPicker.builder()
                .setPhotoCount(9)
                .setShowCamera(true)
                .setShowGif(true)
                .setPreviewEnabled(false)
                .start(getActivity(), PhotoPicker.REQUEST_CODE);
    }*/

    public void openGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.edit_profile_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem doneItem = menu.findItem(R.id.done);
        doneItem.setVisible(true);

        super.onPrepareOptionsMenu(menu);
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
        if (air.isChecked()) airr = 1;
        if (pool.isChecked()) pooll = 1;
        if (tv.isChecked()) tvv = 1;
        if (kitchen.isChecked()) kitchenn = 1;
        if (net.isChecked()) wifi = 1;
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



        if(Utils.isInternetAvailable(getActivity())){
              requests.addRoom(this, getActivity(), placeObject);
            //requests.uploadRoomImages(this,getActivity(),33,selectedPhotos);

        }else
             Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
    }

    /**
     * function to load map. If map is not created it will create it for you
     */

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("onActivityResult", "fragment");
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE )) {
            try {
                //list of photos of seleced
                List<String> photos = null;
                if (data != null) {
                    photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                }
                selectedPhotos.clear();

                if (photos != null) {

                    selectedPhotos.addAll(photos);
                }
                save.setText(getString(R.string.NImages) + AddPicNumber);
            } catch (Exception e) {

                save.setText(getString(R.string.apictures));
            }
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult", "fragment");

        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                //Setting image to ImageView
                Cursor cursor = getActivity().getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    listOfImages = new ArrayList<>();
                    listOfImages.add(imgDecodableString);
                  //  requests.uploadRoomImages(this,getActivity(),33,listOfImages);
                    //requests.uploadRoomImages(this, this, imgDecodableString);

                    // Set the Image in ImageView after decoding the String
//                    Picasso.with(getApplicationContext()).load(new File(imgDecodableString))
//                            .into(profilePicImageview);
                }


            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();

        //stop location updates when Activity is no longer active
/*        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }*/
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
            if (ContextCompat.checkSelfPermission(getActivity(),
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
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
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
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
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
                    if (ContextCompat.checkSelfPermission(getActivity(),
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
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case Const.REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Cannot Open gallery because you deny the permission", Toast.LENGTH_SHORT).show();
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
        PlaceObject placeObject;
        int roomId;
        if (response.getResult() == 1) {
            Utils.snakebar(getResources().getString(R.string.add_room_success), mScrollView);
            //if(response.getObject()!=null){
                placeObject =(PlaceObject)response.getObject();
                roomId=placeObject.getPid();
                if(listOfImages!=null&&listOfImages.size()!=0){
                    Log.d("requests ","uploadImages");
                    requests.uploadRoomImages(this,getActivity(),roomId,listOfImages);
                }
          //  }

        } else if (response.getResult() == 0)
            if(response.getMessage()!=null){
                Utils.snakebar(getResources().getString(R.string.general_error), mScrollView);
            }else Utils.snakebar(getResources().getString(R.string.add_room_fail), mScrollView);


    }
}
