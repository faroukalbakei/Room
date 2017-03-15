package com.example.farouk.roomx.ui.explore;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.ItemRoom;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.RoomPhoto;
import com.example.farouk.roomx.model.RoomReservation;
import com.example.farouk.roomx.model.User;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.util.Const;
import com.example.farouk.roomx.util.CustomListAdapterDialog;
import com.example.farouk.roomx.util.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class PlaceDetailsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnFocusChangeListener, View.OnClickListener, VolleyCallback {

    @Bind(R.id.imageView_place)
    ImageView imageViewPlace;
    @Bind(R.id.title_text_view)
    TextView titleTextView;
    @Bind(R.id.hosted_image)
    CircleImageView hostedImage;
    @Bind(R.id.text_view_hosted_name)
    TextView textViewHostedName;
    @Bind(R.id.text_view_guest)
    TextView textViewGuest;
    @Bind(R.id.text_view_room)
    TextView textViewRoom;
    @Bind(R.id.text_view_bed)
    TextView textViewBed;
    @Bind(R.id.text_view_bathroom)
    TextView textViewBathroom;
    @Bind(R.id.arrow_left_right)
    ImageView arrowLeftRight;
    @Bind(R.id.arrow_up_down)
    ImageView arrowUpDown;
    @Bind(R.id.activity_place_details)
    LinearLayout activityPlaceDetails;
    @Bind(R.id.text_view_room_price)
    TextView textViewRoomPrice;
    @Bind(R.id.button_reserve)
    Button buttonReserve;
    @Bind(R.id.describtion_text_view)
    TextView describtionTextView;
    @Bind(R.id.button_back)
    ImageButton buttonBack;
    @Bind(R.id.room_item_layout)
    RelativeLayout roomItemRelativeLyout;
    long placeId;
    @Bind(R.id.like_toggle)
    ToggleButton likeToggle;
    private PlaceObject placeObject;
    List<RoomPhoto> roomPhotoList;
    List<RoomReservation> roomReservations;
    List<User> user;
    double lang, lat;
    private EditText editTextStart;
    private EditText editTextend;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    String roomId;
    private SimpleDateFormat dateFormatter;
    private AlertDialog alertDialog;
    private ArrayList<ItemRoom> itemRooms;
    private long placePId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        ButterKnife.bind(this);
        placeId = getIntent().getExtras().getLong(Const.PLACE_ID);
        placePId = getIntent().getExtras().getLong(Const.PLACE_PID);

        if (placeId != 0) {
            placeObject = PlaceObject.findById(PlaceObject.class, placePId);
            Log.d("placeObject", placeObject.toString());
            user =  User.find(User.class,"uId=?",String.valueOf(placeObject.getUserId()));
            roomPhotoList = RoomPhoto.find(RoomPhoto.class, "place_Id=?", String.valueOf(placeId));
            roomReservations = RoomReservation.find(RoomReservation.class, "place_Id=?", String.valueOf(placeId));
            Log.d("getUser", user.toString());
            Log.d("roomPhotoList", roomPhotoList.toString());
            Log.d("roomReservations", roomReservations.toString());
            placeObject.setUser(user.get(0));
            placeObject.setRoomPhoto(roomPhotoList);
            placeObject.setRoomReservation(roomReservations);

            //placeObject1 = PlaceObject.findById(PlaceObject.class,9);
        }
        //for(PlaceObject placeObject1 : placeObject) {
        roomId = String.valueOf(placeObject.getPid());
        Log.d("placeObject", placeObject.toString());
        if(placeObject.getUser().getPhotolink()!=null) {
            Log.i(" photo user", placeObject.getUser().getPhotolink());
            Picasso.with(this).load(placeObject.getUser().getPhotolink()).placeholder(R.drawable.ic_profile).resize(70, 70).into(hostedImage);
        }else{
            hostedImage.setImageResource(R.drawable.ic_profile);
        }
        if(placeObject.getRoomPhoto()!=null&&!placeObject.getRoomPhoto().isEmpty()&&placeObject.getRoomPhoto().size()>0) {
            Log.i(" photo placeObject", placeObject.getRoomPhoto().get(0).toString());
            Picasso.with(this).load(placeObject.getRoomPhoto().get(0).getPhotolink()).placeholder(R.drawable.building).resize(200, 85).into(imageViewPlace);
        }else{
            imageViewPlace.setImageResource(R.drawable.building);
        }

        String hostedName = String.format(getResources().getString(R.string.hosted_name), placeObject.getUser().getName());
        titleTextView.setText(placeObject.getName());
        textViewHostedName.setText(hostedName);
        textViewGuest.setText(String.format(getResources().getString(R.string.guest), placeObject.getNumberOfGuests()));
        textViewRoom.setText(String.format(getResources().getString(R.string.room), placeObject.getNumberOfRooms()));
        textViewBed.setText(String.format(getResources().getString(R.string.beds_parms), placeObject.getNumberOfBeds()));
        textViewBathroom.setText(String.format(getResources().getString(R.string.bthroom), placeObject.getNumberOfBaths()));
        textViewRoomPrice.setText(placeObject.getPrice());
        describtionTextView.setText(placeObject.getDescription());
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (placeObject.getIsFavourate() == 1) {
            likeToggle.setChecked(true);
        } else likeToggle.setChecked(false);


        itemRooms = new ArrayList<>();
        itemRooms.add(new ItemRoom("مكيف", placeObject.getAirCondition()));
        itemRooms.add(new ItemRoom("مطبخ", placeObject.getKitchen()));
        itemRooms.add(new ItemRoom("إنترنت لاسكي wifi", placeObject.getWifi()));
        itemRooms.add(new ItemRoom("مسبح", placeObject.getPool()));
        itemRooms.add(new ItemRoom("تلفزيون", placeObject.getTv()));
        likeToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToWishList();

            }
        });
        roomItemRelativeLyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showItemDialog(itemRooms);
            }
        });
        String location = placeObject.getLocation();
        if (location != null&& location !=" " && !location.isEmpty()) {
            StringTokenizer tokens = new StringTokenizer(location, ",");
            lang = Double.parseDouble(tokens.nextToken());
            lat = Double.parseDouble(tokens.nextToken());
        }
        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReserveDiaog();
            }
        });
        // }
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addToWishList() {
        if(Utils.isInternetAvailable(this)){
            Requests requests = new Requests(this);
            requests.addToWishList(this, this, String.valueOf(placeObject.getPid()), placeObject.getId());
        }else
             Toast.makeText(this, "لا يوجد انترنت", Toast.LENGTH_LONG).show();


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
        LatLng placeLocation = new LatLng(lat, lang);
        Log.d("lat", String.valueOf(lat));
        Log.d("lang", String.valueOf(lang));

        googleMap.addMarker(new MarkerOptions().position(placeLocation)
                .title(""));
/*        CameraUpdate center=
                CameraUpdateFactory.newLatLng(placeLocation);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(7);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);*/

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(placeLocation,7));
        // Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(7), 2000, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    public void showReserveDiaog() {
        dateFormatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.reserve_dialoge_layout, null);
        dialogBuilder.setView(dialogView);
        editTextStart = (EditText) dialogView.findViewById(R.id.ed_dialog_start_booking);
        editTextStart.setRawInputType(InputType.TYPE_NULL);

        editTextend = (EditText) dialogView.findViewById(R.id.ed_end_diloge_booking);
        editTextend.setRawInputType(InputType.TYPE_NULL);
        setDateTimeField();
        Button ok = (Button) dialogView.findViewById(R.id.button_dialoge_reserve);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextStart.getText();
                editTextend.getText();

                Log.d("editTextStart", editTextStart.getText().toString());
                Log.d("editTextend", editTextend.getText().toString());
                Log.d("room", roomId);

                getReserveInfo(editTextStart.getText().toString(), editTextend.getText().toString());


            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.setTitle(getResources().getString(R.string.reserve_diaoge_title));
        alertDialog.show();

    }

    private void getReserveInfo(String text, String text1) {

        if(Utils.isInternetAvailable(this)){
            Requests requests = new Requests(getApplicationContext());
            requests.reserveRoom(this, this, roomId, text, text1);
        }else
             Toast.makeText(this, "لا يوجد انترنت", Toast.LENGTH_LONG).show();

    }

    private void setDateTimeField() {
        editTextStart.setOnClickListener(this);
        editTextend.setOnFocusChangeListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                editTextStart.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                editTextend.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if (view == editTextStart) {
            fromDatePickerDialog.show();
        }
    }

    @Override
    public void onSuccess(Response response) {
        if(response.getResult()==1){
            Toast.makeText(this,response.getMessage()+"",Toast.LENGTH_LONG).show();
        }else if(response.getResult()==0){
            Toast.makeText(this,response.getMessage()+"",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), response.getMessage() + "", Toast.LENGTH_LONG).show();
        }
        if(alertDialog!=null){
            alertDialog.dismiss();
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            if (view == editTextend) {
                toDatePickerDialog.show();
            }
        }
    }

    public void showItemDialog(final List<ItemRoom> itemRooms) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(PlaceDetailsActivity.this);
        //builderSingle.setTitle(Html.fromHtml(getString(R.string.dialog_incomplete_questions_title)));
        LayoutInflater factory = LayoutInflater.from(this);
        View content = factory.inflate(R.layout.custom_dialog_layout, null);
        ListView lv = (ListView) content.findViewById(R.id.listDialog);
        //tv.setText(getResources().getString(R.string.dialog_incomplete_questions_message, invalidAnswerList.size()));
        CustomListAdapterDialog customListAdapterDialog = new CustomListAdapterDialog(PlaceDetailsActivity.this, itemRooms);
        //customAlertAdapter.listOfChildOfCompound(listOfChildOfCompound);
        lv.setAdapter(customListAdapterDialog);
        builderSingle.setView(content);
        final AlertDialog alertDialogObject = builderSingle.create();
        // Here you can change the layout direction via setLayoutDirection()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            alertDialogObject.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LOCALE);
        }
        alertDialogObject.show();
    }
}
