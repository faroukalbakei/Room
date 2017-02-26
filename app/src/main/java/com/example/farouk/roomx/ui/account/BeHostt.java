package com.example.farouk.roomx.ui.account;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.util.List;

import pl.polak.clicknumberpicker.ClickNumberPickerListener;
import pl.polak.clicknumberpicker.PickerClickType;

public class BeHostt extends AppCompatActivity implements VolleyCallback,OnMapReadyCallback ,GoogleMap.OnMapClickListener {

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
    private GoogleMap googleMap;
    int AddPicNumber;
    Toolbar toolbar;
    String guestValue,roomValue,bedValue,bathroomValue;
    Boolean tvv,wifi,airr,pooll,kitchenn;
    String lang ,lat;
    private String location;
    EditText price;

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


        pickergust.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                guestValue= String.valueOf((int)currentValue);

            }
        });

        pickerbed.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                bedValue= String.valueOf((int)currentValue);
            }
        });
        pickerbathroom.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                bathroomValue= String.valueOf((int)currentValue);
            }
        });

        pickerRoom.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                roomValue= String.valueOf((int)currentValue);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GalleryConfig config = new GalleryConfig.Build()
                        .limitPickPhoto(8)
                        .singlePhoto(true)
                        .hintOfPick("this is pick hint")
                        .filterMimeTypes(new String[]{"image/*"})
                        .build();
                GalleryActivity.openActivity(BeHostt.this, 9, config);

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
        if(air.isChecked()) airr=true;
        if(pool.isChecked()) pooll=true;
        if(tv.isChecked()) tvv=true;
        if(kitchen.isChecked()) kitchenn=true;
        if(net.isChecked()) wifi=true;
        PlaceObject placeObject =new PlaceObject();
        placeObject.setName(nameo.getText().toString()+"");
        placeObject.setDescription(deproperty.getText().toString()+"");
        placeObject.setAirCondition(String.valueOf(airr));
        placeObject.setTv(String.valueOf(tvv));
        placeObject.setWifi(String.valueOf(wifi));
        placeObject.setKitchen(String.valueOf(kitchenn));
        placeObject.setPool(String.valueOf(pooll));
        placeObject.setNumberOfGuests(guestValue+"");
        placeObject.setNumberOfBaths(bathroomValue+"");
        placeObject.setNumberOfBeds(bedValue+"");
        placeObject.setNumberOfRooms(roomValue+"");
        placeObject.setLocation(location);
        placeObject.setPrice(price.getText().toString()+"");

        Requests requests =new Requests(this);
        requests.addRoom(this,this,placeObject);
    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // check if map is created successfully or not
        if (mapFragment == null) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title(""));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
    public void onMapClick(LatLng latLng) {
        LatLng sydney = new LatLng(-33.852, 151.211);

        location=sydney.toString();
        Log.d("location" ,location.toString());
        lat= String.valueOf(latLng.longitude);
        lang= String.valueOf(latLng.latitude);
        vertical.setText(lat);
        horzintl.setText(lang);

    }

    @Override
    public void onSuccess(Response response) {

        Log.d("response" ,response.toString());

    }
}
