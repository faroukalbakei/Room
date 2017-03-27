package com.dev.farouk.roomx.ui.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.farouk.roomx.R;
import com.dev.farouk.roomx.model.Response;
import com.dev.farouk.roomx.model.User;
import com.dev.farouk.roomx.service.Requests;
import com.dev.farouk.roomx.service.VolleyCallback;
import com.dev.farouk.roomx.util.ApiFunctions;
import com.dev.farouk.roomx.util.Const;
import com.dev.farouk.roomx.util.Utils;
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
    private int profileTpe;


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
        initViews();

            profileTpe = getIntent().getExtras().getInt(Const.profileType);
            if (profileTpe== ApiFunctions.my_profile.getValue()) {
                // Do something
                if(Utils.isInternetAvailable(this)){
                    Requests requests = new Requests(this);
                    requests.getUserProfile(this, this);
                }else
                    Toast.makeText(this, "لا يوجد انترنت", Toast.LENGTH_LONG).show();
                Log.d("profileTpe", "my_profile");
            } else if (profileTpe== ApiFunctions.profile_by_id.getValue()){
                // Do something
                Log.d("profileTpe", "profile_by_id");
                if(Utils.isInternetAvailable(this)){
                    Requests requests = new Requests(this);
                    requests.getUserProfileById(this, this,getIntent().getExtras().getString(Const.userId));
                }else
                    Toast.makeText(this, "لا يوجد انترنت", Toast.LENGTH_LONG).show();
            }


    }

    @Override
    public void onSuccess(Response response) {
        User userResponse = (User) response.getObject();
        if (userResponse != null) {
            Log.d("ShowProfileActivity", userResponse.toString());
            userNameTextview.setText(userResponse.getName());
            mobileTextview.setText(userResponse.getPhone());
            emailTextview.setText(userResponse.getEmail());
            cityTextview.setText(userResponse.getCity());
            countryTextview.setText(userResponse.getCountry());
            dobTextview.setText(userResponse.getDob());
            if (userResponse.getPhotolink() != null) {
                Picasso.with(this).load(userResponse.getPhotolink()).placeholder(R.drawable.no_image_available)
                        .into(profilePicImageview);
            }
        }

    }

    void initViews(){
        userNameTextview.setText("");
        mobileTextview.setText("");
        emailTextview.setText("");
        cityTextview.setText("");
        countryTextview.setText("");
        dobTextview.setText("");
    }
}
