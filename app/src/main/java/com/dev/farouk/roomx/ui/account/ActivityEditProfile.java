package com.dev.farouk.roomx.ui.account;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.dev.farouk.roomx.R;
import com.dev.farouk.roomx.model.Response;
import com.dev.farouk.roomx.model.User;
import com.dev.farouk.roomx.service.Requests;
import com.dev.farouk.roomx.service.VolleyCallback;
import com.dev.farouk.roomx.util.Const;
import com.dev.farouk.roomx.util.Permissions;
import com.dev.farouk.roomx.util.Utils;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AbAbdullah on 12/02/2017.
 */

public class ActivityEditProfile extends AppCompatActivity implements VolleyCallback, DatePickerDialog.OnDateSetListener {

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
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.pic_layout)
    FrameLayout frameLayout;
    private User userResponse;
    private Requests requests;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Bitmap bitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile_edit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.title_activity_edit_profile));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dobEdittext.setRawInputType(InputType.TYPE_NULL);
        dobEdittext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog();
            }
        });
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!Permissions.checkWriteExternalPermission(ActivityEditProfile.this))
                    Permissions.verifyStoragePermissions(ActivityEditProfile.this);
                else loadImagefromGallery();

            }
        });

        if(Utils.isInternetAvailable(this)){
            requests = new Requests(this);
            requests.getUserProfile(this, this);
        }else
            Toast.makeText(getApplicationContext(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();

    }

    public void loadImagefromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                //Setting image to ImageView
                profilePicImageview.setImageBitmap(bitmap);
                Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imgDecodableString = cursor.getString(columnIndex);
                    requests.uploadImage(this, this, imgDecodableString);

                    // Set the Image in ImageView after decoding the String
//                    Picasso.with(getApplicationContext()).load(new File(imgDecodableString))
//                            .into(profilePicImageview);
                }


            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }


    @Override
    public void onSuccess(Response response) {
        userResponse = (User) response.getObject();
        if (userResponse != null) {
            Log.d("userResponse", userResponse.toString());
            usernameEdittext.setText(userResponse.getName());
            mobileEdittext.setText(userResponse.getPhone());
            emailEdittext.setText(userResponse.getEmail());
            cityEdittext.setText(userResponse.getCity());
            countryEdittext.setText(userResponse.getCountry());
            dobEdittext.setText(userResponse.getDob());
            if (userResponse.getPhotolink() != null) {
                Picasso.with(this).load(userResponse.getPhotolink()).placeholder(R.drawable.ic_profile)
                        .into(profilePicImageview);
            }
        }
        if (response.getResult() == 1) {
            Log.d("getResult", "1");
            Toast.makeText(this, getResources().getString(R.string.done_succefully), Toast.LENGTH_LONG);
        }
        if (response.isValid()) {
            Log.d("isValid", String.valueOf(response.isValid()));
            Toast.makeText(this, getResources().getString(R.string.done_succefully), Toast.LENGTH_LONG);
        }

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

    public void onDoneClick() {
        User user = new User();
        user.setName(usernameEdittext.getText().toString() + "");
        user.setPhone(mobileEdittext.getText().toString() + "");
        user.setEmail(emailEdittext.getText().toString() + "");
        user.setCity(cityEdittext.getText().toString() + "");
        user.setCountry(countryEdittext.getText().toString() + "");
        user.setDob(dobEdittext.getText().toString() + "");
        requests.editProfile(this, this, user);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Const.REQUEST_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length <= 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Cannot Open gallery because you deny the permission", Toast.LENGTH_SHORT).show();
                } else loadImagefromGallery();

            }
        }
    }


}
