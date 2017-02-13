package com.example.farouk.roomx.ui.explore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.util.Const;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlaceDetailsActivity extends AppCompatActivity {

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

    int placeId;
    private List<PlaceObject> placeObject = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        ButterKnife.bind(this);
        placeId = getIntent().getExtras().getInt(Const.PLACE_ID);
        if(placeId!=0){
            placeObject = PlaceObject.find(PlaceObject.class,"id =?", String.valueOf(placeId));
        }
        for(PlaceObject placeObject1 : placeObject) {
            Log.d("placeObject", placeObject1.toString());
            //Picasso.with(this).load(placeObject1.getUser().getPhotolink()).into(hostedImage);
            Picasso.with(this).load(placeObject1.getRoomPhoto().get(0).getPhotolink()).into(imageViewPlace);
            String hostedName = String.format(getResources().getString(R.string.hosted_name), placeObject1.getUser().getName());
            titleTextView.setText(placeObject1.getName());
            textViewHostedName.setText(hostedName);
            textViewGuest.setText(String.format(getResources().getString(R.string.guest), placeObject1.getNumberOfGuests()));
            textViewRoom.setText(String.format(getResources().getString(R.string.room), placeObject1.getNumberOfRooms()));
            textViewBed.setText(String.format(getResources().getString(R.string.beds), placeObject1.getNumberOfBeds()));
            textViewBathroom.setText(String.format(getResources().getString(R.string.bthroom), placeObject1.getNumberOfBaths()));
            textViewRoomPrice.setText(placeObject1.getPrice());
        }

    }
}
