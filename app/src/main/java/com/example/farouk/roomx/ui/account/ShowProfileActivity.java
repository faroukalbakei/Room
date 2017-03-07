package com.example.farouk.roomx.ui.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.User;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.util.Utils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowProfileActivity extends AppCompatActivity implements VolleyCallback {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.profilePic_imageview)
    ImageView profilePicImageview;
    @Bind(R.id.user_name_textview)
    TextView userNameTextview;
    @Bind(R.id.mobile_textview)
    TextView mobileTextview;
    @Bind(R.id.country_textview)
    TextView countryTextview;
    @Bind(R.id.city_textview)
    TextView cityTextview;
    @Bind(R.id.email_textview)
    TextView emailTextview;
    @Bind(R.id.dob_textview)
    TextView dobTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setTitle(getResources().getString(R.string.title_activity_edit_profile));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(Utils.isInternetAvailable(this)){
            Requests requests = new Requests(this);
            requests.getUserProfile(this, this);
        }else
            Toast.makeText(this,"لا يوجد انترنت", Toast.LENGTH_LONG);

    }

    @Override
    public void onSuccess(Response response) {
        User userResponse = (User) response.getObject();
        if (userResponse != null) {
            Log.d("userResponse", userResponse.toString());
            userNameTextview.setText(userResponse.getName());
            mobileTextview.setText(userResponse.getPhone());
            emailTextview.setText(userResponse.getEmail());
            cityTextview.setText(userResponse.getCity());
            countryTextview.setText(userResponse.getCountry());
            dobTextview.setText(userResponse.getDob());
            if (userResponse.getPhotolink() != null) {
                Picasso.with(this).load(userResponse.getPhotolink()).placeholder(R.drawable.ic_profile)
                        .into(profilePicImageview);
            }
        }

    }
}
