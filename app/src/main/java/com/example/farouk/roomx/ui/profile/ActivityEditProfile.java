package com.example.farouk.roomx.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.User;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

/**
 * Created by AbAbdullah on 12/02/2017.
 */

public class ActivityEditProfile extends AppCompatActivity implements VolleyCallback,DatePickerDialog.OnDateSetListener {

    @Bind(R.id.profilePic_imageview)
    CircleImageView profilePicImageview;
    @Bind(R.id.iv_camera)
    CircleImageView ivCamera;
    @Bind(R.id.icon1)
    ImageView icon1;
    @Bind(R.id.username_lable)
    TextView usernameLable;
    @Bind(R.id.username_edittext)
    EditText usernameEdittext;
    @Bind(R.id.icon2)
    ImageView icon2;
    @Bind(R.id.icon3)
    ImageView icon3;
    @Bind(R.id.mobile_lable)
    TextView mobileLable;
    @Bind(R.id.mobile_edittext)
    EditText mobileEdittext;
    @Bind(R.id.icon4)
    ImageView icon4;
    @Bind(R.id.email_lable)
    TextView emailLable;
    @Bind(R.id.email_edittext)
    EditText emailEdittext;
    @Bind(R.id.country_lable)
    TextView countryLable;
    @Bind(R.id.country_edittext)
    EditText countryEdittext;
    @Bind(R.id.icon5)
    ImageView icon5;
    @Bind(R.id.city_lable)
    TextView cityLable;
    @Bind(R.id.city_edittext)
    EditText cityEdittext;
    @Bind(R.id.icon6)
    ImageView icon6;
    @Bind(R.id.dob_lable)
    TextView dobLable;
    @Bind(R.id.dob_edittext)
    EditText dobEdittext;
    private User userResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_edit);
        ButterKnife.bind(this);
        dobEdittext.setRawInputType(InputType.TYPE_NULL);
        dobEdittext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog();
            }
        });
        User user = new User();
        user.setName(usernameEdittext.getText().toString());
        user.setPhone(mobileEdittext.getText().toString());
        user.setEmail(emailEdittext.getText().toString());
        user.setCity(cityEdittext.getText().toString());
        user.setCountry(countryEdittext.getText().toString());
        user.setDob(dobEdittext.getText().toString());
        Requests requests = new Requests();
        requests.editProfile(this, this, user);


    }

    @Override
    public void onSuccess(Response response) {
        userResponse = (User) response.getObject();
        Timber.d("userResponse %s" ,userResponse.toString());
        usernameEdittext.setText(userResponse.getName());
        mobileEdittext.setText(userResponse.getPhone());
        emailEdittext.setText(userResponse.getEmail());
        cityEdittext.setText(userResponse.getCity());
        countryEdittext.setText(userResponse.getCountry());
        dobEdittext.setText(userResponse.getDob());
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        dobEdittext.setText(date);
    }

    public void showDateDialog() {

        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.Navy));
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

}
